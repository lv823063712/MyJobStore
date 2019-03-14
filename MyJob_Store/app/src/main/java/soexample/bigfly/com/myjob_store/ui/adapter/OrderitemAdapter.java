package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.SelectData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/23   10:43<p>
 * <p>更改时间：2019/2/23   10:43<p>
 * <p>版本号：1<p>
 */

public class OrderitemAdapter extends RecyclerView.Adapter<OrderitemAdapter.ViewHolder> {
    private int type;
    private Context context;
    private List<SelectData.OrderListBean.DetailListBean> list;
    private String[] split;

    public OrderitemAdapter(Context context, List<SelectData.OrderListBean.DetailListBean> list,int type) {
        this.context = context;
        this.list = list;
        this.type=type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.oreder_recycle_item_recycle, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.order_item_name.setText(list.get(i).getCommodityName());
        viewHolder.order_item_price.setText("￥"+list.get(i).getCommodityPrice()+"");
        viewHolder.order_item_shu.setText(list.get(i).getCommodityCount()+"");
        String picture =list.get(i).getCommodityPic();
        if (picture != null) {
            // \\是转义字符
            split = picture.split("\\,");
            Glide.with(context).load(split[0]).into(viewHolder.oreder_item_img);
        }
        switch (type){
            case 1:
//                viewHolder.order_item_number.setVisibility(View.VISIBLE);
                break;
            case 3:
                if(list.get(i).getCommentStatus()==1){
                    viewHolder.order_item_pingjia.setVisibility(View.VISIBLE);
                }
                break;
            case 9:
                break;
        }
        viewHolder.order_item_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOnclick!=null){
                    getOnclick.pingjia(split[0],list.get(i).getCommodityName(),list.get(i).getCommodityId()+"",list.get(i).getCommodityPrice()+"");
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface  getOnclick{
        void pingjia(String headerpic,String name,String id,String price);
    }

    private getOnclick getOnclick;
    public void onclick(getOnclick getOnclick){
        this.getOnclick =getOnclick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView oreder_item_img;
        public TextView order_item_name;
        public TextView order_item_price;
        public ImageView order_item_jian;
        public TextView order_item_shu;
        public ImageView order_item_jia;
        public LinearLayout order_item_number;
        public Button order_item_pingjia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.oreder_item_img = (ImageView) itemView.findViewById(R.id.oreder_item_img);
            this.order_item_name = (TextView) itemView.findViewById(R.id.order_item_name);
            this.order_item_price = (TextView) itemView.findViewById(R.id.order_item_price);
            this.order_item_jian = (ImageView) itemView.findViewById(R.id.order_item_jian);
            this.order_item_shu = (TextView) itemView.findViewById(R.id.order_item_shu);
            this.order_item_jia = (ImageView) itemView.findViewById(R.id.order_item_jia);
            this.order_item_number = (LinearLayout) itemView.findViewById(R.id.order_item_number);
            this.order_item_pingjia = (Button) itemView.findViewById(R.id.order_item_pingjia);
        }
    }
}
