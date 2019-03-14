package soexample.bigfly.com.myjob_store.mvp.moudle;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

import soexample.bigfly.com.myjob_store.mvp.mycallback.MyCallBack;
import soexample.bigfly.com.myjob_store.utils.RetrofitUtils;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/20   0:05<p>
 * <p>更改时间：2019/2/20   0:05<p>
 * <p>版本号：1<p>
 */

public class MyMoudleImpl implements MyMoudle {
    @Override
    public void startRequestGet(String url, Map<String, String> headerMap, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitUtils.getInstance().get(url, headerMap, map, new RetrofitUtils.setHttpListener() {
            @Override
            public void success(Object data) {
                Gson gson = new Gson();
                Object o = gson.fromJson((String) data,clazz);
                myCallBack.setData(o);
            }

            @Override
            public void error(Object error) {
                Log.e("zzz", "error: "+error );
                myCallBack.setError(error);

            }
        });
    }

    @Override
    public void startRequestPost(String url, Map<String, Object> headerMap, Map<String,String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitUtils.getInstance().post(url, headerMap, map,new RetrofitUtils.setHttpListener() {
            @Override
            public void success(Object data) {
                Gson gson = new Gson();
                Object o = gson.fromJson((String) data,clazz);
                Log.e("评论", "success: "+o );
                myCallBack.setData(o);
            }

            @Override
            public void error(Object error) {
                myCallBack.setError(error);
                Log.e("zzzm", "setError: "+error );
            }
        });
    }

    @Override
    public void startRequestPut(String url, Map<String, String> putMap, Map<String, String> dataMap, final Class clazz, final MyCallBack myCallBack) {
        RetrofitUtils.getInstance().put(url, putMap,dataMap, new RetrofitUtils.setHttpListener() {
            @Override
            public void success(Object data) {
                Gson gson = new Gson();
                Object o = gson.fromJson((String) data,clazz);
                myCallBack.setData(o);
            }

            @Override
            public void error(Object error) {
                myCallBack.setError(error);
            }
        });
    }

    @Override
    public void startRequestDelete(String url, Map<String, String> headerMap, Map<String, Object> deleteMap, final Class clazz, final MyCallBack myCallBack) {
        RetrofitUtils.getInstance().delete(url, headerMap, deleteMap, new RetrofitUtils.setHttpListener() {
            @Override
            public void success(Object data) {
                Gson gson = new Gson();
                Object o = gson.fromJson((String) data,clazz);
                myCallBack.setData(o);
            }

            @Override
            public void error(Object error) {
                myCallBack.setError(error);
            }
        });
    }

    @Override
    public void startRequestImagePost(String url, Map<String, String> headerMap, Map<String, Object> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitUtils.getInstance().postFileMore(url, headerMap, map, new RetrofitUtils.setHttpListener() {
            @Override
            public void success(Object data) {
                Gson gson = new Gson();
                Object o = gson.fromJson((String) data,clazz);
                myCallBack.setData(o);
            }

            @Override
            public void error(Object error) {
                Log.e("ccc1", "setError: "+error);
                myCallBack.setError(error);
            }
        });
    }

    @Override
    public void startRequestImage(String url, Map<String, String> headerMap, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitUtils.getInstance().imagePost(url, headerMap, map, new RetrofitUtils.setHttpListener() {
            @Override
            public void success(Object data) {
                Gson gson = new Gson();
                Object o = gson.fromJson((String) data,clazz);
                myCallBack.setData(o);
            }

            @Override
            public void error(Object error) {
                Log.e("ccc1", "setError: "+error);
                myCallBack.setError(error);
            }
        });
    }
}
