package steam.com.app;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CollectDetailBean;
import steam.com.app.mould.CollectDetailResq;
import steam.com.app.mould.ColletAddReq;
import steam.com.app.mould.ColletAddResq;
import steam.com.app.mould.ColletCancelResq;
import steam.com.app.mould.CourseBean;
import steam.com.app.mould.CourseDetailResq;
import steam.com.app.mould.OrderPlaceResp;

public class CourseDetailActivity3 extends AppCompatActivity{
    private Button mbtn_coursecollect3;
    private Button mbtn_coursecollected3;
    private Button mbtn_learn3;
    private TextView mcourse_name3;
    private TextView mcourse_price3;
    private TextView mcourse_info3;
    private TextView mteacher_name3;
    private TextView mteacher_info3;
    private TextView mmerchant_name3;
    private TextView mmerchant_info3;
    private JzvdStd mvideoplayer3;
    private CourseBean courseBean;
    private ColletAddReq colletAddReq;
    private CollectDetailBean collectDetailBean;
    private List<CollectDetailBean> collectlList = new ArrayList<>();
    private CollectAdapter collectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail3);//etContentView（）方法是给当前活动加载一个布局
        Intent intent = getIntent();
        collectDetailBean = (CollectDetailBean) intent.getSerializableExtra("collectDetailBean");
        initView();
        initListener();
        getcolletlist();
    }

    // xml页面布局中获得对应的UI控件
    private void initView() {
        mbtn_coursecollect3 = findViewById(R.id.btn_coursecollect3);
        mbtn_coursecollected3 = findViewById(R.id.btn_coursecollected3);
        mbtn_learn3 = findViewById(R.id.btn_learn3);
        mcourse_name3 = findViewById(R.id.course_name3);
        mcourse_price3 = findViewById(R.id.course_price3);
        mcourse_info3 = findViewById(R.id.course_info3);
        mteacher_name3 = findViewById(R.id.teacher_name3);
        mteacher_info3 = findViewById(R.id.teacher_info3);
        mmerchant_name3 = findViewById(R.id.merchant_name3);
        mmerchant_info3 = findViewById(R.id.merchant_info3);

        Log.i("collectDetailBean", collectDetailBean.courseName);
        mcourse_name3.setText(collectDetailBean.courseName);
        mcourse_price3.setText(collectDetailBean.priceType);
        if (collectDetailBean.priceType.equals("0")) {
            mcourse_price3.setText("免费");
        } else {
            mcourse_price3.setText("¥" + collectDetailBean.price + "");
        }
        mcourse_info3.setText(collectDetailBean.courseInfo);
        mteacher_name3.setText(collectDetailBean.teacherName);
        mteacher_info3.setText(collectDetailBean.teacherInfo);
        mmerchant_name3.setText(collectDetailBean.merchantName);
        mmerchant_info3.setText(collectDetailBean.merchantInfo);
        JzvdStd jzvdStd = findViewById(R.id.videoplayer3);
        Log.i("videourl", collectDetailBean.videoUrl);
        jzvdStd.setUp(collectDetailBean.videoUrl, collectDetailBean.courseName, Jzvd.SCREEN_WINDOW_NORMAL);
        Glide.with(GlobalCache.getContext()).load(collectDetailBean.coursePic).into(jzvdStd.thumbImageView);


        //已经学习的课程不能重复下单
        if (collectDetailBean.isBuy) {
            mbtn_learn3.setVisibility(View.INVISIBLE);
        }

        //没有收藏为0，收藏按钮显示，已收藏按钮消失
        if(collectDetailBean.isCollect.equals("0")) {
            mbtn_coursecollect3.setVisibility(View.VISIBLE);
            mbtn_coursecollected3.setVisibility(View.GONE);
        }else { //已收藏为1，收藏按钮消失，已收藏按钮显示
            mbtn_coursecollect3.setVisibility(View.GONE);
            mbtn_coursecollected3.setVisibility(View.VISIBLE);
        }

    }
    private void initListener() {
        mbtn_coursecollect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collet(collectDetailBean);
            }
        });

        mbtn_coursecollected3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelcollet(collectDetailBean);
            }
        });
        mbtn_learn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderPlace(collectDetailBean);
            }
        });

    }
    /**
      * 连接口
     */
    //获取收藏列表信息
    @SuppressLint("CheckResult")
    private void getcolletlist() {
        ApiServeice.colletList()
                .subscribe(new Consumer<CollectDetailResq>() {
                    @Override
                    public void accept(CollectDetailResq collectDetailResq) {
                        if (collectDetailResq.code == 0) {

                        } else {
                            ApiServeice.tokenInvalid(CourseDetailActivity3.this, collectDetailResq.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("loadCourseData", throwable.getMessage());
                    }
                });
    }

    /**
     * 下单
     */
    @SuppressLint("CheckResult")
    private void orderPlace(CollectDetailBean item) {
        ApiServeice.orderPlace(item.courseId, item.price)
                .subscribe(new Consumer<OrderPlaceResp>() {
                    @Override
                    public void accept(OrderPlaceResp orderPlaceResp) throws Exception {
                        if (orderPlaceResp.code == 0) {
                            mbtn_learn3.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "添加成功，请去订单列表查看", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post("add");
                        } else {
                            ApiServeice.tokenInvalid(getApplicationContext(), orderPlaceResp.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("order", throwable.getMessage());
                    }
                });
    }
    /**
     * 收藏
     */
    @SuppressLint("CheckResult")
    private void collet(CollectDetailBean item) {
        ApiServeice.colletAdd(item.courseId)
                .subscribe(new Consumer<ColletAddResq>() {
                    @Override
                    public void accept(ColletAddResq colletAddResq) throws Exception {
                        if (colletAddResq.code == 0) {
                            mbtn_coursecollect3.setVisibility(View.GONE);
                            mbtn_coursecollected3.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post("add");
                        } else {
                            ApiServeice.tokenInvalid(getApplicationContext(), colletAddResq.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("order", throwable.getMessage());
                    }
                });
    }


    /**
     * 取消收藏
     */
    @SuppressLint("CheckResult")
    private void cancelcollet(CollectDetailBean item) {
        ApiServeice.colletCancel(item.courseId)
                .subscribe(new Consumer<ColletCancelResq>() {
                    @Override
                    public void accept(ColletCancelResq colletCancelResq) throws Exception {
                        if (colletCancelResq.code == 0) {
                            mbtn_coursecollected3.setVisibility(View.GONE);
                            mbtn_coursecollect3.setVisibility(View.VISIBLE);
                            collectlList.remove(item);
                            collectAdapter.notifyDataSetChanged();
                        } else {
                            ApiServeice.tokenInvalid(getApplicationContext(), colletCancelResq.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("orderCancel", throwable.getMessage());
                    }
                });
    }


    //视频播放控制
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }





}
