package com.frozen.message.component;

import com.frozen.message.adaptor.MessageAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <program> shopparent </program>
 * <description> 消息适配器策略类 </description>
 *
 * @author : lw
 * @date : 2020-03-27 15:08
 **/
@Component
public class MessageAdaptorStrategy {
    /**
     * 策略Map
     */
    private Map<String, MessageAdaptor> msgAdaptorMap = new HashMap<>();

    @Autowired
    public MessageAdaptorStrategy(Map<String, MessageAdaptor> msgAdaptorMap) {
        msgAdaptorMap.forEach((key, value) -> this.msgAdaptorMap.put(key, value));
    }

    /**
     * <description> 根据策略返回对应的策略类 </description>
     * @param strategy :
     * @return : com.frozen.message.adaptor.MessageAdaptor
     * @author : lw
     * @date : 2020/3/27 15:20
     */
    public MessageAdaptor getMessageAdaptor(String strategy){

        return msgAdaptorMap.get(strategy);
    }
}
