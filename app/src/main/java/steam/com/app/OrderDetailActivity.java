package steam.com.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * File: OrderDetailActivity.java
 * <p>
 * Author: zshp
 * <p>
 * Create: 2019/4/20 8:31 PM
 *
 * @author zshp
 */
public class OrderDetailActivity extends AppCompatActivity {

    private ImageView courseImg;
    private TextView merchantName;
    private TextView courseType;
    private TextView courseName;
    private TextView orderTime;
    private TextView coursePrice;
    private Button pay;
    private Button cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initView();
        initListener();
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
    }

    private void initListener() {
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}