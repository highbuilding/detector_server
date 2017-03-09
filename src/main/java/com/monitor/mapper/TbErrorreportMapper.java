package com.monitor.mapper;

import com.monitor.pojo.TbErrorreport;
import com.monitor.pojo.TbErrorreportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbErrorreportMapper {
    int countByExample(TbErrorreportExample example);

    int deleteByExample(TbErrorreportExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbErrorreport record);

    int insertSelective(TbErrorreport record);

    List<TbErrorreport> selectByExample(TbErrorreportExample example);

    TbErrorreport selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbErrorreport record, @Param("example") TbErrorreportExample example);

    int updateByExample(@Param("record") TbErrorreport record, @Param("example") TbErrorreportExample example);

    int updateByPrimaryKeySelective(TbErrorreport record);

    int updateByPrimaryKey(TbErrorreport record);
}