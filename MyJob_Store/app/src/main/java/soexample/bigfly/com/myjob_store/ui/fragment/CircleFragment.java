package soexample.bigfly.com.myjob_store.ui.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.CircleCanclePraiseBean;
import soexample.bigfly.com.myjob_store.bean.MyCircleData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenter;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.adapter.MyCircleAdapter;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment extends Fragment implements IView {

    @BindView(R.id.My_Circle_Xrecy)
    XRecyclerView MyCircleXrecy;
    Unbinder unbinder;
    private int userId;
    private String sessionId;
    private Map<String, String> map = new HashMap<>();
    private int index = 1;
    private MyCircleAdapter myCircleAdapter;
    private List<MyCircleData.ResultBean> datas = new ArrayList<>();
    private IPresenterImpl iPresenter;
    private HashMap<String, String> headMap = new HashMap<>();
    private HashMap<String, Object> maps = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_circle, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        myCircleAdapter = new MyCircleAdapter(datas, getActivity());
        MyCircleXrecy.setAdapter(myCircleAdapter);
        SharedPreferences user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = user.getInt("userId", 0);
        sessionId = user.getString("sessionId", "");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        map.put("count", 50 + "");
        map.put("page", index+"");
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        MyCircleXrecy.setLayoutManager(manager);
        initipresenter();


        initData();
        return inflate;
    }

    private void initipresenter() {
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.CIRCLE_LIST_URL, headMap, map, MyCircleData.class);
    }

    private void initData() {
        MyCircleXrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                index = 1;
                initipresenter();
            }

            @Override
            public void onLoadMore() {
                initipresenter();
            }
        });
        myCircleAdapter.setClickCallBack(new MyCircleAdapter.ClickCallBack() {
            @Override
            public void callBack(int i, int position, int id) {
                if (i==1){
                    maps.put("circleId",id);
                    //取消点赞
                    iPresenter.startRequesDelete(Constant.CANCLE_CIRCLE_GREAT_URL,headMap,maps,CircleCanclePraiseBean.class);
                    //取消点赞数量减少
                    myCircleAdapter.getCancelPraise(position);
                }else if (i == 2){
                    maps.put("circleId",String.valueOf(id));
                    iPresenter.startRequestPost(Constant.ADD_CIRCLE_GREAT_URL,maps,headMap,CircleCanclePraiseBean.class);
                    myCircleAdapter.getGivePraise(position);
                }
            }
        });


    }


    @Override
    public void success(Object data) {
        if (data instanceof MyCircleData) {
            MyCircleData myCircleData = (MyCircleData) data;
            datas.addAll(myCircleData.getResult());
            index++;
            MyCircleXrecy.refreshComplete();
            MyCircleXrecy.loadMoreComplete();
        }else if (data instanceof CircleCanclePraiseBean){
            CircleCanclePraiseBean circleCanclePraiseBean = (CircleCanclePraiseBean) data;
            Toast.makeText(getActivity(), circleCanclePraiseBean.getMessage(), Toast.LENGTH_SHORT).show();
            myCircleAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void error(Object error) {
        Log.e("圈子请求失败", "error: " + error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
