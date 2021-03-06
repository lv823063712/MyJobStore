package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyFindSeconddata;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/3/2   10:22<p>
 * <p>更改时间：2019/3/2   10:22<p>
 * <p>版本号：1<p>
 */

public class MyFindFirstAdapter2 extends RecyclerView.Adapter<MyFindFirstAdapter2.ViewHolder> {
    private ArrayList<MyFindSeconddata.ResultBean> datas;
    private Context context;

    public MyFindFirstAdapter2(ArrayList<MyFindSeconddata.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.findfirst2_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.e("测试", "onBindViewHolder: "+datas.get(i).getName() );
        viewHolder.MyNames.setText(datas.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView MyNames;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MyNames=itemView.findViewById(R.id.MyNames);
        }
    }

    //定义接口
    private ClickCallBack clickCallBack;

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ClickCallBack {
        void callBack(String i);
    }
}
