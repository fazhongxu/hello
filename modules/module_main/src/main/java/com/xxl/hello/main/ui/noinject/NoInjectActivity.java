//package com.xxl.hello.main.ui.noinject;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.xxl.hello.main.R;
//
///**
// * @author xxl.
// * @date 2025/1/9.
// */
//public class NoInjectActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_no_inject);
//
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.fl_container, NoInjectFragment.newInstance());
//
//        fragmentTransaction.commit();
//    }
//
//    public static void navigation(Context context) {
//        Intent intent = new Intent(context, NoInjectActivity.class);
//        context.startActivity(intent);
//    }
//}