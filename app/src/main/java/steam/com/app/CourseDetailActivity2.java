package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CourseBean;
import steam.com.app.mould.CourseDetailResq;
import steam.com.app.mould.OrderBean;

public class CourseDetailActivity2 extends AppCompatActivity  {
    private TextView mcourse_name;
    private TextView mcourse_price;
    private TextView mcourse_info;
    private TextView mteacher_name;
    private TextView mteacher_info;
    private TextView mmerchant_name;
    private TextView mmerchant_info;
    private JzvdStd mvideoplayer;
    private CourseDetailResq courseDetailResq;
    private OrderBean orderBean;
    private CourseBean courseBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        orderBean = (OrderBean) intent.getSerializableExtra("orderBean");

        setContentView(R.layout.course_detail2);//etContentView（）方法是给当前活动加载一个布局
        initView();
        initData();
    }

    // xml页面布局中获得对应的UI控件
    private void initView() {
        mcourse_name = findViewById(R.id.course_name);
        mcourse_price = findViewById(R.id.course_price);
        mcourse_info = findViewById(R.id.course_info);
        mteacher_name = findViewById(R.id.teacher_name);
        mteacher_info = findViewById(R.id.teacher_info);
        mmerchant_name = findViewById(R.id.merchant_name);
        mmerchant_info = findViewById(R.id.merchant_info);
    }
    private void initData() {
        String id =orderBean.courseId;
        renderPage(id, null, null,null);
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
                            mcourse_name.setText(courseDetailResp.courseName);
                            mcourse_price.setText("该课程已加入课程表");
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
                            ApiServeice.tokenInvalid(CourseDetailActivity2.this, courseDetailResp.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("loadCourseData", throwable.getMessage());
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
