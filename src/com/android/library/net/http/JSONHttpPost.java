/**   
 * Copyright © 2015 浙江大华. All rights reserved.
 * 
 * @title: JSONDataSource.java
 * @description: TODO
 * @author: 23536   
 * @date: 2015年12月23日 下午4:07:00 
 */
package com.android.library.net.http;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.android.library.net.base.DataStruct;
import com.android.library.net.req.DataReq;
import com.android.library.net.utils.JSONType;
import com.android.library.net.utils.JSONUtil;


/** 
 * @description:
 * @author: 23536
 * @date: 2015年12月23日 下午4:07:00  
 */
public class JSONHttpPost<T extends DataStruct, K extends DataReq> extends AbstractHttpRequest<T, HttpPostJSONRequest<K>> {

    private JSONType<T> mDataType;
    private K mReqBean;
    private String mUrl;
    private String mMethod;
    
    public void setReqAndResp(String url, String method, K req, JSONType<T> resp) {
        this.mUrl = url;
        mMethod = method;
        mReqBean = req;
        mDataType = resp;
    }

    
    @Override
    protected HttpPostJSONRequest<K> getRequest() {
        JSONHttpRequest<K> req = new JSONHttpRequest<K>(mReqBean){
            @Override
            public String getAPI() {
                return mMethod;
            }

            @Override
            public String getUrl() {
                return mUrl;
            }
            
            @Override
            public byte[] getData() {
                Map<String, Object> map = JSONUtil.json2Map(JSONUtil.obj2Json(mReqBean));
                Set<Entry<String, Object>> set = map.entrySet();
                StringBuffer sb = new StringBuffer();
                for(Entry<String, Object> s:set){
                    sb.append(s.getKey()+"="+s.getValue()+"&");
                }
                return sb.subSequence(0, sb.toString().lastIndexOf("&")).toString().getBytes();
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
