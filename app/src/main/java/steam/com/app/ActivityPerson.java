package steam.com.app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import steam.com.app.mould.Constans;
import steam.com.app.util.Store;

public class ActivityPerson extends FragmentActivity implements View.OnClickListener{

    private RelativeLayout tmain_body;
    private LinearLayout tmain_bottom_bar;

    private TextView tbottom_bar_text_1;
    private ImageView tbottom_bar_image_1;
    private RelativeLayout tbottom_bar_1_btn;

    private TextView tbottom_bar_text_2;
    private ImageView tbottom_bar_image_2;
    private RelativeLayout tbottom_bar_2_btn;

    private TextView tbottom_bar_text_3;
    private ImageView tbottom_bar_image_3;
    private RelativeLayout tbottom_bar_3_btn;

    private TextView tbottom_bar_text_4;
    private ImageView tbottom_bar_image_4;
    private RelativeLayout tbottom_bar_4_btn;

    private int x=0;

    //private TextView mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);//etContentView（）方法是给当前活动加载一个布局
        //initView1();
        setMain();
        initView();
        setSelectStatus(x);
        tbottom_bar_1_btn.setOnClickListener(this);
        tbottom_bar_2_btn.setOnClickListener(this);
        tbottom_bar_3_btn.setOnClickListener(this);
        tbottom_bar_4_btn.setOnClickListener(this);
    }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bottom_bar_1_btn:
                    //添加
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new ActivityFragment1Fragment()).commit();
                    setSelectStatus(0);
                    break;
                case R.id.bottom_bar_2_btn:
                    //添加
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new ActivityFragment2Fragment()).commit();
                    setSelectStatus(1);
                    break;
                case R.id.bottom_bar_3_btn:
                    //添加
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new ActivityFragment3Fragment()).commit();
                    setSelectStatus(2);
                    break;
                case R.id.bottom_bar_4_btn:
                    //添加
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new ActivityFragment4Fragment()).commit();
                    setSelectStatus(3);
                    //center();
                    break;
            }
        }


        //用于打开初始页面
    private void setMain() {
        this.getSupportFragmentManager().beginTransaction().add(R.id.main_body,new ActivityFragment1Fragment()).commit();
    }


    private void initView() {
        tmain_body = (RelativeLayout) findViewById(R.id.main_body);
        tbottom_bar_text_1 = (TextView) findViewById(R.id.bottom_bar_text_1);
        tbottom_bar_image_1 = (ImageView) findViewById(R.id.bottom_bar_image_1);
        tbottom_bar_1_btn = (RelativeLayout) findViewById(R.id.bottom_bar_1_btn);
        tbottom_bar_text_2 = (TextView) findViewById(R.id.bottom_bar_text_2);
        tbottom_bar_image_2 = (ImageView) findViewById(R.id.bottom_bar_image_2);
        tbottom_bar_2_btn = (RelativeLayout) findViewById(R.id.bottom_bar_2_btn);
        tbottom_bar_text_3 = (TextView) findViewById(R.id.bottom_bar_text_3);
        tbottom_bar_image_3 = (ImageView) findViewById(R.id.bottom_bar_image_3);
        tbottom_bar_3_btn = (RelativeLayout) findViewById(R.id.bottom_bar_3_btn);
        tbottom_bar_text_4 = (TextView) findViewById(R.id.bottom_bar_text_4);
        tbottom_bar_image_4 = (ImageView) findViewById(R.id.bottom_bar_image_4);
        tbottom_bar_4_btn = (RelativeLayout) findViewById(R.id.bottom_bar_4_btn);
        tmain_bottom_bar = (LinearLayout) findViewById(R.id.main_bottom_bar);

    }
    private void setSelectStatus(int index) {
        switch (index) {
            case 0:
                //图片点击选择变换图片，颜色的改变，其他变为原来的颜色，并保持原有的图片
                tbottom_bar_image_1.setImageResource(R.drawable.main1_selected);
                tbottom_bar_text_1.setTextColor(Color.parseColor("#0097F7"));
                //其他的文本颜色不变
                tbottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                tbottom_bar_text_3.setTextColor(Color.parseColor("#666666"));
                tbottom_bar_text_4.setTextColor(Color.parseColor("#666666"));
                //图片也不变
                tbottom_bar_image_2.setImageResource(R.drawable.type1);
                tbottom_bar_image_3.setImageResource(R.drawable.schedule1);
                tbottom_bar_image_4.setImageResource(R.drawable.center1);
                break;

            case 1:
                //图片点击选择变换图片，颜色的改变，其他变为原来的颜色，并保持原有的图片
                tbottom_bar_image_2.setImageResource(R.drawable.type1_selected);
                tbottom_bar_text_2.setTextColor(Color.parseColor("#0097F7"));
                //其他的文本颜色不变
                tbottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                tbottom_bar_text_3.setTextColor(Color.parseColor("#666666"));
                tbottom_bar_text_4.setTextColor(Color.parseColor("#666666"));
                //图片也不变
                tbottom_bar_image_1.setImageResource(R.drawable.main1);
                tbottom_bar_image_3.setImageResource(R.drawable.schedule1);
                tbottom_bar_image_4.setImageResource(R.drawable.center1);
                break;

            case 2:
                //图片点击选择变换图片，颜色的改变，其他变为原来的颜色，并保持原有的图片
                tbottom_bar_image_3.setImageResource(R.drawable.schedule1_selected);
                tbottom_bar_text_3.setTextColor(Color.parseColor("#0097F7"));
                //其他的文本颜色不变
                tbottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                tbottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                tbottom_bar_text_4.setTextColor(Color.parseColor("#666666"));
                //图片也不变
                tbottom_bar_image_1.setImageResource(R.drawable.main1);
                tbottom_bar_image_2.setImageResource(R.drawable.type1);
                tbottom_bar_image_4.setImageResource(R.drawable.center1);
                break;

            case 3:
                //图片点击选择变换图片，颜色的改变，其他变为原来的颜色，并保持原有的图片
                tbottom_bar_image_4.setImageResource(R.drawable.center1_selected);
                tbottom_bar_text_4.setTextColor(Color.parseColor("#0097F7"));
                //其他的文本颜色不变
                tbottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                tbottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                tbottom_bar_text_3.setTextColor(Color.parseColor("#666666"));
                //图片也不变
                tbottom_bar_image_1.setImageResource(R.drawable.main1);
                tbottom_bar_image_2.setImageResource(R.drawable.type1);
                tbottom_bar_image_3.setImageResource(R.drawable.schedule1);
                break; } }


    //private void initView1() {
       // TextView mToken = findViewById(R.id.tv_token);
       // String token = Store.getString(ActivityPerson.this, Constans.TOKEN);
       // mToken.setText("token:" + token); }


}

