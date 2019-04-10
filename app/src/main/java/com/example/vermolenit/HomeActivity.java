package com.example.vermolenit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vermolenit.Class.DAC;
import com.example.vermolenit.Class.DialogKlantToevoegen;
import com.example.vermolenit.Class.Singletons;
import com.example.vermolenit.DB.DbVermolenIt;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LinearLayout llVoorraad;
    private HorizontalScrollView horizontalScrollView;

    private TextView lblOmzet;
    private TextView lblBetaald;
    private TextView lblOpenstaand;
    private ProgressBar prgOmzet;

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
        llVoorraad = findViewById(R.id.llVoorraad);
        horizontalScrollView = findViewById(R.id.horizontal_scrollview);

        lblOmzet = findViewById(R.id.lblOmzet);
        lblBetaald = findViewById(R.id.lblBetaald);
        lblOpenstaand = findViewById(R.id.lblOpenstaand);
        prgOmzet = findViewById(R.id.prgOmzet);
    }

    private void handleEvents() {

    }

    private void checkVoorraad() {
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

    private void checkOmzet(){
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
            case R.id.action_add_person:
                DialogKlantToevoegen dialogKlantToevoegen = new DialogKlantToevoegen(this);
                //TODO
                dialogKlantToevoegen.show();
                break;
            case R.id.action_add:
                Toast.makeText(this, "Check toevoegen", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_check_supply:
                Intent intent = new Intent(HomeActivity.this, InventoryActivity.class);
                startActivity(intent);
                break;
            case R.id.action_customers:
                Intent intent1 = new Intent(HomeActivity.this, CustomerActivity.class);
                startActivity(intent1);
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
    }
}
