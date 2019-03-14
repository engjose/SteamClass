package steam.com.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;



import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.mould.RegisterResp;

/**
 * @author zshp
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText mNickname;
    private EditText mPassword;
    private Button mRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }

    private void initView() {
        mNickname = findViewById(R.id.et_nickname);
        mPassword = findViewById(R.id.et_password);
        mRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progress);
    }

    private void initListener() {
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    /**
     * 注册
     */
    @SuppressLint("CheckResult")
    private void register() {
        progressBar.setVisibility(View.VISIBLE);
        String nickname = mNickname.getText().toString();
        String password = mPassword.getText().toString();
        ApiServeice
                .register(nickname, password)
                .subscribe(new Consumer<RegisterResp>() {
                    @Override
                    public void accept(RegisterResp registerResp) {
                        progressBar.setVisibility(View.GONE);
                        if (registerResp.code == 0) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, registerResp.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
