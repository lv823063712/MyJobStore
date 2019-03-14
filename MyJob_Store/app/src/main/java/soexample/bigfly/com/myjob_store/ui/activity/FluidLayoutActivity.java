package soexample.bigfly.com.myjob_store.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.ui.fragmentsun.FluidFragment;
import soexample.bigfly.com.myjob_store.ui.fragmentsun.SearchFragment;

public class FluidLayoutActivity extends MyBaseActivity {
    @BindView(R.id.My_Seach_Fragment)
    FrameLayout MySeachFragment;
    private FragmentManager manager;
    private SearchFragment searchFragment = new SearchFragment();
    private FluidFragment fluidFragment = new FluidFragment();
    private FrameLayout My_Seach_Fragment;
    @Override
    protected int getLayout() {
        return R.layout.activity_fluid_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        MySeachFragment = findViewById(R.id.My_Seach_Fragment);
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.My_Seach_Fragment, fluidFragment).commit();
    }

    @Override
    protected void setClick() {

    }

    @Override
    protected void proLogic() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
