package soexample.bigfly.com.myjob_store.ui.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.AddressShowData;
import soexample.bigfly.com.myjob_store.bean.CreateOrderData;
import soexample.bigfly.com.myjob_store.bean.ShoppingCarData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.adapter.AddressAdaptiveAdapter;
import soexample.bigfly.com.myjob_store.ui.adapter.ShoppingSubMitAdapter;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;
import soexample.bigfly.com.myjob_store.utils.user_defined_view.UserPopwidow;

public class SubmitOrderActivity extends MyBaseActivity implements IView {
    @BindView(R.id.My_Insert_Address)
    TextView MyInsertAddress;
    @BindView(R.id.address_UserName)
    TextView addressUserName;
    @BindView(R.id.address_UserPhone)
    TextView addressUserPhone;
    @BindView(R.id.address_UserLocal)
    TextView addressUserLocal;
    @BindView(R.id.My_Insert_add_Address)
    RelativeLayout MyInsertAddAddress;
    @BindView(R.id.My_Shopping_Car)
    RecyclerView MyShoppingCar;
    @BindView(R.id.Total_Prices)
    TextView TotalPrices;
    @BindView(R.id.Submit_Order)
    TextView SubmitOrder;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, String> hashMap = new HashMap<>();
    private IPresenterImpl iPresenter;
    private PopupWindow mPopupWindow;
    private UserPopwidow userPopwidow;
    private float bgAlpha = 1f;
    private boolean bright = false;
    private static final long DURATION = 500;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;
    private RecyclerView My_Pop_Insert;
    private ArrayList<ShoppingCarData.ResultBean> datas = new ArrayList<>();
    private ArrayList<AddressShowData.ResultBean> addressDatas = new ArrayList();
    private ArrayList<ShoppingCarData.ResultBean> lists = new ArrayList<>();
    private ShoppingSubMitAdapter adapter;
    private AddressAdaptiveAdapter addressAdapter;
    private HashMap<String, Object> emptyMap = new HashMap<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_submit_order;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);


    }

    @Override
    protected void setClick() {

    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.FIND_SHOPPING_CART_URL, map, hashMap, ShoppingCarData.class);
        mPopupWindow = new PopupWindow(this);
        userPopwidow = new UserPopwidow();

    }

    @OnClick({R.id.My_Insert_Address, R.id.Submit_Order,R.id.My_Insert_add_Address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.My_Insert_Address:
                showPop();
                toggleBright();
                addressDatas.clear();
                break;
            case R.id.Submit_Order:
                iPresenter.startRequestPost(Constant.CREATE_ORDER_URL, emptyMap,map, CreateOrderData.class);
                finish();
                break;
            case R.id.My_Insert_add_Address:
                showPop();
                toggleBright();
                addressDatas.clear();
                break;
        }
    }

    private void toggleBright() {
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        userPopwidow.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        userPopwidow.addUpdateListener(new UserPopwidow.UpdateListener() {
            @Override
            public void progress(float progress) {
                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        userPopwidow.addEndListner(new UserPopwidow.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // 在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });

    }

    /**
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
        // everything behind this window will be dimmed.
        // 此方法用来设置浮动层，防止部分手机变暗无效
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void showPop() {
        // 设置布局文件
        View v = LayoutInflater.from(this).inflate(R.layout.pop_add, null);
        mPopupWindow.setContentView(v);
        My_Pop_Insert = v.findViewById(R.id.My_Pop_Insert);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        My_Pop_Insert.setLayoutManager(manager);

        iPresenter.startRequestGet(Constant.RECEIVE_ADDRESS_URL, map, hashMap, AddressShowData.class);

        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        mPopupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量
        mPopupWindow.showAsDropDown(MyInsertAddAddress, -100, 0);
        // 设置pop关闭监听，用于改变背景透明度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });
    }

    @Override
    public void success(Object data) {
        if (data instanceof ShoppingCarData) {
            ShoppingCarData shoppingCarData = (ShoppingCarData) data;
            datas.addAll(shoppingCarData.getResult());
            Intent intent = getIntent();
            String id = intent.getStringExtra("id");
            String[] split = id.split(",");
            float price = 0;
            //此处循环需要大循环套小循环
            for (int i = 0; i < datas.size(); i++) {
                int i1 = datas.get(i).getCommodityId();
                for (int j = 0; j < split.length; j++) {
                    int i2 = Integer.parseInt(split[j]);
                    if (i1 == i2) {
                        lists.add(datas.get(i));
                        Double price1 = Double.valueOf(lists.get(j).getPrice());
                        emptyMap.put("totalPrice", price1 + "");
                        price += datas.get(i).getCount() * datas.get(i).getPrice();
                        int zhi = lists.get(j).getCommodityId();
                        if (lists != null) {
                            JSONArray jsonArray = new JSONArray();
                            JSONObject object = null;
                            object = new JSONObject();
                            try {
                                object.put("commodityId", zhi);
                                object.put("amount", 1);
                                jsonArray.put(object);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            emptyMap.put("orderInfo", jsonArray.toString());
                            jsonArray = null;
                        }
                    }
                }
            }
            TotalPrices.setText("共" + split.length + "件商品,需付款" + price + ".00元");

            LinearLayoutManager manager = new LinearLayoutManager(this);
            MyShoppingCar.setLayoutManager(manager);
            adapter = new ShoppingSubMitAdapter(lists, this);
            MyShoppingCar.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else if (data instanceof AddressShowData) {
            AddressShowData addressShowData = (AddressShowData) data;
            addressDatas.addAll(addressShowData.getResult());
            addressAdapter = new AddressAdaptiveAdapter(addressDatas, this);
            My_Pop_Insert.setAdapter(addressAdapter);
            addressAdapter.notifyDataSetChanged();

            if (addressShowData.getMessage().equals("查询成功")) {
                MyInsertAddress.setVisibility(View.VISIBLE);
                MyInsertAddAddress.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "还没有地址哦", Toast.LENGTH_SHORT).show();
                return;
            }
            addressAdapter.setClickCallBack(new AddressAdaptiveAdapter.ClickCallBack() {

                private int id;

                @Override
                public void callBack(int i) {
                    addressUserName.setText(addressDatas.get(i).getRealName());
                    addressUserPhone.setText(addressDatas.get(i).getPhone());
                    addressUserLocal.setText(addressDatas.get(i).getAddress());
                    id = addressDatas.get(i).getId();
                    emptyMap.put("addressId", id + "");
                    MyInsertAddAddress.setVisibility(View.VISIBLE);
                    MyInsertAddress.setVisibility(View.GONE);
                    addressAdapter.notifyDataSetChanged();
                    mPopupWindow.dismiss();

                }
            });
        } else if (data instanceof CreateOrderData) {
            CreateOrderData createOrderData = (CreateOrderData) data;
            Toast.makeText(this, createOrderData.getMessage()+"快去购物车结算吧", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(Object error) {
        Log.e("添加收货地址有错", "error: " + error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDatach();
    }

}
