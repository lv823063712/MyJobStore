package soexample.bigfly.com.myjob_store.ui.fragmentsun;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyBanner;
import soexample.bigfly.com.myjob_store.bean.MyHotData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.adapter.MyHomeDataAdapter;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmentSun extends Fragment implements IView {


    @BindView(R.id.xbanner)
    XBanner xbanner;
    Unbinder unbinder;
    @BindView(R.id.MyHomeList)
    RecyclerView MyHomeList;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private List<MyHotData.ResultBean> lists = new ArrayList<>();
    private IPresenterImpl iPresenter;
    private HashMap<String, String> headMap = new HashMap<>();
    private HashMap<String, String> map = new HashMap<>();
    private ArrayList<String> datas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home_fragment_sun, null);
        unbinder = ButterKnife.bind(this, inflate);
        shared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");

        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.BANNER_URL, headMap, map, MyBanner.class);
        iPresenter.startRequestGet(Constant.COMMODITYLIST_URL,headMap, map,MyHotData.class);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        MyHomeList.setLayoutManager(manager);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

    }

    @Override
    public void success(Object data) {
        if (data instanceof MyBanner) {
            final MyBanner myBanner = (MyBanner) data;
            //此处需要加判空,否则轮播图不出来
            List<MyBanner.ResultBean> result = myBanner.getResult();
            for (int i = 0; i < myBanner.getResult().size(); i++) {
                datas.add(myBanner.getResult().get(i).getImageUrl());
            }
            if (!datas.isEmpty()) {
                xbanner.setData(myBanner.getResult(), null);
                xbanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(getActivity()).load(myBanner.getResult().get(position).getImageUrl()).into((ImageView) view);
                    }
                });
                //设置样式，里面有很多种样式可以自己都看看效果
                xbanner.setPageTransformer(Transformer.Default);//横向移动
                xbanner.setPageTransformer(Transformer.Alpha); //渐变，效果不明显
                xbanner.setPageTransformer(Transformer.ZoomFade); // 缩小本页，同时放大另一页
                xbanner.setPageTransformer(Transformer.ZoomCenter); //本页缩小一点，另一页就放大
                xbanner.setPageTransformer(Transformer.ZoomStack); // 本页和下页同事缩小和放大
                xbanner.setPageTransformer(Transformer.Stack);  //本页和下页同时左移
                xbanner.setPageTransformer(Transformer.Depth);  //本页左移，下页从后面出来
                xbanner.setPageTransformer(Transformer.Zoom);  //本页刚左移，下页就在后面
                //  设置xbanner求换页面的时间
                xbanner.setPageChangeDuration(2000);

            }
        }else if (data instanceof MyHotData){
            MyHotData myHotData = (MyHotData) data;
            lists.add(myHotData.getResult());
            MyHomeDataAdapter myHomeDataAdapter = new MyHomeDataAdapter(lists,getActivity());
            MyHomeList.setAdapter(myHomeDataAdapter);
        }
    }

    @Override
    public void error(Object error) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
