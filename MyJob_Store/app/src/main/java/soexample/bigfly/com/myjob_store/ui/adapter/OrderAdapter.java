package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.SelectData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/23   9:42<p>
 * <p>更改时间：2019/2/23   9:42<p>
 * <p>版本号：1<p>
 */

public class OrderAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
    private List<SelectData.OrderListBean> list;
    private Context context;
    private int type;
    private final int one = 1;
    private final int two = 2;
    private final int three = 3;
    private final int zero = 0;
    private final int nine = 9;

    public OrderAdapter(List<SelectData.OrderListBean> list, Context context, int type) {
        this.list = list;
        this.context = context;
        this.type = type;
    }


    public void settype(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        XRecyclerView.ViewHolder holder = null;
        switch (i) {
            case one:
//                这是待付款
                view = View.inflate(context, R.layout.order_pendingpayment_item, null);
                holder = new AllHolder(view);
                break;
            case two:
//                这是待收货
                view = View.inflate(context, R.layout.order_receivlinghoods_item, null);
                holder = new ReceiVlingHolder(view);
                break;
            case three:
//                这是待评价
                view = View.inflate(context, R.layout.order_evaluate_item, null);
                holder = new pingjiaHolder(view);
                break;
            case nine:
//                这是已完成
                view = View.inflate(context, R.layout.order_evaluate_item, null);
                holder =new OKHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof AllHolder) {
            ((AllHolder) viewHolder).order_PendingPayment_number.setText(list.get(i).getOrderId());
//           下单时间没有
//           ((AllHolder) viewHolder).order_PendingPayment_time.setText(list.get(i).get);
//           支付金额
            ((AllHolder) viewHolder).order_PendingPayment_price.setText(list.get(i).getPayAmount() + "");
//           商品数量
            int size = 0;
            for (int j = 0; j < list.get(i).getDetailList().size(); j++) {
                size += list.get(i).getDetailList().get(j).getCommodityCount();
            }
            ((AllHolder) viewHolder).order_PendingPayment_size.setText(size + "");
            OrderitemAdapter adapter = new OrderitemAdapter(context, list.get(i).getDetailList(), list.get(i).getOrderStatus());
            ((AllHolder) viewHolder).order_PendingPayment_recycle.setLayoutManager(new LinearLayoutManager(context));
            ((AllHolder) viewHolder).order_PendingPayment_recycle.setAdapter(adapter);

            ((AllHolder) viewHolder).order_PendingPayment_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclick != null) {
                        onclick.btndelete(i, list.get(i).getOrderId());
                    }
                }
            });
            ((AllHolder) viewHolder).order_PendingPayment_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclick != null) {
                        onclick.btnpayment(i, list.get(i).getOrderId(),list.get(i).getPayAmount());
                    }
                }
            });
        } else if (viewHolder instanceof ReceiVlingHolder) {

            ((ReceiVlingHolder) viewHolder).order_ReceivingGoods_number.setText(list.get(i).getOrderId());
//           下单时间没有
//           ((AllHolder) viewHolder).order_PendingPayment_time.setText(list.get(i).get);
            ((ReceiVlingHolder) viewHolder).order_ReceivingGoods_kddh.setText(list.get(i).getExpressSn());
//              这是拿出来的那个样子
            OrderitemAdapter adapter = new OrderitemAdapter(context, list.get(i).getDetailList(),list.get(i).getOrderStatus());
            ((ReceiVlingHolder) viewHolder).order_ReceivingGoods_recycle.setLayoutManager(new LinearLayoutManager(context));
            ((ReceiVlingHolder) viewHolder).order_ReceivingGoods_recycle.setAdapter(adapter);

            ((ReceiVlingHolder) viewHolder).order_ReceivingGoods_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclick != null) {
                        onclick.ReceivingGoods(i, list.get(i).getOrderId());
                    }
                }
            });
        } else if (viewHolder instanceof pingjiaHolder) {
            ((pingjiaHolder) viewHolder).order_pingjia_number.setText(list.get(i).getOrderId());
//           下单时间没有
//           ((AllHolder) viewHolder).order_PendingPayment_time.setText(list.get(i).get);
            OrderitemAdapter adapter = new OrderitemAdapter(context, list.get(i).getDetailList(), list.get(i).getOrderStatus());
            ((pingjiaHolder) viewHolder).order_pingjia_recycle.setLayoutManager(new LinearLayoutManager(context));
            ((pingjiaHolder) viewHolder).order_pingjia_recycle.setAdapter(adapter);
            adapter.onclick(new OrderitemAdapter.getOnclick() {
                @Override
                public void pingjia(String headerpic, String name,String id,String price) {
                    if(onclick!=null){
                        onclick.pingjia(i,list.get(i).getOrderId(),headerpic,name,id,price);
                    }
                }
            });

        }else if(viewHolder instanceof  OKHolder){
            ((OKHolder) viewHolder).order_pingjia_number.setText(list.get(i).getOrderId());
//           下单时间没有
//           ((AllHolder) viewHolder).order_PendingPayment_time.setText(list.get(i).get);
            OrderitemAdapter adapter = new OrderitemAdapter(context, list.get(i).getDetailList(), list.get(i).getOrderStatus());
            ((OKHolder) viewHolder).order_pingjia_recycle.setLayoutManager(new LinearLayoutManager(context));
            ((OKHolder) viewHolder).order_pingjia_recycle.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 0) {
            return list.get(position).getOrderStatus();
        } else {
            if (list.get(position).getOrderStatus() == 1) {
                return 1;
            } else if (list.get(position).getOrderStatus() == 2) {
                return 2;
            } else if (list.get(position).getOrderStatus() == 3) {
                return 3;
            } else {
                return 9;
            }
        }
    }

    /**
     * 待付款
     */
    class AllHolder extends XRecyclerView.ViewHolder {
        public TextView order_PendingPayment_number;
        public TextView order_PendingPayment_time;
        public RecyclerView order_PendingPayment_recycle;
        public TextView order_PendingPayment_size;
        public TextView order_PendingPayment_price;
        public Button order_PendingPayment_cancel;
        public Button order_PendingPayment_payment;

        public AllHolder(View rootView) {
            super(rootView);
            this.order_PendingPayment_number = (TextView) rootView.findViewById(R.id.order_PendingPayment_number);
            this.order_PendingPayment_time = (TextView) rootView.findViewById(R.id.order_PendingPayment_time);
            this.order_PendingPayment_recycle = (RecyclerView) rootView.findViewById(R.id.order_PendingPayment_recycle);
            this.order_PendingPayment_size = (TextView) rootView.findViewById(R.id.order_PendingPayment_size);
            this.order_PendingPayment_price = (TextView) rootView.findViewById(R.id.order_PendingPayment_price);
            this.order_PendingPayment_cancel = (Button) rootView.findViewById(R.id.order_PendingPayment_cancel);
            this.order_PendingPayment_payment = (Button) rootView.findViewById(R.id.order_PendingPayment_payment);
        }

    }

    //    转化时间单位
    public static String longToDate(long lo) {

        Date date = new Date(lo);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sd.format(date);
    }

    /**
     * 待收货
     */
    class ReceiVlingHolder extends XRecyclerView.ViewHolder {
        public TextView order_ReceivingGoods_number;
        public TextView order_ReceivingGoods_time;
        public RecyclerView order_ReceivingGoods_recycle;
        public TextView order_ReceivingGoods_pjgs;
        public TextView order_ReceivingGoods_kddh;
        public Button order_ReceivingGoods_ok;

        public ReceiVlingHolder(View rootView) {
            super(rootView);
            this.order_ReceivingGoods_number = (TextView) rootView.findViewById(R.id.order_ReceivingGoods_number);
            this.order_ReceivingGoods_time = (TextView) rootView.findViewById(R.id.order_ReceivingGoods_time);
            this.order_ReceivingGoods_recycle = (RecyclerView) rootView.findViewById(R.id.order_ReceivingGoods_recycle);
            this.order_ReceivingGoods_pjgs = (TextView) rootView.findViewById(R.id.order_ReceivingGoods_pjgs);
            this.order_ReceivingGoods_kddh = (TextView) rootView.findViewById(R.id.order_ReceivingGoods_kddh);
            this.order_ReceivingGoods_ok = (Button) rootView.findViewById(R.id.order_ReceivingGoods_ok);
        }
    }

    /**
     * 评价
     */
    class pingjiaHolder extends XRecyclerView.ViewHolder {
        public TextView order_pingjia_number;
        public ImageView order_pingjia_more;
        public RecyclerView order_pingjia_recycle;
        public TextView order_pingjia_time;

        public pingjiaHolder(View rootView) {
            super(rootView);
            this.order_pingjia_number = (TextView) rootView.findViewById(R.id.order_pingjia_number);
            this.order_pingjia_more = (ImageView) rootView.findViewById(R.id.order_pingjia_more);
            this.order_pingjia_recycle = (RecyclerView) rootView.findViewById(R.id.order_pingjia_recycle);
            this.order_pingjia_time = (TextView) rootView.findViewById(R.id.order_pingjia_time);
        }
    }

    /**
     * 完成
     */
    class OKHolder extends XRecyclerView.ViewHolder {
        public View rootView;
        public TextView order_pingjia_ddh;
        public TextView order_pingjia_number;
        public ImageView order_pingjia_more;
        public RecyclerView order_pingjia_recycle;
        public TextView order_pingjia_time;

        public OKHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.order_pingjia_ddh = (TextView) rootView.findViewById(R.id.order_pingjia_ddh);
            this.order_pingjia_number = (TextView) rootView.findViewById(R.id.order_pingjia_number);
            this.order_pingjia_more = (ImageView) rootView.findViewById(R.id.order_pingjia_more);
            this.order_pingjia_recycle = (RecyclerView) rootView.findViewById(R.id.order_pingjia_recycle);
            this.order_pingjia_time = (TextView) rootView.findViewById(R.id.order_pingjia_time);
        }

    }

    public interface onclick {
        //        支付订单
        void btnpayment(int i, String orderid,double payment);

        //        取消订单
        void btndelete(int i, String orderid);

        //        确认收货
        void ReceivingGoods(int i, String orderid);
        //        评价
        void pingjia(int i, String orderid,String img,String name,String id,String price);
    }

    private onclick onclick;

    public void getOnclick(onclick onclick) {
        this.onclick = onclick;
    }
}
