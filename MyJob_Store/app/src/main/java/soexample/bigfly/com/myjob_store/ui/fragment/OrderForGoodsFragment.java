package soexample.bigfly.com.myjob_store.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.RecordData;
import soexample.bigfly.com.myjob_store.bean.SelectData;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.activity.CommentActivity;
import soexample.bigfly.com.myjob_store.ui.activity.PayMentActivity;
import soexample.bigfly.com.myjob_store.ui.adapter.OrderAdapter;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;
import soexample.bigfly.com.myjob_store.utils.mystyle.XrecycleStyle;

import static com.luck.picture.lib.permissions.RxPermissions.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderForGoodsFragment extends Fragment implements IView {

    @BindView(R.id.All_List)
    LinearLayout AllList;
    @BindView(R.id.Wait_Money)
    LinearLayout WaitMoney;
    @BindView(R.id.Wait_Receive)
    LinearLayout WaitReceive;
    @BindView(R.id.Wait_Comment)
    LinearLayout WaitComment;
    @BindView(R.id.Have_Finish)
    LinearLayout HaveFinish;
    @BindView(R.id.My_All_List_Fragment)
    XRecyclerView MyAllListFragment;
    Unbinder unbinder;
    @BindView(R.id.order_allorders_dian)
    ImageView orderAllordersDian;
    @BindView(R.id.order_PendingPayment_dian)
    ImageView orderPendingPaymentDian;
    @BindView(R.id.order_ReceivingGoods_dian)
    ImageView orderReceivingGoodsDian;
    @BindView(R.id.order_valuate_dian)
    ImageView orderValuateDian;
    @BindView(R.id.order_complete_dian)
    ImageView orderCompleteDian;
    @BindView(R.id.order_dian_lin)
    LinearLayout orderDianLin;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private int index = 1;
    private HashMap<String, String> headMap = new HashMap<>();
    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, Object> map1 = new HashMap<>();
    private int id;
    /**
     * 这是一个订单的数据
     */
    private List<SelectData.OrderListBean> list = new ArrayList<>();
    private IPresenterImpl iPresenter;
    private OrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_order_for_goods, null);
        unbinder = ButterKnife.bind(this, inflate);
        sphq();
        myClickLisenter();
        pressent();
        return inflate;

    }

    private void sphq() {
        shared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        map.put("status", 0 + "");
        map.put("page", index + "");
        map.put("count", 20 + "");
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp_10);
        MyAllListFragment.addItemDecoration(new XrecycleStyle(spacingInPixels));
        MyAllListFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        list.clear();
        adapter = new OrderAdapter(list, getContext(), 0);
        MyAllListFragment.setAdapter(adapter);
    }

    private void pressent() {
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequestGet(Constant.FIND_ORDER_LIST_BYSTATUS_URL, headMap, map, SelectData.class);
    }


    private void myClickLisenter() {
        adapter.getOnclick(new OrderAdapter.onclick() {

            @Override
            public void btnpayment(int i, String orderid, double payment) {
                /**
                 * 支付
                 */
                id = i;
                Intent intent = new Intent(getActivity(), PayMentActivity.class);
                intent.putExtra("orderId", orderid);
                intent.putExtra("payAmount",payment);
                startActivity(intent);
            }

            @Override
            public void btndelete(int i, String orderid) {
                /**
                 * 删除订单
                 */
                id = i;
                Map<String, Object> map = new HashMap<>();
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("userId", userId + "");
                headerMap.put("sessionId", sessionId);
                map.put("orderId", orderid);
                iPresenter.startRequesDelete(Constant.DELETE_ORDER_URL, headMap, map, RecordData.class);
            }

            @Override
            public void ReceivingGoods(int i, String orderid) {
                /**
                 *确认收货
                 */
                id = i;
                Map<String, String> map = new HashMap<>();
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("userId", userId + "");
                headerMap.put("sessionId", sessionId);
                map.put("orderId", orderid);
                iPresenter.startRequestPut(Constant.CONFIRM_RECEIPT_URL, headMap, map, RecordData.class);
            }

            @Override
            public void pingjia(int i, String orderid, String img, String name, String id, String price) {
                /**
                 *p评价
                 */
                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("commodityId", id);
                intent.putExtra("orderId", orderid);
                intent.putExtra("img", img);
                intent.putExtra("price", price);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.All_List, R.id.Wait_Money, R.id.Wait_Receive, R.id.Wait_Comment, R.id.Have_Finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.All_List:
                adapter.settype(0);
                list.clear();
                Map<String, String> map1 = new HashMap<>();
                Map<String, String> headerMap1 = new HashMap<>();
                headerMap1.put("userId", userId + "");
                headerMap1.put("sessionId", sessionId);
                map1.put("status", "0");
                map1.put("page", index + "");
                map1.put("count", "10");
                iPresenter.startRequestGet(Constant.FIND_ORDER_LIST_BYSTATUS_URL, headerMap1, map1, SelectData.class);
                orderAllordersDian.setVisibility(View.VISIBLE);
                orderPendingPaymentDian.setVisibility(View.INVISIBLE);
                orderReceivingGoodsDian.setVisibility(View.INVISIBLE);
                orderValuateDian.setVisibility(View.INVISIBLE);
                orderCompleteDian.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
                break;
            case R.id.Wait_Money:
                adapter.settype(1);
                list.clear();
                Map<String, String> map2 = new HashMap<>();
                Map<String, String> headerMap2 = new HashMap<>();
                headerMap2.put("userId", userId + "");
                headerMap2.put("sessionId", sessionId);
                map2.put("status", "1");
                map2.put("page", index + "");
                map2.put("count", "10");
                iPresenter.startRequestGet(Constant.FIND_ORDER_LIST_BYSTATUS_URL, headerMap2, map2, SelectData.class);
                orderAllordersDian.setVisibility(View.INVISIBLE);
                orderPendingPaymentDian.setVisibility(View.VISIBLE);
                orderReceivingGoodsDian.setVisibility(View.INVISIBLE);
                orderValuateDian.setVisibility(View.INVISIBLE);
                orderCompleteDian.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
                break;
            case R.id.Wait_Receive:
                adapter.settype(2);
                list.clear();
                Map<String, String> map3 = new HashMap<>();
                Map<String, String> headerMap3 = new HashMap<>();
                headerMap3.put("userId", userId + "");
                headerMap3.put("sessionId", sessionId);
                map3.put("status", "2");
                map3.put("page", index + "");
                map3.put("count", "10");
                iPresenter.startRequestGet(Constant.FIND_ORDER_LIST_BYSTATUS_URL, headerMap3, map3, SelectData.class);
                orderAllordersDian.setVisibility(View.INVISIBLE);
                orderPendingPaymentDian.setVisibility(View.INVISIBLE);
                orderReceivingGoodsDian.setVisibility(View.VISIBLE);
                orderValuateDian.setVisibility(View.INVISIBLE);
                orderCompleteDian.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
                break;
            case R.id.Wait_Comment:
                adapter.settype(3);
                list.clear();
                Map<String, String> map4 = new HashMap<>();
                Map<String, String> headerMap4 = new HashMap<>();
                headerMap4.put("userId", userId + "");
                headerMap4.put("sessionId", sessionId);
                map4.put("status", "3");
                map4.put("page", index + "");
                map4.put("count", "10");
                iPresenter.startRequestGet(Constant.FIND_ORDER_LIST_BYSTATUS_URL, headerMap4, map4, SelectData.class);
                orderAllordersDian.setVisibility(View.INVISIBLE);
                orderPendingPaymentDian.setVisibility(View.INVISIBLE);
                orderReceivingGoodsDian.setVisibility(View.INVISIBLE);
                orderValuateDian.setVisibility(View.VISIBLE);
                orderCompleteDian.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
                break;
            case R.id.Have_Finish:
                adapter.settype(9);
                list.clear();
                Map<String, String> map5 = new HashMap<>();
                Map<String, String> headerMap5 = new HashMap<>();
                headerMap5.put("userId", userId + "");
                headerMap5.put("sessionId", sessionId);
                map5.put("status", "9");
                map5.put("page", index + "");
                map5.put("count", "10");
                iPresenter.startRequestGet(Constant.FIND_ORDER_LIST_BYSTATUS_URL, headerMap5, map5, SelectData.class);
                orderAllordersDian.setVisibility(View.INVISIBLE);
                orderPendingPaymentDian.setVisibility(View.INVISIBLE);
                orderReceivingGoodsDian.setVisibility(View.INVISIBLE);
                orderValuateDian.setVisibility(View.INVISIBLE);
                orderCompleteDian.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof SelectData) {
            SelectData selectData = (SelectData) data;
            list.addAll(selectData.getOrderList());
            adapter.notifyDataSetChanged();
        } else if (data instanceof RecordData) {
            RecordData recordData = (RecordData) data;
            if (recordData.getStatus().equals("0000")) {
                Toast.makeText(getContext(), recordData.getMessage(), Toast.LENGTH_SHORT).show();
                list.remove(id);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), recordData.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void error(Object error) {
        Log.e("订单有问题哦", "error: " + error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
