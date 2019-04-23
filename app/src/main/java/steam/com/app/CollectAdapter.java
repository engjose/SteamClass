package steam.com.app;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CollectDetailBean;
import steam.com.app.mould.OrderBean;

public class CollectAdapter extends BaseQuickAdapter<CollectDetailBean, BaseViewHolder> {


    public CollectAdapter(int layoutResId, @Nullable List<CollectDetailBean> data) {
        super(layoutResId,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectDetailBean item) {
        helper.addOnClickListener(R.id.btn_cancelcollet);
        ImageView courseImg = helper.getView(R.id.iv_course_img1);
        Glide.with(GlobalCache.getContext()).load(item.coursePic).into(courseImg);

        helper.setText(R.id.tv_merchant_name1, item.merchantName);
        helper.setText(R.id.tv_course_name1, item.courseName);
        helper.setText(R.id.tv_course_type1, item.courseTypeDesc);
        if (item.priceType == "0") {
            helper.setText(R.id.tv_course_price1, "免费");
        } else {
            helper.setText(R.id.tv_course_price1, item.price + "");
        }

    }
}