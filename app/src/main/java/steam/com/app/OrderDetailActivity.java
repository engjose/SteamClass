package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.mould.BaseRespBean;
import steam.com.app.mould.OrderBean;


public class OrderDetailActivity extends AppCompatActivity {

    private ImageView courseImg;
    private TextView merchantName;
    private TextView courseType;
    private TextView courseName;
    private TextView orderTime;
    private TextView coursePrice;
    private Button pay;
    private Button cancel;
    private OrderBean orderBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        setContentView(R.layout.activity_course_detail);
        initView();
        initListener();
    }

    private void initIntent() {
        Intent intent = getIntent();
        orderBean = (OrderBean) intent.getSerializableExtra("orderBean");
    }

    private void initView() {
        courseImg = findViewById(R.id.iv_course_img);
        merchantName = findViewById(R.id.tv_merchant_name);
        courseType = findViewById(R.id.tv_course_type);
        courseName = findViewById(R.id.tv_course_name);
        orderTime = findViewById(R.id.tv_order_time);
        coursePrice = findViewById(R.id.tv_course_price);
        pay = findViewById(R.id.btn_pay);
        cancel = findViewById(R.id.btn_cancel);

        Glide.with(this).load(orderBean.coursePic).into(courseImg);
        merchantName.setText(orderBean.merchantName);
        courseType.setText(orderBean.courseTypeDesc);
        courseName.setText(orderBean.courseName);
        orderTime.setText(orderBean.createTime);
        if ("0".equals(orderBean.priceType)) {
            coursePrice.setText("免费");
        } else {
            coursePrice.setText(orderBean.price + "");
        }
        if ("0".equals(orderBean.status)) {
            pay.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        } else {
            pay.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderPay(orderBean);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCancel(orderBean);
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
                            pay.setVisibility(View.GONE);
                            cancel.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "付款成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post("pay");
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
                            pay.setVisibility(View.GONE);
                            cancel.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "您已取消该课程", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post("cancel");
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
}