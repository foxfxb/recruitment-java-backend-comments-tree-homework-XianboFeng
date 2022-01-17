
/**
 *  checkAuth初始化树状图
 *  @param elementId 节点id
 *  @param treeData 需要展示的数据
 */
function initTree(elementId, treeData) {
    $("#"+elementId).treeview({
        backColor: "#FFFFFF",
        color: "#144a74",
        showCheckbox:true,
        levels: 99,
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        // showTags: true,
        showBorder: false,
        data: treeData,
        onNodeChecked: function(event, node) {
            console.info(node);
            var nodeIds = [node.nodeId];
            $.each(node.nodes, function(index, data){
                nodeIds.push(data.nodeId);
            });
            console.info(nodeIds);
            $("#"+elementId).treeview('checkNode', [ nodeIds, { silent: true } ]);
        },
        onNodeUnchecked: function (event, node) {
            console.info(node);
            var nodeIds = [node.nodeId];
            $.each(node.nodes, function(index, data){
                nodeIds.push(data.nodeId);
            });
            console.info(nodeIds);
            $("#"+elementId).treeview('uncheckNode', [ nodeIds, { silent: true} ]);
        },
        onNodeSelected: function(event, node){
            console.info(node);
            var nodeIds = [node.nodeId];
            $.each(node.nodes, function(index, data){
                nodeIds.push(data.nodeId);
            });
            console.info(nodeIds);
            $("#"+elementId).treeview('checkNode', [ nodeIds, { silent: true} ]);
        },
        // onNodeUnselected: function(event, node){
        //     console.info(node);
        //     var nodeIds = [node.nodeId];
        //     $.each(node.nodes, function(index, data){
        //         nodeIds.push(data.nodeId);
        //     });
        //     console.info(nodeIds);
        //     $("#"+elementId).treeview('uncheckNode', [ nodeIds, { silent: true} ]);
        // },
    });

}

/**
 * @param elementId 节点id
 * 获取所有选中tree的tags，返回字符串 menu（菜单权限，例如 MENU_INTENDED） ，数组privileges（接口权限 例如INTENDED_ADD）
 */
function getTreeTags(elementId) {
    var menu = "", privileges = [];
    var tags = $("#"+elementId).treeview('getChecked',{ silent: true });
    $.each(tags, function (index, obj) {
        if(obj.tags[0].indexOf("MENU") !== -1){
            menu +=  obj.tags[0] + ",";
        }else if(obj.tags[0]) {
            privileges.push(obj.tags[0]);
        }
    })
    var data = {};
    data.menu = menu;
    data.privileges = privileges;
    return data;
}

/**
 *
 * 根据后台拉取的权限数据展示到树状选项中
 * @param elementId 节点id String
 * @param menu 已设置的菜单权限 String 例如"MENU_INTENDED,MENU_PERSONAL,"
 * @param privileges 已设置的按钮权限 Array[]
 */
function setCheckedAuth(elementId, menu, privileges) {
    var tags = $("#"+elementId).treeview('getUnchecked',{ silent: true });
    if(menu){
        var menuArray = menu.split(",");
        $.each(tags, function (index, object) {
            $.each(menuArray, function (indexOfMenuArray, objOfMenu) {
                if(objOfMenu == object.tags[0]){
                    $("#"+elementId).treeview('checkNode', [ object.nodeId, { silent: true } ]);
                }
            })
            $.each(privileges, function (indexOfPrivileges, obj) {
                if(obj == object.tags[0]) {
                    $("#"+elementId).treeview('checkNode', [ object.nodeId, { silent: true } ]);
                }
            })
        })
    }
}


/**
 * 将获取的数据权限的数组数据展示到树状图
 * @param element 节点id String
 * @param dataPrivileges 获取的数据权限，Array[]
 */
function setCheckedProccDataAuth(elementId, dataPrivileges) {
    var tags = $("#"+elementId).treeview('getUnchecked',{ silent: true });
    var setIndex = 0;
    $.each(tags, function (index, object) {
        $.each(dataPrivileges, function (indexOfPrivileges, obj) {
            if(obj == object.tags[1]) {
                $("#"+elementId).treeview('checkNode', [ object.nodeId, { silent: true } ]);
                $("#"+elementId).treeview('checkNode', [ object.parentId, { silent: true } ]);
                $("#"+elementId).treeview('expandNode', [ object.parentId, { silent: true } ]);
            }
            setIndex ++;
        })
    })
    console.info("SET" + setIndex);
}

/**
 * 获取设置的数据权限参数
 * @param elementId 节点Id
 * @return {{}} 返回参数,例如{dataPri: {ASSOCIATION_CLASS_DATA: ["ASSOCIATION_CLASS_DATA_PROVINCE", "ASSOCIATION_CLASS_DATA_CITY"]}}
 */
function getCheckedProccDataAuth(elementId) {
    var dataPri = {};
    var tags = $("#"+elementId).treeview('getChecked',{ silent: true });
    var getIndex = 0;
    $.each(tags, function (index, obj) {
        var key = "", privileges = [];
        if(obj.nodes) {
            $.each(obj.nodes, function (indexOfNodes, object) {
                if(object.state.checked){
                    privileges.push(object.tags[1]);
                    key = object.tags[0];
                    dataPri[key] = privileges;
                }
                getIndex ++;
            })
        }
    })
    console.info("GET" + getIndex);
    return dataPri;
}