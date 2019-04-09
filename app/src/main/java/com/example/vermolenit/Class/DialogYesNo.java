package com.example.vermolenit.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vermolenit.R;

public class DialogYesNo extends AlertDialog {

    public Button btnYes;
    public Button btnNo;

    protected DialogYesNo(Context context, String titel, String question) {
        super(context);

        View view = View.inflate(context, R.layout.dialog_yes_no, null);

        ((TextView)view.findViewById(R.id.lblTitel)).setText(titel);
        ((TextView) view.findViewById(R.id.lblQuestion)).setText(question);
        btnYes = view.findViewById(R.id.btnYes);
        btnNo = view.findViewById(R.id.btnNo);

        this.setView(view);
    }
}
