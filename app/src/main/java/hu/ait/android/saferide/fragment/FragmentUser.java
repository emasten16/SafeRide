package hu.ait.android.saferide.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import hu.ait.android.saferide.R;
import hu.ait.android.saferide.data.RequestPickUp;

/**
 * Created by emasten on 5/10/16.
 */
public class FragmentUser extends Fragment{

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
        mMapView.onResume();
        mMap = mMapView.getMap();
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng amherst = new LatLng(42.370829, -72.516884);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amherst, 17.0f));


        // webiste for coordinates:
        // http://www.latlong.net/
        /*LatLng amherst = new LatLng(42.370829, -72.516884);
        mMap.addMarker(new MarkerOptions().position(amherst));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amherst, 17.0f));

        LatLng johnsonChapel = new LatLng(42.370873, -72.517844);
        mMap.addMarker(new MarkerOptions().position(johnsonChapel));

        LatLng tylerHouse = new LatLng(42.377880, -72.515996);
        mMap.addMarker(new MarkerOptions().position(tylerHouse));

        LatLng plimptonHouse = new LatLng(42.377524, -72.515460);
        mMap.addMarker(new MarkerOptions().position(plimptonHouse));*/


        // switches to dialog fragment
        Button btnPickUp = (Button) rootView.findViewById(R.id.btnPickUp);
        btnPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        return rootView;
    }

    void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(FragmentUserPickUp.TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        FragmentUserPickUp newFragment = FragmentUserPickUp.newInstance();
        newFragment.show(getFragmentManager(), FragmentUserPickUp.TAG);
    }

    public static void setPoints(String place) {
        LatLng newPlace = null;

        if (place.equals("Tyler")) {
            newPlace = new LatLng(42.377880, -72.515996);
        } else if (place.equals("Plimpton")) {
            newPlace = new LatLng(42.377524, -72.515460);
        }

        mMap.addMarker(new MarkerOptions().position(newPlace));
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

