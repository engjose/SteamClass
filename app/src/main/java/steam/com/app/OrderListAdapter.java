package steam.com.app;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import steam.com.app.R;
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


        View view = helper.getView(R.id.ll_handle_order);
        Button cancel = helper.getView(R.id.btn_cancel);
        Button pay = helper.getView(R.id.btn_pay);
        TextView hasCanceled = helper.getView(R.id.tv_has_canceled);
        TextView hasPayed = helper.getView(R.id.tv_has_payed);
        if (onlyShowPayed) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
