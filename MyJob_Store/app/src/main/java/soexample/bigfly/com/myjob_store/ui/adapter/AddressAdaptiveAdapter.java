package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.AddressShowData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/22   14:15<p>
 * <p>更改时间：2019/2/22   14:15<p>
 * <p>版本号：1<p>
 */

public class AddressAdaptiveAdapter extends RecyclerView.Adapter<AddressAdaptiveAdapter.ViewHolder> {
    private ArrayList<AddressShowData.ResultBean> datas;
    private Context context;


    public AddressAdaptiveAdapter(ArrayList<AddressShowData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.adaptive_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.address_name2.setText(datas.get(i).getRealName());
        viewHolder.address_phone2.setText(datas.get(i).getPhone());
        viewHolder.address_local2.setText(datas.get(i).getAddress());
        viewHolder.My_Select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallBack != null) {
                    clickCallBack.callBack(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView address_name2;
        private TextView address_phone2;
        private TextView address_local2;
        private TextView My_Select2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address_name2 = itemView.findViewById(R.id.address_name2);
            address_phone2 = itemView.findViewById(R.id.address_phone2);
            address_local2 = itemView.findViewById(R.id.address_local2);
            My_Select2 = itemView.findViewById(R.id.My_Select);
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
