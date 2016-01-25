package com.android.library.net.webservice;

/**
 * @description: webservice的配置文件
 * @author: 23536
 * @date: 2016年1月5日 下午2:17:22
 */
public class WebServiceConfig {
    /*
     * 定义webservice的命名空间
     */
    public static final String SERVICE_NAMESPACE = "http://webservice.dhsoft.com";
    /*
     * 定义webservice提供服务的url
     */
    public static final String SERVICE_URL = "http://172.7.50.170/admin/services/AdminWebService?wsdl";//?wsdl "http://10.33.5.56/admin/services/patrolService";//
   
    public static final String SERVICE_DEFAULT_UPLAOD_TYPE = "ftp";
    
    private static WebServiceConfig mWebService = null;
    
    private int code = -1;
    
    private String message;
    
    private WebServiceConfig(){
    }
    
    public static WebServiceConfig getInstance(){
        if(mWebService == null){
            mWebService = new WebServiceConfig();
        }
        return mWebService;
    }
    
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
