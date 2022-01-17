function initProvinceSelect(provinceId) {
    $("#provinceId").change(function () {
        initCity($(this).val());
    });
    initProvince(provinceId);
}

function initCitySelect(provinceId, cityId) {
    $("#cityId").change(function () {
        initRegion($(this).val());
    });
    initCity(provinceId, cityId);
}

function initProvince(provinceId) {
    var provinceList = top.allProvincesWithCity;
    var provinceHtml = "";
    var provinceOption = $("#provinceId option[value='']");
    if ($(provinceOption).html()) {
        provinceHtml += "<option value=''>" + $(provinceOption).html() + "</option>";
    }

    $.each(provinceList, function (index, obj) {
        if (provinceId == obj.provinceId) {
            provinceHtml += "<option value=\"" + obj.provinceId + "\" selected='selected'>" + obj.provinceName + "</option>";
        } else {
            provinceHtml += "<option value=\"" + obj.provinceId + "\">" + obj.provinceName + "</option>";
        }
    });

    $("#provinceId").html(provinceHtml);
}

function initCity(provinceId, cityId) {
    var cityList = {};
    $.each(top.allProvincesWithCity, function (index, obj) {
        if (provinceId == obj.provinceId) {
            cityList = obj.cityList;
            return false;
        }
    });

    var cityHtml = "";
    var cityOption = $("#cityId option[value='']");
    if ($(cityOption).html()) {
        cityHtml += "<option value=''>" + $(cityOption).html() + "</option>";
    }

    $.each(cityList, function (index, obj) {
        if (obj.cityId == cityId) {
            cityHtml += "<option value='" + obj.cityId + "' selected='selected'>" + obj.cityName + "</option>";
        } else {
            cityHtml += "<option value='" + obj.cityId + "'>" + obj.cityName + "</option>";
        }
    });

    $("#cityId").html(cityHtml);

    if (!cityId && !$("#cityId option[value='']").html()) {
        cityId = cityList[0].cityId;
    }
    initRegion(cityId);
}

function initRegion(cityId, regionId) {
    if(!$("#regionId")){
        return false;
    }
    var regionList = {};
    $.each(top.allCitiesWithRegion, function (index, obj) {
        if (cityId == obj.cityId) {
            regionList = obj.regionList;
            return false;
        }
    });

    var regionHtml = "";
    var regionOption = $("#regionId option[value='']");
    if ($(regionOption).html()) {
        regionHtml += "<option value=''>" + $(regionOption).html() + "</option>";
    }

    $.each(regionList, function (index, obj) {
        if (regionId == obj.regId) {
            regionHtml += "<option value='" + obj.regId + "' selected='selected'>" + obj.regName + "</option>";
        } else {
            regionHtml += "<option value='" + obj.regId + "'>" + obj.regName + "</option>";
        }
    });

    $("#regionId").html(regionHtml);
}

function activedUsers(userId, wgetName) {
    var manageUserText = "", manageUserOption;

    if (wgetName && wgetName != '') {
        manageUserOption = $("#" + wgetName + " option[value='']");
    } else {
        manageUserOption = $("#manageUserId option[value='']");
    }
    if ($(manageUserOption).html()) {
        manageUserText += "<option value=''>" + $(manageUserOption).html() + "</option>";
    }
    $.each(top.crmUserList, function (index, obj) {
        if (userId == obj.userId) {
            manageUserText += "<option value='" + obj.userId + "' selected='selected'>" + obj.name + "</option>";
        } else {
            manageUserText += "<option value='" + obj.userId + "'>" + obj.name + "</option>";
        }
    });
    if (wgetName) {
        $("#" + wgetName).html(manageUserText);
        // $("#"+wgetName).combobox();
    } else {
        $("#manageUserId").html(manageUserText);
        // $("#manageUserId").combobox();
    }
}

/**
 * 负责人
 * @param userId
 * @param wgetName
 */
function projectUsers(userId, wgetName) {
    var manageUserText = "";
    var manageUserOption = $("#partyBContactId option[value='']");
    if ($(manageUserOption).html()) {
        manageUserText += "<option value=''>" + $(manageUserOption).html() + "</option>";
    }
    $.each(top.crmUserList, function (index, obj) {
        if(userId == obj.userId){
            manageUserText += "<option value='" + obj.userId + "' selected='selected'>" + obj.name + "</option>";
        }else {
            manageUserText += "<option value='" + obj.userId + "'>" + obj.name + "</option>";
        }
    });
    if(wgetName && wgetName != ''){
        $("#"+wgetName).html(manageUserText);
    }else{
        $("#partyBContactId").html(manageUserText);
    }
}

function projectUsersName(partyBContactId) {
    var name = '';
    $.each(top.crmUserList, function (index, obj) {
        if(partyBContactId == obj.userId) {
            name = obj.name;
            return false;
        }
    });
    return name;
}

function noticeCreateUsersName(createUserId) {
    var name = '';
    $.each(top.crmUserList, function (index, obj) {
        if(createUserId == obj.userId) {
            name = obj.name;
            return false;
        }
    });
    return name;
}

function userName(userId) {
    var name = '';
    $.each(top.crmUserList, function (index, obj) {
        if(userId == obj.userId) {
            name = obj.name;
            return false;
        }
    });
    return name;
}

function initTeacherOption(serveType, mentorIds, coachIds) {
    if (!coachIds) {
        coachIds = "";
    }
    if(!mentorIds){
        mentorIds = "";
    }
    //查询教师
    var action = "/api/teacher/splitTeacher";
    var data = {};
    query(action, data, function (result) {
        var mentorList = result.data.mentorList;
        var coachList = result.data.coachList;

        var mentorIdText = "<option value=''></option>";
        $.each(mentorList, function (index, obj) {
            var jobType = obj.jobType;
            if (jobType != serveType && jobType != 0) {
                return true;
            }
            if (mentorIds.indexOf("," + obj.mentorId + ",") > -1) {
                mentorIdText += "<option value='" + obj.mentorId + "' selected='selected'>" + obj.mentorName + "</option>";
            } else {
                mentorIdText += "<option value='" + obj.mentorId + "'>" + obj.mentorName + "</option>";
            }
        });
        var coachIdsText = "<option value=''></option>";
        $.each(coachList, function (index, obj) {
            var jobType = obj.jobType;
            if (jobType != serveType && jobType != 0) {
                return true;
            }
            if (coachIds.indexOf("," + obj.coachId + ",") > -1) {
                coachIdsText += "<option value='" + obj.coachId + "' selected='selected'>" + obj.coachName + "</option>";
            } else {
                coachIdsText += "<option value='" + obj.coachId + "'>" + obj.coachName + "</option>";
            }
        });

        $("#coachIds").html(coachIdsText);
        $("#mentorIds").html(mentorIdText);
        initMultipleWget();
    }, function (msg) {
        Toast.fire({
            type: 'error',
            text: msg
        })
    });
}

function initMultipleWget(wgetName) {
    if(!wgetName || wgetName == '') {
        $(".multiple").chosen({
            allow_single_deselect: true,
            width: '245px'
        });
    }else{
        $("#"+wgetName).chosen({
            allow_single_deselect: true,
            width: '245px'
        });

    }
    $('[title="clickable_optgroup"]').addClass('chosen-container-optgroup-clickable');
    $(document).on('click', '[title="clickable_optgroup"] .group-result', function () {
        var unselected = $(this).nextUntil('.group-result').not('.result-selected');
        if (unselected.length) {
            unselected.trigger('mouseup');
        } else {
            $(this).nextUntil('.group-result').each(function () {
                $('a.search-choice-close[data-option-array-index="' + $(this).data('option-array-index') + '"]').trigger('click');
            });
        }
    });
}

function initMentorOption(serveType, mentorId) {
    //查询教师
    var action = "/api/teacher/splitTeacher";
    var data = {};
    query(action, data, function (result) {
        var mentorList = result.data.mentorList;

        var mentorIdText = "<option value=''>---请选择---</option>";
        $.each(mentorList, function (index, obj) {
            var jobType = obj.jobType;
            if (jobType != serveType && jobType != 0) {
                return true;
            }
            if(obj.mentorId == mentorId){
                mentorIdText += "<option value='" + obj.mentorId + "' selected='selected'>" + obj.mentorName + "</option>";
            }else {
                mentorIdText += "<option value='" + obj.mentorId + "'>" + obj.mentorName + "</option>";
            }
        });

        $("#mentorId").html(mentorIdText);
        // $("#mentorId").selectmenu("refresh");
    }, function (msg) {
        Toast.fire({
            type: 'error',
            text: msg
        })
    });
}

function selectOption(obj, arr) {
    var optionName = $(obj).attr("name");
    var optionValue = $(obj).val();
    var optionHtml = "";

    if ($("#" + optionName).html()) {
        optionHtml += $("#" + optionName).html();
    }

    $.each(arr, function (key, obj) {
        if (optionValue == key) {
            optionHtml += "<option value=\"" + key + "\" selected='selected'>" + obj + "</option>";
        } else {
            optionHtml += "<option value=\"" + key + "\">" + obj + "</option>";
        }
    });

    $("#" + optionName).html(optionHtml);
}