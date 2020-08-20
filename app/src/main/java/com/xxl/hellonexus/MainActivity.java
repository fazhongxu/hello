package com.xxl.hellonexus;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xxl.common.TestUtils;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;


public class MainActivity extends AppCompatActivity {

    public static String FLUTTER_ENGINE = "flutter_engine";

    private FlutterEngine mFlutterEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        int random = TestUtils.getRandom();
        TextView tvText = findViewById(R.id.tv_test);
        tvText.setText(String.valueOf(random));
        tvText.setText(String.valueOf(TestUtils.currentTimeMillis()));

        tvText.setOnClickListener(view -> {
            startFlutterActivity();
        });
    }

    /**
     * 初始化Flutter引擎
     */
    private void init() {
        mFlutterEngine = new FlutterEngine(this);
        mFlutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
        FlutterEngineCache.getInstance().put(FLUTTER_ENGINE, mFlutterEngine);
    }

    /**
     * 跳转Flutter页面
     */
    private void startFlutterActivity() {
        startActivity(FlutterActivity.withCachedEngine(FLUTTER_ENGINE)
                .build(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFlutterEngine.destroy();
    }
}
