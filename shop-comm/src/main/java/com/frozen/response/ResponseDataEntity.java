package com.frozen.response;

import lombok.Data;


/**
 * <program> shopparent </program>
 * <description> 接口返回实体类 </description>
 *
 * @author : lw
 * @date : 2020-03-25 14:01
 **/
@Data
public class ResponseDataEntity<T> {

    private Integer resCode;
    private String resMsg;
    private T data;

    public ResponseDataEntity(){

    }

    public ResponseDataEntity(Integer resCode, String resMsg, T data){
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.data = data;

    }
}
