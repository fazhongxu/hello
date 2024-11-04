//
//这里不注掉也可以运行，只不过没必要有这个了，回头就删了
//
//

//package com.xxl.hello.main.ui.main;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.xxl.hello.service.data.repository.DataRepositoryKit;
//import com.xxl.hello.service.qunlifier.ForApplication;
//import com.xxl.hello.service.qunlifier.ForHelloUpload;
//import com.xxl.hello.service.qunlifier.ForTencentUpload;
//import com.xxl.core.ui.ViewModelProviderFactory;
//import com.xxl.hello.service.upload.api.UploadService;
//
//import dagger.Module;
//import dagger.Provides;
//
///**
// * @author xxl
// * @date 2021/07/16.
// */
//@Module
//public class MainFragmentModule {
//
//    @Provides
//    MainViewModel provideMainViewModel(@ForApplication final Application application,
//                                       @NonNull final DataRepositoryKit dataRepositoryKit,
//                                       @ForHelloUpload final UploadService uploadService) {
//        return new MainViewModel(application, dataRepositoryKit, uploadService);
//    }
//
//    @Provides
//    ViewModelProvider.Factory provideMainModelFactory(@NonNull final MainViewModel mainViewModel) {
//        return new ViewModelProviderFactory<>(mainViewModel);
//    }
//}
