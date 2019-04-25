package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import org.greenrobot.eventbus.EventBus;

import java.net.URI;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.BaseRespBean;
import steam.com.app.mould.CollectDetailBean;
import steam.com.app.mould.CollectDetailResq;
import steam.com.app.mould.ColletAddReq;
import steam.com.app.mould.ColletAddResq;
import steam.com.app.mould.ColletCancelResq;
import steam.com.app.mould.CourseBean;
import steam.com.app.mould.CourseDetailResq;
import steam.com.app.mould.OrderBean;
import steam.com.app.mould.OrderPlaceResp;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbtn_coursecollect;
    private Button mbtn_coursecollected;
    private Button mbtn_learn;
    private TextView mcourse_name;
    private TextView mcourse_price;
    private TextView mcourse_info;
    private TextView mteacher_name;
    private TextView mteacher_info;
    private TextView mmerchant_name;
    private TextView mmerchant_info;
    private JzvdStd mvideoplayer;
    private CourseBean courseBean;
    private ColletAddReq colletAddReq;
    private CollectDetailBean collectDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);//etContentView（）方法是给当前活动加载一个布局
        initData();
        initView();
        initListener();
    }

    private void initData() {
        Intent intent = getIntent();
        courseBean = (CourseBean) intent.getSerializableExtra("course");
        String id =courseBean.courseId;
        renderPage(id, null, null,null);
    }

    // xml页面布局中获得对应的UI控件
    private void initView() {
        mbtn_coursecollect = findViewById(R.id.btn_coursecollect);
        mbtn_coursecollected = findViewById(R.id.btn_coursecollected);
        mbtn_learn = findViewById(R.id.btn_learn);
        mcourse_name = findViewById(R.id.course_name);
        mcourse_price = findViewById(R.id.course_price);
        mcourse_info = findViewById(R.id.course_info);
        mteacher_name = findViewById(R.id.teacher_name);
        mteacher_info = findViewById(R.id.teacher_info);
        mmerchant_name = findViewById(R.id.merchant_name);
        mmerchant_info = findViewById(R.id.merchant_info);

        Log.i("coursName", courseBean.courseName);
        mcourse_name.setText(courseBean.courseName);
        mcourse_price.setText(courseBean.priceType);
        if (courseBean.priceType.equals("0")) {
            mcourse_price.setText("免费");
        } else {
            mcourse_price.setText("¥" + courseBean.price + "");
        }

        //已经学习的课程不能重复下单
        if (courseBean.isBuy) {
            mbtn_learn.setVisibility(View.INVISIBLE);
        }

        //收藏
//        if(collectDetailBean.isCollect.equals("0")){
//            mbtn_coursecollect.setVisibility(View.VISIBLE);
//            mbtn_coursecollected.setVisibility(View.GONE);
//        }
//        if(collectDetailBean.isCollect.equals("1")){
//            mbtn_coursecollect.setVisibility(View.GONE);
//            mbtn_coursecollected.setVisibility(View.VISIBLE);
//        }

    }

    //“收藏”和“加入学习”按钮添加监听器
    private void initListener() {
        mbtn_coursecollect.setOnClickListener(this);
        mbtn_coursecollected.setOnClickListener(this);
        mbtn_learn.setOnClickListener(this);
    }

    //“收藏”和“加入学习”功能实现
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_coursecollect:
               collet();
                break;
            case R.id.btn_coursecollected:
                cancelcollet();
                break;
            case R.id.btn_learn:
                orderPlace();
                break;
            default:
                break;
        }
    }

    /**
     * 获取课程详情
     */
    @SuppressLint("CheckResult")
    private void renderPage(String courseId,String courseNameMatch, String courseType, String priceSort) {
        ApiServeice.courseDetail(courseId,courseNameMatch, courseType, priceSort)
                .subscribe(new Consumer<CourseDetailResq>() {
                    @Override
                    public void accept(CourseDetailResq courseDetailResp) {
                        if (courseDetailResp.code == 0) {
                            mcourse_info.setText(courseDetailResp.courseInfo);
                            mteacher_name.setText(courseDetailResp.teacherName);
                            mteacher_info.setText(courseDetailResp.teacherInfo);
                            mmerchant_name.setText(courseDetailResp.merchantName);
                            mmerchant_info.setText(courseDetailResp.merchantInfo);
                            JzvdStd jzvdStd = findViewById(R.id.videoplayer);
                            Log.i("videourl", courseDetailResp.videoUrl);
                            jzvdStd.setUp(courseDetailResp.videoUrl, courseDetailResp.courseName, Jzvd.SCREEN_WINDOW_NORMAL);
                            Glide.with(GlobalCache.getContext()).load(courseDetailResp.coursePic).into(jzvdStd.thumbImageView);
                        } else {
                            ApiServeice.tokenInvalid(CourseDetailActivity.this, courseDetailResp.code);
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
    private void orderPlace() {
        ApiServeice.orderPlace(courseBean.courseId, courseBean.price)
                .subscribe(new Consumer<OrderPlaceResp>() {
                    @Override
                    public void accept(OrderPlaceResp orderPlaceResp) throws Exception {
                        if (orderPlaceResp.code == 0) {
                            mbtn_learn.setVisibility(View.INVISIBLE);
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
    private void collet() {
        ApiServeice.colletAdd(courseBean.courseId)
                .subscribe(new Consumer<ColletAddResq>() {
                    @Override
                    public void accept(ColletAddResq colletAddResq) throws Exception {
                        if (colletAddResq.code == 0) {
                            mbtn_coursecollect.setVisibility(View.GONE);
                            mbtn_coursecollected.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post("collect");
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
    private void cancelcollet() {
        ApiServeice.colletCancel(courseBean.courseId)
                .subscribe(new Consumer<ColletCancelResq>() {
                    @Override
                    public void accept(ColletCancelResq colletCancelResq) throws Exception {
                        if (colletCancelResq.code == 0) {
                            mbtn_coursecollected.setVisibility(View.GONE);
                            mbtn_coursecollect.setVisibility(View.VISIBLE);
                            EventBus.getDefault().post("collectcancel");
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
