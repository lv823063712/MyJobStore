package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MySeachData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/21   20:08<p>
 * <p>更改时间：2019/2/21   20:08<p>
 * <p>版本号：1<p>
 */

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.ViewHolder> {
    private ArrayList<MySeachData.ResultBean> datas;
    private Context context;

    public MySearchAdapter(ArrayList<MySeachData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.search_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv_search_title.setText(datas.get(i).getCommodityName());
        viewHolder.tv_search_price.setText("¥" + datas.get(i).getPrice() + "");
        Glide.with(context).load(datas.get(i).getMasterPic()).into(viewHolder.iv_search_img);

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
        private final ImageView iv_search_img;
        private final TextView tv_search_title;
        private final TextView tv_search_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_search_img = itemView.findViewById(R.id.My_search_Img);
            tv_search_title = itemView.findViewById(R.id.My_search_title);
            tv_search_price = itemView.findViewById(R.id.My_search_Price);
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
