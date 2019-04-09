package com.example.vermolenit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.vermolenit.Class.ArtikelGridAdapter;

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
