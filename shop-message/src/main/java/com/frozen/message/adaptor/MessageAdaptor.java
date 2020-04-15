package com.frozen.message.adaptor;

/**
 * <program> shopparent </program>
 * <description> 消息适配器接口 </description>
 *
 * @author : lw
 * @date : 2020-03-27 13:46
 **/
public interface MessageAdaptor {
    /** 
     * <description> 发送消息 </description>
     * @param message :
     * @author : lw 
     * @date : 2020/3/27 13:46
     */
    void sendMsg(Object message);
}
