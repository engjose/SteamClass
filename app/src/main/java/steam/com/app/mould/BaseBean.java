package steam.com.app.mould;

import java.io.Serializable;

/**
 * File: BaseBean.java
 * <p>
 * Author: zshp
 * <p>
 * Create: 2019/3/12 10:24 PM
 */
public class BaseBean implements Cloneable, Serializable {
    public int code;
    public String message;

    @Override
    public BaseBean clone() throws CloneNotSupportedException {
        return (BaseBean) super.clone();
    }
}
