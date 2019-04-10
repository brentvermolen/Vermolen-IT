package com.example.vermolenit.Class;

import android.app.AlertDialog;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vermolenit.Model.Aanspreking;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Klant;
import com.example.vermolenit.R;

public class DialogKlantToevoegen extends AlertDialog {

    private Context mContext;
    private Klant klant;

    private TextView lblTitel;

    private RadioGroup rdgAanspreking;
    private EditText txtVoornaam;
    private EditText txtAchternaam;
    private EditText txtAdres;
    private EditText txtPostcode;
    private EditText txtWoonplaats;
    private EditText txtTelNr;
    private EditText txtEmailAdres;

    public Button btnToevoegen;
    public Button btnAnnuleren;

    public DialogKlantToevoegen(Context context){
        super(context);

        init(context);
        klant = new Klant();
    }

    public DialogKlantToevoegen(Context context, Klant klant) {
        super(context);

        init(context);

        this.klant = klant;
        setEdit();
    }

    private void init(Context context) {
        mContext = context;
        View view = View.inflate(context, R.layout.dialog_add_customer, null);

        lblTitel = view.findViewById(R.id.lblTitel);
        rdgAanspreking = view.findViewById(R.id.rdgAanspreking);

        txtVoornaam = view.findViewById(R.id.txtVoornaam);
        txtAchternaam = view.findViewById(R.id.txtNaam);
        txtAdres = view.findViewById(R.id.txtAdres);
        txtPostcode = view.findViewById(R.id.txtPostCode);
        txtWoonplaats = view.findViewById(R.id.txtWoonplaats);
        txtTelNr = view.findViewById(R.id.txtTelNr);
        txtEmailAdres = view.findViewById(R.id.txtEmail);

        btnToevoegen = view.findViewById(R.id.btnToevoegen);
        btnAnnuleren = view.findViewById(R.id.btnAnnuleren);

        this.setView(view);
    }


    private void setEdit() {
        int rdgId = klant.getAanspreking().getId();
        RadioButton rdb = rdgAanspreking.findViewWithTag(String.valueOf(rdgId));

        rdb.setChecked(true);
        txtVoornaam.setText(klant.getVoornaam());
        txtAchternaam.setText(klant.getNaam());
        txtAdres.setText(klant.getAdres());
        txtPostcode.setText(String.valueOf(klant.getPostcode()));
        txtWoonplaats.setText(klant.getWoonplaats());
        txtTelNr.setText(klant.getTel_nr());
        txtEmailAdres.setText(klant.getEmail());

        btnToevoegen.setText("Wijzigen");
        lblTitel.setText("Klant Wijzigen");
    }

    public Klant getKlant() {
        int id = Integer.parseInt(findViewById(rdgAanspreking.getCheckedRadioButtonId()).getTag().toString());
        klant.setAanspreking(Aanspreking.fromId(id));

        klant.setNaam(txtAchternaam.getText().toString());
        klant.setVoornaam(txtVoornaam.getText().toString());
        klant.setAdres(txtAdres.getText().toString());
        klant.setPostcode(Integer.parseInt(txtPostcode.getText().toString()));
        klant.setWoonplaats(txtWoonplaats.getText().toString());
        klant.setTel_nr(txtTelNr.getText().toString());
        klant.setEmail(txtEmailAdres.getText().toString());

        return klant;
    }
}
