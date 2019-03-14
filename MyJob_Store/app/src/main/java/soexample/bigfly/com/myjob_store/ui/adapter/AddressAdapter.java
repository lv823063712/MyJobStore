package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.AddressShowData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/22   17:12<p>
 * <p>更改时间：2019/2/22   17:12<p>
 * <p>版本号：1<p>
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private ArrayList<AddressShowData.ResultBean> datas;
    private Context context;


    public AddressAdapter(ArrayList<AddressShowData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.showaddress_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.address_name.setText(datas.get(i).getRealName());
        viewHolder.address_phone.setText(datas.get(i).getPhone());
        viewHolder.address_local.setText(datas.get(i).getAddress());
        if (datas.get(i).getWhetherDefault() == 1) {
            datas.get(i).setChecked(true);
        } else {
            datas.get(i).setChecked(false);
        }
        viewHolder.address_radio.setChecked(datas.get(i).isChecked());
        viewHolder.address_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallBack != null) {
                    clickCallBack.callBack(v, i);
                }
            }
        });
        viewHolder.address_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.callBack(v, v.getId());


            }
        });
        viewHolder.address_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.callBack(v, v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView address_name;
        private TextView address_phone;
        private TextView address_local;
        private CheckBox address_radio;
        private Button address_update;
        private Button address_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address_name = itemView.findViewById(R.id.address_name);
            address_phone = itemView.findViewById(R.id.address_phone);
            address_local = itemView.findViewById(R.id.address_local);
            address_radio = itemView.findViewById(R.id.address_radio);
            address_update = itemView.findViewById(R.id.address_update);
            address_delete = itemView.findViewById(R.id.address_delete);
        }
    }

    //判断是否被选中
    public void isChildChecked(int childPosition, boolean isChecked) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setChecked(false);
            datas.get(i).setWhetherDefault(2);
        }
        if (isChecked == false) {
            datas.get(childPosition).setChecked(true);
            datas.get(childPosition).setWhetherDefault(1);
        } else {
            datas.get(childPosition).setChecked(isChecked);
            datas.get(childPosition).setWhetherDefault(1);
        }
    }

    //给布尔值进行判断
    public boolean setChildCheck() {
        for (int i = 0; i < datas.size(); i++) {
            int whetherDefault = datas.get(i).getWhetherDefault();
            if (whetherDefault == 1) {
                return true;
            }
        }
        return false;
    }


    //定义接口
    private ClickCallBack clickCallBack;

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ClickCallBack {
        void callBack(View view, int i);
    }

}
