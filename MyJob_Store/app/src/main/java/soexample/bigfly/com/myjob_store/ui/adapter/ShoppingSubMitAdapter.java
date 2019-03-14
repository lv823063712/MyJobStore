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
import soexample.bigfly.com.myjob_store.bean.ShoppingCarData;
import soexample.bigfly.com.myjob_store.utils.user_defined_view.AddAndSubtractView;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/22   14:02<p>
 * <p>更改时间：2019/2/22   14:02<p>
 * <p>版本号：1<p>
 */

public class ShoppingSubMitAdapter extends RecyclerView.Adapter<ShoppingSubMitAdapter.ViewHolder> {

    private ArrayList<ShoppingCarData.ResultBean> datas;
    private Context context;
    private View inflate;
    private ArrayList<ShoppingCarData> lists;


    public ShoppingSubMitAdapter(ArrayList<ShoppingCarData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        inflate = LayoutInflater.from(context).inflate(R.layout.shoppingsubmit_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Glide.with(context).load(datas.get(i).getPic()).into(viewHolder.ShoppingCar_head2);
        viewHolder.ShoppingCar_title2.setText(datas.get(i).getCommodityName());
        viewHolder.ShoppingCar_price2.setText("¥" + datas.get(i).getPrice());
        viewHolder.Myjiajian2.setNumber(datas.get(i).getCount());
        //对自定义的控件进行监听
        viewHolder.Myjiajian2.setOnChange(new AddAndSubtractView.onCountChange() {
            @Override
            public void setCount(int count) {
                if (shoppingCallBack != null) {
                    shoppingCallBack.setNumber(count);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ShoppingCar_head2;
        private TextView ShoppingCar_title2;
        private TextView ShoppingCar_price2;
        private AddAndSubtractView Myjiajian2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ShoppingCar_head2 = itemView.findViewById(R.id.ShoppingCar_head2);
            ShoppingCar_title2 = itemView.findViewById(R.id.ShoppingCar_title2);
            ShoppingCar_price2 = itemView.findViewById(R.id.ShoppingCar_price2);
            Myjiajian2 = itemView.findViewById(R.id.Myjiajian2);
        }
    }

    public String getGoodsID() {
        String Id = "";
        for (int i = 0; i < datas.size(); i++) {
            boolean childChecked = datas.get(i).isChildChecked();
            if (childChecked) {
                int commodityId = datas.get(i).getCommodityId();
                Id += commodityId + ",";
            }

        }
        return Id;
    }

    //查看当前这个商品有没有被选中
    public boolean isChildChecked(int childPosition) {
        if (!datas.get(childPosition).isChildChecked()) {

            return false;
        }
        datas.get(childPosition).getCommodityId();
        return true;
    }

    //给布尔值进行判断
    public void setChildCheck(int childPosition, boolean isChecked) {
        datas.get(childPosition).setChildChecked(isChecked);
    }

    //全选/全不选
    public void checkedAll(boolean boo) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setChildChecked(boo);
        }
    }

    //
    public boolean checkedNoAll() {
        for (int i = 0; i < datas.size(); i++) {
            if (!datas.get(i).isChildChecked()) {
                return false;
            }
        }
        return true;
    }

    //计算商品价格
    public float setShoppingNumber() {
        float price = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).isChildChecked()) {
                price += datas.get(i).getPrice();
            }
        }
        return price;
    }

    //因为点击Group和Child第CheckBox在主页面都需要刷新值所以做成接口回调
    public interface ShoppingCallBack {
        void setChildCheck(int childPosition);

        void setNumber(int num);
    }

    private ShoppingCallBack shoppingCallBack;

    //定义一个回调接口
    public void setCallBack(ShoppingCallBack callBack) {
        this.shoppingCallBack = callBack;

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
