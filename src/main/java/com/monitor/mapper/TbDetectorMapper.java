package com.monitor.mapper;

import com.monitor.pojo.TbDetector;
import com.monitor.pojo.TbDetectorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbDetectorMapper {
    int countByExample(TbDetectorExample example);

    int deleteByExample(TbDetectorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbDetector record);

    int insertSelective(TbDetector record);

    List<TbDetector> selectByExample(TbDetectorExample example);

    TbDetector selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbDetector record, @Param("example") TbDetectorExample example);

    int updateByExample(@Param("record") TbDetector record, @Param("example") TbDetectorExample example);

    int updateByPrimaryKeySelective(TbDetector record);

    int updateByPrimaryKey(TbDetector record);
}