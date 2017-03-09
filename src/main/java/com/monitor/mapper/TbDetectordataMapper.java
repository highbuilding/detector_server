package com.monitor.mapper;

import com.monitor.pojo.TbDetectordata;
import com.monitor.pojo.TbDetectordataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbDetectordataMapper {
    int countByExample(TbDetectordataExample example);

    int deleteByExample(TbDetectordataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbDetectordata record);

    int insertSelective(TbDetectordata record);

    List<TbDetectordata> selectByExample(TbDetectordataExample example);

    TbDetectordata selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbDetectordata record, @Param("example") TbDetectordataExample example);

    int updateByExample(@Param("record") TbDetectordata record, @Param("example") TbDetectordataExample example);

    int updateByPrimaryKeySelective(TbDetectordata record);

    int updateByPrimaryKey(TbDetectordata record);
}