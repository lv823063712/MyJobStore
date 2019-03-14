package soexample.bigfly.com.myjob_store.mvp.ipresenter;

import android.util.Log;

import java.util.Map;

import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.mvp.moudle.MyMoudleImpl;
import soexample.bigfly.com.myjob_store.mvp.mycallback.MyCallBack;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/20   0:13<p>
 * <p>更改时间：2019/2/20   0:13<p>
 * <p>版本号：1<p>
 */

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private MyMoudleImpl myMoudle;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        myMoudle = new MyMoudleImpl();
    }

    @Override
    public void startRequestGet(String url, Map<String, String> headerMap, Map<String, String> map, Class clazz) {
        myMoudle.startRequestGet(url, headerMap, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                iView.success(data);
            }

            @Override
            public void setError(Object error) {
                iView.error(error);
            }
        });
    }

    @Override
    public void startRequestPost(String url, Map<String, Object> headerMap, Map<String, String> map, Class clazz) {
        myMoudle.startRequestPost(url, headerMap, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                iView.success(data);
            }

            @Override
            public void setError(Object error) {
                Log.e("zzz", "setError: "+error );
                iView.error(error);
            }
        });
    }

    @Override
    public void startRequestPut(String url, Map<String, String> putMap, Map<String, String> dataMap, Class clazz) {
        myMoudle.startRequestPut(url, putMap, dataMap, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                iView.success(data);
            }

            @Override
            public void setError(Object error) {
                iView.error(error);
            }
        });
    }

    @Override
    public void startRequesDelete(String url, Map<String, String> headMap, Map<String, Object> deleteMap, Class clazz) {
        myMoudle.startRequestDelete(url, headMap, deleteMap, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                iView.success(data);
            }

            @Override
            public void setError(Object error) {
                iView.error(error);
            }
        });
    }

    @Override
    public void startRequestImagePost(String url, Map<String, String> headerMap, Map<String, Object> map, Class clazz) {
        myMoudle.startRequestImagePost(url, headerMap, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                iView.success(data);
            }

            @Override
            public void setError(Object error) {
                Log.e("ccc", "setError: "+error);
                iView.error(error);
            }
        });
    }

    @Override
    public void startRequestImage(String url, Map<String, String> headerMap, Map<String, String> map, Class clazz) {
        myMoudle.startRequestImage(url, headerMap, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                iView.success(data);
            }

            @Override
            public void setError(Object error) {
                Log.e("ccc", "setError: "+error);
                iView.error(error);
            }
        });
    }

    //防止内存泄漏
    public void onDatach() {
        if (myMoudle != null) {
            myMoudle = null;
        }
        if (iView != null) {
            iView = null;
        }
    }

}
