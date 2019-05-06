package steam.com.app.mould;

import java.io.Serializable;

public class CourseBean extends BaseRespBean implements Serializable {
    public String courseId;
    public String courseInfo;
    public String courseName;
    public String coursePic;
    public String courseType;
    public String courseTypeDesc;
    public boolean isBuy;
    public String isCollect;
    public String merchantInfo;
    public String merchantName;
    public float price;
    public String priceType;
    public String teacherInfo;
    public String teacherName;
    public String videoUrl;
}
