package steam.com.app.mould;

import java.io.Serializable;

/**
 * File: OrderBean.java
 * Create: 2019/4/20 5:32 PM
 *
 * @author zshp
 */
public class OrderBean implements Serializable {
    public String orderId;
    public String userId;
    public String courseId;
    public String courseName;
    public String priceType;
    public String priceTypeDesc;
    public String status;
    public String statusDesc;
    public float price;
    public String courseType;
    public String courseTypeDesc;
    public String merchantName;
    public String coursePic;
    public String createTime;
}
