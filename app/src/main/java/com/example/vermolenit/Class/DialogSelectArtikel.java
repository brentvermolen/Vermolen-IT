package com.example.vermolenit.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Klant;
import com.example.vermolenit.R;

import java.util.Comparator;
import java.util.List;

public class DialogSelectArtikel extends AlertDialog {
    public GridView grdItems;

    public DialogSelectArtikel(Context context, String titel, List<Artikel> items){
        super(context);

        View view = View.inflate(context, R.layout.dialog_select_item, null);

        ((TextView)view.findViewById(R.id.lblTitel)).setText(titel);
        grdItems = view.findViewById(R.id.grdItems);

        items.sort(new Comparator<Artikel>() {
            @Override
            public int compare(Artikel o1, Artikel o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });

        ListAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items);
        grdItems.setAdapter(adapter);

        this.setView(view);
    }
}
