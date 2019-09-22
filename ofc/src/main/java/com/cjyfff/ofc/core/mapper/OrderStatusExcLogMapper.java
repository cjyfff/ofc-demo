package com.cjyfff.ofc.core.mapper;

import com.cjyfff.ofc.core.model.OrderStatusExcLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusExcLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderStatusExcLog record);

    int insertSelective(OrderStatusExcLog record);

    OrderStatusExcLog selectByPrimaryKey(Long id);

    OrderStatusExcLog selectByOrderIdAndStatus(@Param("orderId") String orderId, @Param("status") Integer status);

    int updateByPrimaryKeySelective(OrderStatusExcLog record);

    int updateByPrimaryKey(OrderStatusExcLog record);
}