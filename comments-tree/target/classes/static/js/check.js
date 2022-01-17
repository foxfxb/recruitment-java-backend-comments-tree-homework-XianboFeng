function phoneCheck(phoneObj) {
    var str = $(phoneObj).val();
    var reg = /^((0\d{2,3}-\d{7,8})|(1[3584796]\d{9}))$/;
    if (reg.test(str)){
        $(phoneObj).parent().find('.tips').hide();
        $(phoneObj).parent().find('.trueImg').css('display','block');
        $(phoneObj).parent().find('.errImg').css('display','none');
        return true;
    } else {
        $(phoneObj).parent().find('.tips').css('display','block');
        $(phoneObj).parent().find('.trueImg').css('display','none');
        $(phoneObj).parent().find('.errImg').css('display','block');
        return false;
    }
}

function birthdayCheck(birthdayObj) {
    var value = $(birthdayObj).val().split('-');
    var birthYear = value[0];
    var birthMonth =value[1];
    var birthDay = value[2];
    var nowDate = new Date();
    var nowYear = nowDate.getFullYear();
    var nowMonth = nowDate.getMonth()+1;
    var nowDay = nowDate.getDate();
    if (nowYear - birthYear < 18){
        $(birthdayObj).parent().find('.tips').css('display','block');
        $(birthdayObj).parent().find('.trueImg').css('display','none');
        $(birthdayObj).parent().find('.errImg').css('display','block');
        return false;
    } else if (nowYear - birthYear == 18){
        if (nowMonth < birthMonth){
            $(birthdayObj).parent().find('.tips').css('display','block');
            $(birthdayObj).parent().find('.trueImg').css('display','none');
            $(birthdayObj).parent().find('.errImg').css('display','block');
            return false;
        }else if (nowMonth == birthMonth){
            if (nowDay < birthDay){
                $(birthdayObj).parent().find('.tips').css('display','block');
                $(birthdayObj).parent().find('.trueImg').css('display','none');
                $(birthdayObj).parent().find('.errImg').css('display','block');
                return false;
            }
        }

    }
    $(birthdayObj).parent().find('.tips').hide();
    $(birthdayObj).parent().find('.trueImg').css('display','block');
    $(birthdayObj).parent().find('.errImg').css('display','none');
    return true;
}
function identityCheck(identityObj) {
    var identity = $(identityObj).val();
    var idReg = /^(([1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}[0-9Xx]))$/;
    if (idReg.test(identity)){
        $(identityObj).parent().find('.tips').hide();
        $(identityObj).parent().find('.trueImg').css('display','block');
        $(identityObj).parent().find('.errImg').css('display','none');
        return true;
    } else {
        $(identityObj).parent().find('.tips').css('display','block');
        $(identityObj).parent().find('.trueImg').css('display','none');
        $(identityObj).parent().find('.errImg').css('display','block');
        return false;


    }
}
function passwordCheck(passwordObj) {
    var password = $(passwordObj).val();
    var passwordReg = /^.*(?=.{8,25})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*?,./-=_]).*$/;
    if (passwordReg.test(password)){
        $(passwordObj).parent().find('.tips').hide();
        $(passwordObj).parent().find('.trueImg').css('display','block');
        $(passwordObj).parent().find('.errImg').css('display','none');
        return true;
    } else {
        $(passwordObj).parent().find('.tips').css('display','block');
        $(passwordObj).parent().find('.trueImg').css('display','none');
        $(passwordObj).parent().find('.errImg').css('display','block');
        return false;


    }
}

function emailCheck(emailObj) {
    var email = $(emailObj).val();
    var emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    if (email == "" || emailReg.test(email)){
        $(emailObj).parent().find('.tips').hide();
        $(emailObj).parent().find('.trueImg').css('display','block');
        $(emailObj).parent().find('.errImg').css('display','none');
        return true;
    } else {
        $(emailObj).parent().find('.tips').css('display','block');
        $(emailObj).parent().find('.trueImg').css('display','none');
        $(emailObj).parent().find('.errImg').css('display','block');
        return false;


    }
}



