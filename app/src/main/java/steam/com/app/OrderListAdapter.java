package steam.com.app;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import steam.com.app.R;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.OrderBean;

/**
 * File: OrderListAdapter.java
 * Create: 2019/4/20 5:31 PM
 *
 * @author zshp
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {
    private boolean onlyShowPayed = false;

    public OrderListAdapter(int layoutResId, @Nullable List<OrderBean> data, boolean onlyShowPayed) {
        super(layoutResId, data);
        this.onlyShowPayed = onlyShowPayed;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.addOnClickListener(R.id.btn_cancel);
        helper.addOnClickListener(R.id.btn_pay);
        ImageView courseImg = helper.getView(R.id.iv_course_img);
        Glide.with(GlobalCache.getContext()).load(item.coursePic).into(courseImg);

        helper.setText(R.id.tv_merchant_name, item.merchantName);
        helper.setText(R.id.tv_order_time, item.createTime);
        helper.setText(R.id.tv_course_name, item.courseName);
        helper.setText(R.id.tv_course_type, item.courseTypeDesc);
        if (item.priceType == "0") {
            helper.setText(R.id.tv_course_price, "免费");
        } else {
            helper.setText(R.id.tv_course_price, item.price + "");
        }

        View view = helper.getView(R.id.ll_handle_order);
        Button cancel = helper.getView(R.id.btn_cancel);
        Button pay = helper.getView(R.id.btn_pay);
        TextView hasCanceled = helper.getView(R.id.tv_has_canceled);
        TextView hasPayed = helper.getView(R.id.tv_has_payed);
        if (onlyShowPayed) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            //待付款
            if ("0".equals(item.status)) {
                cancel.setVisibility(View.VISIBLE);
                pay.setVisibility(View.VISIBLE);
                hasPayed.setVisibility(View.GONE);
                hasCanceled.setVisibility(View.GONE);
            }
            //已完成
            if ("1".equals(item.status)) {
                cancel.setVisibility(View.GONE);
                pay.setVisibility(View.GONE);
                hasPayed.setVisibility(View.VISIBLE);
                hasCanceled.setVisibility(View.GONE);
            }
            //已取消
            if ("2".equals(item.status)) {
                cancel.setVisibility(View.GONE);
                pay.setVisibility(View.GONE);
                hasPayed.setVisibility(View.GONE);
                hasCanceled.setVisibility(View.VISIBLE);
            }
        }
    }
}
