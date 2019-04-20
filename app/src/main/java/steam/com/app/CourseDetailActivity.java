package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CourseBean;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbtn_collet;
    private TextView mcourse_name;
    private TextView mcourse_price;
    private TextView mcourse_info;
    private TextView mteacher_name;
    private TextView mteacher_info;
    private TextView mmerchant_name;
    private TextView mmerchant_info;
    private JzvdStd mvideoplayer;
    private CourseBean courseBean;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        courseBean = (CourseBean) intent.getSerializableExtra("course");
        setContentView(R.layout.course_detail);//etContentView（）方法是给当前活动加载一个布局
        initView();
        initListener();
        getCourseDetail();
    }

    // xml页面布局中获得对应的UI控件
    private void initView() {
        mbtn_collet = findViewById(R.id.btn_collect);
        mcourse_name =findViewById(R.id.course_name);
        mcourse_price = findViewById(R.id.course_price);
        mcourse_info = findViewById(R.id.course_info);
        mteacher_name = findViewById(R.id.teacher_name);
        mteacher_info = findViewById(R.id.teacher_info);
        mmerchant_name =findViewById(R.id.merchant_name);
        mmerchant_info =findViewById(R.id.merchant_info);


        Log.i("coursName",courseBean.courseName);
        mcourse_name.setText(courseBean.courseName);
        mcourse_price.setText(courseBean.priceType);
        if (courseBean.priceType.equals("0")) { mcourse_price.setText("免费"); }
        else { mcourse_price.setText(courseBean.price + ""); }
        mcourse_info.setText(courseBean.courseInfo);
        JzvdStd jzvdStd = (JzvdStd) findViewById(R.id.videoplayer);
        Log.i("videourl",courseBean.videoUrl);
        jzvdStd.setUp(courseBean.videoUrl, courseBean.courseName, Jzvd.SCREEN_WINDOW_NORMAL);
        Glide.with(GlobalCache.getContext()).load(courseBean.coursePic).into(jzvdStd.thumbImageView);

    }

    //“收藏”和“加入学习”按钮添加监听器
    private void  initListener() {
        mbtn_collet.setOnClickListener(this);
    }
    //“收藏”和“加入学习”按钮功能实现
    @Override
    public void onClick(View v) {

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

    @SuppressLint("CheckResult")
    public void getCourseDetail() {

    }


}
