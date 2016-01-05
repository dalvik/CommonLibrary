/**   
 * Copyright © 2016 浙江大华. All rights reserved.
 * 
 * @title: WebServiceRequest.java
 * @author: 23536   
 * @date: 2016年1月5日 下午3:10:26 
 */
package com.android.library.net.webservice;

import com.android.library.net.req.BaseReqest;
import com.android.library.net.req.DataReq;

/** 
 * @description: 基于webservice的网络请求
 * @author: 23536
 * @date: 2016年1月5日 下午3:10:26  
 */
public abstract class WebServiceRequest<T extends DataReq> implements BaseReqest {

    @Override
    public String getUrl() {
        return WebServiceConfig.SERVICE_URL;
    }
    
    @Override
    public String getNamespace() {
        return WebServiceConfig.SERVICE_NAMESPACE;
    }

}
