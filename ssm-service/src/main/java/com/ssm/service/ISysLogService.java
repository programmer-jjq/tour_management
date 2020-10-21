package com.ssm.service;

import com.ssm.domain.SysLog;

import java.util.List;

// 日志业务层接口
public interface ISysLogService {

    // 保存日志
    public void save(SysLog sysLog) throws Exception;

    // 查询所有日志
    public List<SysLog> findAll(int page,int size) throws Exception;

}
