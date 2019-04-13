package com.example.vermolenit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vermolenit.Class.ArtikelKasticketAdapter;
import com.example.vermolenit.Class.DAC;
import com.example.vermolenit.Class.DialogArtikelToevoegen;
import com.example.vermolenit.Class.DialogEditDate;
import com.example.vermolenit.Class.DialogSelectArtikel;
import com.example.vermolenit.Class.DialogSelectKlant;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    private TextView lblTotaal;

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
    }

    private void initViews() {
        kasticket = new Kasticket();
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

        btnArtikelToevoegen = findViewById(R.id.btnArtikelToevoegen);
        grdArtikels = findViewById(R.id.grdArtikels);
        ArtikelKasticketAdapter adapter = new ArtikelKasticketAdapter(this, kasticketArtikels);
        grdArtikels.setAdapter(adapter);

        lblTotaal = findViewById(R.id.lblTotaal);
        lblTotaal.setText(String.format("€ %.2f", 0f));
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
                        ((BaseAdapter)grdArtikels.getAdapter()).notifyDataSetChanged();
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

        switch (id){
            case R.id.action_save:
                Toast.makeText(this, "Opslaan", Toast.LENGTH_SHORT).show();
                break;
            case 16908332:
                onBackPressed();
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }
}
