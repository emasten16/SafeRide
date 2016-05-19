package hu.ait.android.saferide.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import hu.ait.android.saferide.R;
import hu.ait.android.saferide.data.RequestPickUp;

/**
 * Created by emasten on 5/17/16.
 */
public class FragmentDriverPickUp extends DialogFragment {

    public static final String TAG = "DialogFragmentDriverPickUp";
    public static final String KEY_PICKUP = "KEY_PICKUP";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Pick Up Request");

        // inflates layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_driver_pickup, null);
        alertDialogBuilder.setView(view);

        // sets dialog data to the request
        final RequestPickUp requestPickUp = (RequestPickUp) getArguments().getSerializable(KEY_PICKUP);

        TextView tvName = (TextView) view.findViewById(R.id.driver_tvName);
        tvName.setText("User: " + requestPickUp.getUser());
        TextView tvLocation = (TextView) view.findViewById(R.id.driver_tvLocation);
        tvLocation.setText("Location: " + requestPickUp.getLocation());
        TextView tvDestination = (TextView) view.findViewById(R.id.driver_tvDestination);
        tvDestination.setText("Destination: " + requestPickUp.getDestination());
        TextView tvNumPeople = (TextView) view.findViewById(R.id.driver_tvNumPeople);
        tvNumPeople.setText("Number of People: " + String.valueOf(requestPickUp.getNumPeople()));

        // Emergency????


        // accepts request
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // SENDS MESSAGE TO USER SAYING DRIVER IS ON ITS WAY

                // starts drive UI
                FragmentDriver fd = new FragmentDriver();
                fd.startDrive(requestPickUp);

                FragmentDriver.driver_state = FragmentDriver.ARRIVING;
            }
        });


        // dismisses request
        alertDialogBuilder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // SENDS MESSAGE TO USER SAYING REQUEST WAS DECLINED
                dismiss();

                FragmentDriver.driver_state = FragmentDriver.REFRESH;
            }
        });


        return alertDialogBuilder.create();
    }
}
