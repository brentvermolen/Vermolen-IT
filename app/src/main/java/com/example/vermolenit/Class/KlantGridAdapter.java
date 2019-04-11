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
import com.example.vermolenit.Model.Klant;
import com.example.vermolenit.R;

import java.util.Comparator;
import java.util.List;

public class KlantGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<Klant> klanten;

    private KlantDAO dao;

    public KlantGridAdapter(Context context){
        this.mContext = context;

        dao = DbVermolenIt.getDatabase(mContext).klantDAO();
        klanten = DAC.Klanten;
        klanten.sort(new Comparator<Klant>() {
            @Override
            public int compare(Klant o1, Klant o2) {
                if (o1.getNaam().equals(o2.getNaam())){
                    return o1.getVoornaam().compareTo(o2.getVoornaam());
                }

                return o1.getNaam().compareTo(o2.getNaam());
            }
        });
    }

    @Override
    public int getCount() {
        return klanten.size();
    }

    @Override
    public Object getItem(int position) {
        return klanten.get(position);
    }

    @Override
    public long getItemId(int position) {
        return klanten.get(position).getId();
    }

    private class ViewHolder {
        public TextView lblTitel;
        public TextView lblWoonplaats;
        public TextView lblTelNr;
        public TextView lblEmailadres;

        public ImageView btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.klant_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.lblTitel = convertView.findViewById(R.id.lblTitel);
            viewHolder.lblWoonplaats = convertView.findViewById(R.id.lblWoonplaats);
            viewHolder.lblTelNr = convertView.findViewById(R.id.lblTelNr);
            viewHolder.lblEmailadres = convertView.findViewById(R.id.lblEmail);

            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Klant klant = (Klant) viewHolder.lblTitel.getTag();

                    final DialogYesNo dialogYesNo = new DialogYesNo(mContext, "Ben je zeker?", "Ben je zeker dat je '" + klant.toString() + "' wil verwijderen?");
                    dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            klanten.remove(klant);
                            dao.delete(klant.getId());
                            DAC.Klanten.remove(klant);
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

        Klant klant = klanten.get(position);

        viewHolder.lblTitel.setText(klant.getAanspreking().getVerkort() + " " + klant.getNaam() + " " + klant.getVoornaam());
        viewHolder.lblWoonplaats.setText(klant.getPostcode() + " " + klant.getWoonplaats());
        viewHolder.lblEmailadres.setText(klant.getEmail());
        viewHolder.lblTelNr.setText(klant.getTel_nr());

        viewHolder.lblTitel.setTag(klant);

        return convertView;
    }
}
