package steam.com.app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    Spinner mspinner_price;
    String[] s_price={"价格排序","价格升序","价格降序"};

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
        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,s_price);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到mspinner_price
        mspinner_price.setAdapter(arrayAdapter1);
        mspinner_price.setOnItemSelectedListener(new spinnerSelectedListenner());//绑定事件监听
    }
    private class spinnerSelectedListenner implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            /*
             * parent接收的是被选择的数据项所属的 Spinner对象，
             * *view参数接收的是显示被选择的数据项的TextView对象
             * position接收的是被选择的数据项在适配器中的位置
             * id被选择的数据项的行号
             */
                    if (mspinner_price.getSelectedItem().toString().equals("价格排序"))
                    { renderPage(null, null, null); }
                    else if (mspinner_price.getSelectedItem().toString().equals("价格升序"))
                    { renderPage(null, null, "0"); }
                    else if (mspinner_price.getSelectedItem().toString().equals("价格降序"))
                    { renderPage(null, null, "1"); }
        }
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RelativeLayout_s:
                renderPage(null, "0", null);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_t:
                renderPage(null, "1", null);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_e:
                renderPage(null, "2", null);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_a:
                renderPage(null, "3", null);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_m:
                renderPage(null, "4", null);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#F5F5DC"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.RelativeLayout_all:
                renderPage(null, null, null);
                mRelativeLayout_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_t.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_e.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRelativeLayout_all.setBackgroundColor(Color.parseColor("#F5F5DC"));
                break;
            case R.id.bottom_search:
                String textSearch = mtext_search.getText().toString();//获取文本框内容
                renderPage(textSearch, null, null);
                break;
            default:
                break;
        }
    }

    }

