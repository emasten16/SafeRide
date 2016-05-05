package hu.ait.android.saferide.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.ait.android.saferide.R;

/**
 * Created by emasten on 5/5/16.
 */
public class FragmentSettings extends Fragment {

    public static final String TAG = "FragmentSettings";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, null, false);

        return rootView;
    }
}
