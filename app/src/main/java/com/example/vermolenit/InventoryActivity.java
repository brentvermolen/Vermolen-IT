package com.example.vermolenit;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.vermolenit.Class.ArtikelGridAdapter;
import com.example.vermolenit.Class.DAC;
import com.example.vermolenit.Class.DialogArtikelToevoegen;
import com.example.vermolenit.Class.DialogEditInteger;
import com.example.vermolenit.DB.RemoteArtikelDAO;
import com.example.vermolenit.Model.Artikel;

public class InventoryActivity extends AppCompatActivity {

    Toolbar mToolbar;
    GridView grdArtikels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        setTitle("Inventory");

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        handleEvents();
    }

    private void initViews() {
        grdArtikels = findViewById(R.id.grdArtikels);
        grdArtikels.setAdapter(new ArtikelGridAdapter(this));
    }

    private void handleEvents() {
        grdArtikels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Artikel artikel = (Artikel)grdArtikels.getAdapter().getItem(position);

                final DialogArtikelToevoegen dialogArtikelToevoegen = new DialogArtikelToevoegen(InventoryActivity.this, artikel);
                dialogArtikelToevoegen.btnAnnuleren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogArtikelToevoegen.cancel();
                    }
                });
                dialogArtikelToevoegen.btnToevoegen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        artikel.setOmschrijving(dialogArtikelToevoegen.getOmschrijing());
                        artikel.setPrijs(dialogArtikelToevoegen.getPrijs());
                        artikel.setVoorraad(dialogArtikelToevoegen.getVoorraad());
                        artikel.setMeldingOpVoorraad(dialogArtikelToevoegen.getMeldingOp());
                        artikel.setEenheid(dialogArtikelToevoegen.getEenheid());
                        new AsyncTask<Void, Void, Void>(){
                            @Override
                            protected Void doInBackground(Void... voids) {
                                RemoteArtikelDAO.update(artikel);
                                return null;
                            }
                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        ((ArtikelGridAdapter)grdArtikels.getAdapter()).update();
                        dialogArtikelToevoegen.cancel();
                    }
                });
                dialogArtikelToevoegen.show();

                return true;
            }
        });

        grdArtikels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Artikel artikel = (Artikel) grdArtikels.getAdapter().getItem(position);

                if (artikel.getVoorraad() != -1){
                    final DialogEditInteger dialogEditInteger = new DialogEditInteger(InventoryActivity.this, "Voorraad Toevoegen", 1, 15, 1);
                    dialogEditInteger.btnAnnuleren.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogEditInteger.cancel();
                        }
                    });
                    dialogEditInteger.btnOke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            artikel.setVoorraad(artikel.getVoorraad() + dialogEditInteger.getValue());
                            new AsyncTask<Void, Void, Void>(){
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    RemoteArtikelDAO.update(artikel);
                                    return null;
                                }
                            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            dialogEditInteger.cancel();
                            ((ArtikelGridAdapter)grdArtikels.getAdapter()).update();
                        }
                    });
                    dialogEditInteger.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_artikels, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_add:
                final DialogArtikelToevoegen dialogArtikelToevoegen = new DialogArtikelToevoegen(this);
                dialogArtikelToevoegen.btnAnnuleren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogArtikelToevoegen.cancel();
                    }
                });
                dialogArtikelToevoegen.btnToevoegen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Artikel artikel = new Artikel();
                        artikel.setOmschrijving(dialogArtikelToevoegen.getOmschrijing());
                        artikel.setPrijs(dialogArtikelToevoegen.getPrijs());
                        artikel.setVoorraad(dialogArtikelToevoegen.getVoorraad());
                        artikel.setMeldingOpVoorraad(dialogArtikelToevoegen.getMeldingOp());
                        artikel.setEenheid(dialogArtikelToevoegen.getEenheid());
                        new AsyncTask<Void, Void, Void>(){
                            @Override
                            protected Void doInBackground(Void... voids) {
                                artikel.setId((int)RemoteArtikelDAO.insert(artikel));
                                return null;
                            }
                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        DAC.Artikels.add(artikel);
                        ((ArtikelGridAdapter) grdArtikels.getAdapter()).update();
                        dialogArtikelToevoegen.cancel();
                    }
                });
                dialogArtikelToevoegen.show();
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
