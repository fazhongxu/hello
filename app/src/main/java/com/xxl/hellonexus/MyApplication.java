package com.xxl.hellonexus;

import android.app.Application;

/**
 * @author xxl.
 * @date 2020/8/20.
 */
public class MyApplication extends Application {

    public static final String FLUTTER_ENGINE_ID = "flutter_engine_id";

    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiate a FlutterEngine.
//        FlutterEngine flutterEngine = new FlutterEngine(this);
//
//        // Start executing Dart code to pre-warm the FlutterEngine.
//        flutterEngine.getDartExecutor().executeDartEntrypoint(
//                DartExecutor.DartEntrypoint.createDefault()
//        );
//
//        // Cache the FlutterEngine to be used by FlutterActivity.
//        FlutterEngineCache
//                .getInstance()
//                .put(FLUTTER_ENGINE_ID, flutterEngine);
    }
}
