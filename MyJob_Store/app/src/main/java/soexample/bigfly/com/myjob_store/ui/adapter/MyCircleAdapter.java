package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ldoublem.thumbUplib.ThumbUpView;

import java.text.SimpleDateFormat;
import java.util.List;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyCircleData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/21   0:03<p>
 * <p>更改时间：2019/2/21   0:03<p>
 * <p>版本号：1<p>
 */

public class MyCircleAdapter extends RecyclerView.Adapter<MyCircleAdapter.ViewHolder> {
    private List<MyCircleData.ResultBean> datas;
    private Context context;


    public MyCircleAdapter(List<MyCircleData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.circle_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.My_User_Name.setText(datas.get(i).getNickName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(datas.get(i).getCreateTime());
        viewHolder.My_Time.setText(format + "");
        viewHolder.My_Comment.setText(datas.get(i).getContent());
        Glide.with(context).load(datas.get(i).getHeadPic()).into(viewHolder.My_User_Photo);
        Glide.with(context).load(datas.get(i).getImage()).into(viewHolder.img1);

        //设置赞
        viewHolder.My_Like_Num.setText(datas.get(i).getGreatNum() + "");
        viewHolder.My_Like.setUnLikeType(ThumbUpView.LikeType.broken);
        viewHolder.My_Like.setCracksColor(Color.rgb(22, 33, 44));
        viewHolder.My_Like.setFillColor(Color.rgb(11, 200, 77));
        viewHolder.My_Like.setEdgeColor(Color.rgb(33, 3, 219));
        viewHolder.My_Like.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (clickCallBack != null) {
                    clickCallBack.callBack(datas.get(i).getWhetherGreat(), i, datas.get(i).getId());
                }
            }
        });
    }

    //点赞的方法
    public void getGivePraise(int position) {
        datas.get(position).setWhetherGreat(1);
        datas.get(position).setGreatNum(datas.get(position).getGreatNum() + 1);
        notifyDataSetChanged();
    }

    //取消赞的方法
    public void getCancelPraise(int position) {
        datas.get(position).setWhetherGreat(2);
        datas.get(position).setGreatNum(datas.get(position).getGreatNum() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView My_User_Photo;
        private TextView My_User_Name;
        private TextView My_Time;
        private TextView My_Comment;
        private ImageView img1;
        private ImageView img2;
        private ThumbUpView My_Like;
        private TextView My_Like_Num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            My_User_Photo = itemView.findViewById(R.id.My_User_Photo);
            My_User_Name = itemView.findViewById(R.id.My_User_Name);
            My_Time = itemView.findViewById(R.id.My_Time);
            My_Comment = itemView.findViewById(R.id.My_Comment);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            My_Like = itemView.findViewById(R.id.My_Like);
            My_Like_Num = itemView.findViewById(R.id.My_Like_Num);
        }
    }


    //定义接口
    private ClickCallBack clickCallBack;

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ClickCallBack {
        void callBack(int i, int position, int id);
    }
}
