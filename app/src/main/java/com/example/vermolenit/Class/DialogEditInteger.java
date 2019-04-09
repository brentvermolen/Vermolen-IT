package com.example.vermolenit.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.vermolenit.R;

public class DialogEditInteger extends AlertDialog {

    private NumberPicker numberPicker;
    public Button btnOke;
    public Button btnAnnuleren;

    protected DialogEditInteger(Context context, String titel, int min, int max) {
        super(context);

        View view = View.inflate(context, R.layout.dialog_edit_integer, null);

        numberPicker = view.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(min);
        numberPicker.setMaxValue(max);
        ((TextView)view.findViewById(R.id.lblTitel)).setText(titel);
        btnOke = view.findViewById(R.id.btnOke);
        btnAnnuleren = view.findViewById(R.id.btnAnnuleren);

        this.setView(view);
    }

    public int getValue(){
        return numberPicker.getValue();
    }
}
