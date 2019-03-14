package soexample.bigfly.com.myjob_store.mvp.ipresenter;

import java.util.Map;

import soexample.bigfly.com.myjob_store.mvp.mycallback.MyCallBack;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/20   0:12<p>
 * <p>更改时间：2019/2/20   0:12<p>
 * <p>版本号：1<p>
 */

public interface IPresenter {
    //get方法
    void startRequestGet(String url, Map<String, String> headerMap, Map<String, String> map, Class clazz);
    //post方法
    void startRequestPost(String url, Map<String, Object> headerMap, Map<String, String> map, Class clazz);
    //put方法
    void startRequestPut(String url, Map<String, String> putMap, Map<String, String> dataMap, Class clazz);
    //delete方法
    void startRequesDelete(String url, Map<String, String> headMap, Map<String, Object> deleteMap, Class clazz);
    //imagepost方法
    void startRequestImagePost(String url, Map<String, String> headerMap, Map<String, Object> map, Class clazz);
    void startRequestImage(String url, Map<String, String> headerMap, Map<String, String> map, Class clazz);
}