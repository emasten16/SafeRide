package hu.ait.android.saferide.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.BackendlessCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;

import hu.ait.android.saferide.R;
import hu.ait.android.saferide.data.Message;

/**
 * Created by emasten on 5/10/16.
 */
public class FragmentUser extends Fragment {

    public static final String TAG = "FragmentUser";

    private static MapView mMapView;
    private static GoogleMap mMap;
    private static View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // sets layout
        rootView = inflater.inflate(R.layout.fragment_user, container, false);

        // sets map
        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        setMap();


        // switches to dialog fragment
        Button btnPickUp = (Button) rootView.findViewById(R.id.btnPickUp);
        btnPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                showDialog();
            }
        });

        // searches for messages to specific user and displays them
        Button btnMessages = (Button) rootView.findViewById(R.id.btnMessages);
        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               refreshMessages();
            }
        });


        return rootView;
    }

    public void setMap() {
        mMapView.onResume();
        mMap = mMapView.getMap();
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng amherst = new LatLng(42.370829, -72.516884);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amherst, 17.0f));
    }

    public void refreshMessages() {
        // Gets messages from backendless
        Backendless.Persistence.of(Message.class).find(new BackendlessCallback<BackendlessCollection<Message>>() {
            @Override
            public void handleResponse(BackendlessCollection<Message> response) {
                Iterator<Message> iterator = response.getCurrentPage().iterator();

                while (iterator.hasNext()) {
                    Message message = iterator.next();

                    // if the message is meant for current user, it posts it
                    if (message.getToUser().equals(Backendless.UserService.CurrentUser().getEmail())) {
                        Toast.makeText(getActivity(), message.getMessageText(), Toast.LENGTH_SHORT).show();
                    }

                    // DELETE MESSAGE FROM BACKENDLESS ONCE DISPLAYED
                }
            }
        });
    }


    // shows User dialog to make a request
    public void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(FragmentUserPickUp.TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        FragmentUserPickUp newFragment = FragmentUserPickUp.newInstance();
        newFragment.show(getFragmentManager(), FragmentUserPickUp.TAG);
    }

    public static void setPoints(String place, int i) {
        LatLng newPlace = null;

        // webiste for coordinates:
        // http://www.latlong.net/

        if (place.equals("Appleton")) {
            newPlace = new LatLng(42.370256, -72.517930);
        } else if (place.equals("Chapman")) {
            newPlace = new LatLng(42.370000, -72.520332);
        } else if (place.equals("Charles Drew")) {
            newPlace = new LatLng(42.373516, -72.516122);
        } else if (place.equals("Charles Pratt")) {
            newPlace = new LatLng(42.370192, -72.516120);
        } else if (place.equals("Cohan")) {
            newPlace = new LatLng(42.373809, -72.516595);
        } else if (place.equals("Garman")) {
            newPlace = new LatLng(42.373532, -72.518504);
        } else if (place.equals("Greenway")) {
            newPlace = new LatLng(42.369117, -72.515017);
        } else if (place.equals("The Hill")) {
            newPlace = new LatLng(42.377524, -72.515460);
        } else if (place.equals("Humphries")) {
            newPlace = new LatLng(42.367008, -72.522892);
        } else if (place.equals("James")) {
            newPlace = new LatLng(42.371090, -72.516037);
        } else if (place.equals("Jenkins")) {
            newPlace = new LatLng(42.372451, -72.513674);
        } else if (place.equals("King and Wieland")) {
            newPlace = new LatLng(42.369732, -72.513223);
        } else if (place.equals("Leland")) {
            newPlace = new LatLng(42.374544, -72.517114);
        } else if (place.equals("Lipton")) {
            newPlace = new LatLng(42.373446, -72.517332);
        } else if (place.equals("Moore")) {
            newPlace = new LatLng(42.372858, -72.514953);
        } else if (place.equals("Morris Pratt")) {
            newPlace = new LatLng(42.372384, -72.517611);
        } else if (place.equals("Morrow")) {
            newPlace = new LatLng(42.372423, -72.516345);
        } else if (place.equals("Newport")) {
            newPlace = new LatLng(42.372335, -72.520972);
        } else if (place.equals("North")) {
            newPlace = new LatLng(42.371155, -72.518062);
        } else if (place.equals("Porter")) {
            newPlace = new LatLng(42.374127, -72.518566);
        } else if (place.equals("Seligman")) {
            newPlace = new LatLng(42.372287, -72.523021);
        } else if (place.equals("South")) {
            newPlace = new LatLng(42.370592, -72.518067);
        } else if (place.equals("Stearns")) {
            newPlace = new LatLng(42.370662, -72.516026);
        } else if (place.equals("Taplin")) {
            newPlace = new LatLng(42.372907, -72.513856);
        } else if (place.equals("The Triangle")) {
            newPlace = new LatLng(42.373596, -72.520395);
        } else if (place.equals("Valentine")) {
            newPlace = new LatLng(42.372937, -72.515650);
        } else if (place.equals("Williston")) {
            newPlace = new LatLng(42.371463, -72.518000);
        }

        // 0 for location, 1 for destination
        if (i == 0) {
            mMap.addMarker(new MarkerOptions()
                    .position(newPlace)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        } else {
            mMap.addMarker(new MarkerOptions()
                    .position(newPlace));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
    }

}

