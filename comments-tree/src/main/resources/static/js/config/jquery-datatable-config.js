//jquery DataTable 通用配置
$.extend( true, $.fn.dataTable.defaults, {
    "pageLength":10,
    "paging": true,
    "lengthChange": false,
    "info": true,
    "autoWidth": true,
    "searching": false,
    "ordering": false,
    "oLanguage":{
        "sLengthMenu": "每页显示 _MENU_ 条记录",
        "sZeroRecords": "对不起，没有匹配的数据",
        "sInfo": "第 _START_ - _END_ 条 / 共 _TOTAL_ 条数据",
        "sInfoEmpty": "没有匹配的数据",
        "sInfoFiltered": "(数据表中共 _MAX_ 条记录)",
        "sProcessing": "正在加载中...",
        "sSearch": "全文搜索：",
        "sEmptyTable": "没有匹配的数据",
        "oPaginate": {
            "sFirst": "第一页",
            "sPrevious": " 上一页 ",
            "sNext": " 下一页 ",
            "sLast": " 最后一页 "
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    }
});

function currentPage(start, size){

    var current = (start / size) + 1;
    return current;
}