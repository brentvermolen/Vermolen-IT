package com.example.vermolenit.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.vermolenit.R;

import java.util.Calendar;
import java.util.Date;

public class DialogEditDate extends AlertDialog {

    final Calendar calendar;

    private DatePicker datePicker;
    public Button btnOke;
    public Button btnAnnuleren;

    public DialogEditDate(Context context, String titel){
        this(context, titel, Calendar.getInstance());
    }

    public DialogEditDate(Context context, String titel, Calendar startDate) {
        super(context);
        calendar = Calendar.getInstance();

        View view = View.inflate(context, R.layout.dialog_edit_date, null);

        ((TextView)view.findViewById(R.id.lblTitel)).setText(titel);
        datePicker = view.findViewById(R.id.datePicker);
        datePicker.setMaxDate(calendar.getTimeInMillis());
        datePicker.updateDate(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));

        btnOke = view.findViewById(R.id.btnOke);
        btnAnnuleren = view.findViewById(R.id.btnAnnuleren);

        this.setView(view);
    }

    public Calendar getValue(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

        return calendar;
    }
}
