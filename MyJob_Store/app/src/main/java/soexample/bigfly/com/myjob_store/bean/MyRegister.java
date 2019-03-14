package soexample.bigfly.com.myjob_store.bean;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/20   20:27<p>
 * <p>更改时间：2019/2/20   20:27<p>
 * <p>版本号：1<p>
 */

public class MyRegister {
    /**
     * message : 注册成功
     * status : 0000
     */

    private String message;
    private String status;
    private String SUCCESS_PARAMETER="0000";
    public boolean isSuccess(){
        return status.equals(SUCCESS_PARAMETER);
    }
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
