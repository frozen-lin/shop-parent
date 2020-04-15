package com.frozen.mybatis.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 * @author : lw
 * @date : 2020-04-03 15:27
 **/
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
