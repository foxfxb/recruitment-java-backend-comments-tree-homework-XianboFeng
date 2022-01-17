var HEAD_MODEL = 1;
var TRACK_MODEL = 2;
var OPERATION_MODEL = 3;

function getCNValue(data, type){
    var text = data[type];
    if(!text){
        text = "";
    }
    return text;
}

var serveType = {
	"0": "全能",
	"1": "个人",
	"2": "企业",
	"3": "社群",
}
function dicServeTypeCN(type){
 	return getCNValue(serveType, type);
}

var teacherType = {
	"1" : "全职",
	"2" : "兼职",
}
function dicTeacherTypeCN(type){
    return getCNValue(teacherType, type);
}

var source ={
	"0": "推荐",
	"1": "渠道",
	"2": "官网",
	"3": "微信",
	"4": "开发",
	"5": "edu注册",
	"6": "转入"
}
function dicSourceCN(type){
    return getCNValue(source, type);
}

var teacherStatus = {
	"0": "申请",
	"1": "准备中",
	"2": "可授课",
	"3": "待激活",
	"4": "禁用"
}
function dicTeacherStatusCN(type){
    return getCNValue(teacherStatus, type);
}

//跟进类型，1.创建，2.编辑，3.文档，4.跟进，5.转入，6.开课，7.差评，8.好评，9.筹备,10.创建课程
var trackDicConfig = {
	1: "创建",
	2: "编辑",
	3: "文档",
	4: "跟进",
	5: "转入",
	6: "开课",
	7: "差评",
	8: "好评",
	9: "筹备",
	10: "创建课程",
	11: "删除课程",
	12: "扣减课时",
	13: "累加课时",
	14: "教练带教"
}
function dicTrackTypeCN(type){
    return getCNValue(trackDicConfig, type);
}

var courseStatusDicConfig = {
	1: "上架",
	2: "下架",
}
function dicCourseStatusCN(type){
    return getCNValue(courseStatusDicConfig, type);
}

var userStatus={
	"-1":"注销",
	"0":"禁用",
	"1":"激活",
}
function dicUserStatusCN(type){
    return getCNValue(userStatus, type);
}

var classStatus={
	"1": "筹备",
	"2": "授教",
	"3": "完成",
	"4": "搁置",
}
function dicClazzStatusCN(type){
    return getCNValue(classStatus, type);
}

var courseStage={
	"1": "一阶段",
	"2": "一阶末",
	"3": "二阶段",
	"4": "二阶末",
	"5": "三阶段",
	"6": "三阶末",
}
function dicCourseStageCN(type){
	return getCNValue(courseStage,type);
}

var intdMemStatus={
	"1": "跟进中",
	"2": "已付款",
	"3": "已转入",
	"4": "暂缓",
}
function dicIntdMemberStatusCN(type){
    return getCNValue(intdMemStatus, type);
}

var officialMemStatus={
	"1": "课程中",
	"2": "已结业",
	"3": "暂缓",
    "4": "退学",
}
function dicPersonalMemberStatusCN(type){
    return getCNValue(officialMemStatus, type);
}

var notice={
	"0": "未读",
	"1": "已读",
}
function dicNoticeTypeCN(type){
    return getCNValue(notice, type);
}

var entClazzStatus={
	"1": "筹备中",
	"2": "进行中",
	"3": "总结中",
	"4": "签字中",
	"5": "已完成",
}
function dicEntClazzStatusCN(type){
    return getCNValue(entClazzStatus, type);
}

var entTrainingType={
	"1": "拜访",
	"2": "问诊",
	"3": "咨询",
}
function dicEntTrainingTypeCN(type){
    return getCNValue(entTrainingType, type);
}

var entTrainingType={
	"1": "拜访",
	"2": "问诊",
	"3": "咨询",
}
function dicEntTrainingTypeCN(type){
    return getCNValue(entTrainingType, type);
}

var entEmployeeStatus={
	"1": "在职",
	"2": "离职",
}
function dicEmployeeStatusCN(type){
    return getCNValue(entEmployeeStatus, type);
}

var wmpCourseStatus={
	"1": "已上架",
	"2": "未上架",
	"3": "初审",
	"4": "待审核",
	"5": "未通过",
}
function dicWMPCourseStatus(type){
	return getCNValue(wmpCourseStatus, type);
}

// 活动状态
var newsStatus={
	"0": "隐藏",
	"1": "显示",
}
function dicNewsStatusCN(type){
	return getCNValue(newsStatus, type);
}

// 活动类型
var newsType={
	"1": "图文",
	"2": "网页",
}
function dicNewsTypeCN(type){
	return getCNValue(newsType, type);
}

var wmpDiscussType={
	"1": "图文",
	"2": "音频",
	"3": "视频",
	"4": "专栏",
}
function getDiscussValue(type){
	return getCNValue(wmpDiscussType, type);
}

var wmpDiscussStatus={
	"0": "隐藏",
	"1": "显示",
}
function getDiscussStatus(type){
	return getCNValue(wmpDiscussStatus, type);
}

var feedBackSource={
	"1": "微信小程序",
	"2": "H5",
	"3": "官网",
}
// 判断反馈来源类型
function getFeedBackSource(type){
	return getCNValue(feedBackSource, type);
}

var feedBackStatus={
	"0": "未处理",
	"1": "已处理",
}
// 判断反馈处理情况
function getFeedBackStatus(type){
	return getCNValue(feedBackStatus, type);
}

var bannerType={
	"1": "纯图片",
	"2": "链接",
	"3": "公众号",
	"4": "某模块",
}
function getBannerType(type){
	return getCNValue(bannerType, type);
}

var bannerStatus={
	"0": "下架",
	"1": "发布",
}
function getBannerStatus(type){
	return getCNValue(bannerStatus, type);
}

var openidStatus={
	"0": "未激活",
	"1": "激活",
}
function getOpenIdStatus(type){
	return getCNValue(openidStatus, type);
}

var wmpCourseType={
	"1": "图文",
	"2": "音频",
	"3": "视频",
}
function dicWMPCourseType(type){
	return getCNValue(wmpCourseType, type);
}

var courseGroup={
	"1": "财商学院",
	"2": "个人学院",
	"3": "家庭学院",
	"4": "咨询师学院",
	"5": "企业学院",
	"6": "创业学院",
}
function dicWMPCourseGroup(type){
	return getCNValue(courseGroup, type);
}

var industryCategory={
	"1": "房地产",
	"2": "消费品",
	"3": "金融",
	"4": "服务业",
}
function dicWMPIndustryCategory(type){
	return getCNValue(industryCategory, type);
}

var gradeAssignment={
	"1": "高级",
	"2": "中级",
	"3": "初级",
	"4": "入门",
}
function dicWMPGradeAssignment(type){
	return getCNValue(gradeAssignment, type);
}

var activityModule={
	"0": "首页轮播图",
	"1": "推荐海报图",
}
function dicActivityModule(type){
	return getCNValue(activityModule, type);
}
var isRecommend={
	"1": "不加入",
	"2": "加入",
}
function dicIsRecommend(type){
	return getCNValue(isRecommend, type);
}

var projectStatus={
	"1": "进行中",
	"2": "已结束",
	"3": "暂缓",
	"4": "中断",
}
function dicProjectStatus(type){
	return getCNValue(projectStatus, type);
}

var projectNode={
	"1": "开始",
	"2": "问诊",
	"3": "方案制定",
	"4": "落地实施",
	"5": "验收",
	"6": "结束",
}
function dicProjectNode(type){
	return getCNValue(projectNode, type);
}

var EDUAuthorStatus={
	"1": "正常",
	"2": "禁用"
}
function dicEDUAuthorStatus(type){
	return getCNValue(EDUAuthorStatus, type);
}

var projectMemberType={
	"1": "个人",
	"2": "企业",
}
function dicProjectMemberType(type){
	return getCNValue(projectMemberType, type);
}

var memberType={
	"1": "个人",
	"2": "企业",
	"3": "社群",
}
function dicMemberType(type){
	var text = getCNValue(memberType, type);
	if(isNullValue(text) && !isNullValue(type)){
		var arr = type.split(',');
		for(var index = 0; index < arr.length; index ++){
            var t = getCNValue(memberType, arr[index]);
            if(t > ''){
            	text += t + ",";
			}
		}
		if(!isNullValue(text)){
			text = text.substring(0, text.length - 1);
		}
	}
	return text;
}

var projectServiceType={
	"1": "拜访",
	"2": "问诊",
	"3": "咨询",
	"4": "开课"
}
function dicProjectServiceType(type){
	return getCNValue(projectServiceType, type);
}

var teachersType={
	"1": "咨询师",
	"2": "导师"
}
function dicTeacherType(type){
	return getCNValue(teachersType, type);
}

var courseType={
	"1": "个人",
	"2": "企业",
	"3": "社群"
}
function dicCourseType(type){
	return getCNValue(courseType, type);
}

var intendedCourseStatus = {
	"1" : "已推荐",
	"2" : "已购买"
}
function dicIntendedCourseStatus(type) {
	return getCNValue(intendedCourseStatus,type);
}

var trackCategory = {
	1: "意向人员",
	2: "个人会员",
	3: "企业会员",
	4: "社群会员",
	5: "导师",
	6: "咨询师",
	7: "导师开课",
	8: "教练带教",
	9: "企业开课",
	10: "个人课程",
	11: "社群开课",
	12: "意向企业",
	13: "项目信息",
	14: "普通客户"
}
function dicTrackCategoryCN(type){
	return getCNValue(trackCategory, type);
}

//行业标签
var memberTradeLabel = {
	"1": "房地产",
	"2": "消费品",
	"3": "金融",
	"4": "服务业",
	"5": "其它"
}
function dicTradeLabelCN(type){
	return getCNValue(memberTradeLabel, type);
}

//统计类型
var statisticType = {
	"1": "上架统计",
	"2": "阅读量统计",
	"3": "销售量统计",
	"4": "动态阅读量统计"
}
function dicStatisticTypeCN(type){
	return getCNValue(statisticType, type);
}

var errCode = {
	'Select files' : '选择文件',
	'Add files to the upload queue and click the start button.' : '添加文件到上传队列，然后点击启动按钮。',
	'Filename' : '文件名',
	'Status' : '状态',
	'Size' : '大小',
	'Add Files' : '添加文件',
	'Stop Upload' : '停止上传',
	'Start Upload' : '开始上传',
	'Add files' : '添加文件',
	'Add files.' : '添加文件。',
	'Stop current upload' : '停止当前上传',
	'Start uploading queue' : '开始上传队列',
	'Stop upload' : '停止上传',
	'Start upload' : '开始上传',
	'Uploaded %d/%d files': '已经上传 %d/%d 个文件',
	'N/A' : 'N/A',
	'Drag files here.' : '拖动文件到这里',
	'File extension error.': '文件扩展名错误',
	'File size error.': '文件大小错误',
	'File count error.': '文件数量错误',
	'Init error.': '初始化错误',
	'HTTP Error.': 'HTTP 错误',
	'Security error.': '安全错误',
	'Generic error.': '常见错误',
	'IO error.': 'IO错误',
	'File: %s': '文件百分比 : %s',
	'Close': '关闭',
	'%d files queued': '%d 个队列中',
	'Using runtime: ': '使用运行时: ',
	'File: %f, size: %s, max file size: %m': '文件名: %f, 大小: %s, 文件最大值: %m',
	'Upload element accepts only %d file(s) at a time. Extra files were stripped.': '上传文件只接受%d，其他的文件被忽略',
	'Upload URL might be wrong or doesn\'t exist': '上传文件有错误或者文件不存在',
	'Error: File too large: ': '错误：文件太大: ',
	'Error: Invalid file extension: ': '错误：禁止的文件扩展名: '
}

function dicErrCodeCN(type){
	return getCNValue(errCode, type);
}

// 公司编号
var companyNumber = {
	"成都小白创客教育咨询有限公司": "CO10001"
}
function dicCompanyNumberCN(type){
	return getCNValue(companyNumber, type);
}

//普通会员级别
var regularMemberLevel = {
	"1": "大型",
	"2": "中型",
	"3": "小型"
}
function dicRegularMemberLevelCN(type){
	return getCNValue(regularMemberLevel, type);
}

//普通会员状态
var regularStatus = {
	"1": "初登",
	"2": "意向",
	"3": "报价",
	"4": "成交",
	"5": "搁置",
	"6": "已转入"
}
function dicRegularStatusCN(type){
	return getCNValue(regularStatus, type);
}