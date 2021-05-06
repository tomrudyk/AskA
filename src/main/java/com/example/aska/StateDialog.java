package com.example.aska;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class StateDialog extends DialogFragment {

    public String ChosenState;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String[] States = getActivity().getResources().getStringArray(R.array.States);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose State");
        builder.setItems(States, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                ChosenState=States[i];
            }
        });

        return builder.create();
    }

    public String getChosenState(){return this.ChosenState;}
}
