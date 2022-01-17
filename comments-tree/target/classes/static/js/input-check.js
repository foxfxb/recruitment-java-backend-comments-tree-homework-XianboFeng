/**
 * type表示值类型，Float可有小数点。
 * length表示长度，
 * reg 正则判断的规则
 */
var className = {
    shortString : { "type" : "String", "length" : 10,},
    middleString : { "type" : "String", "length" : 50,},
    hundredString : { "type" : "String", "length" : 100,},
    hundred2String : { "type" : "String", "length" : 200,},
    longString : { "type" : "String", "length" : 1000,},
    hours : { "type" : "Float", "reg" : "hoursFloat", "event": "keyup paste change blur", "intLength": 2, "decimalLength": 1},
    price : { "type" : "Float", "reg" : "priceFloat", "event": "keyup paste change blur", "intLength": 6, "decimalLength": 2},
    threeDigitInteger : { "type" : "Integer", "reg" : "threeDigitInteger", "event": "keyup paste change blur", "intLength": 3},
    fourDigitInteger : { "type" : "Integer", "reg" : "fourDigitInteger",  "event": "keyup paste change blur", "intLength": 4},
    sixDecimalPlaces : { "type" : "Float", "reg" : "sixDecimalPlaces",  "event": "keyup paste change blur", "intLength": 6, "decimalLength": 2},
    eightDecimalPlaces : { "type" : "Float", "reg" : "sixDecimalPlaces",  "event": "keyup paste change blur", "intLength": 8, "decimalLength": 2},
    sevenDigitInteger : { "type" : "Integer", "reg" : "sevenDigitInteger",  "event": "keyup paste change blur", "intLength": 7},
}

/**
 * 当不符合正则时，提示的文本内容
 * @type {{hoursFloat: string, priceFloat: string, threeDigitInteger: string}}
 *
 */
var errorMes = {
    threeDigitInteger : "请输入三位整数",
    fourDigitInteger : "请输入四位整数",
    sixDecimalPlaces : "整数位6位以内，小数位2位",
    eightDecimalPlaces : "整数位8位以内，小数位2位",
    sevenDigitInteger : "请输入七位整数",
    hoursFloat : "整数位2位以内，小数位1位",
    priceFloat : "整数位4位以内，小数位2位",
}

/**
 *  正则规则
 * threeDigitInteger: integer, 三位整数
 * fourDigitInteger：integer，四位整数
 * sixDecimalPlaces：integer，六位整数
 * sevenDigitInteger：integer，七位整数
 * hoursFloat: 可为整数或小数，整数位2位以内，小数位1位
 * priceFloat：可为整数或小数，整数位4位以内，小数位2位
 *
 */
var REGEXP = {
    threeDigitInteger : "^([1-9]([0-9]{0,2})|0)$",
    fourDigitInteger : "^([1-9]([0-9]{0,3})|0)$",
    sixDecimalPlaces : "^(([0-9]|[1-9]([0-9]{1,5}))((\\.\\d{1,2})?))$",
    eightDecimalPlaces : "^(([0-9]|[1-9]([0-9]{1,7}))((\\.\\d{1,2})?))$",
    sevenDigitInteger : "^(([1-9]([0-9]{0,6}))|0)$",
    hoursFloat : "^([1-9]?[0-9](\\.\\d)?|0)$",
    priceFloat: "^(([0-9]|[1-9]([0-9]{1,3}))((\\.\\d{1,2})?))$",
}


/**
 *
 * @param value 检验的值，
 */
function limitCharacters(value, classAttributes) {
    if(classAttributes.type === "String"){
        return value.substring(0, classAttributes.length);
    }else{
        // let name = classAttributes["reg"];
        // var rex = new RegExp(REGEXP[name]);
        // if(rex.test(value)){
        //     return value;
        // }else {
        //     // errorDialog(errorMes[name]);
        //     return "";
        // }

        var intLength = classAttributes.intLength;
        if(!intLength){
            intLength = 5;
        }

        var decimalLength = classAttributes.decimalLength;
        if(!decimalLength){
            decimalLength = 2;
        }
        var decemalRegStr = "^(\\-)*(\\d+)\\.(\\d{0," + decimalLength + "}).*$";
        var decemalReg = new RegExp(decemalRegStr);
        value = value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
        if(classAttributes.type === "Float") {
            value = value.replace(/^\./g, ""); //验证第一个字符是数字
            value = value.replace(/\.{2,}/g, "."); //只保留第一个, 清除多余的
            value = value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            value = value.replace(decemalReg, '$1$2.$3'); //只能输入两个小数
        }else{
            // value = value.replace(/\./g, ""); //替换.
            if(value.length == 0){
                value = "";
            }else {
                value = parseInt(value).toFixed(0);
            }
        }
        //截断超长的整数
        var intValue = value.split(".")[0];
        if(intValue.length > intLength){
            if(value.length == 0){
                value = "";
            }else {
                value = value.substring(0, intLength);
            }
        }
    }
    var reg = new RegExp("^(0{1,})$");
    if(value.split(".")[1]){
        if(reg.test(value.split(".")[0])){
            return "0." + value.split(".")[1];
        }
    }else {
        if(reg.test(value)){
            return 0;
        }
    }
    return value;
}

/**
 *  shortString : 字符串不超过10位
 *  middleString : 字符串不超过50位
 *  hundredString : 字符串不超过100位
 *  longString : 字符串不超过1000位
 *  hours : 整数位2位以内，小数位1位
 *  price : 整数位4位以内，小数位2位
 *  threeDigitInteger : 请输入三位整数
 *  fourDigitInteger : "请输入四位整数",
 *  sixDecimalPlaces : "整数位6位以内，小数位2位",
 *  eightDecimalPlaces : "整数位8位以内，小数位2位",
 *  sevenDigitInteger : "请输入七位整数",
 * @param array 需要判断值得的class数组
 */
function initLimit(array) {
    $.each(array, function (index, obj) {
        event(obj);
    })
}

/**
 * 触发值判断的事件
 * @param elementClass
 */
function event(elementClass) {
    var eventSource = className[elementClass];
    if(!eventSource){
        return false;
    }
    var eventName = eventSource["event"];
    if(!eventName){
        eventName = "blur";
    }

    $("." + elementClass).on(eventName, function () {
        var value = limitCharacters($(this).val(), className[elementClass]);
        $(this).val(value);
    })
    return true;
}


