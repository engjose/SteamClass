package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CenterResp;
import steam.com.app.mould.CollectDetailBean;
import steam.com.app.mould.CollectDetailResq;
import steam.com.app.mould.UserBean;


public class CollectActivity extends AppCompatActivity implements View.OnClickListener{

    private List<CollectDetailBean> collectlList = new ArrayList<>();
    private RecyclerView mRv;
    private CollectAdapter collectAdapter;
    private int position;
    private ImageView mAvatar;
    private TextView mNickName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);

        setContentView(R.layout.course_collet_list);
        initView();
        getuserinfo();
        getcolletlist();
    }


    private void initView() {
        mAvatar =findViewById(R.id.mAvatar);
        mNickName =findViewById(R.id.nick_name);

        mRv = findViewById(R.id.rv_collet_list);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        collectAdapter = new CollectAdapter(R.layout.course_collet_item, collectlList);
        mRv.setAdapter(collectAdapter);
        collectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CollectDetailBean item = (CollectDetailBean) adapter.getItem(position);
                if (item == null) {
                    return;
                }
                Intent intent = new Intent(CollectActivity.this, OrderDetailActivity.class);
                intent.putExtra("collectDetailBean", item);
                startActivity(intent);
            }
        });


    }

    //获取用户信息
    @SuppressLint("CheckResult")
    public void getuserinfo() {
        ApiServeice.center()
                .subscribe(new Consumer<CenterResp>() {
                    @Override
                    public void accept(CenterResp centerResp) throws Exception {
                        if (centerResp.code == 0) {
                            UserBean userInfo = centerResp.userInfo;
                            Glide.with(GlobalCache.getContext()).load(userInfo.headPic).into(mAvatar);
                            mNickName.setText(userInfo.nickName);
                        } else {
                            Toast.makeText(CollectActivity.this, centerResp.message, Toast.LENGTH_SHORT).show();
                            ApiServeice.tokenInvalid(GlobalCache.getContext(), centerResp.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override//异常处理
                    public void accept(Throwable throwable) {
                        Toast.makeText(CollectActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //获取收藏列表信息
    @SuppressLint("CheckResult")
    private void getcolletlist() {
        ApiServeice.colletList()
                .subscribe(new Consumer<CollectDetailResq>() {
                    @Override
                    public void accept(CollectDetailResq collectDetailResq) {
                        if (collectDetailResq.code == 0) {
                            collectlList.clear();
                            collectlList.addAll(collectDetailResq.collectList);
                            collectAdapter.notifyDataSetChanged();
                        } else {
                            ApiServeice.tokenInvalid(CollectActivity.this, collectDetailResq.code);
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
    public void onClick(View v) {

    }

}
