package steam.com.app.mould;

import java.io.Serializable;

/**
 * public interface Serializable类通过实现 java.io.Serializable 接口以启用其序列化功能。
 * 未实现此接口的类将无法使其任何状态序列化或反序列化。可序列化类的所有子类型本身都是可序列化的。
 * 序列化接口没有方法或字段，仅用于标识可序列化的语义。
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
