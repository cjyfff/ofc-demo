package com.cjyfff.ofc.core.mapper;

import java.util.List;

import com.cjyfff.ofc.core.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    List<String> selectOrderIdsByStatus(Integer status);

    Order selectOrderByOrderId(String orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateHandleStatus(String orderId, Integer oldStatus, Integer newStatus);
}