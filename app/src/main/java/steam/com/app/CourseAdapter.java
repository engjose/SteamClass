package steam.com.app;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CourseBean;

public class CourseAdapter extends BaseQuickAdapter<CourseBean, BaseViewHolder> {
    public CourseAdapter(int layoutResId, @Nullable List<CourseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseBean item) {
        ImageView courseImage = helper.getView(R.id.iv_course_image);
        Glide.with(GlobalCache.getContext()).load(item.coursePic).into(courseImage);

        helper.setText(R.id.tv_course_name, item.courseName);

        TextView priceType = helper.getView(R.id.tv_price_type);
        if (item.priceType.equals(0)) {
            priceType.setText("免费");
        } else {
            priceType.setText(item.price + "");
        }
    }
}
