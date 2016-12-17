package com.android.alex.slotmachine.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.android.alex.slotmachine.R;

import java.util.Date;

/**
 * Trigger this dialog for a winning combination
 *
 * Created by Alex on 26-Nov-15.
 */
public class WinnerDialog extends Dialog{

    private Activity activity;

    private String tag1; // c1 slot
    private String tag2; // c2 slot
    private String tag3; // c3 slot

    public WinnerDialog(Activity activity, String tag1, String tag2, String tag3) {
        super(activity);

        this.activity = activity;

        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_win);

        //TODO: display wining combination
    }
}
