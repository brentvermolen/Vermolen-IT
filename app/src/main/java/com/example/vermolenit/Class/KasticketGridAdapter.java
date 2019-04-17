package com.example.vermolenit.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vermolenit.DB.DbVermolenIt;
import com.example.vermolenit.DB.KasticketArtikelDAO;
import com.example.vermolenit.DB.KasticketDAO;
import com.example.vermolenit.HomeActivity;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.R;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class KasticketGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<Kasticket> kastickets;

    private KasticketDAO dao;
    private KasticketArtikelDAO kasticketArtikelDAO;

    public KasticketGridAdapter(Context context){
        this.mContext = context;

        dao = DbVermolenIt.getDatabase(mContext).kasticketDAO();
        kasticketArtikelDAO = DbVermolenIt.getDatabase(mContext).kasticketArtikelDAO();

        kastickets = DAC.Kastickets;
        kastickets.sort(new Comparator<Kasticket>() {
            @Override
            public int compare(Kasticket o1, Kasticket o2) {
                return o2.getDatum().compareTo(o1.getDatum());
            }
        });
    }

    public void update(){
        notifyDataSetChanged();

        kastickets.sort(new Comparator<Kasticket>() {
            @Override
            public int compare(Kasticket o1, Kasticket o2) {
                return o2.getDatum().compareTo(o1.getDatum());
            }
        });
    }

    @Override
    public int getCount() {
        return kastickets.size();
    }

    @Override
    public Object getItem(int position) {
        return kastickets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return kastickets.get(position).getId();
    }

    private class ViewHolder {
        public TextView lblTitel;
        public TextView lblPrijs;
        public TextView lblDatum;
        public TextView lblConcept;

        public ImageView btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.kasticket_item, null);

            viewHolder = new ViewHolder();
            viewHolder.lblTitel = convertView.findViewById(R.id.lblTitel);
            viewHolder.lblPrijs = convertView.findViewById(R.id.lblPrijs);
            viewHolder.lblDatum = convertView.findViewById(R.id.lblDatum);
            viewHolder.lblConcept = convertView.findViewById(R.id.lblBetaald);

            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);

            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Kasticket kasticket = (Kasticket) viewHolder.lblTitel.getTag();

                    final DialogYesNo dialogYesNo = new DialogYesNo(mContext, "Ben je zeker?", "Ben je zeker dat je het kasticket van " + kasticket.getKlant().toString() + " wil verwijderen?");
                    dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            kastickets.remove(kasticket);
                            kasticketArtikelDAO.deleteByKasticket(kasticket.getId());
                            dao.delete(kasticket.getId());
                            DAC.KasticketArtikels.removeIf(new Predicate<KasticketArtikel>() {
                                @Override
                                public boolean test(KasticketArtikel kasticketArtikel) {
                                    if (kasticketArtikel.getKasticket_id() == kasticket.getId()){
                                        return true;
                                    }

                                    return false;
                                }
                            });
                            DAC.Kastickets.remove(kasticket);
                            notifyDataSetChanged();
                            ((HomeActivity)mContext).checkOmzet();
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

        Kasticket kasticket = kastickets.get(position);

        viewHolder.lblTitel.setText(kasticket.getKlant().toString());
        double prijs = 0f;

        for (KasticketArtikel ka : kasticket.getKasticketArtikels()){
            prijs += ka.getAantal() * ka.getHuidige_prijs();
        }
        viewHolder.lblPrijs.setText(String.format("€ %.2f", prijs));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        viewHolder.lblDatum.setText(simpleDateFormat.format(kasticket.getDatum()));

        if (!kasticket.isBetaald())
        {
            viewHolder.lblConcept.setText("OPEN");
            viewHolder.lblConcept.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark, null));
        }else{
            viewHolder.lblConcept.setText("BETAALD");
            viewHolder.lblConcept.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_dark, null));
        }

        viewHolder.lblTitel.setTag(kasticket);

        return convertView;
    }
}
