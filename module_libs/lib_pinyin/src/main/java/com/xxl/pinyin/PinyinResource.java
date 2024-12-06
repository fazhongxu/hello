package com.xxl.pinyin;

import android.content.Context;

import com.xxl.kit.AppUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源文件加载类
 */
public final class PinyinResource {

    private PinyinResource() {
    }

    // 从assets加载资源文件
    protected static Reader newAssetsReader(Context context, String assetPath) throws IOException {
        InputStream is = context.getAssets().open(assetPath);
        return new InputStreamReader(is, "UTF-8");
    }

    protected static Map<String, String> getResource(Context context, String assetPath) {
        Map<String, String> map = new ConcurrentHashMap<>();
        try {
            Reader reader = newAssetsReader(context, assetPath);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("=");
                if (tokens.length == 2) { // 确保有两个部分
                    map.put(tokens[0], tokens[1]);
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    // 修改为从assets加载拼音资源
    protected static Map<String, String> getPinyinResource() {
        return getResource(AppUtils.getApplication(), "pinyin/pinyin.dict");
    }

    protected static Map<String, String> getMutilPinyinResource() {
        return getResource(AppUtils.getApplication(), "pinyin/mutil_pinyin.dict");
    }

    protected static Map<String, String> getChineseResource() {
        return getResource(AppUtils.getApplication(), "pinyin/chinese.dict");
    }
}