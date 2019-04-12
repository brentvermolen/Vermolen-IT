package com.example.vermolenit.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Eenheid;
import com.example.vermolenit.R;

public class DialogArtikelToevoegen extends AlertDialog {

    private Artikel artikel;

    private TextView lblTitel;
    private EditText txtOmschrijving;
    private EditText txtPrijs;
    private RadioGroup rdgEenheid;
    public Button btnToevoegen;
    public Button btnAnnuleren;
    private NumberPicker numberPickerVoorraad;
    private NumberPicker numberPickerMeldingOp;

    final int minValueVoorraad = -1;
    final int maxValueVoorraad = 99;
    final int minValueMelding = -1;
    final int maxValueMelding = 10;

    public DialogArtikelToevoegen(Context context){
        super(context);

        init(context);
        artikel = new Artikel();
    }

    public DialogArtikelToevoegen(Context context, Artikel artikel) {
        super(context);

        init(context);

        this.artikel = artikel;
        setEdit();
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.dialog_add_artikel, null);

        lblTitel = view.findViewById(R.id.lblTitel);

        btnToevoegen = view.findViewById(R.id.btnToevoegen);
        btnAnnuleren = view.findViewById(R.id.btnAnnuleren);

        txtOmschrijving = view.findViewById(R.id.txtOmschrijing);
        txtPrijs = view.findViewById(R.id.txtPrijs);

        rdgEenheid = view.findViewById(R.id.rdgEenheid);

        numberPickerVoorraad = view.findViewById(R.id.numberPickerVoorraad);
        numberPickerVoorraad.setMinValue(0);
        numberPickerVoorraad.setMaxValue(maxValueVoorraad - minValueVoorraad);
        numberPickerVoorraad.setValue(0 - minValueVoorraad);
        numberPickerVoorraad.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                if (index == 0){
                    return "Onbeperkt";
                }

                return Integer.toString(index + minValueVoorraad);
            }
        });
        numberPickerMeldingOp = view.findViewById(R.id.numberPickerMeldingOp);
        numberPickerMeldingOp.setMinValue(0);
        numberPickerMeldingOp.setMaxValue(maxValueMelding - minValueMelding);
        numberPickerMeldingOp.setValue(0 - minValueMelding);
        numberPickerMeldingOp.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                if (index == 0){
                    return "Nooit";
                }

                return Integer.toString(index + minValueMelding);
            }
        });

        this.setView(view);
    }


    private void setEdit() {
        lblTitel.setText("Artikel Wijzigen");
        btnToevoegen.setText("Wijzigen");

        int rdgId = artikel.getEenheid().getId();
        RadioButton rdb = rdgEenheid.findViewWithTag(String.valueOf(rdgId));

        rdb.setChecked(true);

        txtOmschrijving.setText(artikel.getOmschrijving());
        txtPrijs.setText(String.format("%.2f", artikel.getPrijs()));
        numberPickerVoorraad.setValue(artikel.getVoorraad() - minValueVoorraad);
        numberPickerMeldingOp.setValue(artikel.getMeldingOpVoorraad() - minValueMelding);
    }

    public String getOmschrijing(){
        return txtOmschrijving.getText().toString();
    }

    public double getPrijs(){
        return Double.parseDouble(txtPrijs.getText().toString().replace(",", "."));
    }

    public int getVoorraad(){
        return numberPickerVoorraad.getValue() + minValueVoorraad;
    }

    public int getMeldingOp(){
        return numberPickerMeldingOp.getValue() + minValueMelding;
    }

    public Eenheid getEenheid(){
        return Eenheid.fromId(Integer.parseInt(((RadioButton) findViewById(rdgEenheid.getCheckedRadioButtonId())).getTag().toString()));
    }
}
