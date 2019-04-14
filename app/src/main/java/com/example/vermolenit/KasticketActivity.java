package com.example.vermolenit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.vermolenit.Class.DialogYesNo;
import com.example.vermolenit.Class.KasticketGridAdapter;
import com.example.vermolenit.Class.KlantGridAdapter;
import com.example.vermolenit.DB.DbVermolenIt;
import com.example.vermolenit.DB.KasticketDAO;
import com.example.vermolenit.Model.Kasticket;

public class KasticketActivity extends AppCompatActivity {

    Toolbar mToolbar;
    GridView grdKasticket;
    private KasticketDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kastickets);

        setTitle("Kastickets");

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        handleEvents();
    }

    private void initViews() {
        dao = DbVermolenIt.getDatabase(this).kasticketDAO();

        grdKasticket = findViewById(R.id.grdKastickets);
        grdKasticket.setAdapter(new KasticketGridAdapter(this));
    }

    private void handleEvents() {
        grdKasticket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Kasticket kasticket = (Kasticket) grdKasticket.getAdapter().getItem(position);
                if (!kasticket.isBetaald()){
                    final DialogYesNo dialogYesNo = new DialogYesNo(KasticketActivity.this, "Kasticket Wijzigen", "Wilt u het concept openen?");
                    dialogYesNo.btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogYesNo.cancel();
                        }
                    });
                    dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(KasticketActivity.this, CheckActivity.class);
                            intent.putExtra("kasticket_id", kasticket.getId());
                            startActivity(intent);
                            dialogYesNo.cancel();
                            finish();
                        }
                    });
                    dialogYesNo.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kastickets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case 16908332:
                onBackPressed();
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }
}
