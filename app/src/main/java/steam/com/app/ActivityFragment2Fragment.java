package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.mould.CourseBean;
import steam.com.app.mould.CourseResp;

public class ActivityFragment2Fragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRv;
    private List<CourseBean> courseBeanList = new ArrayList<>();
    private CourseAdapter courseAdapter;
    private EditText mtext_search;
    private RelativeLayout mRelativeLayout_s;
    private RelativeLayout mRelativeLayout_t;
    private RelativeLayout mRelativeLayout_e;
    private RelativeLayout mRelativeLayout_a;
    private RelativeLayout mRelativeLayout_m;
    private RelativeLayout mRelativeLayout_all;
    private String courseType = null;
    private String priceSort = null;
    private String textSearch = null;

    Spinner mspinner_price;
    String[] s_price = {"价格排序", "价格升序", "价格降序"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_2, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        renderPage(null, null, null);
    }

    private void initView(View view) {
        mRv = view.findViewById(R.id.rv_course2);
        mRv.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        courseAdapter = new CourseAdapter(R.layout.item_course, courseBeanList);
        mRv.setAdapter(courseAdapter);
        //Item的点击事件
        courseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
                CourseBean item = (CourseBean) adapter.getItem(position);
                Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                intent.putExtra("course",item);
                startActivity(intent);
            }
        });

        mtext_search = view.findViewById(R.id.text_search);
        mRelativeLayout_s = view.findViewById(R.id.RelativeLayout_s);
        view.findViewById(R.id.RelativeLayout_s).setOnClickListener(this);
        mRelativeLayout_t = view.findViewById(R.id.RelativeLayout_t);
        view.findViewById(R.id.RelativeLayout_t).setOnClickListener(this);
        mRelativeLayout_e = view.findViewById(R.id.RelativeLayout_e);
        view.findViewById(R.id.RelativeLayout_e).setOnClickListener(this);
        mRelativeLayout_a = view.findViewById(R.id.RelativeLayout_a);
        view.findViewById(R.id.RelativeLayout_a).setOnClickListener(this);
        mRelativeLayout_m = view.findViewById(R.id.RelativeLayout_m);
        view.findViewById(R.id.RelativeLayout_m).setOnClickListener(this);
        mRelativeLayout_all = view.findViewById(R.id.RelativeLayout_all);
        view.findViewById(R.id.RelativeLayout_all).setOnClickListener(this);
        view.findViewById(R.id.bottom_search).setOnClickListener(this);

        //1.为下拉列表定义一个数组适配器，这个数组适配器就用到里前面定义的s_price。装的都是s_price所添加的内容
        mspinner_price = (Spinner) view.findViewById(R.id.spinner_price);
        //2.样式为安卓里面有的android.R.layout.simple_spinner_item，让这个数组适配器装s_price内容。
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, s_price);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到mspinner_price
        mspinner_price.setAdapter(arrayAdapter1);
        mspinner_price.setOnItemSelectedListener(new spinnerSelectedListenner());//绑定事件监听
    }

    private class spinnerSelectedListenner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            /*
             * parent接收的是被选择的数据项所属的 Spinner对象，
             * *view参数接收的是显示被选择的数据项的TextView对象
             * position接收的是被选择的数据项在适配器中的位置
             * id被选择的数据项的行号
             */
            if (mspinner_price.getSelectedItem().toString().equals("价格排序")) {
                priceSort = null;
                renderPage(textSearch, courseType, priceSort);
            } else if (mspinner_price.getSelectedItem().toString().equals("价格升序")) {
                priceSort = "0";
                renderPage(textSearch, courseType, priceSort);
            } else if (mspinner_price.getSelectedItem().toString().equals("价格降序")) {
                priceSort = "1";
                renderPage(textSearch, courseType, priceSort);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            renderPage(null, null, null);
        }
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

    @Override
    public void onClick(View view) {
        textSearch = TextUtils.isEmpty(mtext_search.getText().toString().trim()) ? null : mtext_search.getText().toString().trim();
        switch (view.getId()) {
            case R.id.RelativeLayout_s:
                courseType = "0";
                renderPage(textSearch, courseType, priceSort);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_t:
                courseType = "1";
                renderPage(textSearch, courseType, priceSort);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_e:
                courseType = "2";
                renderPage(textSearch, courseType, priceSort);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_a:
                courseType = "3";
                renderPage(textSearch, courseType, priceSort);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_m:
                courseType = "4";
                renderPage(textSearch, courseType, priceSort);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_all:
                courseType = null;
                renderPage(textSearch, courseType, priceSort);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#F5F5DC"));
                break;
            case R.id.bottom_search:
                courseType = null;
                priceSort = null;
                renderPage(textSearch, courseType, priceSort);
                break;
            default:
                break;
        }
    }

}

