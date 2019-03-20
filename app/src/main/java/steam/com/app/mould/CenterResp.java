package steam.com.app.mould;

public class CenterResp extends BaseRespBean{

    /** 用户信息 */
    public UserBean userInfo;

    /** 积分信息 */
    public PointBean point;


    /***
     *  1. CenterResp对应整个返回对象， 就是下面这个， 分为三个模块： 1：-code， -message 在Base里面
     *
     *  {
     *     "code":0,
     *     "message":"string",
     *     "point":{
     *         "pointList":[
     *             {
     *                 "source":"string",
     *                 "value":0
     *             }
     *         ],
     *         "totalPoint":0
     *     },
     *     "userInfo":{
     *         "headPic":"string",
     *         "nickName":"string",
     *         "registerDate":"string"
     *     }
     * }
     *
     * 2.userInfo :是有三个属性所以你要单独定义一个 UserBean
     *
     *
     *
     *
     */
}