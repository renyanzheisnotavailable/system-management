package com.example.api.domain;

import com.example.common.enums.BusinessType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.annotation.Target;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "log")
public class OperLog {
    /** 日志主键 */
    @Id
    private String operId;

    /** 操作模块 */
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    private BusinessType businessType;


    /** 请求方法 */
    private String method;

    /** 请求方式 */
    private String requestMethod;

    /** 操作人员 */
    private Integer operUserId;

    /** 部门名称 */
    private Integer deptId;

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
    @Field(type = FieldType.Date,format = DateFormat.date,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date operTime;

    /** 消耗时间 */
    private Long costTime;
}
