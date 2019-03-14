package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyRegister;
import soexample.bigfly.com.myjob_store.bean.RecordData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class PayMentActivity extends MyBaseActivity implements IView {
    @BindView(R.id.way)
    TextView way;
    @BindView(R.id.balancepaid)
    ImageView balancepaid;
    @BindView(R.id.balancepaid_text)
    TextView balancepaidText;
    @BindView(R.id.alipay)
    ImageView alipay;
    @BindView(R.id.alipay_text)
    TextView alipayText;
    @BindView(R.id.wechat)
    ImageView wechat;
    @BindView(R.id.wechat_text)
    TextView wechatText;
    @BindView(R.id.balancepaid_radio)
    RadioButton balancepaidRadio;
    @BindView(R.id.wechat_radio)
    RadioButton wechatRadio;
    @BindView(R.id.alipay_radio)
    RadioButton alipayRadio;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.success)
    ImageView success;
    @BindView(R.id.success_text)
    TextView successText;
    @BindView(R.id.homebutton)
    Button homebutton;
    @BindView(R.id.examinebutton)
    Button examinebutton;
    @BindView(R.id.con_success)
    ConstraintLayout layout_success;
    @BindView(R.id.error)
    ImageView error;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.zan)
    TextView zan;
    @BindView(R.id.yu)
    TextView yu;
    @BindView(R.id.goon)
    Button goon;
    @BindView(R.id.con_error)
    ConstraintLayout layout_error;
    @BindView(R.id.con_yv)
    ConstraintLayout layout_yv;
    private String orderId;
    private int type;
    private double payAmount;
    private IPresenterImpl iPresenter;
    private HashMap<String, String> headMap = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private double payment;

    @Override
    protected int getLayout() {
        return R.layout.activity_pay_ment;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        payment = intent.getDoubleExtra("payment", 0);
        textView.setText("余额支付" + payment + "元");
    }

    @Override
    protected void setClick() {

    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);
    }


    @OnClick({R.id.balancepaid_radio, R.id.wechat_radio, R.id.alipay_radio, R.id.text, R.id.homebutton, R.id.examinebutton, R.id.goon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.balancepaid_radio:
                type = 1;
                textView.setText("余额支付" + payment + "元");
                break;
            case R.id.wechat_radio:
                type = 2;
                textView.setText("微信支付" + payment + "元");
                break;
            case R.id.alipay_radio:
                type = 3;
                textView.setText("支付宝支付" + payment + "元");
                break;
            case R.id.text:
                getData();
                break;
            case R.id.homebutton:
                finish();
                break;
            case R.id.examinebutton:
                finish();
                break;
            case R.id.goon:
                layout_success.setVisibility(View.GONE);
                layout_yv.setVisibility(View.VISIBLE);
                layout_error.setVisibility(View.GONE);
                break;
        }
    }

    private void getData() {
        map.put("orderId",orderId);
        map.put("payType",type);
        iPresenter.startRequestPost(Constant.PAY_URL, map, headMap, MyRegister.class);
    }

    @Override
    public void success(Object data) {
        if (data instanceof MyRegister) {
            MyRegister payOrderBean = (MyRegister) data;
            if (payOrderBean == null || !payOrderBean.isSuccess()) {
                Toast.makeText(PayMentActivity.this, payOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
                layout_error.setVisibility(View.VISIBLE);
                layout_success.setVisibility(View.GONE);
            } else {
                layout_success.setVisibility(View.VISIBLE);
                layout_error.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void error(Object error) {
        Toast.makeText(PayMentActivity.this, error + "", Toast.LENGTH_SHORT).show();
    }
    
}
