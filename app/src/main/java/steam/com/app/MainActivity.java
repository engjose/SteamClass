package steam.com.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import steam.com.app.util.Store;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TextView mToken = findViewById(R.id.tv_token);
        String token = Store.getString(MainActivity.this, Constans.TOKEN);
        mToken.setText("token:" + token);
    }
}
