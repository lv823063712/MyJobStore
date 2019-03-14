package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPickerView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.InsertAddressData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class InsertAddressActivity extends MyBaseActivity implements IView {
    @BindView(R.id.add_person)
    EditText addPerson;
    @BindView(R.id.add_phone)
    EditText addPhone;
    @BindView(R.id.add_text)
    TextView addText;
    @BindView(R.id.add_local)
    TextView addLocal;
    @BindView(R.id.add_image)
    ImageView addImage;
    @BindView(R.id.add_content)
    EditText addContent;
    @BindView(R.id.get_text)
    TextView getText;
    @BindView(R.id.add_email)
    EditText addEmail;
    @BindView(R.id.add_save)
    Button addSave;
    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, Object> headMap = new HashMap<>();
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private IPresenterImpl iPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_insert_address;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
    }

    @Override
    protected void setClick() {

    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);
    }

    @OnClick({R.id.add_image, R.id.add_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_save:
                String realName = addPerson.getText().toString().trim();
                String phone = addPhone.getText().toString().trim();
                String local = addLocal.getText().toString().trim();
                String address = addContent.getText().toString().trim();
                String zipCode = addEmail.getText().toString().trim();
                if (realName != null || phone != null || local != null || address != null || zipCode != null) {
                    map.put("userId", userId + "");
                    map.put("sessionId", sessionId);
                    Log.e("对比",userId+"..."+ sessionId );
                    headMap.put("realName", realName);
                    headMap.put("phone", phone);
                    headMap.put("address", local + " " + address);
                    headMap.put("zipCode", zipCode);

                    iPresenter.startRequestPost(Constant.ADD_RECEIVE_ADDRESS_URL,headMap ,map,InsertAddressData.class);
                    Intent intent = new Intent(InsertAddressActivity.this, AddressActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "必须全部填写,不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_image:
                CityPickerView cityPickerView = new CityPickerView(InsertAddressActivity.this);
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县
                        String district = citySelected[2];
                        //邮编
                        String code = citySelected[3];
                        addLocal.setText(province + " " + city + " " + district);
                    }
                });
                cityPickerView.show();
                break;
        }
    }

    @Override
    public void success(Object data) {
        InsertAddressData insertAddressData = (InsertAddressData) data;
        Toast.makeText(this, insertAddressData.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(Object error) {
        Log.e("添加地址报错", "error: "+error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDatach();
    }
}
