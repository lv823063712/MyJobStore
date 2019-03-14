package soexample.bigfly.com.myjob_store.mvp.iview;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/18   20:45<p>
 * <p>更改时间：2019/2/18   20:45<p>
 * <p>版本号：1<p>
 */

public interface IView<T> {
    void success(T data);
    void error(T error);
}
