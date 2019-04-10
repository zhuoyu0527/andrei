
package com.telfa.andrei.base;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 能用MyBatis数据接口, 继承能用Mapper接口
 * @since 1.8
 */
public interface BaseMapper<T> extends Mapper<T>, InsertListMapper<T> {
}