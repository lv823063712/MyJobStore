package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.GoodsParticularsData;
import soexample.bigfly.com.myjob_store.bean.ShoppingCarData;
import soexample.bigfly.com.myjob_store.bean.SynchronizationShoppingData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.adapter.DetailsAdapter;
import soexample.bigfly.com.myjob_store.ui.adapter.MyViewPageAdapter;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class DetailsActivity extends MyBaseActivity implements IView {
    @BindView(R.id.My_Details_Back)
    ImageView MyDetailsBack;
    @BindView(R.id.My_Dteails_Banner)
    ViewPager MyDteailsBanner;
    @BindView(R.id.My_Details_Show)
    RecyclerView MyDetailsShow;
    @BindView(R.id.My_Web)
    WebView MyWeb;
    @BindView(R.id.My_Details_Comment)
    RecyclerView MyDetailsComment;
    @BindView(R.id.AddShoppingCar)
    Button AddShoppingCar;
    @BindView(R.id.AddBuyBuyBuy)
    Button AddBuyBuyBuy;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private int zhi;
    private IPresenterImpl iPresenter;
    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, String> headMap = new HashMap<>();
    private HashMap<String, String> dataMap = new HashMap<>();
    private ArrayList<GoodsParticularsData.ResultBean> datas = new ArrayList<>();
    private ArrayList<ShoppingCarData.ResultBean> myLists = new ArrayList<>();
    private List<String> lists = new ArrayList<>();
    private JSONArray jsonArray;
    private DetailsAdapter detailsAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_details;
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
        zhi = intent.getIntExtra("zhi", 0);
        map.put("commodityId", zhi + "");

    }

    @Override
    protected void setClick() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        MyDetailsShow.setLayoutManager(manager);
        detailsAdapter = new DetailsAdapter(datas, this);
        MyDetailsShow.setAdapter(detailsAdapter);

    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.FIND_COMMODITY_DETAILS_BYID_URL, headMap, map, GoodsParticularsData.class);
    }

    @OnClick({R.id.My_Details_Back, R.id.AddShoppingCar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.My_Details_Back:
                finish();
                break;
            case R.id.AddShoppingCar:
                iPresenter.startRequestGet(Constant.FIND_SHOPPING_CART_URL, map,headMap, ShoppingCarData.class);
                break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof GoodsParticularsData) {
            GoodsParticularsData goodsParticularsData = (GoodsParticularsData) data;
            datas.add(goodsParticularsData.getResult());
            String picture = goodsParticularsData.getResult().getPicture();
            String[] split = picture.split("\\,");
            for (int i = 0; i < split.length; i++) {
                lists.add(split[i]);
            }
            MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(lists, this);
            MyDteailsBanner.setAdapter(myViewPageAdapter);

            WebSettings settings = MyWeb.getSettings();
            settings.setJavaScriptEnabled(true);//支持JS
            String js = "<script type=\"text/javascript\">" +
                    "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                    "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                    "imgs[i].style.width = '100%';" +  // 宽度改为100%
                    "imgs[i].style.height = 'auto';" +
                    "}" +
                    "</script>";
            MyWeb.loadDataWithBaseURL(null, goodsParticularsData.getResult().getDetails() + js, "text/html", "utf-8", null);
        } else if (data instanceof ShoppingCarData) {
            ShoppingCarData carData = (ShoppingCarData) data;
            myLists.addAll(carData.getResult());
            if (myLists != null) {
                jsonArray = new JSONArray();
                for (int i = 0; i < myLists.size(); i++) {
                    JSONObject object = null;
                    object = new JSONObject();
                    try {
                        //查出来的值
                        object.put("commodityId", myLists.get(i).getCommodityId());
                        object.put("count", 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(object);
                }
                JSONObject object = null;
                object = new JSONObject();
                try {
                    //将要添加的值
                    object.put("commodityId", zhi);
                    object.put("count", 1);
                    jsonArray.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dataMap.put("data", jsonArray.toString());
                iPresenter.startRequestPut(Constant.SYNC_SHOPPING_CART_URL, map, dataMap, SynchronizationShoppingData.class);
            }
        } else if (data instanceof SynchronizationShoppingData) {
            Toast.makeText(this, "添加成功,快去购物车结算吧", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(Object error) {
        Log.e("zzz", "error: "+error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDatach();
    }
}
