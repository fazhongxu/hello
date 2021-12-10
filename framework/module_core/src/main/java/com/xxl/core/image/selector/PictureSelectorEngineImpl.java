package com.xxl.core.image.selector;

import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

/**
 * 图片选择库图片加载引擎
 *
 * @author xxl.
 * @date 2021/9/1.
 */
public class PictureSelectorEngineImpl implements PictureSelectorEngine {

    /**
     * Create ImageLoad Engine
     *
     * @return
     */
    @Override
    public ImageEngine createEngine() {
        return GlideEngine.createGlideEngine();
    }

    /**
     * Create Result Listener
     *
     * @return
     */
    @Override
    public OnResultCallbackListener<LocalMedia> getResultCallbackListener() {
        return null;
    }

}