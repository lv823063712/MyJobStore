package soexample.bigfly.com.myjob_store.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/20   21:10<p>
 * <p>更改时间：2019/2/20   21:10<p>
 * <p>版本号：1<p>
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> datas;

    public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int i) {
        return datas.get(i);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
