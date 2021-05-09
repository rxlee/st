package cn.jy.stork.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryTimeDao {

	Object insert(Integer id, Date fromDate, Date toDate);

}
