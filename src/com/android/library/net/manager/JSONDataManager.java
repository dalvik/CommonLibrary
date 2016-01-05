/**   
 * Copyright © 2015 浙江大华. All rights reserved.
 * 
 * @title: JSONDataManager.java
 * @description: 基于JSON的数据请求
 * @author: 23536   
 * @date: 2015年12月23日 下午3:23:30 
 */
package com.android.library.net.manager;

import com.android.library.net.base.AbstractData;
import com.android.library.net.base.IDataCallback;
import com.android.library.net.base.JSONDataSource;
import com.android.library.net.req.DataReq;
import com.android.library.net.utils.JSONType;

/** 
 * @description: 基于JSON的数据请求
 * @author: 23536
 * @date: 2015年12月23日 下午3:23:30  
 */
public abstract class JSONDataManager<T extends AbstractData, K extends DataReq> extends AbstractDataManager<T> {

    protected DataManagerListener listener = new DataManagerListener();
    
    private JSONType<T> respType;
    
    public JSONDataManager(IDataCallback callback) {
        super(callback);
        respType = initRespType();
    }
    
    /**
     * 返回Json2Obj类型
     *
     * @return
     */
    protected JSONType<T> initRespType(){
        return null;
    }
    
    /**
     * 发送请求
     *
     * @param req
     * @return
     */
    protected int doRequest(String api, K req) {
        if(respType == null){
            throw new RuntimeException("method initRespType() must be overide");
        }
        JSONDataSource<T, K> source = new JSONDataSource<T, K>();
        source.setReqAndResp(api, req, respType);
        source.setListener(listener);
        source.doRequest();
        return source.getWhat();
    }
    
    /**
     * 发送请求
     *
     * @param req
     * @return
     */
    protected int doRequest(String api, K req, JSONType type) {
        JSONDataSource<T, K> source = new JSONDataSource<T, K>();
        source.setReqAndResp(api, req, type);
        source.setListener(listener);
        source.doRequest();
        return source.getWhat();
    }
}
