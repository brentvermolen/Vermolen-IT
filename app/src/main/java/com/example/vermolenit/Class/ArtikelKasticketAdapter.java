package com.example.vermolenit.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vermolenit.DB.ArtikelDAO;
import com.example.vermolenit.DB.DbVermolenIt;
import com.example.vermolenit.DB.KlantDAO;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.Model.Klant;
import com.example.vermolenit.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArtikelKasticketAdapter extends BaseAdapter {
    private Context mContext;
    private List<KasticketArtikel> artikels;

    public ArtikelKasticketAdapter(Context context){
        this.mContext = context;

        artikels = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return artikels.size();
    }

    @Override
    public Object getItem(int position) {
        return artikels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return artikels.get(position).getArtikel().getId();
    }

    private class ViewHolder {
        public TextView lblTitel;
        public TextView lblAantal;

        public ImageView btnDelete;
        public ImageView btnMin;
        public ImageView btnMax;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.klant_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.lblTitel = convertView.findViewById(R.id.lblTitel);

            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Klant klant = (Klant) viewHolder.lblTitel.getTag();

                    final DialogYesNo dialogYesNo = new DialogYesNo(mContext, "Ben je zeker?", "Ben je zeker dat je '" + klant.toString() + "' wil verwijderen?");
                    dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogYesNo.cancel();
                        }
                    });
                    dialogYesNo.btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogYesNo.cancel();
                        }
                    });

                    dialogYesNo.show();
                }
            });

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        KasticketArtikel artikel = (KasticketArtikel) getItem(position);

        viewHolder.lblTitel.setText(artikel.getArtikel().getOmschrijving());

        viewHolder.lblTitel.setTag(artikel);

        return convertView;
    }
}
