//package com.xxl.hello.main.ui.noinject;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.xxl.hello.main.R;
//import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
//import com.xxl.hello.service.data.repository.DataRepositoryKit;
//import com.xxl.hello.service.data.repository.api.UserRepositoryApi;
//
//import javax.inject.Inject;
//
//import dagger.android.support.AndroidSupportInjection;
//import dagger.android.support.DaggerFragment;
//
///**
// * @author xxl.
// * @date 2025/1/9.
// */
//public class NoInjectFragment extends DaggerFragment {
//
//    //region: 成员变量
//
//    @Inject
//    DataRepositoryKit mDataRepositoryKit;
//
//    //endregion
//
//    //region: 构造函数
//
//    private NoInjectFragment() {
//
//    }
//
//    public final static NoInjectFragment newInstance() {
//        return new NoInjectFragment();
//    }
//
//    //endregion
//
//    //region: 页面生命周期
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AndroidSupportInjection.inject(this);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_no_inject, container, false);
//        UserRepositoryApi userRepositoryApi = mDataRepositoryKit.getUserRepositoryApi();
//        LoginUserEntity currentLoginUserEntity = userRepositoryApi.getCurrentLoginUserEntity();
//        Log.e("aa", "onCreate: " + currentLoginUserEntity.getUserName());
//
//        TextView tvContent = rootView.findViewById(R.id.tv_content);
//
//        tvContent.setText(currentLoginUserEntity.getUserId());
//        return rootView;
//    }
//
//    //endregion
//
//    //region: 内部辅助方法
//
//    //endregion
//
//}