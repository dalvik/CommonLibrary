package com.android.library.net.http;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.android.library.net.base.OnReslutListener;
import com.android.library.net.utils.HttpUtil;
import com.android.library.net.utils.LogUtil;

/**
 * 
 * @description: 基于HTTP的网络请求
 * @author: 23536
 * @date: 2016年1月5日 下午5:12:53 
 * @param <T>
 */
public class HttpRequestTask<T extends HttpRequest> implements Runnable {
    private final static int COUNT_TIMES = 1;
    private OnReslutListener listener = null;
    private T req = null;

    public HttpRequestTask(OnReslutListener listener, T req) {
        this.listener = listener;
        this.req = req;
    }

    private byte[] postMethod() {
        return HttpUtil.loadData(req instanceof HTTPPostRequest ? true : false, req.getUrl(), req.getHeader(), req.getData(), new InputStreamParser<byte[]>() {
            @Override
            public byte[] parser(final InputStream inputStream) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                try {
                    byte[] readBuffer = new byte[1024*3];
                    int len = 0;
                    while ((len = inputStream.read(readBuffer)) != -1) {
                        buffer.write(readBuffer, 0, len);
                    }
                    LogUtil.i("HttpRequestTask", "response:" + buffer.toString());
                    return buffer.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                    } finally {
                        try {
                            buffer.close();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        });
    }

    @Override
    public void run() {
        int times = 0;
        while ((times++) < COUNT_TIMES) {
            byte[] result = postMethod();
            if (null == result) {
                if (times == COUNT_TIMES) {
                    listener.onFailed();
                } else {
                    continue;
                }
            } else {
                listener.onSucess(new String(result));
                break;
            }
        }
    }
}