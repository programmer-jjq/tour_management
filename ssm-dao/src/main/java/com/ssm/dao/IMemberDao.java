package com.ssm.dao;

import com.ssm.domain.Member;
import org.apache.ibatis.annotations.Select;

// 联系人持久层接口
public interface IMemberDao {

    // 根据id查询联系人
    @Select("select * from member where id=#{id}")
    public Member findById(Integer id) throws Exception;

}
