package steam.com.app.mould;

import java.io.Serializable;

/**
 * File: BaseBean.java
 */
public class BaseBean implements Cloneable, Serializable {
    public int code;
    public String message;

    @Override
    public BaseBean clone() throws CloneNotSupportedException {
        return (BaseBean) super.clone();
    }
}
