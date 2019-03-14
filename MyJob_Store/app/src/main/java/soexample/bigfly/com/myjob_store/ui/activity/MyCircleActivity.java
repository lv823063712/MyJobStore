package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyCircleData;
import soexample.bigfly.com.myjob_store.bean.MyRegister;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.adapter.MyMainCircleAdapter;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class MyCircleActivity extends MyBaseActivity implements IView {

    @BindView(R.id.my_cirlce_text)
    TextView myCirlceText;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.clrcle_recycle)
    XRecyclerView clrcleRecycle;
    private IPresenterImpl iPresenter;
    private Map<String, String> headMap = new HashMap<>();
    private Map<String, String> map = new HashMap<>();
    private Map<String, Object> map1 = new HashMap<>();
    private int index = 1;
    private int mCount = 10;

    private ArrayList<MyCircleData.ResultBean> datas = new ArrayList<>();
    private MyMainCircleAdapter myMainCircleAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_circle;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = user.getInt("userId", 0);
        String sessionId = user.getString("sessionId", "");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        map.put("page", index + "");
        map.put("count", 20 + "");

        clrcleRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                proLogic();

            }

            @Override
            public void onLoadMore() {

            }
        });

    }


    @Override
    protected void setClick() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        clrcleRecycle.setLayoutManager(manager);
        myMainCircleAdapter = new MyMainCircleAdapter(datas, this);
        clrcleRecycle.setAdapter(myMainCircleAdapter);
        myMainCircleAdapter.setClickCallBack(new MyMainCircleAdapter.ClickCallBack() {
            @Override
            public void callBack(int i, int position, int id) {
                map1.put("circleId", id + "");
                //http://172.17.8.100/small/circle/verify/v1/deleteCircle
                iPresenter.startRequesDelete(Constant.DELETE_CIRCLE_URL, headMap, map1, MyRegister.class);

            }
        });

    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.FIND_MYCIRCLE_BYID_URL, headMap, map, MyCircleData.class);

    }

    @OnClick(R.id.delete)
    public void onViewClicked() {

    }

    @Override
    public void success(Object data) {
        if (data instanceof MyCircleData) {
            MyCircleData myCircleData = (MyCircleData) data;
            datas.addAll(myCircleData.getResult());

        } else if (data instanceof MyRegister) {
            MyRegister myRegister = (MyRegister) data;
            Toast.makeText(this, myRegister.getMessage(), Toast.LENGTH_SHORT).show();
            iPresenter.startRequestGet(Constant.FIND_MYCIRCLE_BYID_URL, headMap, map, MyCircleData.class);

        }
        clrcleRecycle.loadMoreComplete();
        clrcleRecycle.refreshComplete();
        myMainCircleAdapter.notifyDataSetChanged();
    }

    @Override
    public void error(Object error) {

    }
}
