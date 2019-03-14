package soexample.bigfly.com.myjob_store.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.XEditText;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyLogin;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.activity.HomePageActivity;
import soexample.bigfly.com.myjob_store.ui.activity.RegisterActivity;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class MainActivity extends MyBaseActivity implements IView {
    @BindView(R.id.UserPhone)
    XEditText UserPhone;
    @BindView(R.id.UserPassword)
    XEditText UserPassword;
    @BindView(R.id.Rememb)
    CheckBox Rememb;
    @BindView(R.id.Quick_Registration)
    TextView QuickRegistration;
    @BindView(R.id.Login)
    Button Login;
    private HashMap<String, String> emptyMap = new HashMap<>();
    private HashMap<String, Object> headMap = new HashMap<>();
    private SharedPreferences shared;
    private SharedPreferences.Editor edit;
    private IPresenterImpl iPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //创建一个sp的工具类   进行本地信息保存
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        edit = shared.edit();
        //获取控件
        UserPhone = findViewById(R.id.UserPhone);
        UserPassword = findViewById(R.id.UserPassword);
        Rememb = findViewById(R.id.Rememb);
        QuickRegistration = findViewById(R.id.Quick_Registration);
        Login = findViewById(R.id.Login);

        boolean rem = shared.getBoolean("rem", false);
        //进行判断
        if (rem) {
            String phone = shared.getString("phone", "");
            String pwd = shared.getString("pwd", "");
            UserPhone.setText(phone);
            UserPassword.setText(pwd);
            Rememb.setChecked(true);
        }
    }

    @Override
    protected void setClick() {

        UserPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String phone = UserPhone.getTrimmedString().trim();
                    boolean regular = isRegular(phone);
                    //进行判断,输入号码的长度不能超过限定的
                    if (phone.length() != 11 && regular != true) {
                        Toast.makeText(MainActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Rememb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //进行判断
                if (isChecked) {
                    String phone = UserPhone.getTrimmedString().trim();
                    String pwd = UserPassword.getTrimmedString().trim();
                    //进行一个保存,将数据保存到本地
                    edit.putBoolean("rem", true);
                    edit.putString("phone", phone);
                    edit.putString("pwd", pwd);
                    edit.commit();
                } else {
                    edit.clear();
                    edit.commit();
                }
            }
        });
    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);

    }

    @OnClick({R.id.Quick_Registration, R.id.Login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Quick_Registration:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.Login:
                String phone = UserPhone.getTrimmedString().trim();
                String password = UserPassword.getTrimmedString().trim();
                //此处查看接口参数,将拼接的key值和参数保持一致,否则登录不成功
                headMap.put("phone", phone);
                headMap.put("pwd", password);
                iPresenter.startRequestPost(Constant.LOGIN_URL, headMap,emptyMap, MyLogin.class);
                break;
        }
    }



    @Override
    public void success(Object data) {
        MyLogin myLogin = (MyLogin) data;
        if (myLogin.getMessage().equals("登录成功")) {
            Toast.makeText(this, myLogin.getMessage(), Toast.LENGTH_SHORT).show();
            int userId = myLogin.getResult().getUserId();
            String sessionId = myLogin.getResult().getSessionId();
            String nickName = myLogin.getResult().getNickName();
            String headPic = myLogin.getResult().getHeadPic();
            int sex = myLogin.getResult().getSex();

            edit.putInt("userId", userId);
            edit.putString("sessionId", sessionId);
            edit.putString("nickName", nickName);
            edit.putString("headPic", headPic);
            edit.commit();
            Log.e("zzz",userId+"..."+ sessionId );
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, myLogin.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void error(Object error) {
        Log.e("登陆出现错误", "错误信息:" + error);
    }

    //正则表达式
    public static boolean isRegular(String string) {
        String phone = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        Pattern compile = Pattern.compile(phone);
        //匹配器
        Matcher matcher = compile.matcher(string);
        //将匹配结果返回给方法
        return matcher.matches();

    }

}
