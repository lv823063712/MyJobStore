package soexample.bigfly.com.myjob_store.utils.mystyle;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * <p>文件描述：<p>
 * <p>作者：${adai}<p>
 * <p>创建时间：2019/1/3 15:28<p>
 * <p>更改时间：2019/1/3 15:28<p>
 * <p>版本号：1<p>
 */
public class XrecycleStyle extends XRecyclerView.ItemDecoration {
    private int space;

    public XrecycleStyle(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}
