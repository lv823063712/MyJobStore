package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.WalletData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.adapter.WalletAdapter;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class WalletActivity extends MyBaseActivity implements IView {

    @BindView(R.id.Back_Wallet)
    ImageView BackWallet;
    @BindView(R.id.My_Balance)
    TextView MyBalance;
    @BindView(R.id.xiaofei)
    LinearLayout xiaofei;
    @BindView(R.id.My_Consumption_Detail)
    XRecyclerView MyConsumptionDetail;
    private Map<String,String> map = new HashMap<>();
    private Map<String,String> headMap = new HashMap<>();
    private IPresenterImpl iPresenter;
    private ArrayList<WalletData.ResultBean.DetailListBean>datas = new ArrayList<>();
    @Override
    protected int getLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = user.getInt("userId", 0);
        String sessionId = user.getString("sessionId", "");
        headMap.put("userId",userId+"");
        headMap.put("sessionId",sessionId);
        map.put("page",1+"");
        map.put("count",50+"");

    }

    @Override
    protected void setClick() {

    }

    @Override
    protected void proLogic() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        MyConsumptionDetail.setLayoutManager(manager);
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.FIND_USER_WALLET_URL,headMap,map,WalletData.class);
    }

    @Override
    public void success(Object data) {
        WalletData walletData = (WalletData) data;
        MyBalance.setText(walletData.getResult().getBalance()+"");
        datas.addAll(walletData.getResult().getDetailList());
        WalletAdapter walletAdapter = new WalletAdapter(datas,this);
        MyConsumptionDetail.setAdapter(walletAdapter);
    }

    @Override
    public void error(Object error) {

    }
}
