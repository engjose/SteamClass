package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import steam.com.app.mould.PointBean;
import steam.com.app.mould.PointItemBean;

public class P_PointActivity extends AppCompatActivity {
    private TextView m_point_total;
    private PointBean point;
    private LinearLayout pointDetailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);//etContentView（）方法是给当前活动加载一个布局
        initIntent();
        initView();
    }

    //得到“我的”活动中传过来的数据
    private void initIntent() {
        Intent intent = getIntent();
        point = (PointBean) intent.getSerializableExtra("point");
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        m_point_total = (TextView) findViewById(R.id.point_total);
        m_point_total.setText(point.totalPoint + "");

        pointDetailContainer = findViewById(R.id.point_detail_list);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pointDetailContainer.removeAllViews();//继承于ViewGroup的所有Layout都有removeAllViews方法,此方法就是把layout容器中的的views视图都移除掉，这样子就得到了一个空layout容器
        for (PointItemBean pointItem : point.pointList) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.point_detail_item, null, false);
            TextView pointSource = itemView.findViewById(R.id.point_source);
            TextView pointValue = itemView.findViewById(R.id.point_value);
            pointSource.setText(pointItem.source);
            pointValue.setText(pointItem.value + "");
            pointDetailContainer.addView(itemView, layoutParams);//使用addView()方法 动态地添加控件到界面
        }
    }
}
