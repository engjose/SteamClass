package steam.com.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//先实例化控件
public class PcenterActivity extends AppCompatActivity {
    //来自activity_main.xml
    private RelativeLayout main_body1;
    private  ImageView head_pic;
    private TextView nick_name;
    private Button b_information;
   // private Button b_order;
   // private Button b_collect;
    //private Button b_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcenter);//etContentView（）方法是给当前活动加载一个布局
        initView1();//自己定义的方法
        initListener1();
    }
// 从activity_pcenter.xml页面布局中获得对应的UI控件
        private void initView1() {
            head_pic = findViewById(R.id.et_head_pic);//调用findViewById（）方法获取到et_head_pic
            nick_name = findViewById(R.id.et_nick_name);
        }

    private void initListener1() {


//前往“我的信息”控件的点击事件，跳转到“我的信息”界面
        b_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PcenterActivity.this, P_InformationActivity.class);
                startActivity(intent);
            }
        });
    }





}

