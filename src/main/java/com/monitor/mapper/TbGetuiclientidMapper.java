package com.monitor.mapper;

import com.monitor.pojo.TbGetuiclientid;
import com.monitor.pojo.TbGetuiclientidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbGetuiclientidMapper {
    int countByExample(TbGetuiclientidExample example);

    int deleteByExample(TbGetuiclientidExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbGetuiclientid record);

    int insertSelective(TbGetuiclientid record);

    List<TbGetuiclientid> selectByExample(TbGetuiclientidExample example);

    TbGetuiclientid selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbGetuiclientid record, @Param("example") TbGetuiclientidExample example);

    int updateByExample(@Param("record") TbGetuiclientid record, @Param("example") TbGetuiclientidExample example);

    int updateByPrimaryKeySelective(TbGetuiclientid record);

    int updateByPrimaryKey(TbGetuiclientid record);
}