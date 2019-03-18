package steam.com.app.mould;

import java.io.Serializable;

import steam.com.app.application.GlobalCache;
import steam.com.app.util.Store;

/**
 * File: BaseRespBean.java
 */
public class BaseReqBean implements Cloneable, Serializable {
    public String token = Store.getString(GlobalCache.getContext(), Constans.TOKEN);

    @Override
    public BaseReqBean clone() throws CloneNotSupportedException {
        return (BaseReqBean) super.clone();
    }
}
