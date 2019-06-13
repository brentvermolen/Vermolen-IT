package com.example.vermolenit.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vermolenit.CheckActivity;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.R;

import java.util.ArrayList;
import java.util.List;

public class ArtikelKasticketAdapter extends BaseAdapter {
    private Context mContext;
    private List<KasticketArtikel> artikels;

    private TextView lblTotaal;

    String m_Text = null;

    public ArtikelKasticketAdapter(Context context, List<KasticketArtikel> artikels){
        this.mContext = context;
        this.lblTotaal = ((CheckActivity)context).lblTotaal;

        this.artikels = artikels;
    }

    public List<KasticketArtikel> getArtikels(){
        return artikels;
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

    public void update() {
        berekenPrijs();
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public TextView lblTitel;
        public TextView lblAantal;

        public ImageView btnDelete;
        public ImageView btnMin;
        public ImageView btnPlus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.artikel_kasticket, null);

            viewHolder = new ViewHolder();
            viewHolder.lblTitel = convertView.findViewById(R.id.lblTitel);
            viewHolder.lblAantal = convertView.findViewById(R.id.lblAantal);

            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);
            viewHolder.btnMin = convertView.findViewById(R.id.btnMin);
            viewHolder.btnPlus = convertView.findViewById(R.id.btnPlus);


            viewHolder.lblTitel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final KasticketArtikel kasticketArtikel = (KasticketArtikel) viewHolder.lblTitel.getTag();

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Omschrijving");

                    // Set up the input
                    final EditText input = new EditText(mContext);
                    input.setText(kasticketArtikel.getOmschrijving() == null ? kasticketArtikel.getArtikel().getOmschrijving() : kasticketArtikel.getOmschrijving());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            kasticketArtikel.setOmschrijving(input.getText().toString());
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final KasticketArtikel kasticketArtikel = (KasticketArtikel) viewHolder.lblTitel.getTag();

                    final DialogYesNo dialogYesNo = new DialogYesNo(mContext, "Ben je zeker?", "Ben je zeker dat je '" + kasticketArtikel.getArtikel().getOmschrijving() + "' wil verwijderen?");
                    dialogYesNo.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final KasticketArtikel kasticketArtikel = (KasticketArtikel) viewHolder.lblTitel.getTag();
                            artikels.remove(kasticketArtikel);
                            notifyDataSetChanged();
                            dialogYesNo.cancel();
                            berekenPrijs();
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

            viewHolder.btnMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final KasticketArtikel kasticketArtikel = (KasticketArtikel) viewHolder.lblTitel.getTag();

                    kasticketArtikel.setAantal(kasticketArtikel.getAantal() - 1);
                    viewHolder.lblAantal.setText(String.valueOf(kasticketArtikel.getAantal()));

                    if (kasticketArtikel.getAantal() == 1){
                        viewHolder.btnMin.setEnabled(false);
                    }

                    berekenPrijs();
                }
            });

            viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final KasticketArtikel kasticketArtikel = (KasticketArtikel) viewHolder.lblTitel.getTag();
                    viewHolder.btnMin.setEnabled(true);

                    kasticketArtikel.setAantal(kasticketArtikel.getAantal() + 1);
                    viewHolder.lblAantal.setText(String.valueOf(kasticketArtikel.getAantal()));

                    berekenPrijs();
                }
            });

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        KasticketArtikel artikel = (KasticketArtikel) getItem(position);
        m_Text = artikel.getOmschrijving();

        viewHolder.lblTitel.setText(artikel.getArtikel().getOmschrijving());
        if (m_Text != null){
            artikel.setOmschrijving(m_Text);
            viewHolder.lblTitel.setText(artikel.getOmschrijving());
        }
        viewHolder.lblAantal.setText(String.valueOf(artikel.getAantal()));
        if (artikel.getAantal() == 1){
            viewHolder.btnMin.setEnabled(false);
        }

        viewHolder.lblTitel.setTag(artikel);

        return convertView;
    }

    public void berekenPrijs(){
        double prijs = 0f;
        boolean metKaart = false;

        for (KasticketArtikel kasticketArtikel : artikels){
            if (kasticketArtikel.getArtikel_id() != 0){
                prijs += kasticketArtikel.getArtikel().getPrijs() * kasticketArtikel.getAantal();
            }else {
                metKaart = true;
            }
        }

        if (metKaart){
            double toelage = (prijs / 0.9725f) - prijs;
            if (toelage < 0.85f){
                toelage = 0.85f;
            }
            prijs += toelage;

            for (KasticketArtikel kasticketArtikel : artikels){
                if (kasticketArtikel.getArtikel_id() == 0){
                    kasticketArtikel.setHuidige_prijs(toelage);
                }
            }
        }

        lblTotaal.setText(String.format("€ %.2f", prijs));
    }
}
