package soexample.bigfly.com.myjob_store.utils.apiservice;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/18   20:49<p>
 * <p>更改时间：2019/2/18   20:49<p>
 * <p>版本号：1<p>
 */

public interface ApiService {
    @GET
    Observable<ResponseBody> get(@Url String url, @HeaderMap Map<String, String> headMap, @QueryMap Map<String, String> map);

    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String, Object> headMap,@HeaderMap Map<String, String> map);

    @PUT
    Observable<ResponseBody> put(@Url String url, @HeaderMap Map<String, String> headMap,@QueryMap Map<String,String> map);

    @DELETE
    Observable<ResponseBody> delete(@Url String url, @HeaderMap Map<String, String> headMap,@QueryMap Map<String,Object> map);

    @POST
    Observable<ResponseBody> imagePost(@Url String url, @HeaderMap Map<String, String> headMap, @Body MultipartBody multipartBody);
}
