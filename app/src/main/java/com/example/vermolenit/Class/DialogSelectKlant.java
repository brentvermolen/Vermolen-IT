package com.example.vermolenit.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.vermolenit.CheckActivity;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Klant;
import com.example.vermolenit.R;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DialogSelectKlant extends AlertDialog {
    public GridView grdItems;

    public DialogSelectKlant(Context context, String titel, List<Klant> items){
        super(context);

        View view = View.inflate(context, R.layout.dialog_select_item, null);

        ((TextView)view.findViewById(R.id.lblTitel)).setText(titel);
        grdItems = view.findViewById(R.id.grdItems);

        ListAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items);
        grdItems.setAdapter(adapter);

        this.setView(view);
    }
}
