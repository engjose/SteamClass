package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.mould.LoginResp;
import steam.com.app.util.Store;

public class LoginActivity extends AppCompatActivity {

    private EditText mNickname;
    private EditText mPassword;
    private Button mLogin;
    private TextView mToRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initView() {
        mNickname = findViewById(R.id.et_nickname);
        mPassword = findViewById(R.id.et_password);
        mLogin = findViewById(R.id.btn_login);
        mToRegister = findViewById(R.id.tv_to_register);
        progressBar = findViewById(R.id.progress);
    }

    private void initListener() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 登录
     */
    @SuppressLint("CheckResult")
    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        String nickname = mNickname.getText().toString();
        String password = mPassword.getText().toString();
        ApiServeice
                .login(nickname, password)
                .subscribe(new Consumer<LoginResp>() {
                    @Override
                    public void accept(LoginResp loginResp) {
                        progressBar.setVisibility(View.GONE);
                        if (loginResp.code == 0) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Store.putString(LoginActivity.this, Constans.TOKEN, loginResp.token);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, loginResp.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
