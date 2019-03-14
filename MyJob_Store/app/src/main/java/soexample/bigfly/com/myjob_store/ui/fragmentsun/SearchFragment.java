package soexample.bigfly.com.myjob_store.ui.fragmentsun;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MySeachData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.activity.DetailsActivity;
import soexample.bigfly.com.myjob_store.ui.adapter.MySearchAdapter;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;
import soexample.bigfly.com.myjob_store.utils.user_defined_view.user_defined_View;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements IView, View.OnClickListener {


    @BindView(R.id.My_Seach_Result)
    user_defined_View MySeachResult;
    @BindView(R.id.cannot_find_img)
    ImageView cannotFindImg;
    @BindView(R.id.cannot_find_tv)
    TextView cannotFindTv;
    @BindView(R.id.My_Search_XRecy)
    XRecyclerView MySearchXRecy;
    Unbinder unbinder;
    private int index = 1;
    private String mName;
    private String name;
    private MySearchAdapter mySearchAdapter;
    private ArrayList<MySeachData.ResultBean> datas = new ArrayList<>();
    private FragmentManager manager;
    private IPresenterImpl iPresenter;
    private HashMap<String, String> headMap = new HashMap<>();
    private HashMap<String, String> map = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        EventBus.getDefault().register(this);

        headMap.put("count",50+"");
        headMap.put("page",index+"");
        headMap.put("keyword",mName);
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.FIND_COMMODITY_BYKEYWORD_URL,map,headMap,MySeachData.class);
        setListener();
        return view;
    }

    private void setListener() {
        mySearchAdapter.setClickCallBack(new MySearchAdapter.ClickCallBack() {
            @Override
            public void callBack(int i) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("zhi", i);
                startActivity(intent);
            }
        });


        MySearchXRecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override//上拉刷新
            public void onRefresh() {
                datas.clear();
                mySearchAdapter.notifyDataSetChanged();
                index = 1;
                iPresenter.startRequestGet(Constant.FIND_COMMODITY_BYKEYWORD_URL,headMap,map,MySeachData.class);
            }

            @Override
            public void onLoadMore() {
                index++;
                iPresenter.startRequestGet(Constant.FIND_COMMODITY_BYKEYWORD_URL,headMap,map,MySeachData.class);
            }
        });
    }

    private void initData() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        MySearchXRecy.setLayoutManager(manager);
        mySearchAdapter = new MySearchAdapter(datas, getActivity());
        MySearchXRecy.setAdapter(mySearchAdapter);
        MySearchXRecy.setLoadingMoreEnabled(true);
        MySearchXRecy.setPullRefreshEnabled(true);
        MySeachResult.getEdit().setOnClickListener(this);
        name = MySeachResult.getSearch().trim();
        MySeachResult.getBack().setOnClickListener(this);
        MySeachResult.setOnClickListener(this);
    }
    //eventbus粘性传值
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getInfo(String Name) {
        //传值
        this.mName = Name;
        MySeachResult.getEdit().setText(mName);
        Toast.makeText(getActivity(), mName, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void success(Object data) {
        MySeachData mySeachData = (MySeachData) data;
        datas.addAll(mySeachData.getResult());
        if (datas.size() == 0) {
            cannotFindImg.setVisibility(View.VISIBLE);
            cannotFindTv.setVisibility(View.VISIBLE);
        } else {
            cannotFindImg.setVisibility(View.GONE);
            cannotFindTv.setVisibility(View.GONE);
        }
        MySearchXRecy.refreshComplete();
        MySearchXRecy.loadMoreComplete();
    }

    @Override
    public void error(Object error) {
        Log.e("搜索报错", "error: "+error );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Btn_Back:
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.My_Seach_Fragment, new FluidFragment()).commit();
                break;
            case R.id.my_Search:
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.My_Seach_Fragment, new FluidFragment()).commit();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
