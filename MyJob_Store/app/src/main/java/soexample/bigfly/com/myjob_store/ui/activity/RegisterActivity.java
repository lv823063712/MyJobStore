package soexample.bigfly.com.myjob_store.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.XEditText;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyRegister;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class RegisterActivity extends MyBaseActivity implements IView, View.OnClickListener {
    @BindView(R.id.MyPhone)
    XEditText MyPhone;
    @BindView(R.id.MyPwd)
    XEditText MyPwd;
    @BindView(R.id.MyLogin)
    TextView MyLogin;
    @BindView(R.id.MyRegister)
    Button myRegister;
    private IPresenterImpl iPresenter;
    private HashMap<String, Object> map = new HashMap<>();
    private HashMap<String, String> emptyMap = new HashMap<>();
    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setClick() {
        /*MyLogin.setOnClickListener(this);
        myRegister.setOnClickListener(this);*/
    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MyLogin:

                break;
            case R.id.MyRegister:

                break;
        }
    }


    @Override
    public void success(Object data) {
        MyRegister myRegister = (MyRegister) data;
        if (myRegister.getMessage().equals("注册成功")) {
            Toast.makeText(this, myRegister.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, myRegister.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(Object error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDatach();
    }


    @OnClick({R.id.MyLogin, R.id.MyRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.MyLogin:
                finish();
                break;
            case R.id.MyRegister:
                String phone = MyPhone.getText().toString().trim();
                String pwd = MyPwd.getText().toString().trim();
                map.put("phone", phone);
                map.put("pwd", pwd);
                iPresenter.startRequestPost(Constant.REGISTER_URL, map,emptyMap, MyRegister.class);
                break;
        }
    }
}
