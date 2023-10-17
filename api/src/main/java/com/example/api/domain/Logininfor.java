package com.example.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.elasticsearch.search.aggregations.metrics.InternalGeoBounds;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "logininfor")
public class Logininfor {
    @Id
    private Integer logId;
    /**
     * 用户昵称
     */
    private String username;
    private String email;
    /**
     * 用户部门
     */
    private String departmentName;
    /**
     * 用户单位
     */
    private String companyName;
    private String avatar;
    private Integer role;
    private Integer status;
    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地点 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 提示消息 */
    private String msg;

    /** 访问时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
}
