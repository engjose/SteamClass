package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CourseBean;
import steam.com.app.mould.CourseResp;
import steam.com.app.mould.OrderBean;

public class ActivityFragment1Fragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private LinearLayout ll_point_container;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private RecyclerView mRv;
    private List<CourseBean> courseBeanList = new ArrayList<>();
    private CourseAdapter courseAdapter;
    List<CourseBean> imgUrlList = new ArrayList<>();
    List<String> contentDescsList = new ArrayList<>();
    private int position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    //这里重写了Fragment的onCreateView（）方法，
// 然后再这个方法中通过LayoutInflater的inflater（）方法将定义好的activity_fragment_1布局动态加载进来
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_1, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData(); // Model数据
    }

    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        ll_point_container = (LinearLayout) view.findViewById(R.id.ll_point_container);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);

//RecyclerView.LayoutManager  负责Item视图的布局
        mRv = view.findViewById(R.id.rv_course);
        mRv.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        courseAdapter = new CourseAdapter(R.layout.item_course, courseBeanList);
        mRv.setAdapter(courseAdapter);
        //Item的点击事件
        courseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ActivityFragment1Fragment.this.position = position;
                CourseBean item = (CourseBean) adapter.getItem(position);
                Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                intent.putExtra("courseId", item.courseId);
                startActivity(intent);
            }
        });
    }

    private void timer() {
        new Thread() {
            @Override
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("设置当前位置: " + viewPager.getCurrentItem());
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            }
                        });
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    /**
     * 初始化要显示的数据
     */
    public void initData() {
        renderPage(null, null, null);
    }

    @SuppressLint("CheckResult")
    private void renderPage(String courseNameMatch, String courseType, String priceSort) {
        ApiServeice.course(courseNameMatch, courseType, priceSort)
                .subscribe(new Consumer<CourseResp>() {
                    @Override
                    public void accept(CourseResp courseResp) {
                        if (courseResp.code == 0) {
                            courseBeanList.clear();
                            courseBeanList.addAll(courseResp.courseList);
                            courseAdapter.notifyDataSetChanged();
                            if (courseResp.courseList.size() > 0) {
                                for (int i = 0; i < courseResp.courseList.size(); i++) {
                                    if (i > 5) {
                                        break;
                                    }
                                    CourseBean courseBean = courseResp.courseList.get(i);
                                    imgUrlList.add(courseBean);
                                    contentDescsList.add(courseBean.courseName);
                                }
                                initAdapter();
                            }
                        } else {
                            ApiServeice.tokenInvalid(getContext(), courseResp.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("loadCourseData", throwable.getMessage());
                    }
                });
    }

    private void initAdapter() {
        for (int i = 0; i < imgUrlList.size(); i++) {
            // 加小白点, 指示器
            View pointView = new View(getActivity());
            pointView.setBackgroundResource(R.drawable.select_dot);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
            if (i != 0) {
                layoutParams.leftMargin = 10;
            }
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听

        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescsList.get(0));
        previousSelectedPosition = 0;
        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imgUrlList.size());
        viewPager.setCurrentItem(5000000); // 设置到某个位置

        // 开启轮询
        timer();
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//			System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem初始化: " + position);
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 5
            int newPosition = position % imgUrlList.size();
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(GlobalCache.getContext()).load(imgUrlList.get(newPosition).coursePic).into(imageView);
            // a. 把View对象添加到container中
            container.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                    intent.putExtra("courseId", imgUrlList.get(newPosition).courseId);
                    startActivity(intent);
                }
            });
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常


        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            System.out.println("destroyItem销毁: " + position);
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        System.out.println("onPageSelected: " + position);
        int newPosition = position % imgUrlList.size();
        //设置文本
        tv_desc.setText(contentDescsList.get(newPosition));
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);
        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String type) {
        if ("add".equals(type)) {
            CourseBean item = (CourseBean) courseAdapter.getItem(position);
            item.isBuy = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
