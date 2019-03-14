package soexample.bigfly.com.myjob_store.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.ui.adapter.MyFragmentAdapter;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.ui.fragment.CircleFragment;
import soexample.bigfly.com.myjob_store.ui.fragment.HomePageFragment;
import soexample.bigfly.com.myjob_store.ui.fragment.MainFragment;
import soexample.bigfly.com.myjob_store.ui.fragment.OrderForGoodsFragment;
import soexample.bigfly.com.myjob_store.ui.fragment.ShoppingTrolleyFragment;
import soexample.bigfly.com.myjob_store.utils.user_defined_view.NoScrollViewPager;

public class HomePageActivity extends MyBaseActivity {

    @BindView(R.id.My_Fragment)
    NoScrollViewPager MyFragment;
    @BindView(R.id.My_tab_btn_home)
    RadioButton MyTabBtnHome;
    @BindView(R.id.My_tab_btn_circle)
    RadioButton MyTabBtnCircle;
    @BindView(R.id.My_tab_btn_comment)
    RadioButton MyTabBtnComment;
    @BindView(R.id.My_tab_btn_list)
    RadioButton MyTabBtnList;
    @BindView(R.id.My_tab_btn_my)
    RadioButton MyTabBtnMy;
    @BindView(R.id.My_RG)
    RadioGroup MyRG;
    private ArrayList<Fragment> datas = new ArrayList<>();
    private MyFragmentAdapter myFragmentAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        datas.add(new HomePageFragment());
        datas.add(new CircleFragment());
        datas.add(new ShoppingTrolleyFragment());
        datas.add(new OrderForGoodsFragment());
        datas.add(new MainFragment());

        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), datas);
        MyFragment.setAdapter(myFragmentAdapter);
    }

    @Override
    protected void setClick() {
        MyRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.My_tab_btn_home) {
                    MyFragment.setCurrentItem(0);
                } else if (checkedId == R.id.My_tab_btn_circle) {
                    MyFragment.setCurrentItem(1);
                } else if (checkedId == R.id.My_tab_btn_comment) {
                    MyFragment.setCurrentItem(2);
                } else if (checkedId == R.id.My_tab_btn_list) {
                    MyFragment.setCurrentItem(3);
                } else if (checkedId == R.id.My_tab_btn_my) {
                    MyFragment.setCurrentItem(4);
                }

            }
        });
    }

    @Override
    protected void proLogic() {

    }

}
