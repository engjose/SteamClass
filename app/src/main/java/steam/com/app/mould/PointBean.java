package steam.com.app.mould;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PointBean implements Serializable {

    /** 总积分 */
    public Integer totalPoint;

    /** 积分明细列表 */
    public List<PointItemBean> pointList = new ArrayList<>();
}
