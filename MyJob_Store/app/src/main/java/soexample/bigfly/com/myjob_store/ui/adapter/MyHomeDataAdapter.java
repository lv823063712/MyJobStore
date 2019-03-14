package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyHotData;
import soexample.bigfly.com.myjob_store.ui.activity.DetailsActivity;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/20   22:51<p>
 * <p>更改时间：2019/2/20   22:51<p>
 * <p>版本号：1<p>
 */

public class MyHomeDataAdapter extends RecyclerView.Adapter<MyHomeDataAdapter.ViewHolder> {

    private List<MyHotData.ResultBean> datas;
    private Context context;
    private List<MyHotData.ResultBean.RxxpBean.CommodityListBean> lists_r = new ArrayList<>();
    private List<MyHotData.ResultBean.MlssBean.CommodityListBeanXX> lists_m = new ArrayList<>();
    private List<MyHotData.ResultBean.PzshBean.CommodityListBeanX> lists_p = new ArrayList<>();
    private MyHotAdapter myHotAdapter;
    private MyMLSSAdapter myMLSSAdapter;
    private MyPZSHAdapter myPZSHAdapter;
    public MyHomeDataAdapter(List<MyHotData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_home_data_adapter_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.TitleR.setText(datas.get(i).getRxxp().getName());
        viewHolder.TitleM.setText(datas.get(i).getMlss().getName());
        viewHolder.TitleP.setText(datas.get(i).getPzsh().getName());
        //数据管理显示
        GridLayoutManager manager = new GridLayoutManager(context, 3);
        viewHolder.MyHotSale.setLayoutManager(manager);

        LinearLayoutManager manager_m = new LinearLayoutManager(context);
        viewHolder.MyMagicVogue.setLayoutManager(manager_m);

        GridLayoutManager manager_p = new GridLayoutManager(context, 2);
        viewHolder.MyQualtyLife.setLayoutManager(manager_p);
        lists_r.addAll(datas.get(i).getRxxp().getCommodityList());
        lists_m.addAll(datas.get(i).getMlss().getCommodityList());
        lists_p.addAll(datas.get(i).getPzsh().getCommodityList());
        myHotAdapter = new MyHotAdapter(lists_r, context);
        viewHolder.MyHotSale.setAdapter(myHotAdapter);
        myMLSSAdapter = new MyMLSSAdapter(lists_m, context);
        viewHolder.MyMagicVogue.setAdapter(myMLSSAdapter);
        myPZSHAdapter = new MyPZSHAdapter(lists_p, context);
        viewHolder.MyQualtyLife.setAdapter(myPZSHAdapter);
        //热销商品的点击事件
        myHotAdapter.setClickCallBack(new MyHotAdapter.ClickCallBack() {
            @Override
            public void callBack(int i) {

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("zhi", i);
                context.startActivity(intent);
            }
        });
        //魔丽时尚的点击事件
        myMLSSAdapter.setClickCallBack(new MyMLSSAdapter.ClickCallBack() {
            @Override
            public void callBack(int i) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("zhi", i);
                context.startActivity(intent);
            }
        });
        //品质生活的点击事件
        myPZSHAdapter.setClickCallBack(new MyPZSHAdapter.ClickCallBack() {
            @Override
            public void callBack(int i) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("zhi", i);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Title_r)
        TextView TitleR;
        @BindView(R.id.My_hot_sale)
        RecyclerView MyHotSale;
        @BindView(R.id.Title_m)
        TextView TitleM;
        @BindView(R.id.My_magic_vogue)
        RecyclerView MyMagicVogue;
        @BindView(R.id.Title_p)
        TextView TitleP;
        @BindView(R.id.My_qualty_life)
        RecyclerView MyQualtyLife;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleR = itemView.findViewById(R.id.Title_r);
            MyHotSale = itemView.findViewById(R.id.My_hot_sale);
            TitleM = itemView.findViewById(R.id.Title_m);
            MyMagicVogue = itemView.findViewById(R.id.My_magic_vogue);
            TitleP = itemView.findViewById(R.id.Title_p);
            MyQualtyLife = itemView.findViewById(R.id.My_qualty_life);
        }
    }
}
