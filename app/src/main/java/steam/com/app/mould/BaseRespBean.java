package steam.com.app.mould;

import java.io.Serializable;

/**
 * File: BaseRespBean.java
 */
public class BaseRespBean implements Cloneable, Serializable {
    public int code;
    public String message;

    @Override
    public BaseRespBean clone() throws CloneNotSupportedException {
        return (BaseRespBean) super.clone();
    }
}
