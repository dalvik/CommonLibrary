/**   
 * Copyright © 2016 浙江大华. All rights reserved.
 * 
 * @title: JSONHttpRequest.java
 * @description: TODO
 * @author: 23536   
 * @date: 2016年1月6日 上午10:34:07 
 */
package com.android.library.net.http;

import java.util.HashMap;

import android.os.Build;

import com.android.library.BaseApplication;
import com.android.library.net.req.DataReq;
import com.android.library.net.utils.LogUtil;
import com.android.library.utils.SecurityUtils;
import com.android.library.utils.TextUtils;

/** 
 * @description: TODO
 * @author: 23536
 * @date: 2016年1月6日 上午10:34:07  
 */
public abstract class JSONHttpRequest<T extends DataReq> extends HttpPostJSONRequest {

    private String TAG = "JSONHttpRequest";
    
    public JSONHttpRequest(DataReq t) {
        super(t);
    }

    private long getRealTime() {
        return BaseApplication.getRealTime();
    }
    
    @Override
    public HashMap<String, String> getHeader() {
        HashMap<String, String> headers = new HashMap<String, String>();
        long time = getRealTime();
        //签名
        headers.put("m-sign", getSign(time));
        //SID
        headers.put("m-sid", getSID());
        //协议版本
        headers.put("m-v", getV());
        //APP版本
        headers.put("m-av", "" + BaseApplication.VERSION_CODE);
        //时间
        headers.put("m-t", "" + time);
        //IMEI
        //headers.put("m-imei", ClientInfo.getInstance().imei);
        //API名称
        headers.put("m-api", getAPI());
        return headers;
    }
    
    private String getSign(long time) {
        StringBuilder builder = new StringBuilder();
        builder.append(getAPI());
        builder.append("&" + BaseApplication.VERSION_CODE);
        /*if (!TextUtils.isEmpty(ClientInfo.getInstance().imei)) {
            builder.append("&" + ClientInfo.getInstance().imei);
        }*/
        if (!TextUtils.isEmpty(getSID())) {
            builder.append("&" + getSID());
        }
        builder.append("&" + time);
        builder.append("&" + getV());
        builder.append("&" + getJsonData());
        String value = builder.toString();
        LogUtil.e(TAG,value);
        return SecurityUtils.sign(value, SecurityUtils.KEY);
    }
    private String getV() {
        return "1";
    }
    
    private String getSID() {
        return BaseApplication.getSid();
    }
    
    public abstract String getAPI();
}
