package com.xxl.hello.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xxl.hello.common.TestUtils;
import com.xxl.hello.nexus.R;
import com.xxl.hello.user.ui.LoginActivity;

/**
 * @author xxl
 * @date 2021/07/16.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int random = TestUtils.getRandom();
        TextView tvText = findViewById(R.id.tv_test);
        tvText.setText(String.valueOf(random));
        tvText.setText(String.valueOf(TestUtils.currentTimeMillis()));

        tvText.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}
