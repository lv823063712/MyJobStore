package soexample.bigfly.com.myjob_store.bean;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/3/2   8:58<p>
 * <p>更改时间：2019/3/2   8:58<p>
 * <p>版本号：1<p>
 */

public class FindFirstData {

    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
