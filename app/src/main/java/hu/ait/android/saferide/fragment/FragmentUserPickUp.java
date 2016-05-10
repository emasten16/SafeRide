package hu.ait.android.saferide.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

        View view = inflater.inflate(R.layout.fragment_user_setpickup, null);
        alertDialogBuilder.setView(view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.places_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerLocation = (Spinner) view.findViewById(R.id.spinnerLocation);
        spinnerLocation.setAdapter(adapter);
        Spinner spinnerDestination = (Spinner) view.findViewById(R.id.spinnerDestination);
        spinnerDestination.setAdapter(adapter);


        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }
}
