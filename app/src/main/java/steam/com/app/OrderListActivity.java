package steam.com.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import steam.com.app.mould.OrderBean;

/**
 * File: OrderListActivity.java
 * Create: 2019/4/20 4:38 PM
 *
 * @author zshp
 */
public class OrderListActivity extends AppCompatActivity implements View.OnClickListener {
    private List<OrderBean> orderList = new ArrayList<>();

    private RecyclerView mRv;
    private OrderListAdapter orderListAdapter;
    private TextView mDraftFilter;
    private TextView mPayedFilter;
    private TextView mCancelFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        for (int i = 0; i < 20; i++) {
            OrderBean orderBean = new OrderBean();
            orderList.add(orderBean);
        }
        initView();
        initListener();
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
                Toast.makeText(getApplicationContext(), "item", Toast.LENGTH_SHORT).show();
            }
        });

        orderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int id = view.getId();
                switch (id) {
                    case R.id.btn_cancel:
                        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_pay:
                        Toast.makeText(getApplicationContext(), "pay", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
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
                break;
            case R.id.tv_payed:
                mDraftFilter.setSelected(false);
                mPayedFilter.setSelected(true);
                mCancelFilter.setSelected(false);
                break;
            case R.id.tv_cancel:
                mDraftFilter.setSelected(false);
                mPayedFilter.setSelected(false);
                mCancelFilter.setSelected(true);
                break;
            default:
                break;
        }
    }
}
