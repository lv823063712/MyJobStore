package soexample.bigfly.com.myjob_store.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.ShoppingCarData;
import soexample.bigfly.com.myjob_store.bean.SynchronizationShoppingData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.activity.SubmitOrderActivity;
import soexample.bigfly.com.myjob_store.ui.adapter.ShoppingCarAdapter;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingTrolleyFragment extends Fragment implements IView {

    @BindView(R.id.My_Shopping_Car)
    RecyclerView MyShoppingCar;
    @BindView(R.id.My_Select_All)
    CheckBox MySelectAll;
    @BindView(R.id.My_All_Price)
    TextView MyAllPrice;
    @BindView(R.id.Settle_Accounts)
    TextView SettleAccounts;
    Unbinder unbinder;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private View inflate;
    private HashMap<String, String> headMap = new HashMap<>();
    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, String> dataMap = new HashMap<>();
    private ArrayList<ShoppingCarData.ResultBean> datas = new ArrayList<>();
    private IPresenterImpl iPresenter;
    private ShoppingCarAdapter adapter;
    private boolean childChecked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_shopping_trolley, null);
        unbinder = ButterKnife.bind(this, inflate);

        shared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        initView(inflate);
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.FIND_SHOPPING_CART_URL, headMap, map, ShoppingCarData.class);


        return inflate;
    }

    private void initView(View inflate) {

        adapter = new ShoppingCarAdapter(datas, getActivity());
        MyShoppingCar.setAdapter(adapter);

    }

    @OnClick({R.id.My_Select_All, R.id.Settle_Accounts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.My_Select_All:
                boolean b = adapter.checkedNoAll();
                adapter.checkedAll(!b);
                CalculateTheTotalPrice();
                adapter.notifyDataSetChanged();
                break;
            case R.id.Settle_Accounts:
                if (!childChecked) {
                    String goodsID = adapter.getGoodsID();
                    Intent intent = new Intent(getContext(), SubmitOrderActivity.class);
                    intent.putExtra("id", goodsID);
                    startActivity(intent);
                    datas.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "还没有选中任何商品哦", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void CalculateTheTotalPrice() {
        boolean b = adapter.checkedNoAll();
        MySelectAll.setChecked(b);
        float v = adapter.setShoppingNumber();
        MyAllPrice.setText(v + "0");
    }

    @Override
    public void success(Object data) {
        if (data instanceof ShoppingCarData) {
            ShoppingCarData shoppingCarData = (ShoppingCarData) data;
            datas.clear();
            datas.addAll(shoppingCarData.getResult());
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            MyShoppingCar.setLayoutManager(manager);
            adapter.notifyDataSetChanged();
            adapter.setClickCallBack(new ShoppingCarAdapter.ClickCallBack() {
                @Override
                public void callBack(int i, int k) {
                    if (datas.size() != 0) {

                        datas.remove(i);
                    }
                    int zhi = k;
                    if (datas != null) {
                        JSONArray jsonArray = new JSONArray();
                        for (int j = 0; j < datas.size(); j++) {

                            JSONObject object = null;
                            object = new JSONObject();
                            try {
                                object.put("commodityId", datas.get(j).getCommodityId());
                                object.put("count", 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(object);
                        }
                        dataMap.put("data", jsonArray.toString());
                        iPresenter.startRequestPut(Constant.SYNC_SHOPPING_CART_URL, headMap, dataMap, SynchronizationShoppingData.class);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            adapter.setCallBack(new ShoppingCarAdapter.ShoppingCallBack() {
                @Override
                public void setChildCheck(int childPosition) {
                    childChecked = adapter.isChildChecked(childPosition);
                    adapter.setChildCheck(childPosition, !childChecked);
                    CalculateTheTotalPrice();
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void setNumber(int num) {

                }
            });
        } else if (data instanceof SynchronizationShoppingData) {
            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void error(Object error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
