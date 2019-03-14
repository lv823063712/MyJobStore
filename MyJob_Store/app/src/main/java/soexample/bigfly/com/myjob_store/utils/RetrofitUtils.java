package soexample.bigfly.com.myjob_store.utils;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import soexample.bigfly.com.myjob_store.utils.apiservice.ApiService;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/19   23:24<p>
 * <p>更改时间：2019/2/19   23:24<p>
 * <p>版本号：1<p>
 */

public class RetrofitUtils {

    private final ApiService apiService;

    private RetrofitUtils() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    //静态内部类
    public static RetrofitUtils getInstance() {
        return ViewHolder.RETROFIT_UTILS;
    }

    private static class ViewHolder {
        private static final RetrofitUtils RETROFIT_UTILS = new RetrofitUtils();
    }

    //封装get
    public void get(String url, Map<String, String> headerMap, Map<String, String> map, final setHttpListener httpListener) {
        apiService.get(url, headerMap, map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted", "get_onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", "get_onError" + e.getMessage());
                        if (httpListener != null) {
                            httpListener.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        if (httpListener != null) {
                            try {
                                httpListener.success(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    /**
     *上传头像
     * */
    public static MultipartBody filesMultipar(Map<String,String> map){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for(Map.Entry<String,String> entry:map.entrySet()){
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(),"tp.png",
                    RequestBody.create(MediaType.parse("multipart/form-data"),file));
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }
    public void imagePost(String url, Map<String,String> headMap , Map<String,String> map, final setHttpListener httpListener){
        if(map == null){
            map = new HashMap<>();
        }
        MultipartBody multipartBody = filesMultipar(map);
        apiService.imagePost(url,headMap,multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", "get_onError" + e.getMessage());
                        if (httpListener != null) {
                            httpListener.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (httpListener != null) {
                            try {
                                httpListener.success(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    /**
     * 上传多张图片
     */
    public void postFileMore(String url, Map<String, String> headMap , Map<String, Object> map, final setHttpListener httpListener) {
        if (map == null) {
            map = new HashMap<>();
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("commodityId", String.valueOf(map.get("commodityId")));
        if(!String.valueOf(map.get("orderId")).equals("")){
            builder.addFormDataPart("orderId", String.valueOf(map.get("orderId")));
        }
        builder.addFormDataPart("content", String.valueOf(map.get("content")));
        if (!map.get("image").equals("")) {
            List<String> image = (List<String>) map.get("image");
            for(int i=0;i<image.size();i++){
                File file = new File(image.get(i));
                builder.addFormDataPart("image", file.getName(),RequestBody.create(MediaType.parse("multipart/form-data"),file));
            }

        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        apiService.imagePost(url,headMap, multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", "get_onError" + e.getMessage());
                        if (httpListener != null) {
                            httpListener.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (httpListener != null) {
                            try {
                                httpListener.success(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    //封装post
    public void post(String url, Map<String, Object> headerMap, Map<String, String> map, final setHttpListener httpListener) {
        apiService.post(url, headerMap, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted", "post_onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", "get_onError" + e.getMessage());
                        if (httpListener != null) {
                            httpListener.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (httpListener != null) {
                            try {
                                httpListener.success(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    //封装put
    public void put(String url, Map<String, String> putMap, Map<String, String> dataMap, final setHttpListener httpListener) {
        apiService.put(url, putMap, dataMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("put失败", "get_onError" + e.getMessage());
                        if (httpListener != null) {
                            httpListener.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (httpListener != null) {
                            try {
                                httpListener.success(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    //封装delete方法
    public void delete(String url, Map<String, String> putMap, Map<String, Object> deleteMap, final setHttpListener httpListener) {
        apiService.delete(url, putMap,deleteMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("delete失败", "get_onError" + e.getMessage());
                        if (httpListener != null) {
                            httpListener.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (httpListener != null) {
                            try {
                                httpListener.success(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    //接口回调
    public interface setHttpListener<T> {
        void success(T data);

        void error(T error);
    }

    private setHttpListener httpListener;
}
