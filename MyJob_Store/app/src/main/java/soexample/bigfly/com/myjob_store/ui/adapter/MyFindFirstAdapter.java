package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.FindFirstData;
import soexample.bigfly.com.myjob_store.bean.MyFindSeconddata;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/3/2   10:22<p>
 * <p>更改时间：2019/3/2   10:22<p>
 * <p>版本号：1<p>
 */

public class MyFindFirstAdapter extends RecyclerView.Adapter<MyFindFirstAdapter.ViewHolder> implements IView {
    private ArrayList<FindFirstData.ResultBean> datas;
    private ArrayList<MyFindSeconddata.ResultBean> lists = new ArrayList<>();
    private Context context;
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> headMap = new HashMap<>();
    private MyFindFirstAdapter2 myFindFirstAdapter2;
    private RecyclerView MyNna1;
    public MyFindFirstAdapter(ArrayList<FindFirstData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.findfirst_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        this.MyNna1 = viewHolder.MyNna;
        viewHolder.MyMen.setText(datas.get(i).getName());
        map.put("firstCategoryId",datas.get(i).getId());
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        viewHolder.MyNna.setLayoutManager(manager);

        IPresenterImpl iPresenter = new IPresenterImpl(MyFindFirstAdapter.this);
        iPresenter.startRequestGet(Constant.FIND_SECOND_CATEGORY_URL,map,headMap,MyFindSeconddata.class);


        viewHolder.MyMen.setOnClickListener(new View.OnClickListener() {

            private IPresenterImpl iPresenter;

            @Override
            public void onClick(View v) {
                map.put("firstCategoryId",datas.get(i).getId());
                iPresenter = new IPresenterImpl(MyFindFirstAdapter.this);
                iPresenter.startRequestGet(Constant.FIND_SECOND_CATEGORY_URL,headMap,map,MyFindSeconddata.class);

            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();

    }

    @Override
    public void success(Object data) {
        MyFindSeconddata myFindSeconddata = (MyFindSeconddata) data;
        lists.addAll(myFindSeconddata.getResult());
        myFindFirstAdapter2 = new MyFindFirstAdapter2(lists,context);
        MyNna1.setAdapter(myFindFirstAdapter2);
        myFindFirstAdapter2.notifyDataSetChanged();
    }

    @Override
    public void error(Object error) {
        Toast.makeText(context,error+"",Toast.LENGTH_SHORT).show();
        Log.e("zzzzz", "error: "+error );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView MyMen;
        private RecyclerView MyNna;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MyMen=itemView.findViewById(R.id.MyMen);
            MyNna=itemView.findViewById(R.id.MyNna);
        }
    }

}
