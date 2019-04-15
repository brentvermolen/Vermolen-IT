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
        artikels = DAC.Artikels;
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

    public void update() {
        artikels.sort(new Comparator<Artikel>() {
            @Override
            public int compare(Artikel o1, Artikel o2) {
                return o1.getOmschrijving().compareTo(o2.getOmschrijving());
            }
        });

        notifyDataSetChanged();
    }

    private class ViewHolder {
        public TextView lblTitel;
        public TextView lblPrijs;
        public TextView lblVoorraad;
        public TextView lblMeldingOp;

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
            viewHolder.lblPrijs = convertView.findViewById(R.id.lblPrijs);
            viewHolder.lblMeldingOp = convertView.findViewById(R.id.lblMeldingOp);
            viewHolder.lblVoorraad = convertView.findViewById(R.id.lblVoorraad);

            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);

            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Artikel artikel = (Artikel)viewHolder.lblTitel.getTag();

                    final DialogYesNo dialogYesNo = new DialogYesNo(mContext, "Ben je zeker?", "Ben je zeker dat je '" + artikel.getOmschrijving() + "' wil verwijderen?");
                    dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            artikels.remove(artikel);
                            dao.delete(artikel.getId());
                            DAC.Artikels.remove(artikel);
                            notifyDataSetChanged();
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

        Artikel artikel = artikels.get(position);

        viewHolder.lblTitel.setText(artikel.getOmschrijving());
        viewHolder.lblPrijs.setText(String.format("€ %.2f %s", artikel.getPrijs(), artikel.getEenheid().getVolledig()));

        if (artikel.getVoorraad() == -1){
            viewHolder.lblVoorraad.setText("Onbeperkt");
        }else{
            viewHolder.lblVoorraad.setText(String.valueOf(artikel.getVoorraad()));
        }
        if (artikel.getMeldingOpVoorraad() == -1){
            viewHolder.lblMeldingOp.setText("Geen Melding");
        }else{
            viewHolder.lblMeldingOp.setText(String.valueOf(artikel.getMeldingOpVoorraad()));
        }

        viewHolder.lblTitel.setTag(artikel);

        return convertView;
    }
}
