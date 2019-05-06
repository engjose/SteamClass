package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
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
import steam.com.app.mould.Constans;
import steam.com.app.mould.CourseDetailReq;
import steam.com.app.mould.CourseDetailResq;
import steam.com.app.mould.OrderBean;
import steam.com.app.mould.UserBean;
import steam.com.app.util.Store;

public class ActivityFragment3Fragment extends Fragment {
    private List<OrderBean> orderList = new ArrayList<>();

    private RecyclerView mRv;
    private OrderListAdapter orderListAdapter;
    private View rootView;
    private ImageView mAvatar;
    private TextView mNickName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_fragment_3, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mAvatar = rootView.findViewById(R.id.avatar);
        mNickName = rootView.findViewById(R.id.nick_name);

        mRv = rootView.findViewById(R.id.rv_order_list);
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        orderListAdapter = new OrderListAdapter(R.layout.item_order_list, orderList, true);
        mRv.setAdapter(orderListAdapter);
    }

    private void initListener() {
        orderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderBean item = (OrderBean) adapter.getItem(position);
                Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                intent.putExtra("courseId", item.courseId);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        getUserInfo();
    }

    @SuppressLint("CheckResult")
    public void getUserInfo() {
        ApiServeice.center()
                .subscribe(new Consumer<CenterResp>() {
                    @Override
                    public void accept(CenterResp centerResp) throws Exception {
                        if (centerResp.code == 0) {
                            UserBean userInfo = centerResp.userInfo;
                            Glide.with(GlobalCache.getContext()).load(userInfo.headPic).into(mAvatar);
                            mNickName.setText(userInfo.nickName);

                            orderList.clear();
                            orderList.addAll(centerResp.orderInfo.payOrderList);
                            orderListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), centerResp.message, Toast.LENGTH_SHORT).show();
                            ApiServeice.tokenInvalid(GlobalCache.getContext(), centerResp.code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override//异常处理
                    public void accept(Throwable throwable) {
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
