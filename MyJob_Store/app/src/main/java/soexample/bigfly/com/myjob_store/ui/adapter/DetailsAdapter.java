package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.GoodsParticularsData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/21   16:56<p>
 * <p>更改时间：2019/2/21   16:56<p>
 * <p>版本号：1<p>
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private List<GoodsParticularsData.ResultBean> datas;
    private Context context;
    private View inflate;


    public DetailsAdapter(List<GoodsParticularsData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        inflate = LayoutInflater.from(context).inflate(R.layout.details_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.My_Details_Goods_Price.setText("¥" + datas.get(i).getPrice());
        viewHolder.My_Details_Goods_Sold.setText("已售出" + datas.get(i).getStock() + "件");
        viewHolder.My_Goods_Describe.setText(datas.get(i).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView My_Details_Goods_Price;
        private TextView My_Details_Goods_Sold;
        private TextView My_Goods_Describe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            My_Details_Goods_Price = itemView.findViewById(R.id.My_Details_Goods_Price);
            My_Details_Goods_Sold = itemView.findViewById(R.id.My_Details_Goods_Sold);
            My_Goods_Describe = itemView.findViewById(R.id.My_Goods_Describe);
        }
    }
}
