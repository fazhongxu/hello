package com.xxl.hellonexus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xxl.common.TestUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int random = TestUtils.getRandom();
        TextView tvText = findViewById(R.id.tv_test);
        tvText.setText(String.valueOf(random));
        tvText.setText(String.valueOf(TestUtils.currentTimeMillis()));
    }
}
