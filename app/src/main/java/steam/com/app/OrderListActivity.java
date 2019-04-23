package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.mould.BaseRespBean;
import steam.com.app.mould.OrderBean;
import steam.com.app.mould.OrderInfo;

public class OrderListActivity extends AppCompatActivity implements View.OnClickListener {
    private List<OrderBean> orderList = new ArrayList<>();

    private RecyclerView mRv;
    private OrderListAdapter orderListAdapter;
    private TextView mDraftFilter;
    private TextView mPayedFilter;
    private TextView mCancelFilter;
    private OrderInfo orderInfo;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initIntent();
        setContentView(R.layout.activity_order_list);
        initView();
        initListener();
    }

    private void initIntent() {
        Intent intent = getIntent();
        orderInfo = (OrderInfo) intent.getSerializableExtra("orderInfo");
        orderList.clear();
        orderList.addAll(orderInfo.draftOrderList);
    }

    private void initView() {
        mDraftFilter = findViewById(R.id.tv_draft);
        mPayedFilter = findViewById(R.id.tv_payed);
        mCancelFilter = findViewById(R.id.tv_cancel);
        mDraftFilter.setSelected(true);
        mPayedFilter.setSelected(false);
        mCancelFilter.setSelected(false);

        mRv = findViewById(R.id.rv_order_list);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orderListAdapter = new OrderListAdapter(R.layout.item_order_list, orderList, false);
        mRv.setAdapter(orderListAdapter);
    }

    private void initListener() {
        mDraftFilter.setOnClickListener(this);
        mPayedFilter.setOnClickListener(this);
        mCancelFilter.setOnClickListener(this);

        orderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderBean item = (OrderBean) adapter.getItem(position);
                if (item == null) {
                    return;
                }
                Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                intent.putExtra("orderBean", item);
                startActivity(intent);
            }
        });

        orderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OrderListActivity.this.position = position;
                OrderBean item = (OrderBean) adapter.getItem(position);
                int id = view.getId();
                switch (id) {
                    case R.id.btn_cancel:
                        orderCancel(item);
                        break;
                    case R.id.btn_pay:
                        orderPay(item);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private void orderPay(OrderBean item) {
        ApiServeice.orderPay(item.orderId)
                .subscribe(new Consumer<BaseRespBean>() {
                    @Override
                    public void accept(BaseRespBean baseRespBean) throws Exception {
                        if (baseRespBean.code == 0) {
                            orderList.remove(item);
                            orderInfo.draftOrderList.remove(item);
                            orderListAdapter.notifyDataSetChanged();
                            item.status = "1";
                            orderInfo.payOrderList.add(item);
                        } else {
                            ApiServeice.tokenInvalid(getApplicationContext(), baseRespBean.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("orderPay", throwable.getMessage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void orderCancel(OrderBean item) {
        ApiServeice.orderCancel(item.orderId)
                .subscribe(new Consumer<BaseRespBean>() {
                    @Override
                    public void accept(BaseRespBean baseRespBean) throws Exception {
                        if (baseRespBean.code == 0) {
                            orderInfo.draftOrderList.remove(item);
                            orderList.remove(item);
                            orderListAdapter.notifyDataSetChanged();
                            item.status = "2";
                            orderInfo.cancelOrderList.add(item);
                        } else {
                            ApiServeice.tokenInvalid(getApplicationContext(), baseRespBean.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("orderCancel", throwable.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_draft:
                mDraftFilter.setSelected(true);
                mPayedFilter.setSelected(false);
                mCancelFilter.setSelected(false);
                orderList.clear();
                orderList.addAll(orderInfo.draftOrderList);
                orderListAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_payed:
                mDraftFilter.setSelected(false);
                mPayedFilter.setSelected(true);
                mCancelFilter.setSelected(false);
                orderList.clear();
                orderList.addAll(orderInfo.payOrderList);
                orderListAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_cancel:
                mDraftFilter.setSelected(false);
                mPayedFilter.setSelected(false);
                mCancelFilter.setSelected(true);
                orderList.clear();
                orderList.addAll(orderInfo.cancelOrderList);
                orderListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String type) {
        if ("pay".equals(type)) {
            OrderBean item = orderListAdapter.getItem(position);
            orderInfo.draftOrderList.remove(item);
            orderList.remove(item);
            orderListAdapter.notifyDataSetChanged();
            item.status = "1";
            orderInfo.payOrderList.add(item);
        }
        if ("cancel".equals(type)) {
            OrderBean item = orderListAdapter.getItem(position);
            orderInfo.draftOrderList.remove(item);
            orderList.remove(item);
            orderListAdapter.notifyDataSetChanged();
            item.status = "2";
            orderInfo.cancelOrderList.add(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
