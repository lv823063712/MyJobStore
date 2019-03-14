package soexample.bigfly.com.myjob_store.bean;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/22   14:26<p>
 * <p>更改时间：2019/2/22   14:26<p>
 * <p>版本号：1<p>
 */

public class CreateOrderData {
    /**
     * orderId : 20190113191425585809
     * message : 创建订单成功
     * status : 0000
     */

    private String orderId;
    private String message;
    private String status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
