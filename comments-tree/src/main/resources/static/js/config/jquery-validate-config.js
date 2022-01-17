jQuery.validator.addMethod("isPhone", function (value, element) {
    var length = value.length;
    var mobile = /^(((1[2-9][0-9]{1})))+\d{8}$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请填写正确的手机号码");//可以自定义默认提示信息

jQuery.validator.addMethod("loginNameCheck", function (value, element) {
    var length = value.length;
    var psw = /^[A-Za-z0-9]{5,20}$/;
    return this.optional(element) || (length > 5 && psw.test(value));
}, "只能使用字母和数字，长度在5~20之间");//可以自定义默认提示信息

jQuery.validator.addMethod("pwdCheck", function (value, element) {
    var length = value.length;
    var psw = /^.*(?=.{5,20})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*?,.]).*$$/;
    return this.optional(element) || (length > 5 && psw.test(value));
}, "密码应为长度5-20位大小写字母+数字+特殊字符组成");//可以自定义默认提示信息

// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element, params) {
    if(!value){
        return true;
    }else {
        var length = value.length;
        return this.optional(element) || (length == 11 && /^((0\d{2,3}-\d{7,8})|(1[3584796]\d{9}))$/.test(value));
    }
}, "请正确填写您的手机号码");

// 身份证号码验证
jQuery.validator.addMethod("isIdNumber", function(value, element, params) {
    if(!value){
        return true;
    }else {
        var length = value.length;
        return this.optional(element) || (length == 18 && /^(([1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}[0-9Xx]))$/.test(value));
    }
}, "请正确填写身份证号码");

// 课程上架时间不能低于当前时间
jQuery.validator.addMethod("effectiveTimeCheck", function(value, element) {
    if($("#courseStatus").val() == 3){
        var nowTime = defaultDateFormat(new Date().getTime());
        return (!isNullValue(value) && value >= nowTime);
    }else {
        return true;
    }
}, "发布时间不能为空且不能小于当前时间");

// 试看空值判断
jQuery.validator.addMethod("generalContentCheck", function(value, element) {
    return  generalContentUe.getContent();
}, "试看内容不能为空");

// 描述空值判断
jQuery.validator.addMethod("editorDetailCheck", function(value, element) {
    return  detailUe.getContent();
}, "描述内容不能为空");

jQuery.validator.addMethod("notNullMentors", function(value, element) {
    return  this.optional(element) || ($("#mentorIds_chosen .chosen-choices .search-choice").length != 0);
}, "请选择导师");

jQuery.validator.addMethod("notNullCourseIds", function(value, element) {
    return  this.optional(element) || ($("#courseIds_chosen .chosen-choices .search-choice").length != 0);
}, "请选择课程");

jQuery.validator.addMethod("recommendSourceCheck", function(value, element) {
    if($(".recommend").hasClass("displayNone")){
        return true;
    }else {
        return  !isNullValue($("#recommendName").val());
    }
}, "渠道名不能为空");

jQuery.validator.addMethod("memberReferralsCheck", function(value, element) {
    if($("#memberReferrals").parent().parent().hasClass("displayNone")){
        return true;
    }else {
        return  !isNullValue($("#memberReferrals").val());
    }
}, "渠道名不能为空");

jQuery.validator.addMethod("contactNameCheck", function(value, element) {
    if($("#contactName").parent().parent().hasClass("displayNone")){
        return true;
    }else {
        return  !isNullValue($("#contactName").val());
    }
}, "联系人不能为空");

jQuery.validator.addMethod("contactPositionCheck", function(value, element) {
    if($("#contactPosition").parent().parent().hasClass("displayNone")){
        return true;
    }else {
        return  !isNullValue($("#contactPosition").val());
    }
}, "联系人关系不能为空");

/**
 * 验证数值为正整数
 */
jQuery.validator.addMethod("isInteger", function(value, element) {
    var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
    return reg.test(value);
}, "请输入正整数");

jQuery.validator.addMethod("dateCheck", function (value, element) {
    var length = value.length;
    var date = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
    return this.optional(element) || (length > 8 && date.test(value));
}, "请填入正确的日期格式：yyyy-dd-mm");//可以自定义默认提示信息