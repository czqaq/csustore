package org.csu.store.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserDTO {
    private Integer id;

    @NotBlank(message = "邮箱不能为空")

    private String email;
    @NotBlank(message = "电话号码不能为空")
    private String phone;

    private String question;
    private String answer;
}