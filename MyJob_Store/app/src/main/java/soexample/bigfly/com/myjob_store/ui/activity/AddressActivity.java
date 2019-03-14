package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.AddressShowData;
import soexample.bigfly.com.myjob_store.bean.DefaultAddressData;
import soexample.bigfly.com.myjob_store.bean.NewAddressData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.adapter.AddressAdapter;
import soexample.bigfly.com.myjob_store.ui.adapter.MyAddressAdapter;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class AddressActivity extends MyBaseActivity implements IView {
    @BindView(R.id.My_Address_Complete)
    TextView MyAddressComplete;
    @BindView(R.id.Add_Address)
    Button AddAddress;
    @BindView(R.id.My_Address_Recy)
    RecyclerView MyAddressRecy;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private HashMap<String, String> headMap = new HashMap<>();
    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, Object> map1 = new HashMap<>();
    private HashMap<String, String> emptyMap = new HashMap<>();
    private IPresenterImpl iPresenter;
    private ArrayList<NewAddressData> lists = new ArrayList();
    private MyAddressAdapter myAddressAdapter;
    private AddressAdapter addressAdapter;
    private ArrayList<AddressShowData.ResultBean> datas = new ArrayList();

    @Override
    protected int getLayout() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        MyAddressRecy.setLayoutManager(manager);
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);


    }

    @Override
    protected void setClick() {

    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.RECEIVE_ADDRESS_URL, headMap, map, NewAddressData.class);
    }

    @OnClick({R.id.My_Address_Complete, R.id.Add_Address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.My_Address_Complete:
                finish();
                break;
            case R.id.Add_Address:
                Intent intent = new Intent(AddressActivity.this, InsertAddressActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof NewAddressData) {
            NewAddressData newAddress = (NewAddressData) data;
            lists.add(newAddress);
            if (newAddress.getMessage().equals("你还没有收货地址，快去添加吧")) {
                myAddressAdapter = new MyAddressAdapter(lists, this);
                MyAddressRecy.setAdapter(myAddressAdapter);
                Toast.makeText(this, newAddress.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                iPresenter.startRequestGet(Constant.RECEIVE_ADDRESS_URL, headMap, map, AddressShowData.class);
            }
        } else if (data instanceof AddressShowData) {
            AddressShowData addressShowData = (AddressShowData) data;
            datas.addAll(addressShowData.getResult());
            SharedPreferences.Editor edit = shared.edit();
            edit.putString("id", addressShowData.getResult().get(0).getId() + "");
            addressAdapter = new AddressAdapter(datas, this);
            MyAddressRecy.setAdapter(addressAdapter);
            addressAdapter.notifyDataSetChanged();
            addressAdapter.setClickCallBack(new AddressAdapter.ClickCallBack() {
                @Override
                public void callBack(View view, int i) {
                    boolean childChecked = addressAdapter.setChildCheck();
                    addressAdapter.isChildChecked(i, childChecked);
                    addressAdapter.notifyDataSetChanged();
                    int id = datas.get(i).getId();
                    map1.put("id", id);
                    iPresenter.startRequestPost(Constant.SET_DEFAULT_RECEIVE_ADDRESS_URL, map1,emptyMap, DefaultAddressData.class);

                }
            });
        } else if (data instanceof DefaultAddressData) {
            DefaultAddressData defaultAddressData = (DefaultAddressData) data;
            String message = defaultAddressData.getMessage();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(Object error) {
        Log.e("zzz", "error: " + error);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        addressAdapter.notifyDataSetChanged();
    }
}
