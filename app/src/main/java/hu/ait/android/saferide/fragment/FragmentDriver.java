package hu.ait.android.saferide.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.BackendlessCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import hu.ait.android.saferide.R;
import hu.ait.android.saferide.data.RequestPickUp;

/**
 * Created by emasten on 5/10/16.
 */
public class FragmentDriver extends Fragment {

    public static final String TAG = "FragmentDriver";

    private static MapView mMapView;
    private static GoogleMap mMap;

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



        /*LatLng amherst = new LatLng(42.3708794, -72.5174623);
        mMap.addMarker(new MarkerOptions().position(amherst));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(amherst));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amherst, 17.0f));*/

        //Queue<RequestPickUp> res = refreshQ();

        //Toast.makeText(getActivity(), res.remove().getDestination().toString(), Toast.LENGTH_SHORT).show();

        return rootView;
    }

    /*public Queue<RequestPickUp> refreshQ() {

        final Queue<RequestPickUp> requests = null;

        Backendless.Persistence.of(RequestPickUp.class).find(new BackendlessCallback<BackendlessCollection<RequestPickUp>>() {
            @Override
            public void handleResponse(BackendlessCollection<RequestPickUp> response) {
                Iterator<RequestPickUp> requestIterator = response.getCurrentPage().iterator();

                while (requestIterator.hasNext()) {
                    RequestPickUp r = requestIterator.next();

                    requests.add(r);
                }
            }
        });

        return requests;
    }*/

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
