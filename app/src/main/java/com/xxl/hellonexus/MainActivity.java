package com.xxl.hellonexus;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
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
        tvText.setText(getBurnAfterReadingTips());
    }


    private CharSequence getBurnAfterReadingTips() {
        String tips1 = "消息已读后自动销毁";
        String tips2 = "消息在各端不留痕迹";
        StringBuilder stringBuilder = new StringBuilder(" ")
                .append(tips1+"\n ")
                .append(tips2+"\n ");

        SpannableString spannableString = new SpannableString(stringBuilder.toString());

        char[] chars = spannableString.toString().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            //content.getBytes()
            char c = chars[i];
            if (TextUtils.equals(String.valueOf(c)," ")){
                Log.e("aa", "onCreate: "+c+"--"+i );
                Drawable drawable = getDrawable(R.mipmap.ic_launcher);
                drawable.setBounds(0,0,45,45);
                ImageSpan imageSpan = new ImageSpan(drawable);
                spannableString.setSpan(imageSpan,i,i+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
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
