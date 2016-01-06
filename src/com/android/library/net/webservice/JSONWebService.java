/**   
 * Copyright © 2015 浙江大华. All rights reserved.
 * 
 * @title: JSONDataSource.java
 * @description: TODO
 * @author: 23536   
 * @date: 2015年12月23日 下午4:07:00 
 */
package com.android.library.net.webservice;

import com.android.library.net.base.AbstractWebServiceRequest;
import com.android.library.net.base.DataStruct;
import com.android.library.net.req.DataReq;
import com.android.library.net.utils.JSONType;
import com.android.library.net.utils.JSONUtil;


/** 
 * @description:
 * @author: 23536
 * @date: 2015年12月23日 下午4:07:00  
 */
public class JSONWebService<T extends DataStruct, K extends DataReq> extends AbstractWebServiceRequest<T, WebServiceRequest<K>> {

    private JSONType<T> mDataType;
    private K mReqBean;
    private String mMethod;
    
    public void setReqAndResp(String method, K req, JSONType<T> resp) {
        mMethod = method;
        mReqBean = req;
        mDataType = resp;
    }

    
    @Override
    protected WebServiceRequest<K> getRequest() {
        JSONWebServiceRequest<K> req = new JSONWebServiceRequest<K>(mReqBean){
            @Override
            public String getMethod() {
                return mMethod;
            }
        };
        return req;
    }
    
    /**
     * parse to resp
     */
    @Override
    protected T parseResp(String jsonContent) {
        return JSONUtil.json2Obj(jsonContent, mDataType);
    }
}