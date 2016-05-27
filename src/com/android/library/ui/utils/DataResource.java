package com.android.library.ui.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.android.library.net.base.IDataCallback;

public class DataResource implements IDataCallback {

    private static DataResource instance;

    private static China china;

    private static ArrayList<Object> contacts;
    private static HashMap<String, Object> mobilesMap = new HashMap();
    private static HashMap<Long, Object> idsMap = new HashMap();
    //
   // private ContactManager contactManager;
    private int whatContact;

    private DataResource() {
        //contactManager = new ContactManager(this);
    }

    public static DataResource getInstance() {
        if (instance == null) {
            instance = new DataResource();
            instance.init();
        }
        return instance;
    }

    private void init() {
        //初始化城市信息
        /*WorkThreadExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                initCities();
            }
        });*/
    }

    private void initCities() {
        /*china = FileUtil.loadAssetsFile("cities", new InputStreamParser<China>() {
            @Override
            public China parser(InputStream inputStream) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int len = -1;
                byte[] b = new byte[1024 * 8];
                try {
                    while ((len = inputStream.read(b)) != -1) {
                        bos.write(b, 0, len);
                    }
                    return JSONUtil.json2Obj(bos.toString(), China.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });*/
    }

    public static JSONObject  initAppraiseList() {
       /*return FileUtil.loadAssetsFile("cities", new InputStreamParser<JSONObject>() {
            @Override
            public JSONObject parser(InputStream inputStream) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int len = -1;
                byte[] b = new byte[1024 * 8];
                try {
                    while ((len = inputStream.read(b)) != -1) {
                        bos.write(b, 0, len);
                    }

                    return new JSONObject(new String(bos.toByteArray()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });*/
        return null;
    }

    public China getChina() {
        if (china == null) {
            initCities();
        }
        return china;
    }

    public void setContacts(ArrayList<Object> contacts) {
        /*if (contacts != null) {
            DataResource.contacts = contacts;
            for (ContactResp friend : contacts) {
                mobilesMap.put(friend.mobile, friend);
                idsMap.put(friend.userId, friend);
            }
        }
        ContactDAO.getInstance().deleteAll();
        ContactDAO.getInstance().inster(contacts);*/
    }

    /**
     * 明文查询
     *
     * @param mobile
     * @return
     */
    public boolean isContact(String mobile) {
        return true;//mobilesMap.containsKey(SecurityUtils.encodeByDES(mobile, AppApplication.getSid()));
    }

    public boolean isContactById(long id) {
        return true;//idsMap.containsKey(id);
    }

    public boolean isContactByDES(String mobile) {
        return true;//mobilesMap.containsKey(mobile);
    }

    public void getContactList() {
        //whatContact = contactManager.getFriendList();
    }

    @Override
    public void onCallback(int what, int result, int arg2, Object obj) {
        /*if (result != AbstractDataManager.RESULT_SUCCESS) {
            return;
        }
        if (what == whatContact) {
            BaseObjectData<ArrayList<ContactResp>> data = (BaseObjectData<ArrayList<ContactResp>>) obj;
            setContacts(data.data);
        }*/
    }

}
