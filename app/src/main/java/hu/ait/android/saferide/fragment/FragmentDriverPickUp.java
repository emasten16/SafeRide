package hu.ait.android.saferide.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.backendless.Backendless;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

import hu.ait.android.saferide.R;
import hu.ait.android.saferide.data.Message;
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
        alertDialogBuilder.setTitle(R.string.driver_pickUpRequest);

        // inflates layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_driver_pickup, null);
        alertDialogBuilder.setView(view);

        // sets dialog data to the request
        final RequestPickUp requestPickUp = (RequestPickUp) getArguments().getSerializable(KEY_PICKUP);

        final String user = requestPickUp.getUser();
        TextView tvName = (TextView) view.findViewById(R.id.driver_tvName);
        tvName.setText("User: " + user);
        TextView tvLocation = (TextView) view.findViewById(R.id.driver_tvLocation);
        tvLocation.setText("Location: " + requestPickUp.getLocation());
        TextView tvDestination = (TextView) view.findViewById(R.id.driver_tvDestination);
        tvDestination.setText("Destination: " + requestPickUp.getDestination());
        TextView tvNumPeople = (TextView) view.findViewById(R.id.driver_tvNumPeople);
        tvNumPeople.setText("Number of People: " + String.valueOf(requestPickUp.getNumPeople()));
        TextView tvEmergency = (TextView) view.findViewById(R.id.driver_tvEmergency);
        if (requestPickUp.isEmergency()) {
            tvEmergency.setText(R.string.emergency);
            tvEmergency.setTextColor(Color.RED);
        }


        final Activity activity = getActivity();
        // accepts request
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Sends message to user saying driver has accepted request and is on his/her way
                sendRequestAcceptedMessage(user, activity);

                // sets driving points
                // driving state changed to arriving
                FragmentDriver fd = new FragmentDriver();
                fd.startDrive(requestPickUp);
                FragmentDriver.driver_state = FragmentDriver.ARRIVING;
            }
        });


        return alertDialogBuilder.create();
    }


    public void sendRequestAcceptedMessage(String user, final Activity activity) {
        Message message = new Message();
        message.setToUser(user);
        message.setMessageText(activity.getString(R.string.message_driveEnRoute));
        Backendless.Persistence.save(message, new BackendlessCallback<Message>() {
            @Override
            public void handleResponse(Message response) {
                Toast.makeText(activity, R.string.messageSent, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                super.handleFault(fault);
                Toast.makeText(activity, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
