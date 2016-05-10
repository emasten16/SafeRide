package hu.ait.android.saferide.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

        rootView = inflater.inflate(R.layout.fragment_user, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        mMap = mMapView.getMap();
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // webiste for coordinates:
        // http://www.latlong.net/
        LatLng amherst = new LatLng(42.370829, -72.516884);
        mMap.addMarker(new MarkerOptions().position(amherst));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amherst, 17.0f));

        LatLng johnsonChapel = new LatLng(42.370873, -72.517844);
        mMap.addMarker(new MarkerOptions().position(johnsonChapel));

        LatLng tylerHouse = new LatLng(42.377880, -72.515996);
        mMap.addMarker(new MarkerOptions().position(tylerHouse));

        LatLng plimptonHouse = new LatLng(42.377524, -72.515460);
        mMap.addMarker(new MarkerOptions().position(plimptonHouse));

        Button btnPickUp = (Button) rootView.findViewById(R.id.btnPickUp);
        btnPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragmentUserPickUp().show(getActivity().getFragmentManager(), FragmentUserPickUp.TAG);
            }
        });


        return rootView;
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

