package soexample.bigfly.com.myjob_store.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.zackratos.ultimatebar.UltimateBar;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/18   20:39<p>
 * <p>更改时间：2019/2/18   20:39<p>
 * <p>版本号：1<p>
 */

public abstract class MyBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar.Companion.with(this)
                .statusDark(false)                  // 状态栏灰色模式(Android 6.0+)，默认 flase
                .applyNavigation(true)              // 应用到导航栏，默认 flase
                .navigationDark(false)              // 导航栏灰色模式(Android 8.0+)，默认 false
                .create()

                .immersionBar();
        init();

    }
    //引入视图
    protected abstract int getLayout();
    //初始化控件
    protected abstract void initView();
    //事件监听
    protected abstract void setClick();
    //逻辑代码书写
    protected abstract void proLogic();
    //代码书写顺序
    void init(){
        if (getLayout()!=0){
            setContentView(getLayout());
            initView();
            setClick();
            proLogic();
        }
    }
}
