package hu.ait.android.saferide;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import hu.ait.android.saferide.fragment.FragmentAbout;
import hu.ait.android.saferide.fragment.FragmentHelp;
import hu.ait.android.saferide.fragment.FragmentSettings;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        if (id == R.id.nav_user) {

        } else if (id == R.id.nav_driver) {

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
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment == null) {
            if (tag.equals(FragmentSettings.TAG)) {
                fragment = new FragmentSettings();
            } else if (tag.equals(FragmentHelp.TAG)) {
                fragment = new FragmentHelp();
            } else if (tag.equals(FragmentAbout.TAG)) {
                fragment = new FragmentAbout();
            }
        }

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        trans.replace(R.id.layoutContent, fragment, tag);

        trans.addToBackStack(null);

        trans.commit();
    }
}
