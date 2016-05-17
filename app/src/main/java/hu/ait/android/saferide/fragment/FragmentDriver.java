package hu.ait.android.saferide.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
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
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
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
    ArrayList<RequestPickUp> requests;

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
        LatLng amherst = new LatLng(42.3708794, -72.5174623);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amherst, 17.0f));



        requests = new ArrayList<>();
        refreshQ();

        /*if (!requests.isEmpty()) {
            RequestPickUp current = requests.remove(0);
            Backendless.Persistence.of(RequestPickUp.class).remove(current);

            showDialog(current);
        }*/


        return rootView;
    }

    public void refreshQ() {
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

        Toast.makeText(getActivity(), "b: " + requests.size() + "", Toast.LENGTH_SHORT).show();
    }

    protected void showDialog(RequestPickUp r) {
        FragmentDriverPickUp dialog = new FragmentDriverPickUp();
        Bundle b = new Bundle();
        b.putSerializable(FragmentDriverPickUp.KEY_PICKUP, r);
        dialog.setArguments(b);
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), FragmentDriverPickUp.TAG);
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
