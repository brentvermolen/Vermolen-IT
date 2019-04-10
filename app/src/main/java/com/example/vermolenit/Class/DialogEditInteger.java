package com.example.vermolenit.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.vermolenit.R;

public class DialogEditInteger extends AlertDialog {

    final int minValue;
    final int maxValue;

    private NumberPicker numberPicker;
    public Button btnOke;
    public Button btnAnnuleren;

    protected DialogEditInteger(Context context, String titel, int min, int max) {
        this(context, titel, min, max, 0);
    }

    public DialogEditInteger(Context context, String titel, int min, int max, int startValue){
        super(context);

        View view = View.inflate(context, R.layout.dialog_edit_integer, null);

        minValue = min;
        maxValue = max;

        numberPicker = view.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(maxValue - minValue);
        numberPicker.setValue(startValue - minValue);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                return Integer.toString(index + minValue);
            }
        });
        ((TextView)view.findViewById(R.id.lblTitel)).setText(titel);
        btnOke = view.findViewById(R.id.btnOke);
        btnAnnuleren = view.findViewById(R.id.btnAnnuleren);

        this.setView(view);
    }

    public int getValue(){
        return numberPicker.getValue() + minValue;
    }
}
