var accessid = '';
var accesskey = '';
var host = '';
var policyBase64 = '';
var signature = '';
var callbackbody = '';
var filename = '';
var dirname = '';
var expire = 0;
var g_object_name = '';
var g_object_name_type = '';
var suffix = '';
var expireTime = new Date().getTime();

var model = null;
var type = null;
var id = null;
var needCallBack = false;

function send_request() {
    var xmlhttp = null;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    if (xmlhttp != null) {
        var data = (JSON.stringify(
            {
                'id': id,
                'type': type,
                'model': model
            }
        ));
        // serverUrl是 用户获取 '签名和Policy' 等信息的应用服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        var serverUrl = '/api/upload/ali/oss/policy';
        xmlhttp.open("POST", serverUrl, false);
        xmlhttp.setRequestHeader("Content-Type", "application/json;charset=utf-8");  //用POST的时候一定要有这句
        xmlhttp.send(data);
        return xmlhttp.responseText;
    } else {
        alert("Your browser does not support XMLHTTP.");
    }
};

function check_object_radio() {
    g_object_name_type = 'random_name';
}

function get_signature() {

    var now = new Date().getTime();
    if (expireTime < now + 30) {
        var body = send_request();
        var obj = eval("(" + body + ")");
        host = obj['host']
        policyBase64 = obj['policy']
        accessid = obj['accessid']
        signature = obj['signature']
        expire = parseInt(obj['expire']);
        expireTime = new Date().getTime() + expire * 1000;
        callbackbody = obj['callback']
        dirname = obj['dir']
        if (dirname != '' && dirname.indexOf('/') != dirname.length - 1)
        {
            dirname = dirname + '/'
        }
        return true;
    }
    return false;
};

function random_string(len) {
    len = len || 32;
    var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

function get_suffix(filename) {
    pos = filename.lastIndexOf('.')
    suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function calculate_object_name(filename) {

    suffix = get_suffix(filename)
    g_object_name = dirname + random_string(10) + suffix

    return ''
}

function get_uploaded_object_name(filename) {
    if (g_object_name_type == 'local_name')
    {
        var tmp_name = g_object_name;
        tmp_name = tmp_name.replace("${filename}", filename);
        return tmp_name
    }
    else if(g_object_name_type == 'random_name')
    {
        return g_object_name
    }
}

function set_upload_param(up, filename, ret) {
    if (ret == false) {
        ret = get_signature();
    }
    g_object_name = dirname;
    if (filename != '') {
        suffix = get_suffix(filename);
        this.filename = filename;
        calculate_object_name(filename);
    }
    var new_multipart_params = {
        'key': g_object_name,
        'policy': policyBase64,
        'OSSAccessKeyId': accessid,
        'success_action_status': '200', //让服务端返回200,不然，默认会返回204
        'callback': callbackbody,
        'signature': signature,
    };

    if(!needCallBack){
        new_multipart_params = {
            'key': g_object_name,
            'policy': policyBase64,
            'OSSAccessKeyId': accessid,
            'success_action_status': '200', //让服务端返回200,不然，默认会返回204
            'signature': signature,
        };
    }

    up.setOption({
        'url': host,
        'multipart_params': new_multipart_params
    });

    up.start();
}

function initUploadeross(uploadedFun, filters, cmodel, ctype, cid, wgets) {

    // $("body").append("<div id=\"dialog\" style=\" display: none\" title=\"上传文件\">\n" +
    //     "<div class=\"progress-label\">上传中...</div>\n" +
    //     "<div id=\"progressbar\"></div>\n" +
    //     "</div>");


    if (!filters) {
        filters = {};
    }
    model = cmodel;
    type = ctype;
    id = cid;
    if(model == 1){
        needCallBack = true;
    }else{
        needCallBack = false;
    }
    // var progressbar = $("#progressbar"), progressLabel = $(".progress-label");
    $.each(wgets, function () {
        var self = this;
        var uploader = new plupload.Uploader({
            runtimes: 'html5,flash,silverlight,html4',
            browse_button: $(this).attr("id"), // you can pass an id...
            container: document.getElementById(self.id).parentElement.getElementsByTagName("b")[0], // ... or DOM Element itself
            url: host,
            flash_swf_url: '../../js/plupload/Moxie.swf',
            silverlight_xap_url: '../../js/plupload/Moxie.xap',
            multi_selection: false,
            filters: filters,
            init: {
                PostInit: function () {
                    $("#filelist").html('');
                    $("#upload").click(function () {
                        showToast(uploader);
                    });
                },
                FilesAdded: function (up, files) {
                    set_upload_param(uploader, '', false);
                    uploader.start();
                    // dialog.dialog("open");
                    showToast(uploader);
                },

                UploadProgress: function (up, file) {
                    var percent = file.percent - 1;
                    $("#progressbar").css("width", percent + "%");
                    $("#progressbar span").text(percent + "%");
                    // progressbar.progressbar("value", file.percent - 1);

                },
                Error: function (up, err) {
                    errorDialog(dicErrCodeCN(err.message));
                    // $(self).parent().find("b").html("Error #" + err.code + ": " + err.message);
                },
                BeforeUpload: function (up, file) {
                    check_object_radio();
                    set_upload_param(up, file.name, true);
                },
                FileUploaded: function (up, file, info) {

                    $("#progressbar").css("width", "100%");
                    $("#progressbar span").text("100%");
                    // progressbar.progressbar("value", 100);
                    var response = eval('('+info.response+')');
                    if (needCallBack && response.code != 200) {
                        // $(self).parent().find("b").html("文件上传失败");
                        errorDialog("文件上传失败");
                    } else {
                        var urlAddress = host + get_uploaded_object_name();

                        var fileObject = {};
                        fileObject.urlAddress = urlAddress;
                        fileObject.fileName = filename;

                        swal.getCancelButton().style = "display: none";
                        swal.getConfirmButton().style = "display: block";
                        successTips("上传成功");
                        uploadedFun(fileObject, self);
                    }
                },
            },
        });

        uploader.init();
    })
}


/**
 *企业员工excel批量导入所使用
 */
function initUploader(url, uploadedFun, lastFileWget, filters){
    if(!filters){
        filters = {};
    }
    // var progressbar = $("#progressbar"), progressbar = $("#progressbar"), progressLabel = $(".progress-label");
    var uploader = new plupload.Uploader({
        runtimes: 'html5,flash,silverlight,html4',
        browse_button: 'pickfiles', // you can pass an id...
        container: document.getElementById('container'), // ... or DOM Element itself
        url: url,
        flash_swf_url: '../../js/plupload/Moxie.swf',
        silverlight_xap_url: '../../js/plupload/Moxie.xap',
        multi_selection: false,

        filters: filters,
        init: {
            PostInit: function () {
                $("#filelist").html('');
                $("#upload").click(function () {
                    uploader.start();
                });
                // showToast(uploader);
            },
            FilesAdded: function (up, files) {
                uploader.start();
                showToast(uploader);
            },
            UploadProgress: function (up, file) {
                // progressbar.progressbar("value", file.percent - 1);
                var percent = file.percent - 1;
                $("#progressbar").css("width", percent + "%");
                $("#progressbar span").text(percent + "%");
            },
            Error: function (up, err) {
                errorDialog(dicErrCodeCN(err.message));
                // $(self).parent().find("b").html("Error #" + err.code + ": " + err.message);
            },
            BeforeUpload: function () {
                if(lastFileWget && lastFileWget != '') {
                    uploader.settings.url = url + "?lastFilePath=" + $(lastFileWget).val();
                }else{
                    uploader.settings.url = url;
                }
            },
            FileUploaded: function (up, files, objResponse) {

                $("#progressbar").css("width", "100%");
                $("#progressbar span").text("100%");
                uploadedFun(objResponse);
                // progressbar.progressbar("value", 100);

            }
        },

    });
    // var dialogButtons = [{
    //     text: "取消",
    //     click: stopUpload
    // }];
    // progressbar.progressbar({
    //     value: false,
    //     change: function () {
    //         progressLabel.text("进度" + progressbar.progressbar("value") + "%");
    //     },
    //     complete: function () {
    //         progressLabel.text("完成!");
    //         dialog.dialog("option", "buttons", [{
    //             text: "Close",
    //             click: stopUpload
    //         }]);
    //         $(".ui-dialog button").last().trigger("focus");
    //     }
    // });

    uploader.init();
}

function stopUpload(uploader) {
    uploader.stop();
}

function showToast(uploader) {

    var swalPart = swal.fire({
        title: '上传文件',
        type: 'info',
        html: "<div class=\"progress-label\">" +
            "   <div class=\"progress active\">" +
            "       <div class=\"progress-bar bg-primary progress-bar-striped\" id='progressbar' role=\"progressbar\"\n" +
            "             aria-valuenow=\"0\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 0%\">" +
            "           <span class=\"sr-only\">0%</span>\n" +
            "       </div>\n" +
            "   </div>" +
            "</div>",
        showConfirmButton: false,
        showCloseButton: true,
        showCancelButton: true,
        // focusConfirm: false,
        confirmButtonText: "完成",
        cancelButtonText: '取消',
        allowOutsideClick: false,
        onClose: function(){
            stopUpload(uploader);
        }
    })
    console.log(swalPart);
    return swalPart;
}