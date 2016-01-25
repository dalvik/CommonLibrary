package com.android.library.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

public class AssetsUtil {
    /**
     * 从asserts 目录下读取图片文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static BitmapDrawable readImgFromAssert(Context context, String imgFileName) {
        InputStream inputStream = null;
        BitmapDrawable drawable = null;

        if (null == imgFileName) {
            return null;
        }

        try {
            inputStream = context.getResources().getAssets().open(imgFileName);
            drawable = new BitmapDrawable(context.getResources(), inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return drawable;
    }
}
