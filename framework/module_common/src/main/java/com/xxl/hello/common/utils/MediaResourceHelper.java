//package com.xxl.hello.common.utils;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//
//import androidx.annotation.NonNull;
//
//import com.edujia.aop.annotation.Safe;
//import com.edujia.services.base.data.enums.MediaShareEnums;
//import com.edujia.services.base.data.model.entity.media_share.MediaShareResourceEntity;
//import com.edujia.utils.FileUtils;
//import com.edujia.utils.ListUtils;
//import com.edujia.utils.PathUtils;
//import com.edujia.utils.StringUtils;
//import com.edujia.utils.ToastUtils;
//import com.edujia.wm.main.R;
//import com.xxl.kit.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 媒体资源辅助类
// *
// * @author xxl.
// * @date 2019/08/20.
// */
//public class MediaResourceHelper {
//    //region : 成员变量
//
//    /**
//     * 最大选择数量
//     */
//    private static final int MAX_DATA = 9;
//
//    //endregion
//
//    //region : 构造方法
//
//    private MediaResourceHelper() {
//
//    }
//
//    //endregion
//
//    //region : 构建单例
//
//    private static class Holder {
//        private static final MediaResourceHelper INSTANCE = new MediaResourceHelper();
//    }
//
//    /**
//     * 获取单例对象
//     *
//     * @return
//     */
//    public static MediaResourceHelper getInstance() {
//        return Holder.INSTANCE;
//    }
//
//    //endregion
//
//    //region : 获取外部应用分享进入的多媒体数据
//
//    /**
//     * 获取外部分享的多媒体数据
//     *
//     * @param activity
//     * @param intent
//     * @return
//     */
//    public List<MediaShareResourceEntity> getShareData(@NonNull Activity activity,
//                                                       @NonNull Intent intent) {
//        Bundle extras = intent.getExtras();
//        String action = intent.getAction();
//        String type = intent.getType();
//        //单选文件
//        if (Intent.ACTION_SEND.equals(action) && type != null && extras != null) {
//            return createSingleData(activity, extras, type);
//        }
//
//        //多选文件
//        if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null && extras != null) {
//            return createMultipleData(activity, extras);
//        }
//
//        //用别的app分享
//        if (Intent.ACTION_VIEW.equals(action) && type != null) {
//            return createActionViewData(intent, type);
//        }
//
//        return null;
//    }
//
//    /**
//     * 文件单选数据创建
//     */
//    @Safe(callBack = "createFileException")
//    private List<MediaShareResourceEntity> createSingleData(@NonNull final Activity activity,
//                                                            @NonNull final Bundle extras, String type) {
//        List<MediaShareResourceEntity> mediaShareResourceEntities = new ArrayList<>();
//        Uri uri = extras.getParcelable(Intent.EXTRA_STREAM);
//        if (uri != null) {
//            String filePath = getRealPathFromUri(activity, uri);
//            MediaShareResourceEntity uiMediaShareResourceEntity;
//            String[] textSuffixArray = StringUtils.getStringArray(R.array.file_text_suffix);
//            // 是否是文本类型的文件  .txt .h .html .log 等
//            boolean isText = false;
//            if (!TextUtils.isEmpty(filePath)) {
//                for (String suffix : textSuffixArray) {
//                    isText = filePath.endsWith(suffix);
//                    if (isText) {
//                        break;
//                    }
//                }
//            }
//            if (!TextUtils.isEmpty(filePath) && isText) {
//                uiMediaShareResourceEntity = new MediaShareResourceEntity(filePath, MediaShareEnums.Type.APPLICATION);
//            } else {
//                uiMediaShareResourceEntity = new MediaShareResourceEntity(filePath, type);
//            }
//            mediaShareResourceEntities.add(uiMediaShareResourceEntity);
//            return mediaShareResourceEntities;
//        } else {
//            String textUrl = extras.getString(Intent.EXTRA_TEXT);
//            //web类型
//            if (StringUtils.isHttp(textUrl)) {
//                if (!TextUtils.isEmpty(textUrl)) {
//                    MediaShareResourceEntity uiMediaShareResourceEntity = new MediaShareResourceEntity(textUrl, MediaShareEnums.Type.WEB);
//                    mediaShareResourceEntities.add(uiMediaShareResourceEntity);
//                    return mediaShareResourceEntities;
//                }
//            }
//
//            //普通文本类型
//            MediaShareResourceEntity uiMediaShareResourceEntity = new MediaShareResourceEntity(textUrl, MediaShareEnums.Type.TEXT);
//            mediaShareResourceEntities.add(uiMediaShareResourceEntity);
//            return mediaShareResourceEntities;
//        }
//    }
//
//    /**
//     * 异常处理方法 创建文件异常
//     *
//     * @param throwable 异常信息
//     */
//    public void createFileException(@NonNull Throwable throwable) {
//        ToastUtils.warning(R.string.resources_no_resources_found).show();
//    }
//
//    /**
//     * 多条文件数据
//     */
//    @Safe(callBack = "createFileException")
//    private List<MediaShareResourceEntity> createMultipleData(@NonNull final Activity activity,
//                                                              @NonNull final Bundle extras) {
//        List<MediaShareResourceEntity> mediaShareResourceEntities = new ArrayList<>();
//        ArrayList<Uri> uris = extras.getParcelableArrayList(Intent.EXTRA_STREAM);
//        if (ListUtils.isEmpty(uris)) {
//            return null;
//        }
//
//        //最多展示9条数据
//        int size = ListUtils.getSize(uris) > MAX_DATA ? MAX_DATA : ListUtils.getSize(uris);
//
//        for (int i = 0; i < size; i++) {
//            Uri uri = uris.get(i);
//            String filePath = getRealPathFromUri(activity, uri);
//            MediaShareResourceEntity uiMediaShareResourceEntity = new MediaShareResourceEntity(filePath, getFileType(activity, filePath));
//            mediaShareResourceEntities.add(uiMediaShareResourceEntity);
//        }
//        return mediaShareResourceEntities;
//    }
//
//    /**
//     * 从别的app进入微脉分享数据创建
//     */
//    @Safe(callBack = "createFileException")
//    private List<MediaShareResourceEntity> createActionViewData(@NonNull final Intent intent,
//                                                                @NonNull final String type) {
//        List<MediaShareResourceEntity> mediaShareResourceEntities = new ArrayList<>();
//        Uri uri = intent.getData();
//        if (uri != null) {
//            String filePath = PathUtils.getFilePathByUri(uri);
//            MediaShareResourceEntity uiMediaShareResourceEntity;
//            String[] textSuffixArray = StringUtils.getStringArray(R.array.file_text_suffix);
//            // 是否是文本类型的文件  .txt .h .html .log 等
//            boolean isText = false;
//            if (!TextUtils.isEmpty(filePath)) {
//                for (String suffix : textSuffixArray) {
//                    isText = filePath.endsWith(suffix);
//                    if (isText) {
//                        break;
//                    }
//                }
//            }
//            if (!TextUtils.isEmpty(filePath) && isText) {
//                uiMediaShareResourceEntity = new MediaShareResourceEntity(filePath, MediaShareEnums.Type.APPLICATION);
//            } else {
//                uiMediaShareResourceEntity = new MediaShareResourceEntity(filePath, type);
//            }
//            mediaShareResourceEntities.add(uiMediaShareResourceEntity);
//            return mediaShareResourceEntities;
//        }
//        return null;
//    }
//
//    /**
//     * 通过Uri获取文件在本地存储的真实路径
//     */
//    private String getRealPathFromUri(@NonNull final Activity activity,
//                                      @NonNull final Uri contentUri) {
//        return PathUtils.getFilePathByUri(activity, contentUri);
//    }
//
//    /**
//     * 获取文件类型
//     */
//    private String getFileType(@NonNull final Activity activity,
//                               @NonNull final String path) {
//        if (FileUtils.checkSuffix(path, activity.getResources().getStringArray(R.array.file_image_suffix))) {
//            return MediaShareEnums.Type.IMAGE;
//        } else if (FileUtils.checkSuffix(path, activity.getResources().getStringArray(R.array.file_video_suffix))) {
//            return MediaShareEnums.Type.VIDEO;
//        } else {
//            return MediaShareEnums.Type.APPLICATION;
//        }
//    }
//    //endregion
//}
