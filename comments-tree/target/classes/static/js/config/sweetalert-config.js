
var swal, Toast;

$(function() {
    try {
        if(undefined == Swal){
            swal = top.Swal;
        }else{
            swal = Swal;
        }
    }catch (e) {
        swal = top.Swal;
    }

    Toast = swal.mixin({
        toast: true,
        position: 'top',
        showConfirmButton: false,
        timer: 3000
    });
})

function confirmDialog(title,icon, msg, sureFunc) {
    swal.fire({
        title: title,
        text: msg,
        icon: icon,
        type: "question",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: '确认',
        cancelButtonText:"取消"
    }).then((result) => {
        if (result.value) {
            if(sureFunc){
                sureFunc();
            }
        }
    })
}

function successDialog(callback, title, timer){

    if(!title){
        title = "操作成功";
    }
    if(!timer){
        timer = 1500;
    }

    Toast.fire({
        type: 'success',
        title: title,
        showConfirmButton: false,
        timer: timer
    }).then((result) => {
        if (callback) {
            callback();
        }
    });
}

function errorDialog(msg, func){
    if(!msg){
        msg = "数据异常，请重试";
    }
    Toast.fire({
        type: 'error',
        text: msg,
    }).then((result) => {
        if (func) {
            func();
        }
    });
}

function confirmDialogWithAjax(title, msg, ajaxFunc) {
    swal.fire({
        title: title,
        text: msg,
        type: "question",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: '确认',
        cancelButtonText:"取消",
        showLoaderOnConfirm: true,
        preConfirm: ajaxFunc
    })
}

function successTips(msg, callback){
    if(!msg){
        msg = "操作成功";
    }
    Toast.fire({
        type: 'success',
        text: msg,
    }).then((result) => {
        if (callback) {
            callback();
        }
    });
}