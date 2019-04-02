package steam.com.app;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.mould.CourseBean;
import steam.com.app.mould.CourseResp;

public class ActivityFragment1Fragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private RecyclerView mRv;
    private List<CourseBean> courseBeanList = new ArrayList<>();
    private CourseAdapter courseAdapter;

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
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
        ll_point_container = (LinearLayout) view.findViewById(R.id.ll_point_container);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);

        mRv = view.findViewById(R.id.rv_course);
        mRv.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));


        initData(); // Model数据
        initAdapter(); // Controller 控制器

        // 开启轮询
        timer();
    }

    private void timer() {
        new Thread() {
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
        loadCourseData();
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

        // 文本描述
        contentDescs = new String[]{
                "风靡全球的儿童编程",
                "STEAM儿童启蒙积木课等你来",
                "揭秘神秘新课程",
                "听听著名老师如何讲吧",
                "趣味数学，给孩子更好的成长"
        };

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(getActivity());
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(10, 10);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    @SuppressLint("CheckResult")
    private void loadCourseData() {
        ApiServeice.course(null, null, null)
                .subscribe(new Consumer<CourseResp>() {
                    @Override
                    public void accept(CourseResp courseResp) {
                        if (courseResp.code == 0) {
                            Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                            courseBeanList.clear();
                            courseBeanList.addAll(courseResp.courseList);
                            //courseAdapter.notifyDataSetChanged();
                            courseAdapter = new CourseAdapter(R.layout.item_course, courseBeanList);
                            mRv.setAdapter(courseAdapter);
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
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        viewPager.setCurrentItem(5000000); // 设置到某个位置
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
            // position: 当前要显示条目的位置 0 -> 4

            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
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
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);

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
}
