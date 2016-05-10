package hu.ait.android.saferide.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import hu.ait.android.saferide.R;

/**
 * Created by emasten on 5/10/16.
 */
public class FragmentUserPickUp extends DialogFragment {

    public static final String TAG = "DialogFragmentUserPickUp";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Set Pick Up");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        alertDialogBuilder.setView(inflater.inflate(R.layout.fragment_user_setpickup, null));


        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }
}
