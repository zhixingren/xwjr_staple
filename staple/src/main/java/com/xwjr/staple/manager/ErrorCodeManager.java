package com.xwjr.staple.manager;

public class ErrorCodeManager {

    public static String getMessage(String key, String defaultMessage) {
        switch (key) {
            case "PASSWORD_NULL":
                return "请填写密码,不能为空字符";
            case "PASSWORD_LENGTH":
                return "请填写至少 6 位密码，不能包含空字符";
            case "PASSWORD_AGAIN_NULL":
                return "请填写密码确认";
            case "PASSWORD_AGAIN_INVALID":
                return "两次输入的密码不一致";
            case "PASSWORD_INVALID":
                return "您输入的密码不符合格式规范，请重新输入";
            case "REPASSWORD_NULL":
                return "请填写密码确认";
            case "REPASSWORD_INVALID":
                return "两次输入的密码不一致";
            case "MOBILE_USED":
                return "手机号码已被使用";
            case "MOBILE_CAPTCHA_NO_MATCH":
                return "短信验证码不正确";
            case "USER_NOT_EXIST":
                return "用户不存在";
            case "MOBILE_CAPTCHA_NULL":
                return "请填写手机短信验证码";
            case "MOBILE_CAPTCHA_INVALID":
                return "验证码无效或已过期，请尝试重新发送";
            case "MOBILE_CAPTCHA_EXPIRED":
                return "验证码无效或已过期，请尝试重新发送";
            case "AGREEMENT_NULL":
                return "注册需先同意服务条款";
            case "CAPTCHA_NULL":
                return "请填写验证码";
            case "CAPTCHA_INVALID":
                return "验证码不正确";
            case "MOBILE_NULL":
                return "请填写手机号码";
            case "MOBILE_INVALID":
                return "请输入正确的手机号";
            case "LOGINNAME_EXISTS":
                return "用户名已存在";
            case "LOGINNAME_STRICT":
                return "只能为2至15位英文字符、数字或下划线";
            case "LOGINNAME_NULL":
                return "请填写用户名";
            case "LOGINNAME_INVALID":
                return "只能为2至15位英文字符、数字或下划线";
            case "LOGINNAME_SIZE":
                return "只能为2至15位英文字符、数字或下划线";
            case "LOGINNAME_NOT_MOBILE":
                return "用户名不能是手机号（注册后可以用手机号登录）";
            case "NAME_NULL":
                return "请填写真实姓名";
            case "NAME_INVALID":
                return "真实姓名错误，应为2-15位中文汉字";
            case "EMAIL_NULL":
                return "请填写电子邮箱";
            case "EMAIL_INVALID":
                return "请输入正确的邮箱";
            case "IDNUMBER_INVALID":
                return "请正确填写 18 位身份证号码";
            case "LOGIN_INVALID":
                return "用户名或密码错误";
            case "INVALID_CAPTCHA":
                return "图形验证码错误";
            case "LOGINNAME_NOT_MATCH":
                return "手机号码与登录名不匹配";
            case "INVITATION_INVALID":
                return "H码无效";
            case "INVITATION_NULL":
                return "H码为空";
            case "PAYMENT_ACCOUNT_CREATE_ERROR":
                return "国政通实名认证校验未通过";
            case "SMSCAPTCHA_INVALID":
                return "验证码为6位";
            case "SMSCAPTCHA_NULL":
                return "验证码不能为空";
            case "INVITECODE_DELETED":
                return "邀请码过期";
            case "INVITECODE_INVALID":
                return "邀请码无效";
            case "MOBILE_EXISTS":
                return "手机号已被注册";
            case "INVALID_REQUIRED":
                return "图形验证码不正确";
            case "LOAN_CODE_ISNULL":
                return "标的密码为空";
            case "LOAN_CODE_ERROR":
                return "标的密码错误";
            default:
                return defaultMessage;
        }
    }
}

