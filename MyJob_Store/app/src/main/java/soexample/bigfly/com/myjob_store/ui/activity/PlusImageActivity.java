package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.ui.adapter.ViewPagerAdapter;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.CancelOrOkDialog;
import soexample.bigfly.com.myjob_store.utils.apiservice.MainConstant;

public class PlusImageActivity extends MyBaseActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.position_tv)
    TextView positionTv;
    @BindView(R.id.delete_iv)
    ImageView deleteIv;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private ArrayList<String> imgList;
    /**
     * 第几张图片
     */
    private int mPosition;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_plus_image;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        imgList = getIntent().getStringArrayListExtra(MainConstant.IMG_LIST);
        mPosition = getIntent().getIntExtra(MainConstant.POSITION, 0);

        viewPagerAdapter = new ViewPagerAdapter(this, imgList);
        viewPager.setAdapter(viewPagerAdapter);
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
    }

    @Override
    protected void setClick() {

    }

    @Override
    protected void proLogic() {

    }

    @OnClick({R.id.back_iv, R.id.delete_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                //返回
                back();
                break;
            case R.id.delete_iv:
                //删除图片
                deletePic();
                break;
        }
    }

    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
        setResult(MainConstant.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }

    private void deletePic() {
        CancelOrOkDialog dialog = new CancelOrOkDialog(this, "要删除这张图片吗?") {
            @Override
            public void ok() {
                super.ok();
                //从数据源移除删除的图片
                imgList.remove(mPosition);
                setPosition();
                dismiss();
            }
        };
        dialog.show();
    }

    private void setPosition() {
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
        viewPagerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mPosition = i;
        positionTv.setText(i + 1 + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
