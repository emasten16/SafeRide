package hu.ait.android.saferide;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.backendless.Backendless;

import hu.ait.android.saferide.data.RequestPickUp;
import hu.ait.android.saferide.fragment.FragmentAbout;
import hu.ait.android.saferide.fragment.FragmentDriver;
import hu.ait.android.saferide.fragment.FragmentHelp;
import hu.ait.android.saferide.fragment.FragmentSettings;
import hu.ait.android.saferide.fragment.FragmentUser;
import hu.ait.android.saferide.fragment.FragmentUserPickUp;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentUserPickUp.RequestFragmentInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // need this for navigation view and fragments
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // sets side menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // sets username to titly in side menu
        View navHeaderView =  navigationView.getHeaderView(0);
        TextView tvHeader = (TextView) navHeaderView.findViewById(R.id.nav_tvHeader);
        tvHeader.setText(Backendless.UserService.CurrentUser().getEmail().toString());

        // if person is a USER open user fragment
        showFragment(FragmentUser.TAG);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // switches between fragments
        if (id == R.id.nav_user) {
            showFragment(FragmentUser.TAG);
        } else if (id == R.id.nav_driver) {
            showFragment(FragmentDriver.TAG);
        } else if (id == R.id.nav_settings) {
            showFragment(FragmentSettings.TAG);
        } else if (id == R.id.nav_help) {
            showFragment(FragmentHelp.TAG);
        } else if (id == R.id.nav_about) {
            showFragment(FragmentAbout.TAG);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(String tag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        //Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment == null) {
            if (tag.equals(FragmentSettings.TAG)) {
                fragment = new FragmentSettings();
            } else if (tag.equals(FragmentHelp.TAG)) {
                fragment = new FragmentHelp();
            } else if (tag.equals(FragmentAbout.TAG)) {
                fragment = new FragmentAbout();
            } else if (tag.equals(FragmentUser.TAG)) {
                fragment = new FragmentUser();
            } else if (tag.equals(FragmentDriver.TAG)) {
                fragment = new FragmentDriver();
            }
        }

        FragmentTransaction trans = getFragmentManager().beginTransaction();

        trans.replace(R.id.layoutContent, fragment, tag);

        trans.addToBackStack(null);

        trans.commit();
    }


    // info from dialog fragment for user fragment
    @Override
    public void onRequestFragmentResult(RequestPickUp pickUpRequest) {
        String loc = pickUpRequest.getLocation();
        String dest = pickUpRequest.getDestination();

        FragmentUser.setPoints(loc);
        FragmentUser.setPoints(dest);
    }

}
