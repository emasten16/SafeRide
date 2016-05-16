package hu.ait.android.saferide.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

import hu.ait.android.saferide.MainActivity;
import hu.ait.android.saferide.R;
import hu.ait.android.saferide.data.RequestPickUp;

/**
 * Created by emasten on 5/10/16.
 */
public class FragmentUserPickUp extends DialogFragment {

    public static final String TAG = "DialogFragmentUserPickUp";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Set Pick Up");

        // inflates layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_user_setpickup, null);
        alertDialogBuilder.setView(view);

        // implements both spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.places_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinnerLocation = (Spinner) view.findViewById(R.id.spinnerLocation);
        spinnerLocation.setAdapter(adapter);
        final Spinner spinnerDestination = (Spinner) view.findViewById(R.id.spinnerDestination);
        spinnerDestination.setAdapter(adapter);


        final EditText numPeople = (EditText) view.findViewById(R.id.etUserNumPeople);
        final CheckBox isEmergency = (CheckBox) view.findViewById(R.id.cbEmergency);

        // when user presses "OK", checks to see if user is valid (numPeople isnt empty)
        // saves request to Backendless and goes back to user fragment
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestPickUp pickUp = new RequestPickUp();
                boolean valid = false;
                // figure out how to get user from backendless
                pickUp.setUser("setUser");
                pickUp.setLocation(spinnerLocation.getSelectedItem().toString());
                pickUp.setDestination(spinnerDestination.getSelectedItem().toString());


                final Activity activity = getActivity();

                String numP = numPeople.getText().toString();
                if (numP.equals("")) {
                    Toast.makeText(activity, "Set Number of People", Toast.LENGTH_SHORT).show();
                } else {
                    pickUp.setNumPeople(Integer.parseInt(numP));
                    valid = true;
                }

                pickUp.setIsEmergency(isEmergency.isChecked());

                if (valid) {
                    Backendless.Persistence.save(pickUp, new BackendlessCallback<RequestPickUp>() {
                        @Override
                        public void handleResponse(RequestPickUp response) {
                            Toast.makeText(activity, "Request Sent", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            super.handleFault(fault);
                            Toast.makeText(activity, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.dismiss();
                }
            }
        });

        return alertDialogBuilder.create();
    }
}
