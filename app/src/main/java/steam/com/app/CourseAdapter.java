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

public class CourseAdapter extends BaseQuickAdapter<CourseBean, BaseViewHolder>{
    public CourseAdapter(int layoutResId, @Nullable List<CourseBean> data) {
        super(layoutResId, data);
    }
    //RecyclerView.ViewHolder  承载Item视图的子视图
    //首先需要继承BaseQuickAdapter,然后BaseQuickAdapter<Status, BaseViewHolder>
   //第一个泛型Status是数据实体类型，第二个BaseViewHolder是ViewHolder其目的是为了支持扩展ViewHolder。
    //可以直接使用viewHolder对象点相关方法通过传入viewId和数据进行，方法支持链式调用。
    // 如果是加载网络图片或自定义view可以通过viewHolder.getView(viewId)获取该控件。
    @Override
    protected void convert(BaseViewHolder helper, CourseBean item) {
        ImageView courseImage = helper.getView(R.id.iv_course_image);
        Glide.with(GlobalCache.getContext()).load(item.coursePic).into(courseImage);

        helper.setText(R.id.tv_course_name, item.courseName);
        TextView priceType = helper.getView(R.id.tv_price_type);
        if (item.priceType.equals("0")) {
            priceType.setText("免费");
        } else {
            priceType.setText("¥"+item.price + "");
        }
    }

}
