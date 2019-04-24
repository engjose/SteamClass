package steam.com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.reactivex.functions.Consumer;
import steam.com.app.api.ApiServeice;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CenterResp;
import steam.com.app.mould.CollectDetailBean;
import steam.com.app.mould.OrderInfo;
import steam.com.app.mould.PointBean;
import steam.com.app.mould.UserBean;

public class ActivityFragment4Fragment extends Fragment implements View.OnClickListener {

    private RelativeLayout m_rBody;
    private ImageView m_headPic;
    private TextView m_nickName;
    // 积分属性
    private PointBean point;
    private OrderInfo orderInfo;
    private CollectDetailBean collectDetailBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_4, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        m_rBody = (RelativeLayout) view.findViewById(R.id.r_body);
        m_headPic = (ImageView) view.findViewById(R.id.avatar);
        m_nickName = (TextView) view.findViewById(R.id.nick_name);
        view.findViewById(R.id.btn_information).setOnClickListener(this);
        view.findViewById(R.id.btn_order).setOnClickListener(this);
        view.findViewById(R.id.btn_collect).setOnClickListener(this);
        view.findViewById(R.id.btn_point).setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);

        getUserCenter();
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
                            point = centerResp.point;

                            orderInfo = centerResp.orderInfo;

                            Glide.with(GlobalCache.getContext()).load(userInfo.headPic).into(m_headPic);
                            m_nickName.setText(userInfo.nickName);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_information: {
                Intent intent = new Intent(getActivity(), P_InformationActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_order: {
                Intent intent = new Intent(getContext(), OrderListActivity.class);
                intent.putExtra("orderInfo", orderInfo);
                startActivity(intent);
                break;
            }
            case R.id.btn_collect:
                Intent intent = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_point: {
                if (point != null) {
                    Intent pointIntent = new Intent(getActivity(), P_PointActivity.class);//切换到P_PointActivity页面
                    pointIntent.putExtra("point", point);//附带积分属性相关信息，调用putExtra（）方法传递
                    startActivity(pointIntent);
                }
            }
                break;
                case R.id.btn_back: {
                    Intent intentback = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentback);
                    }
                    break;

            default:
                break;
        }
    }


}
