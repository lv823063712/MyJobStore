package soexample.bigfly.com.myjob_store.bean;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/21   0:03<p>
 * <p>更改时间：2019/2/21   0:03<p>
 * <p>版本号：1<p>
 */

public class MyCircleData {
    /**
     * result : [{"commodityId":1,"content":"哒哒哒哒哒哒哒哒","createTime":1546556451000,"greatNum":53,"headPic":"http://172.17.8.100/images/small/head_pic/2019-01-04/20190104093616.jpg","id":239,"image":"http://172.17.8.100/images/small/circle_pic/2019-01-03/6827420190103170051.jfif","nickName":"几块钱","userId":70,"whetherGreat":2},{"commodityId":1,"content":"只是无奈这些问题无人交流","createTime":1546031563000,"greatNum":56,"headPic":"http://172.17.8.100/images/small/head_pic/2018-12-14/20181214202231.jpg","id":238,"image":"http://172.17.8.100/images/small/circle_pic/2018-12-28/7861220181228151243.jpg","nickName":"后来呢","userId":17,"whetherGreat":2},{"commodityId":1,"content":"zyz","createTime":1545788982000,"greatNum":23,"headPic":"http://172.17.8.100/images/small/default/user.jpg","id":237,"image":"http://172.17.8.100/images/small/circle_pic/2018-12-25/1039420181225194942.png","nickName":"fy_Nt8h8","userId":91,"whetherGreat":2},{"commodityId":7,"content":"","createTime":1545341013000,"greatNum":16,"headPic":"http://172.17.8.100/images/small/head_pic/2018-12-18/20181218160753.jpg","id":236,"image":"http://172.17.8.100/images/small/circle_pic/2018-12-20/8436520181220152333.jpg","nickName":"少年闰土","userId":186,"whetherGreat":2},{"commodityId":6,"content":"要答辩了，超不爽","createTime":1545234528000,"greatNum":50,"headPic":"http://172.17.8.100/images/small/head_pic/2018-12-18/20181218160753.jpg","id":235,"image":"http://172.17.8.100/images/small/circle_pic/2018-12-19/9076320181219094848.jpg","nickName":"少年闰土","userId":186,"whetherGreat":2},{"commodityId":4,"content":"吼吼吼","createTime":1545234310000,"greatNum":11,"headPic":"http://172.17.8.100/images/small/head_pic/2018-12-18/20181218160753.jpg","id":234,"image":"http://172.17.8.100/images/small/circle_pic/2018-12-19/9780320181219094510.gif","nickName":"少年闰土","userId":186,"whetherGreat":2}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;
    private final String SUCCESS_STATUS="0000";
    public boolean isSuccess(){
        return status.equals(SUCCESS_STATUS);
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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * commodityId : 1
         * content : 哒哒哒哒哒哒哒哒
         * createTime : 1546556451000
         * greatNum : 53
         * headPic : http://172.17.8.100/images/small/head_pic/2019-01-04/20190104093616.jpg
         * id : 239
         * image : http://172.17.8.100/images/small/circle_pic/2019-01-03/6827420190103170051.jfif
         * nickName : 几块钱
         * userId : 70
         * whetherGreat : 2
         */

        private int commodityId;
        private String content;
        private long createTime;
        private int greatNum;
        private String headPic;
        private int id;
        private String image;
        private String nickName;
        private int userId;
        private int whetherGreat;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getWhetherGreat() {
            return whetherGreat;
        }

        public void setWhetherGreat(int whetherGreat) {
            this.whetherGreat = whetherGreat;
        }

    }
}
