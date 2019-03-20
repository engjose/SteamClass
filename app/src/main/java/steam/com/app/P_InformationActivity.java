package steam.com.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CenterResp;
import steam.com.app.mould.UserBean;

public class P_InformationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView m_et_head_pic;
    private TextView m_et_nick_name;
    private TextView m_text_psw;
    private TextView m_text_time;
    private Button m_btn_edit;
    private Button m_btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);//etContentView（）方法是给当前活动加载一个布局
        initView();
        getUserCenter();
       // submit();
    }

    private void initView() {
        m_et_head_pic = (ImageView) findViewById(R.id.et_head_pic);
        m_et_nick_name = (TextView) findViewById(R.id.et_nick_name);
        m_text_psw = (TextView) findViewById(R.id.text_psw);
        m_text_time = (TextView) findViewById(R.id.text_time);
        m_btn_edit = (Button) findViewById(R.id.btn_edit);
        m_btn_save =findViewById(R.id.btn_save) ;
        m_btn_edit.setOnClickListener(this);

        m_text_psw.setFocusable(false);
        m_text_psw.setFocusableInTouchMode(false);

    }

    /**
     * get user center info
     */
    @SuppressLint("CheckResult")
    public void getUserCenter() {
        ApiServeice.center()
                .subscribe(new Consumer<CenterResp>() {
                    @Override
                    public void accept(CenterResp centerResp) throws Exception {
                        if (centerResp.code == 0) {
                            // 设置参数
                            UserBean userInfo = centerResp.userInfo;
                            // 用户基本信息
                            Glide.with(GlobalCache.getContext()).load(userInfo.headPic).into(m_et_head_pic);
                            m_et_nick_name.setText(userInfo.nickName);
                            m_text_time.setText(userInfo.registerDate);
                        } else {
                            Toast.makeText(P_InformationActivity.this, centerResp.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override//异常处理
                    public void accept(Throwable throwable) {
                        Toast.makeText(P_InformationActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                m_text_psw.setFocusable(true);
                m_text_psw.setFocusableInTouchMode(true);
                m_btn_edit.setVisibility(View.GONE);
                m_btn_save.setVisibility(View.VISIBLE);
                break;
        }
    }





}

