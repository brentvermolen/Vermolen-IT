package com.example.vermolenit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vermolenit.Class.DAC;
import com.example.vermolenit.Class.DialogKlantToevoegen;
import com.example.vermolenit.Class.DialogSelectKasticket;
import com.example.vermolenit.Class.DialogYesNo;
import com.example.vermolenit.Class.KasticketGridAdapter;
import com.example.vermolenit.Class.Methodes;
import com.example.vermolenit.Class.Singletons;
import com.example.vermolenit.DB.DbVermolenIt;
import com.example.vermolenit.DB.KlantDAO;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.Model.Klant;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LinearLayout llVoorraad;
    private HorizontalScrollView horizontalScrollView;

    private TextView lblOmzet;
    private TextView lblBetaald;
    private TextView lblOpenstaand;
    private ProgressBar prgOmzet;

    GridView grdKasticket;

    KlantDAO klantDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(getResources().getString(R.string.app_name));

        Singletons.context = this;

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_shut_down);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        handleEvents();

        checkVoorraad();
        checkOmzet();
    }

    private void initViews() {
        klantDAO = DbVermolenIt.getDatabase(this).klantDAO();

        llVoorraad = findViewById(R.id.llVoorraad);
        horizontalScrollView = findViewById(R.id.horizontal_scrollview);

        lblOmzet = findViewById(R.id.lblOmzet);
        lblBetaald = findViewById(R.id.lblBetaald);
        lblOpenstaand = findViewById(R.id.lblOpenstaand);
        prgOmzet = findViewById(R.id.prgOmzet);

        grdKasticket = findViewById(R.id.grdKastickets);
        grdKasticket.setAdapter(new KasticketGridAdapter(this));
    }

    private void handleEvents() {
        grdKasticket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Kasticket kasticket = (Kasticket) grdKasticket.getAdapter().getItem(position);
                if (!kasticket.isBetaald()){
                    final DialogYesNo dialogYesNo = new DialogYesNo(HomeActivity.this, "Kasticket Wijzigen", "Wilt u het concept openen?");
                    dialogYesNo.btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogYesNo.cancel();
                        }
                    });
                    dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(HomeActivity.this, CheckActivity.class);
                            intent.putExtra("kasticket_id", kasticket.getId());
                            startActivity(intent);
                            dialogYesNo.cancel();
                        }
                    });
                    dialogYesNo.show();
                }else{
                    final DialogYesNo dialogYesNo = new DialogYesNo(HomeActivity.this, "Kasticket Afdrukken", "Wilt u het kasticket afdrukken?");
                    dialogYesNo.btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogYesNo.cancel();
                        }
                    });
                    dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            printKasticket(kasticket);

                            dialogYesNo.cancel();
                        }
                    });
                    dialogYesNo.show();
                }
            }
        });
        grdKasticket.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Kasticket kasticket = (Kasticket) grdKasticket.getAdapter().getItem(position);

                String status = "";

                if (kasticket.isBetaald() == true) {
                    status = "onbetaald";
                } else if (kasticket.isBetaald() == false) {
                    status = "betaald";
                }

                final DialogYesNo dialogYesNo = new DialogYesNo(HomeActivity.this, "Betaald Wijzigen", "Kasticket is " + status);
                dialogYesNo.btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogYesNo.cancel();
                    }
                });
                dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kasticket.setBetaald(!kasticket.isBetaald());
                        DbVermolenIt.getDatabase(HomeActivity.this).kasticketDAO().update(kasticket);
                        ((KasticketGridAdapter)grdKasticket.getAdapter()).update();
                        checkOmzet();
                        dialogYesNo.cancel();
                    }
                });
                dialogYesNo.show();
                return true;
            }
        });
    }

    private void printKasticket(Kasticket kasticket) {
        WebView webView = new WebView(HomeActivity.this);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        String htmlChanges = Methodes.getIntroHtml() + Methodes.getMiddleHtml(kasticket) + Methodes.getEndHtml();

        webView.loadDataWithBaseURL(null, htmlChanges, "text/HTML", "UTF-8", null);
        Methodes.printText(HomeActivity.this, webView, "Kasticket " + String.format("%06d", kasticket.getId()));
    }

    public void checkVoorraad() {
        List<Artikel> voorraadTekort = DbVermolenIt.getDatabase(this).artikelDAO().getWhereVoorraadIsLow();
        llVoorraad.removeAllViews();

        if (voorraadTekort.size() > 0){
            llVoorraad.setVisibility(View.VISIBLE);
            horizontalScrollView.setVisibility(View.VISIBLE);

            for (Artikel artikel : voorraadTekort){
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

                LinearLayout view = (LinearLayout) inflater.inflate(R.layout.artikel_tekort_item, null);
                ((TextView) view.findViewById(R.id.lblTitel)).setText(artikel.getOmschrijving());
                TextView lblVoorraad = view.findViewById(R.id.lblVoorraad);
                lblVoorraad.setText(artikel.getVoorraad() + " stuks");
                if (artikel.getVoorraad() <= artikel.getMeldingOpVoorraad()){
                    lblVoorraad.setTextColor(getResources().getColor(android.R.color.holo_red_dark, null));
                }else {
                    lblVoorraad.setTextColor(getResources().getColor(android.R.color.black, null));
                }

                llVoorraad.addView(view);
            }
        }else {
            llVoorraad.setVisibility(View.GONE);
            horizontalScrollView.setVisibility(View.GONE);
        }
    }

    public void checkOmzet(){
        double openstaand = 0;
        double betaald = 0;

        for (Kasticket kasticket : DAC.Kastickets) {
            for (KasticketArtikel ka : kasticket.getKasticketArtikels()) {
                if (kasticket.isBetaald()){
                    betaald += ka.getHuidige_prijs() * ka.getAantal();
                }else{
                    openstaand += ka.getHuidige_prijs() * ka.getAantal();
                }
            }
        }

        prgOmzet.setMax((int)(betaald + openstaand));
        prgOmzet.setProgress((int)betaald);

        lblOmzet.setText(String.format("€ %.2f", betaald + openstaand));
        lblBetaald.setText(String.format("€ %.2f ontvangen", betaald));
        lblOpenstaand.setText(String.format("€ %.2f openstaand", openstaand));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_check_supply:
                Intent intent = new Intent(HomeActivity.this, InventoryActivity.class);
                startActivity(intent);
                break;
            case R.id.action_customers:
                Intent intent1 = new Intent(HomeActivity.this, CustomerActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_add:
                Intent intent2 = new Intent(HomeActivity.this, CheckActivity.class);
                startActivity(intent2);
                break;
            case R.id.action_search:
                final DialogSelectKasticket dialogSelectKasticket = new DialogSelectKasticket(this, "Selecteer Kasticket", DAC.Kastickets);
                dialogSelectKasticket.grdItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Kasticket kasticket = (Kasticket) dialogSelectKasticket.grdItems.getAdapter().getItem(position);

                        printKasticket(kasticket);
                        dialogSelectKasticket.cancel();
                    }
                });
                dialogSelectKasticket.show();
                break;
            case 16908332:
                finish();
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkVoorraad();
        checkOmzet();
        ((KasticketGridAdapter)grdKasticket.getAdapter()).update();
    }
}
