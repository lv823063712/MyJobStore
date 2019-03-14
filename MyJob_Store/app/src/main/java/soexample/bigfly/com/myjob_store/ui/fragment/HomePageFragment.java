package soexample.bigfly.com.myjob_store.ui.fragment;


import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xw.repo.XEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.FindFirstData;
import soexample.bigfly.com.myjob_store.bean.MyFindSeconddata;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.activity.FluidLayoutActivity;
import soexample.bigfly.com.myjob_store.ui.adapter.MyFindFirstAdapter;
import soexample.bigfly.com.myjob_store.ui.fragmentsun.HomeFragmentSun;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;
import soexample.bigfly.com.myjob_store.utils.user_defined_view.UserPopwidow;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment implements IView {


    @BindView(R.id.My_btn_menu)
    ImageView MyBtnMenu;
    @BindView(R.id.my_search_edittext)
    XEditText mySearchEdittext;
    @BindView(R.id.My_search_Text)
    TextView MySearchText;
    @BindView(R.id.My_Fragment_Sun)
    FrameLayout MyFragmentSun;
    Unbinder unbinder;
    private FragmentManager manager;
    private IPresenterImpl iPresenter;
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> headMap = new HashMap<>();
    private UserPopwidow userPopwidow;
    private PopupWindow mPopupWindow;
    private float bgAlpha = 1f;
    private boolean bright = false;
    private static final long DURATION = 500;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;
    private RecyclerView My_Pop_List;
    private ArrayList<FindFirstData.ResultBean> datas = new ArrayList<>();
    /*private ArrayList<MyFindSeconddata.ResultBean> lists = new ArrayList<>();*/
    private MyFindFirstAdapter myFindFirstAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_page, null);
        unbinder = ButterKnife.bind(this, v);
        iPresenter = new IPresenterImpl(this);
        mPopupWindow = new PopupWindow(getActivity());
        userPopwidow = new UserPopwidow();
        initView(v);
        return v;
    }

    private void initView(View v) {
        manager = getChildFragmentManager();
        manager.beginTransaction().replace(R.id.My_Fragment_Sun, new HomeFragmentSun()).commit();

    }

    @OnClick({R.id.my_search_edittext, R.id.My_btn_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_search_edittext:
                Intent intent = new Intent(getActivity(), FluidLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.My_btn_menu:
                showPop();
                toggleBright();
                iPresenter.startRequestGet(Constant.FIND_FIRST_CATEGORY_URL, headMap, map, FindFirstData.class);
                break;
        }

    }

    private void toggleBright() {
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        userPopwidow.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        userPopwidow.addUpdateListener(new UserPopwidow.UpdateListener() {
            @Override
            public void progress(float progress) {
                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        userPopwidow.addEndListner(new UserPopwidow.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // 在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });

    }
    /**
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
        // everything behind this window will be dimmed.
        // 此方法用来设置浮动层，防止部分手机变暗无效
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void showPop() {
        // 设置布局文件
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.pop_list, null);
        mPopupWindow.setContentView(v);
        My_Pop_List = v.findViewById(R.id.My_Pop_List);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        My_Pop_List.setLayoutManager(manager);
        iPresenter.startRequestGet(Constant.FIND_FIRST_CATEGORY_URL, headMap, map, FindFirstData.class);

        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        mPopupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量
        mPopupWindow.showAsDropDown(MyBtnMenu, -100, 0);
        // 设置pop关闭监听，用于改变背景透明度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });
    }



    @Override
    public void success(Object data) {
        if (data instanceof FindFirstData){
            FindFirstData findFirstData = (FindFirstData) data;
            datas.addAll(findFirstData.getResult());
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
            My_Pop_List.setLayoutManager(manager);
            myFindFirstAdapter = new MyFindFirstAdapter(datas,getActivity());
            My_Pop_List.setAdapter(myFindFirstAdapter);

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
