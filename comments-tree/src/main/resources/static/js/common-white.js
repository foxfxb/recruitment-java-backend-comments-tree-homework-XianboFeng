
var serverName = "http://localhost:8082";
var domainUrl = "/";
var pageSize = 20;

$(document).ready(function () {
    $('head').append("<script language=javascript src='/plugins/fakeLoader/js/fakeLoader.min.js'></script>");
    $('head').append("<script language=javascript src='/js/input-check.js'></script>");
    $('head').append("<link rel='stylesheet' href='/plugins/fakeLoader/css/fakeLoader.min.css'>");

    var array = ["shortString", "shortString20", "middleString", "hundredString", "longString", "longString1000", "hours", "numberOfPeople", "price", "fourDigitInteger", "sixDecimalPlaces", "eightDecimalPlaces", "sevenDigitInteger"];
    initLimit(array);
    // var linkList = document.getElementsByTagName('link');
    var linkList = document.getElementsByTagName('script');
    // changeVersion(linkList, "css");
    changeVersion(linkList, "script");
    $("body").click(function () {
        top.$(".dropdown.show").click();
    });
    addTitle();
});

/**
 * 为button div a标签中有title的更换title样式
 */
function addTitle() {
    $("button[title]").tooltip({
        placement: "right",
        container: "body",
        trigger: "hover",
    });
    $("a[title]").tooltip({
        placement: "top",
        container: "body",
        trigger: "hover",
    });
    $("div[title]").tooltip({
        placement: "top",
        container: "body",
        trigger: "hover",
    });
    $("div span[title]").tooltip({
        placement: "top",
        container: "body",
        trigger: "hover",
    });
    $("i[title]").tooltip({
        placement: "top",
        container: "body",
        trigger: "hover",
    });
}

function changeVersion(linkList,flag){
    var versionNum = new Date().getTime();
    if(flag=='css'){
        for (var item of linkList) {
            item.href = item.href+'?version='+versionNum;
        }
    }else if(flag=='script'){
        for (var item of linkList) {
            item.src = item.src+'?version='+versionNum;
        }
    }
}

function doMethod(method, action, data, sucFun, errFun, async){

    if(async == undefined){
        async = true;
    }

    var errCallBack = function (response) {
        console.error(response);
        if (errFun == undefined) {
            errFun = function () {
                alert("系统错误");
            };
        }
        var status = response.status;
        if (status == 401) {
            window.top.location.href = domainUrl + "login.html";
        } else {
            errFun("系统错误");
        }
    };

    $.ajax({
        url: action,// 跳转到 action
        data: data,
        type: method,
        cache: false,
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        beforeSend: function (xhr) {
            var loadingHtml =   '<div class="fakeLoader"></div>';
            $("body").append(loadingHtml);
            $.fakeLoader({
                timeToHide: 60000,
                bgColor: "#A8A8A8",
                spinner: "spinner7"
            });
        },
        global : false,
        crossDomain: true,
        xhrFields: {
            withCredentials: true
        },
        success: sucFun,
        async: async,
        error: errCallBack,
        complete: function (XMLHttpRequest, status) {
            $.fakeLoader({
                timeToHide: 1,
                bgColor: "#A8A8A8",
                spinner: 'spinner7'
            });
            $(".fakeLoader").remove();
            addTitle();
            $(".tooltip").remove();
        }
    });
}


function post(action, data, sucFun, errFun) {
    return doMethod('POST', action, JSON.stringify(data), sucFun, errFun);
}

function delreq(action, data, sucFun, errFun) {
    return doMethod('DELETE', action, JSON.stringify(data), sucFun, errFun);
}

function put(action, data, sucFun, errFun) {
    return doMethod('PUT', action, JSON.stringify(data), sucFun, errFun);
}

function query(action, data, sucFun, errFun, async) {
    for(var key  in data){
        if(typeof(data[key])=="object"){
            data[key]=JSON.stringify(data[key]);
        }
    }
    doMethod('GET', action, data, sucFun, errFun, async);
}

$.ajaxSetup({
    type: 'post',
    cache: false,
    dataType: 'json',
    contentType: "application/json;charset=utf-8",
    global : false,
});

$.fn.serializeJson = function() {
    var serializeObj = {};
    var array = this.serializeArray();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    return JSON.stringify(serializeObj);
};

function getValue(obj, defualt){
    if(undefined == obj || obj == null || obj === "" || obj === "null"){
        if(defualt === 0){
            return 0
        }else {
            return !defualt ? "" : defualt;
        }
    }else{
        return obj;
    }
}
function isNullValue(obj){
    if(undefined == obj || obj == '' || obj == null){
        return true;
    }else{
        return false;
    }
}

function parseUriParams(){
    var qutoIndex = window.location.href.indexOf("?");
    var obj={};
    if(qutoIndex > 0) {
        var paramStr = window.location.href.substr(qutoIndex + 1);
        var paramArr = paramStr.split('&');
        $.each(paramArr, function (i, param) {
            var key = param.split("=")[0];
            var val = param.split("=")[1];
            obj[key] = val;
        });
    }
    return obj;
}

function resize(iframe){
    var minheight = $(document).find("html").height() - 58 - 57;

    var userAgent = navigator.userAgent;
    var subdoc = iframe.contentDocument || iframe.contentWindow.document;
    var subbody = subdoc.body;
    var realHeight;
    // //谷歌浏览器特殊处理
    // if(userAgent.indexOf("Chrome") > -1){
    //     realHeight = subdoc.documentElement.scrollHeight;
    // }else{
        realHeight = subbody.scrollHeight;
    // }
    // console.info(minheight);
    // console.info(realHeight);
    if(realHeight < minheight){
        $(iframe).height(minheight);
    }else{
        $(iframe).height(realHeight);
    }
    // console.info($(iframe).height());
}

/**
 *
 * @param value
 * @return {string}
 */
function setNull(value) {
    if((value == "---请选择---") || (value == "全部")){
        return "";
    }else {
        return value;
    }
}

function color16() {//十六进制颜色随机
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    var color = '#' + r.toString(16) + g.toString(16) + b.toString(16);
    return color;
}

function isLogin(){
    if(top.userInfo == null || top.userInfo.userId == null){
        return false;
    }
    return true;
}

