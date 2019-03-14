package soexample.bigfly.com.myjob_store.utils.user_defined_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xw.repo.XEditText;

import soexample.bigfly.com.myjob_store.R;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/21   19:30<p>
 * <p>更改时间：2019/2/21   19:30<p>
 * <p>版本号：1<p>
 */

public class user_defined_View extends LinearLayout {

    private ImageView My_Btn_Back;
    private XEditText my_Search;
    private TextView My_Search_Btn;

    public user_defined_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        //引入头部布局
        LayoutInflater.from(context).inflate(R.layout.my_header, this);
        //查找控件
        My_Btn_Back = findViewById(R.id.My_Btn_Back);
        my_Search = findViewById(R.id.my_Search);
        My_Search_Btn = findViewById(R.id.My_Search_Btn);
    }

    //获取输入框中的值
    public String getSearch() {
        return my_Search.getTrimmedString().trim();
    }

    //按钮点击后返回的值
    public TextView getAdd() {
        return My_Search_Btn;
    }

    //对返回按钮进行操作
    public ImageView getBack() {
        return My_Btn_Back;
    }

    //对editText进行操作
    public XEditText getEdit() {
        return my_Search;
    }
}
