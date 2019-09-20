package com.cjyfff.ofc.core.mapper;

import com.cjyfff.ofc.core.model.OrderStatusExcLog;

public interface OrderStatusExcLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderStatusExcLog record);

    int insertSelective(OrderStatusExcLog record);

    OrderStatusExcLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderStatusExcLog record);

    int updateByPrimaryKey(OrderStatusExcLog record);
}