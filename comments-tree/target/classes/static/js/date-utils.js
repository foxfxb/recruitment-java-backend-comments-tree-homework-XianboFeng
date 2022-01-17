var DEFAULT_FORMAT = "YYYY-MM-DD HH:mm:ss";
var SIMPLE_FORMAT = "YYYY-MM-DD";
var CLASS_FORMAT = "YYYY-MM-DD HH:mm";
function format(date, format){
    if(date == undefined || date == ""){
        return "";
    }
	var d = new Date(date);
    return d.Format(format);
}

function classDateFormat(d){
    return format(d, CLASS_FORMAT);
}
function simpleDateFormat(d){
    return format(d, SIMPLE_FORMAT);
}
function defaultDateFormat(d){
    return format(d, DEFAULT_FORMAT);
}

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "D+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(Y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


/**
 * 初始化时间选择输入框 className指定需要被初始化的input，不指定，默认为pick-date。timeFormat时间格式，不设置默认为YYYY-MM-DD
 * @param className
 * @param timeFormat
 */
function initDateInput(className,timeFormat, config) {
    var className, showTimePickerSeconds = false, isShowMin = false;
    if(!timeFormat){
        timeFormat = SIMPLE_FORMAT;
    }
    if(timeFormat == SIMPLE_FORMAT){
    }else if(timeFormat == CLASS_FORMAT){
        isShowMin = true;
    }else {// DEFAULT_FORMAT
        isShowMin = true;
        showTimePickerSeconds = true;
    }
    if(!className){
        className = "pick-date";
    }
    className = "input." + className;

    var minYear = 1950, maxYear = 2100;
    var format = function(moment, timeFormat){
        var year = moment.year();
        if(year < minYear){
            year = minYear;
        }else if(year > maxYear){
            year = maxYear;
        }
        moment.year(year);
        return moment.format(timeFormat);
    }

    if(!config){
        config = {
            minYear: minYear,
            maxYear: maxYear,
            // autoApply: true,
            autoUpdateInput: false,
            singleDatePicker: true,
            showDropdowns: true,
            timePicker: isShowMin, //显示时间
            timePicker24Hour: isShowMin, //时间制
            timePickerSeconds: showTimePickerSeconds, //时间显示到秒
            locale: {
                cancelLabel: '清除',
                format: timeFormat
            },
            ranges: {
                '今日': [moment().startOf('day'), moment()],
                '昨天': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
                '最近7日': [moment().subtract(6, 'days'), moment()],
                '最近30天': [moment().subtract(29, 'days'), moment()]
            },
        };
    }else{
        if(!config.minYear){
            config.minYear = minYear;
        }
        if(!config.maxYear){
            config.maxYear = maxYear;
        }
    }

    $(className).daterangepicker(config);
    $(className).on('apply.daterangepicker', function(ev, picker) {
        var dateStr = format(picker.startDate, timeFormat);
        $(this).val(dateStr);
    });
    $(className).on('cancel.daterangepicker', function() {
        $(this).val('');
    });
    $(className).on('change.daterangepicker', function() {
        var dateStr = format(moment($(this).val()), timeFormat);
        if(dateStr == "Invalid date"){
            dateStr = "";
        }else if(dateStr == "NaN-aN-aN aN:aN:aN"){
            dateStr = "";
        }
        $(this).val(dateStr);
    });
}

function getLastMonth(date){
    if(date == undefined || date == ""){
        return "";
    }
    var d = new Date(date);
    var month;
    if(d.getMonth() <= 0){
        d.setMonth(11);
        month = d.getMonth();
    }else {
        month = d.getMonth() - 1;
    }
    return month;
}

function getDateDiff (timestap) {
    var publishTime = timestap/1000,
        d_seconds,
        d_minutes,
        d_hours,
        d_days,
        timeNow = parseInt(new Date().getTime()/1000),
        d,

        date = new Date(publishTime*1000),
        Y = date.getFullYear(),
        M = date.getMonth() + 1,
        D = date.getDate(),
        H = date.getHours(),
        m = date.getMinutes(),
        s = date.getSeconds();
    //小于10的在前面补0
    if (M < 10) {
        M = '0' + M;
    }
    if (D < 10) {
        D = '0' + D;
    }
    if (H < 10) {
        H = '0' + H;
    }
    if (m < 10) {
        m = '0' + m;
    }
    if (s < 10) {
        s = '0' + s;
    }

    d = timeNow - publishTime;
    d_days = parseInt(d/86400);
    d_hours = parseInt(d/3600);
    d_minutes = parseInt(d/60);
    d_seconds = parseInt(d);

    if(d_days > 0 && d_days < 3){
        return d_days + '天前';
    }else if(d_days <= 0 && d_hours > 0){
        return d_hours + '小时前';
    }else if(d_hours <= 0 && d_minutes > 0){
        return d_minutes + '分钟前';
    }else if (d_seconds < 60) {
        if (d_seconds <= 0) {
            return '刚刚发表';
        }else {
            return d_seconds + '秒前';
        }
    }else if (d_days >= 3 && d_days < 30){
        return M + '-' + D + '&nbsp;' + H + ':' + m;
    }else if (d_days >= 30) {
        return Y + '-' + M + '-' + D + '&nbsp;' + H + ':' + m;
    }
}

//字符串转换为时间戳
function getDateTimeStamp (dateStr) {
    return Date.parse(dateStr.replace(/-/gi,"/"));
}

/**
 * 计算时间差,传入相差的毫秒数（正数）
 */
function diffTime(diff) {
    // 计算相差天数
    var days = Math.floor(diff / (24 * 3600 * 1000));

    // 计算小时数
    var leave1 = diff % (24 * 3600 * 1000);// 计算天数剩下的毫秒数
    var hours = Math.floor(leave1 / (3600 * 1000));

    // 计算相差的分钟数
    var leave2 = leave1 % (3600 * 1000);// 计算小时数后剩下的毫秒数
    var minutes = Math.floor(leave2 / (60 * 1000));

    // 计算相差秒数
    var leave3 = leave2 % (60 * 1000);// 计算分钟数后剩余的毫秒数
    var seconds = Math.round(leave3 / 1000);
    var time = {};
    time.seconds = seconds;
    time.minutes = minutes;
    time.hours = hours;
    time.days = days;
    return time;
}