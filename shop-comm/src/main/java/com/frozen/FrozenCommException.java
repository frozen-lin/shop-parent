package com.frozen;

/**
 * <program> shop-parent </program>
 * <description> 自定义异常 </description>
 *
 * @author : lw
 * @date : 2020-04-03 16:05
 **/
public class FrozenCommException extends Exception{
    public FrozenCommException() {
        super();
    }
    public FrozenCommException(String message) {
        super(message);
    }
    public FrozenCommException(String message, Throwable cause) {
        super(message, cause);
    }
    public FrozenCommException(Throwable cause) {
        super(cause);
    }
}
