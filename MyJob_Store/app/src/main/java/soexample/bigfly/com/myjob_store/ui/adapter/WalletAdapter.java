package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.WalletData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/25   21:08<p>
 * <p>更改时间：2019/2/25   21:08<p>
 * <p>版本号：1<p>
 */

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private ArrayList<WalletData.ResultBean.DetailListBean> datas;
    private Context context;


    public WalletAdapter(ArrayList<WalletData.ResultBean.DetailListBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.wallet_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.MyXF.setText("¥"+datas.get(i).getAmount());
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(datas.get(i).getCreateTime()));
        viewHolder.MyXFRQ.setText(date);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView MyXF;
        private TextView MyXFRQ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MyXF=itemView.findViewById(R.id.MyXF);
            MyXFRQ=itemView.findViewById(R.id.MyXFRQ);
        }
    }
}
