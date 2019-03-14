package soexample.bigfly.com.myjob_store.utils.user_defined_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import soexample.bigfly.com.myjob_store.R;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/21   13:53<p>
 * <p>更改时间：2019/2/21   13:53<p>
 * <p>版本号：1<p>
 */

public class AddAndSubtractView extends LinearLayout implements View.OnClickListener {

    private TextView delete_tv;
    private TextView product_number_tv;
    private TextView add_tv;
    private int myCount;

    public AddAndSubtractView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.add_remove_view, this);
        initView();
    }

    private void initView() {
        delete_tv = findViewById(R.id.delete_tv);
        product_number_tv = findViewById(R.id.product_number_tv);
        add_tv = findViewById(R.id.add_tv);

        delete_tv.setOnClickListener(this);
        add_tv.setOnClickListener(this);

    }

    //初始化赋值
    public void setNumber(int number) {
        this.myCount = number;
        product_number_tv.setText(number + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_tv:
                if (myCount > 0) {
                    myCount--;
                    product_number_tv.setText(myCount + "");
                    if (myOnCountChange != null) {
                        myOnCountChange.setCount(myCount);
                    }
                } else {
                    Toast.makeText(getContext(), "数量不能不再少了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_tv:
                myCount++;
                product_number_tv.setText(myCount + "");
                if (myOnCountChange != null) {
                    myOnCountChange.setCount(myCount);
                }
                break;
        }
    }

    public interface onCountChange {
        void setCount(int count);
    }

    private onCountChange myOnCountChange;

    public void setOnChange(onCountChange myOnCountChange) {
        this.myOnCountChange = myOnCountChange;

    }
}
