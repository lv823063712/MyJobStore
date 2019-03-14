package soexample.bigfly.com.myjob_store.bean;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/22   15:07<p>
 * <p>更改时间：2019/2/22   15:07<p>
 * <p>版本号：1<p>
 */

public class NewAddressData {
    /**
     * message : 你还没有收货地址，快去添加吧
     * status : 0000
     */

    private String message;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
