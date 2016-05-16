package hu.ait.android.saferide;

import android.app.Application;

import com.backendless.Backendless;

/**
 * Created by emasten on 5/16/16.
 */
public class SafeRideApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.initApp(this,
                "E7C12938-20E9-CF26-FF77-E8458F43D000",
                "6350DDD6-B59C-C0AB-FFD7-8B02C90F1100",
                "v1");
    }
}
