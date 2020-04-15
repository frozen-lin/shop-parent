package com.frozen.member.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <program> shopparent </program>
 * <description> 通用Mapper基础类 </description>
 *
 * @author : lw
 * @date : 2020-03-26 20:22
 **/
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
