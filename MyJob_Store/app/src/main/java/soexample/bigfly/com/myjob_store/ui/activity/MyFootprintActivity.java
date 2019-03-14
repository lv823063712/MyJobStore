package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyFootprintData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.adapter.MyFootAdapter;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class MyFootprintActivity extends MyBaseActivity implements IView {
    @BindView(R.id.MyFootRecy)
    XRecyclerView MyFootRecy;
    @BindView(R.id.My_FootDetails_Back)
    ImageView MyFootDetailsBack;
    private Map<String, String> headMap = new HashMap<>();
    private Map<String, String> map = new HashMap<>();
    private IPresenterImpl iPresenter;
    private ArrayList<MyFootprintData.ResultBean> datas = new ArrayList<>();
    private MyFootAdapter myFootAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_footprint;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = user.getInt("userId", 0);
        String sessionId = user.getString("sessionId", "");
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        MyFootRecy.setLayoutManager(manager);
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);

    }

    @OnClick(R.id.My_FootDetails_Back)
    public void onViewClicked() {
        finish();
    }
    @Override
    protected void setClick() {
        myFootAdapter = new MyFootAdapter(datas, this);
        MyFootRecy.setAdapter(myFootAdapter);
        myFootAdapter.setClickCallBack(new MyFootAdapter.ClickCallBack() {
            @Override
            public void callBack(int i) {
                Intent intent = new Intent(MyFootprintActivity.this, DetailsActivity.class);
                intent.putExtra("zhi", i);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void proLogic() {
        map.put("page", 1 + "");
        map.put("count", 50 + "");
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.BROWSE_LIST_URL, headMap, map, MyFootprintData.class);
    }

    @Override
    public void success(Object data) {
        MyFootprintData myFootprintData = (MyFootprintData) data;
        datas.addAll(myFootprintData.getResult());

    }

    @Override
    public void error(Object error) {
        Log.e("足迹错误", "error: " + error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDatach();
    }

}
