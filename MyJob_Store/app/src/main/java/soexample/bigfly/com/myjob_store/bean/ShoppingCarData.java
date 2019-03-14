package soexample.bigfly.com.myjob_store.bean;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/21   13:46<p>
 * <p>更改时间：2019/2/21   13:46<p>
 * <p>版本号：1<p>
 */

public class ShoppingCarData {
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
        /**
         * commodityId : 27
         * commodityName : 休闲马衔扣保暖绒里棉鞋懒人鞋毛毛鞋平底女雪地靴女短靴子豆豆鞋女鞋
         * count : 1
         * pic : http://172.17.8.100/images/small/commodity/nx/ddx/3/1.jpg
         * price : 139
         */

        private int commodityId;
        private String commodityName;
        private int count;
        private String pic;
        private int price;
        private boolean isChildChecked;

        public boolean isChildChecked() {
            return isChildChecked;
        }

        public void setChildChecked(boolean childChecked) {
            isChildChecked = childChecked;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
