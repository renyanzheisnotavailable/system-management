package com.example.common.domain;

import com.example.common.enums.BusinessType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.lang.annotation.Target;
import java.util.Date;

@Data
@ToString
public class OperLog {
    /** 日志主键 */
    private String operId;

    /** 操作模块 */
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    private BusinessType businessType;

    /** 业务类型数组 */
    private  Integer[]businessTypes;

    /** 请求方法 */
    private String method;

    /** 请求方式 */
    private String requestMethod;


    /** 操作人员 */
    private Integer operUserId;

    /** 部门名称 */
    private String deptName;

    /** 请求url */
    private String operUrl;

    /** 操作地址 */
    private String operIp;


    /** 请求参数 */
    private String operParam;

    /** 返回参数 */
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    private Integer status;

    /** 错误消息 */
    private String errorMsg;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    /** 消耗时间 */
    private Long costTime;
}
