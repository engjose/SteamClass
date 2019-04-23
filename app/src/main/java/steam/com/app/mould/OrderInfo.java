package steam.com.app.mould;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class OrderInfo implements Serializable {
    public List<OrderBean> draftOrderList = new ArrayList<>();
    public List<OrderBean> payOrderList = new ArrayList<>();
    public List<OrderBean> cancelOrderList = new ArrayList<>();
}
