package com.xxl.hellonexus;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.flutter.embedding.android.FlutterActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvText = findViewById(R.id.tv_test);
        tvText.setOnClickListener(view -> {
            startFlutterActivity();
        });
    }

    /**
     * 跳转Flutter页面
     */
    private void startFlutterActivity() {
        startActivity(FlutterActivity.withCachedEngine(MyApplication.FLUTTER_ENGINE_ID)
                .build(this)
        );
    }

}
