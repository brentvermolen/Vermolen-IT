package com.example.vermolenit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vermolenit.Class.ArtikelGridAdapter;
import com.example.vermolenit.Class.ArtikelKasticketAdapter;
import com.example.vermolenit.Class.DAC;
import com.example.vermolenit.Class.DialogEditDate;
import com.example.vermolenit.Class.DialogSelectArtikel;
import com.example.vermolenit.Class.DialogSelectKlant;
import com.example.vermolenit.Class.DialogYesNo;
import com.example.vermolenit.Class.KasticketGridAdapter;
import com.example.vermolenit.Class.Methodes;
import com.example.vermolenit.DB.RemoteArtikelDAO;
import com.example.vermolenit.DB.RemoteKasticketArtikelDAO;
import com.example.vermolenit.DB.RemoteKasticketsDAO;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.Model.Klant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class CheckActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Kasticket kasticket;
    private List<KasticketArtikel> kasticketArtikels;

    private TextView lblKlant;
    private TextView lblDatum;
    private ImageView btnZoekKlant;
    private ImageView btnZoekDatum;
    private Calendar datum;
    SimpleDateFormat simpleDateFormat;

    private Button btnArtikelToevoegen;
    private GridView grdArtikels;

    public TextView lblTotaal;
    public TextView lblSubtotaal;
    public TextView lblBtw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        setTitle("Kasticket");

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        handleEvents();

        try{
            int klant_id = getIntent().getIntExtra("klant_id", -1);
            if (klant_id != -1){
                kasticket.setKlant_id(klant_id);
                for (Klant klant : DAC.Klanten){
                    if (klant.getId() == klant_id){
                        kasticket.setKlant(klant);
                        lblKlant.setText(klant.toString());
                        break;
                    }
                }
            }
            int kasticket_id = getIntent().getIntExtra("kasticket_id", -1);
            if (kasticket_id != -1){
                kasticket = Methodes.getKasticketById(DAC.Kastickets, kasticket_id);
                kasticket.setId(kasticket_id);
                lblKlant.setText(kasticket.getKlant().toString());
                lblDatum.setText(new SimpleDateFormat("dd/MM/yyyy").format(kasticket.getDatum()));
                kasticketArtikels = kasticket.getKasticketArtikels();
                grdArtikels.setAdapter(new ArtikelKasticketAdapter(this, kasticketArtikels));
                ((ArtikelKasticketAdapter)grdArtikels.getAdapter()).update();
            }
        }catch (NullPointerException ex){ }
    }

    private void initViews() {
        kasticket = new Kasticket();
        kasticket.setKlant_id(-1);
        kasticketArtikels = new ArrayList<>();
        datum = Calendar.getInstance();
        kasticket.setDatum(new Date(datum.getTimeInMillis()));

        lblKlant = findViewById(R.id.lblKlant);
        lblKlant.setText("Geen Klant");
        lblDatum = findViewById(R.id.lblDatum);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        lblDatum.setText(simpleDateFormat.format(new Date(datum.getTimeInMillis())));

        btnZoekKlant = findViewById(R.id.btnZoekKlant);
        btnZoekDatum = findViewById(R.id.btnZoekDatum);

        lblTotaal = findViewById(R.id.lblTotaal);
        lblSubtotaal = findViewById(R.id.lblSubtotaal);
        lblBtw = findViewById(R.id.lblBtw);
        lblTotaal.setText(String.format("€ %.2f", 0f));
        lblSubtotaal.setText(String.format("€ %.2f", 0f));
        lblBtw.setText(String.format("€ %.2f", 0f));

        btnArtikelToevoegen = findViewById(R.id.btnArtikelToevoegen);
        grdArtikels = findViewById(R.id.grdArtikels);
        ArtikelKasticketAdapter adapter = new ArtikelKasticketAdapter(this, kasticketArtikels);
        kasticket.setKasticketArtikels(kasticketArtikels);
        grdArtikels.setAdapter(adapter);
    }

    private void handleEvents() {
        btnZoekKlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogSelectKlant dialogSelectKlant = new DialogSelectKlant(CheckActivity.this, "Kies Klant", DAC.Klanten);
                dialogSelectKlant.grdItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        kasticket.setKlant(DAC.Klanten.get(position));
                        kasticket.setKlant_id(kasticket.getKlant().getId());
                        lblKlant.setText(kasticket.getKlant().toString());
                        dialogSelectKlant.cancel();
                    }
                });
                dialogSelectKlant.show();
            }
        });

        btnZoekDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogEditDate dialogEditDate = new DialogEditDate(CheckActivity.this, "Kies Datum", datum);
                dialogEditDate.btnOke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datum = dialogEditDate.getValue();
                        Date date = new Date(datum.getTimeInMillis());
                        lblDatum.setText(simpleDateFormat.format(date));
                        kasticket.setDatum(date);
                        dialogEditDate.cancel();
                    }
                });
                dialogEditDate.btnAnnuleren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogEditDate.cancel();
                    }
                });
                dialogEditDate.show();
            }
        });

        btnArtikelToevoegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogSelectArtikel dialogSelectArtikel = new DialogSelectArtikel(CheckActivity.this, "Kies Artikel", DAC.Artikels);
                dialogSelectArtikel.grdItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        KasticketArtikel kasticketArtikel = new KasticketArtikel();
                        Artikel artikel = DAC.Artikels.get(position);
                        kasticketArtikel.setArtikel(artikel);
                        kasticketArtikel.setArtikel_id(artikel.getId());
                        kasticketArtikel.setKasticket_id(kasticket.getId());
                        kasticketArtikel.setKasticket(kasticket);

                        if (kasticketArtikels.contains(kasticketArtikel)){
                            for (KasticketArtikel ka : kasticketArtikels){
                                if (ka.getArtikel_id() == artikel.getId()){
                                    ka.setAantal(ka.getAantal() + 1);
                                }
                            }
                        }else{
                            kasticketArtikel.setAantal(1);
                            kasticketArtikels.add(kasticketArtikel);
                        }
                        ((ArtikelKasticketAdapter)grdArtikels.getAdapter()).update();
                        dialogSelectArtikel.cancel();
                    }
                });
                dialogSelectArtikel.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == 16908332){
            onBackPressed();
            return true;
        }

        if (kasticket.getKlant_id() == -1){
            Toast.makeText(this, "Je moet een klant selecteren", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (kasticket.getKasticketArtikels().size() == 0) {
            Toast.makeText(this, "Kasticket is leeg", Toast.LENGTH_SHORT).show();
            return false;
        }

        kasticket.setBetaald(false);
        kasticket.setId((int) RemoteKasticketsDAO.insert(kasticket));
        for (final KasticketArtikel ka : kasticket.getKasticketArtikels()){
            ka.setHuidige_prijs(ka.getArtikel().getPrijs());
            ka.setKasticket_id(kasticket.getId());
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    RemoteKasticketArtikelDAO.insert(ka);
                    return null;
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            if (!DAC.KasticketArtikels.contains(ka)){
                DAC.KasticketArtikels.add(ka);

                if (ka.getArtikel().getVoorraad() > 0){
                    if (ka.getArtikel().getVoorraad() - ka.getAantal() >= 0){
                        ka.getArtikel().setVoorraad(ka.getArtikel().getVoorraad() - ka.getAantal());
                        new AsyncTask<Void, Void, Void>(){
                            @Override
                            protected Void doInBackground(Void... voids) {
                                RemoteArtikelDAO.update(ka.getArtikel());
                                return null;
                            }
                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }else{
                        ka.getArtikel().setVoorraad(0);
                        new AsyncTask<Void, Void, Void>(){
                            @Override
                            protected Void doInBackground(Void... voids) {
                                RemoteArtikelDAO.update(ka.getArtikel());
                                return null;
                            }
                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }
            }
        }
        if (!DAC.Kastickets.contains(kasticket)){
            DAC.Kastickets.add(kasticket);
        }

        switch (id){
            case R.id.action_continue:
                WebView webView = new WebView(this);
                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return false;
                    }
                });

                String htmlChanges = Methodes.getIntroHtml() + Methodes.getMiddleHtml(kasticket) + Methodes.getEndHtml();

                webView.loadDataWithBaseURL(null, htmlChanges, "text/HTML", "UTF-8", null);
                kasticket.setBetaald(true);
                new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        RemoteKasticketsDAO.update(kasticket);
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                Methodes.printText(CheckActivity.this, webView, "Kasticket " + String.format("%06d", kasticket.getId()));
                break;
            case R.id.action_save:
                Toast.makeText(this, "Concept opgeslagen", Toast.LENGTH_SHORT).show();

                finish();
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (kasticket.getKlant_id() == -1 && kasticket.getKasticketArtikels().size() == 0){
            super.onBackPressed();
            return;
        }

        final DialogYesNo dialogYesNo = new DialogYesNo(this, "Verlaten", "Bent u zeker dat u dit kasticket wil verlaten?");
        dialogYesNo.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYesNo.cancel();
            }
        });
        dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYesNo.cancel();
                CheckActivity.super.onBackPressed();
            }
        });
        dialogYesNo.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (kasticket.isBetaald()){
            finish();
        }
    }
}
