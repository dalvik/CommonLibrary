/**   
 * Copyright © 2016 浙江大华. All rights reserved.
 * 
 * @title: HttpPostJSONRequest.java
 * @description: TODO
 * @author: 23536   
 * @date: 2016年1月6日 上午10:24:20 
 */
package com.android.library.net.http;

import com.android.library.net.req.DataReq;
import com.android.library.net.utils.JSONUtil;

/** 
 * @description: TODO
 * @author: 23536
 * @date: 2016年1月6日 上午10:24:20  
 */
public abstract class HttpPostJSONRequest<T extends DataReq> extends HTTPPostRequest {

    private T data;

    public HttpPostJSONRequest(T t) {
        data = t;
    }
    
    public String getJsonData(){
        if (data == null) {
            return "{}";
        }
        return JSONUtil.obj2Json(data);
    }

    @Override
    public byte[] getData() {
        return getJsonData().getBytes();
    }

}
