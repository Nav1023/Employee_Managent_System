package com.example.soc_macmini_15.sqlitepractice.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.soc_macmini_15.sqlitepractice.R;

import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment{
    private DatePicker datePicker;
    private static final int REQUEST_DATE =1;
    public static final String EXTRA_DATE="om.example.soc_macmini_15.sqlitepractice.date";

    public interface DateDialogListener{
        void onFinishDialog(Date date);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date,null);
        datePicker=(DatePicker)v.findViewById(R.id.dialog_date_date_picker);

        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int year=datePicker.getYear();
                                int month=datePicker.getMonth();
                                int day=datePicker.getDayOfMonth();
                                Date date=new GregorianCalendar(year,month,day).getTime();
                                DateDialogListener activity=(DateDialogListener)getActivity();
                                activity.onFinishDialog(date);
                                dismiss();
                            }
                        })
                .create();
    }
}
