package com.frozen.member.bean;

import lombok.Data;


/**
 * <program> shopparent </program>
 * <description> Member实体类 </description>
 *
 * @author : lw
 * @date : 2020-03-25 20:39
 **/
@Data
public class TestMember {
    private Long no;
    private String name;

    public TestMember(){

    }
    public TestMember(Long no, String name) {
        this.no = no;
        this.name = name;
    }
}
