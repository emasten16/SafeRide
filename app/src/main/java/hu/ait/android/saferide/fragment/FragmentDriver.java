package hu.ait.android.saferide.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    private Projection projection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // sets layout
        View rootView = inflater.inflate(R.layout.fragment_driver, container, false);

        // sets map
        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMap = mMapView.getMap();
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng amherst = new LatLng(42.3708794, -72.5174623);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amherst, 17.0f));


        Button btnRefresh = (Button) rootView.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refreshQ();

                if (!requests.isEmpty()) {
                    RequestPickUp current = requests.remove(0);

                    // REMOVE DATA FROM BACKENDLESS
                    //Backendless.Persistence.of(RequestPickUp.class).remove(current);

                    showDialog(current);


                    requests.clear();
                }
            }
        });


        return rootView;
    }


    public void refreshQ() {

        // USE HANDLER AND EVENT BUS

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

        // webiste for coordinates:
        // http://www.latlong.net/

        if (place.equals("Appleton")) {
            newPlace = new LatLng(42.370256, -72.517930);
        } else if (place.equals("Charles Pratt")) {
            newPlace = new LatLng(42.370192, -72.516120);
        } else if (place.equals("The Hill")) {
            newPlace = new LatLng(42.377524, -72.515460);
        } else if (place.equals("King and Wieland")) {
            newPlace = new LatLng(42.369732, -72.513223);
        } else if (place.equals("Morris Pratt")) {
            newPlace = new LatLng(42.372384, -72.517611);
        } else if (place.equals("Morrow")) {
            newPlace = new LatLng(42.372423, -72.516345);
        } else if (place.equals("North")) {
            newPlace = new LatLng(42.371155, -72.518062);
        } else if (place.equals("South")) {
            newPlace = new LatLng(42.370592, -72.518067);
        } else if (place.equals("The Triangle")) {
            newPlace = new LatLng(42.373596, -72.520395);
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
