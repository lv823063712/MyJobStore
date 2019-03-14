package soexample.bigfly.com.myjob_store.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.InformationBean;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.activity.AddressActivity;
import soexample.bigfly.com.myjob_store.ui.activity.MyCircleActivity;
import soexample.bigfly.com.myjob_store.ui.activity.MyFootprintActivity;
import soexample.bigfly.com.myjob_store.ui.activity.MyPersonalActivity;
import soexample.bigfly.com.myjob_store.ui.activity.WalletActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class MainFragment extends Fragment implements IView {

    @BindView(R.id.Main_User_Name)
    TextView MainUserName;
    @BindView(R.id.PersonalData)
    ImageView PersonalData;
    @BindView(R.id.My_personal_data)
    LinearLayout MyPersonalData;
    @BindView(R.id.My_my_circle)
    LinearLayout MyMyCircle;
    @BindView(R.id.My_My_footprint)
    LinearLayout MyMyFootprint;
    @BindView(R.id.My_my_wallet)
    LinearLayout MyMyWallet;
    @BindView(R.id.My_shipping_address)
    LinearLayout MyShippingAddress;
    @BindView(R.id.My_Head_Phone)
    SimpleDraweeView MyHeadPhone;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private Map<String, String> headMap = new HashMap<>();
    private Map<String, String> map = new HashMap<>();
    private String headPic;
    private String nickName;
    private String password;
    private InformationBean.ResultBean result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_main, null);
        unbinder = ButterKnife.bind(this, inflate);
        SharedPreferences user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = user.getInt("userId", 0);
        String sessionId = user.getString("sessionId", "");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.QUERYBYID_URL, headMap, map, InformationBean.class);
        setListener();
        return inflate;
    }

    private void setListener() {

        /*
         * 钱包
         * */
        MyMyWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });
        /*
         * 个人资料
         * */
        MyPersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPersonalActivity.class);
                intent.putExtra("result", result);
                startActivityForResult(intent, 100);
            }
        });
        /*
         * 我的足迹
         * */
        MyMyFootprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyFootprintActivity.class);
                startActivity(intent);
            }
        });
        /*
         * 收货地址
         * */
        MyShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 我的圈子
         */
        MyMyCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCircleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            iPresenter.startRequestGet(Constant.QUERYBYID_URL, headMap, map, InformationBean.class);
        }
    }

    @Override
    public void success(Object data) {
        InformationBean informationBean = (InformationBean) data;
        headPic = informationBean.getResult().getHeadPic();
        nickName = informationBean.getResult().getNickName();
        password = informationBean.getResult().getPassword();
        result = informationBean.getResult();
        if (informationBean == null || !informationBean.isSuccess()) {
            Toast.makeText(getActivity(), informationBean.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            MyHeadPhone.setImageURI(Uri.parse(headPic));
            MainUserName.setText(nickName);
        }

    }

    @Override
    public void error(Object error) {
        Toast.makeText(getActivity(), error + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
