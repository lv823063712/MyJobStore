package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyFootprintData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/25   18:59<p>
 * <p>更改时间：2019/2/25   18:59<p>
 * <p>版本号：1<p>
 */

public class MyFootAdapter extends RecyclerView.Adapter<MyFootAdapter.ViewHolder> {
    private ArrayList<MyFootprintData.ResultBean> datas;
    private Context context;


    public MyFootAdapter(ArrayList<MyFootprintData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.foot_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.MyFootName.setText(datas.get(i).getCommodityName());
        viewHolder.MyFootPrice.setText("¥"+datas.get(i).getPrice());
        viewHolder.MyFootLook.setText("已浏览"+datas.get(i).getBrowseNum()+"次");
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(datas.get(i).getBrowseTime()));
        viewHolder.MyFootDate.setText(date);
        String masterPic = datas.get(i).getMasterPic();
        Uri parse = Uri.parse(masterPic);
        viewHolder.MyFootImg.setImageURI(parse);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallBack != null) {
                    clickCallBack.callBack(datas.get(i).getCommodityId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView MyFootImg;
        private TextView MyFootName;
        private TextView MyFootPrice;
        private TextView MyFootLook;
        private TextView MyFootDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MyFootImg = itemView.findViewById(R.id.MyFootImg);
            MyFootName = itemView.findViewById(R.id.MyFootName);
            MyFootPrice = itemView.findViewById(R.id.MyFootPrice);
            MyFootLook = itemView.findViewById(R.id.MyFootLook);
            MyFootDate = itemView.findViewById(R.id.MyFootDate);

        }
    }

    //定义接口
    private ClickCallBack clickCallBack;

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ClickCallBack {
        void callBack(int i);
    }
}
