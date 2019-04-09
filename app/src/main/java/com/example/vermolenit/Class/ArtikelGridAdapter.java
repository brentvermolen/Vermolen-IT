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
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.R;

import java.util.Comparator;
import java.util.List;

public class ArtikelGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<Artikel> artikels;

    private ArtikelDAO dao;

    public ArtikelGridAdapter(Context context){
        this.mContext = context;

        dao = DbVermolenIt.getDatabase(mContext).artikelDAO();
        artikels = dao.getAll();
        artikels.sort(new Comparator<Artikel>() {
            @Override
            public int compare(Artikel o1, Artikel o2) {
                return o1.getOmschrijving().compareTo(o2.getOmschrijving());
            }
        });
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
        return artikels.get(position).getId();
    }

    private class ViewHolder {
        public TextView lblTitel;
        public TextView lblVoorraad;
        public TextView lblMeldingOp;

        public ImageView btnEditMeldingOp;
        public ImageView btnAddVoorraad;
        public ImageView btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.artikel_voorraad_item, null);

            viewHolder = new ViewHolder();
            viewHolder.lblTitel = convertView.findViewById(R.id.lblTitel);
            viewHolder.lblMeldingOp = convertView.findViewById(R.id.lblMeldingOp);
            viewHolder.lblVoorraad = convertView.findViewById(R.id.lblVoorraad);

            viewHolder.btnAddVoorraad = convertView.findViewById(R.id.btn_add_voorraad);
            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);
            viewHolder.btnEditMeldingOp = convertView.findViewById(R.id.btn_edit_melding_op);

            viewHolder.btnAddVoorraad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Artikel artikel = (Artikel)viewHolder.lblTitel.getTag();
                    final DialogEditInteger dialogEditInteger = new DialogEditInteger(mContext, artikel.getOmschrijving() + "Toevoegen", 1, 99);
                    dialogEditInteger.btnOke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            artikel.setVoorraad(artikel.getVoorraad() + dialogEditInteger.getValue());
                            dao.update(artikel);
                            viewHolder.lblVoorraad.setText(String.valueOf(artikel.getVoorraad()));
                            dialogEditInteger.cancel();
                        }
                    });
                    dialogEditInteger.btnAnnuleren.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogEditInteger.cancel();
                        }
                    });

                    dialogEditInteger.show();
                }
            });

            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Artikel artikel = (Artikel)viewHolder.lblTitel.getTag();
                }
            });

            viewHolder.btnEditMeldingOp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Artikel artikel = (Artikel)viewHolder.lblTitel.getTag();
                }
            });

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Artikel artikel = artikels.get(position);

        viewHolder.lblTitel.setText(artikel.getOmschrijving());
        viewHolder.lblVoorraad.setText(String.valueOf(artikel.getVoorraad()));
        viewHolder.lblMeldingOp.setText(String.valueOf(artikel.getMeldingOpVoorraad()));

        viewHolder.lblTitel.setTag(artikel);

        return convertView;
    }
}
