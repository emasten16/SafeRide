package hu.ait.android.saferide.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;

import hu.ait.android.saferide.R;
import hu.ait.android.saferide.data.RequestPickUp;

/**
 * Created by emasten on 5/10/16.
 */
public class FragmentDriver extends Fragment {

    public static final String TAG = "FragmentDriver";

    private static MapView mMapView;
    private static GoogleMap mMap;
    ArrayList<RequestPickUp> requests = new ArrayList<>();

    public static short REFRESH = 0;
    public static short ARRIVING = 1;
    public static short DROPPED_OFF = 2;
    public static short driver_state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // sets layout
        final View rootView = inflater.inflate(R.layout.fragment_driver, container, false);

        // sets map
        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMap = mMapView.getMap();
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng amherst = new LatLng(42.3708794, -72.5174623);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amherst, 17.0f));


        // Button for driver
        // switches between Refresh, Arriving, and Dropped Off
        driver_state = REFRESH;
        final Button btnRefresh = (Button) rootView.findViewById(R.id.btnRefresh);
        btnRefresh.setText("Refresh");
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Refreshes Q to check for requests
                if (driver_state == REFRESH) {
                    refreshQ();

                    if (!requests.isEmpty()) {
                        RequestPickUp current = requests.remove(0);

                        // REMOVE DATA FROM BACKENDLESS
                        //Backendless.Persistence.of(RequestPickUp.class).remove(current);

                        showDialog(current);
                        btnRefresh.setText("Arriving");


                        requests.clear();
                    }
                }
                // Notifies User that driver is arriving
                else if (driver_state == ARRIVING) {
                    // SEND MESSAGE TO USER

                    Toast.makeText(getActivity(), "Notifiy user I am arriving", Toast.LENGTH_SHORT).show();
                    driver_state = DROPPED_OFF;
                    btnRefresh.setText("Dropped Off");
                }
                // Ends the process and switches back to being able to refresh Q
                else if (driver_state == DROPPED_OFF) {
                    Toast.makeText(getActivity(), "Dropped Off", Toast.LENGTH_SHORT).show();
                    mMap.clear();
                    driver_state = REFRESH;
                    btnRefresh.setText("Refresh");
                }
            }
        });


        return rootView;
    }


    public void refreshQ() {

        // USE HANDLER AND EVENT BUS
        // EMERGENCY Q

        Backendless.Persistence.of(RequestPickUp.class).find(new BackendlessCallback<BackendlessCollection<RequestPickUp>>() {
            @Override
            public void handleResponse(BackendlessCollection<RequestPickUp> response) {
                Iterator<RequestPickUp> iterator = response.getCurrentPage().iterator();

                while (iterator.hasNext()) {
                    RequestPickUp r = iterator.next();

                    requests.add(0, r);
                }
            }
        });

    }


    protected void showDialog(RequestPickUp r) {
        FragmentDriverPickUp dialog = new FragmentDriverPickUp();
        Bundle b = new Bundle();
        b.putSerializable(FragmentDriverPickUp.KEY_PICKUP, r);
        dialog.setArguments(b);
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), FragmentDriverPickUp.TAG);
    }


    public void startDrive(RequestPickUp r) {
        mMap.clear();

        // sets points of location and destination
        String location = r.getLocation();
        String destination = r.getDestination();
        setPoints(location, 0);
        setPoints(destination, 1);
    }

    public static void setPoints(String place, int i) {
        LatLng newPlace = null;

        // ALL DORMS

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
