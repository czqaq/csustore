package org.csu.store.controller;

import org.csu.store.common.CONSTANT;
import org.csu.store.common.CommonResponse;
import org.csu.store.common.ResponseCode;
import org.csu.store.domain.User;
import org.csu.store.dto.UpdateUserDTO;
import org.csu.store.service.UserService;
import org.csu.store.util.CookieUtil;
import org.csu.store.util.JSONUtil;
import org.csu.store.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user/")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("login")
    public CommonResponse<User> login(@RequestParam @Validated @NotBlank(message = "用户名不能为空") String username,
                                      @RequestParam @Validated @NotBlank(message = "密码不能为空") String password,
                                      HttpSession session,
                                      HttpServletResponse httpServletResponse) {
        CommonResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(CONSTANT.LOGIN_USER, response.getData());
            String userJson = JSONUtil.objectToString(response.getData());
            redisUtil.getTemplate().opsForValue().set(session.getId(),userJson,60, TimeUnit.MINUTES);
            CookieUtil.writeLoginToken(httpServletResponse,session.getId());
        }
        return response;
    }
    @PostMapping("login_without_redis")
    public CommonResponse<User> loginWithoutRedis(@RequestParam @Validated @NotBlank(message = "用户名不能为空") String username,
                                      @RequestParam @Validated @NotBlank(message = "密码不能为空") String password,
                                      HttpSession session,
                                      HttpServletResponse httpServletResponse) {
        CommonResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(CONSTANT.LOGIN_USER, response.getData());
        }
        return response;
    }

    @PostMapping("check_field")
    public CommonResponse<String> checkField(
            @RequestParam @Validated @NotBlank(message = "字段名不能为空") String fieldName,
            @RequestParam @Validated @NotBlank(message = "字段值不能为空") String fieldValue) {
        return userService.checkField(fieldName, fieldValue);
    }

    @PostMapping("register")
        public CommonResponse<String> register(@RequestBody @Valid User user){
        return userService.register(user);
    }

    @PostMapping("get_forget_question")
    public CommonResponse<String> getForgetQuestion(
            @RequestParam @Validated @NotBlank(message = "用户名不能为空") String username){
        return userService.getForgetQuestion(username);
    }

    @PostMapping("check_forget_answer")
    public CommonResponse<String> checkForgetAnswer(
            @RequestParam @Validated @NotBlank(message = "用户名不能为空") String username,
            @RequestParam @Validated @NotBlank(message = "忘记密码问题不能为空") String question,
            @RequestParam @Validated @NotBlank(message = "忘记密码问题答案不能为空") String answer){
        return userService.checkForgetAnswer(username,question,answer);
    }

    @PostMapping("reset_forget_password")
    public CommonResponse<String> resetForgetPassword(
            @RequestParam @Validated @NotBlank(message = "用户名不能为空") String username,
            @RequestParam @Validated @NotBlank(message = "新密码不能为空") String newPassword,
            @RequestParam @Validated @NotBlank(message = "重置密码token不能为空") String forgetToken){
        return userService.resetForgetPassword(username,newPassword,forgetToken);
    }

    @PostMapping("reset_password")
    public CommonResponse<String> resetPassword(
            @RequestParam @Validated @NotBlank(message = "旧密码不能为空") String oldPassword,
            @RequestParam @Validated @NotBlank(message = "新密码不能为空") String newPassword,
            HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken == null){
            return CommonResponse.createForError("用户未登录");
        }
        String userJson = redisUtil.getTemplate().opsForValue().get(loginToken);
        User loginUser=JSONUtil.stringToObject(userJson,User.class);
        return userService.resetPassword(oldPassword, newPassword,loginUser);
    }

    @PostMapping("get_user_detail")
    public CommonResponse<User> getUserDetail(HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken == null){
            return CommonResponse.createForError("用户未登录");
        }
        String userJson = redisUtil.getTemplate().opsForValue().get(loginToken);
        User loginUser=JSONUtil.stringToObject(userJson,User.class);
        return userService.getUserDetail(loginUser.getId());
    }

    @PostMapping("update_user_info")
    public CommonResponse<User> updateUserInfo(@RequestBody @Valid UpdateUserDTO updateUser,
                                               HttpSession session,HttpServletRequest request,HttpServletResponse httpServletResponse){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken == null){
            return CommonResponse.createForError("用户未登录");
        }
        String userJson = redisUtil.getTemplate().opsForValue().get(loginToken);
        User loginUser=JSONUtil.stringToObject(userJson,User.class);
        loginUser.setEmail(updateUser.getEmail());
        loginUser.setPhone(updateUser.getPhone());
        loginUser.setQuestion(updateUser.getQuestion());
        loginUser.setAnswer(updateUser.getAnswer());
        System.out.println(loginUser);
        CommonResponse<String> result = userService.updateUserInfo(loginUser);
        if(result.isSuccess()){
            session.setAttribute(CONSTANT.LOGIN_USER, loginUser);
            userJson = JSONUtil.objectToString(loginUser);
            redisUtil.getTemplate().delete(loginToken);
            redisUtil.getTemplate().opsForValue().set(session.getId(),userJson,60, TimeUnit.MINUTES);
            CookieUtil.deleteLoginToken(request,httpServletResponse);
            CookieUtil.writeLoginToken(httpServletResponse,session.getId());
            return CommonResponse.createForSuccess(loginUser);
        }
        return CommonResponse.createForError(result.getMessage());
    }

    @GetMapping("logout")
    public CommonResponse<String> logout(HttpServletRequest request,HttpServletResponse httpServletResponse){
        String loginToken = CookieUtil.readLoginToken(request);
        redisUtil.getTemplate().delete(loginToken);
        CookieUtil.deleteLoginToken(request,httpServletResponse);
        return CommonResponse.createForSuccessMessage("退出登录成功");
    }

}
