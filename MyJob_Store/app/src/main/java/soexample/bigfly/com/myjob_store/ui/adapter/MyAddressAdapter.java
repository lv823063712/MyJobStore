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
import soexample.bigfly.com.myjob_store.bean.NewAddressData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/22   16:50<p>
 * <p>更改时间：2019/2/22   16:50<p>
 * <p>版本号：1<p>
 */

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {
    private ArrayList<NewAddressData> datas;
    private Context context;


    public MyAddressAdapter(ArrayList<NewAddressData> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.newaddress, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.newAddress.setText(datas.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView newAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newAddress = itemView.findViewById(R.id.newAddress);
        }
    }
}
