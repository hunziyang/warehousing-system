package com.yang.security.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class RegisterVo {
    @ApiModelProperty(value = "账号",required = true,dataType = "string",notes = "手机号或邮箱")
    private String account;
    @ApiModelProperty(value = "邮箱",dataType = "string",required = true)
    private String email;
    @ApiModelProperty(value = "手机号",dataType = "string",required = true)
    private String phone;
    @ApiModelProperty(value = "性别",dataType = "int")
    private Integer gender;
    @ApiModelProperty(value = "密码",dataType = "string",required = true)
    private String password;
}
