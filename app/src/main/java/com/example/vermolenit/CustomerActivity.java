package com.example.vermolenit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.GridView;
import android.widget.Toast;

import com.example.vermolenit.Class.ArtikelGridAdapter;
import com.example.vermolenit.Class.DAC;
import com.example.vermolenit.Class.DialogArtikelToevoegen;
import com.example.vermolenit.Class.DialogKlantToevoegen;
import com.example.vermolenit.Class.DialogYesNo;
import com.example.vermolenit.Class.KlantGridAdapter;
import com.example.vermolenit.DB.DbVermolenIt;
import com.example.vermolenit.DB.KlantDAO;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Klant;

public class CustomerActivity extends AppCompatActivity {

    Toolbar mToolbar;
    GridView grdKlanten;
    private KlantDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        setTitle("Klanten");

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        handleEvents();
    }

    private void initViews() {
        dao = DbVermolenIt.getDatabase(this).klantDAO();

        grdKlanten = findViewById(R.id.grdKlanten);
        grdKlanten.setAdapter(new KlantGridAdapter(this));
    }

    private void handleEvents() {
        grdKlanten.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Klant klant = (Klant) grdKlanten.getAdapter().getItem(position);
                final DialogKlantToevoegen dialogKlantToevoegen = new DialogKlantToevoegen(CustomerActivity.this, klant);
                dialogKlantToevoegen.btnAnnuleren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogKlantToevoegen.cancel();
                    }
                });
                dialogKlantToevoegen.btnToevoegen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Klant klant = dialogKlantToevoegen.getKlant();
                        dao.update(klant);
                        ((KlantGridAdapter) grdKlanten.getAdapter()).update();
                        dialogKlantToevoegen.cancel();
                    }
                });
                dialogKlantToevoegen.show();
                return false;
            }
        });
        grdKlanten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final DialogYesNo dialogYesNo = new DialogYesNo(CustomerActivity.this, "Nieuw kasticket", "Wilt u een nieuw kasticket maken met deze klant?");
                dialogYesNo.btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogYesNo.cancel();
                    }
                });
                dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CustomerActivity.this, CheckActivity.class);
                        intent.putExtra("klant_id", ((Klant) grdKlanten.getAdapter().getItem(position)).getId());
                        startActivity(intent);
                        dialogYesNo.cancel();
                        finish();
                    }
                });
                dialogYesNo.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_klanten, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_add:
                final DialogKlantToevoegen dialogKlantToevoegen = new DialogKlantToevoegen(this);
                dialogKlantToevoegen.btnAnnuleren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogKlantToevoegen.cancel();
                    }
                });
                dialogKlantToevoegen.btnToevoegen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Klant klant = dialogKlantToevoegen.getKlant();
                        klant.setId((int)dao.insert(klant));
                        DAC.Klanten.add(klant);
                        ((KlantGridAdapter) grdKlanten.getAdapter()).update();
                        dialogKlantToevoegen.cancel();
                    }
                });
                dialogKlantToevoegen.show();
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
