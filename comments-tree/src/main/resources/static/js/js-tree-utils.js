
/**
 *  checkAuth初始化树状图
 *  @param elementId 节点id
 *  @param treeData 需要展示的数据
 */
function initTree(elementId, treeData) {
    var tree = $("#" + elementId).jstree({
        animation: 0,
        tie_selection: true,
        plugins : ["checkbox","types","wholerow"],
        core : {
            data: treeData
        },
        types : {
            "default" : {
                "icon" : "fa fa-folder-open-o"
            },
            // "menu": {
            //     "icon": "fa fa-bars"
            // },
            "add" : {
                "icon" : "fa fa-plus"
            },
            "edit" : {
                "icon" : "fa fa-pencil"
            },
            "del" : {
                "icon" : "fa fa-trash"
            },
            "track" : {
                "icon" : "fa fa-file-text-o"
            },
            "convert" : {
                "icon" : "fa fa-mail-forward"
            },
            "sign" : {
                "icon" : "fa fa-sign-in"
            },
            "role" : {
                "icon" : "fa fa-hand-o-right"
            },
            "province":{
                "icon" : "fa fa-globe"
            },
            "city":{
                "icon" : "fa fa-flag"
            },
            "comp":{
                "icon" : "fa fa-university"
            },
            "dept":{
                "icon" : "fa fa-users"
            },
            "user":{
                "icon" : "fa fa-id-badge"
            },
            "query":{
                "icon" : "fa fa-search"
            }
        }
    });
    return tree;
}

function getChecked(tree){
    var checked = tree.jstree().get_checked(true);
    var root = [];
    var menu = [];
    var children = [];
    $.each(checked, function(index, obj){
        var value = obj.data;

        // if(tree.jstree().get_parent(obj) == '#'){
        //     if(root.indexOf(value) < 0){
        //         root.push(value);
        //     }
        // } else{
        //     if(children.indexOf(value) < 0){
        //         children.push(value);
        //     }
        //     var parent = obj.parent;
        //     var parentValue = tree.jstree().get_node(parent).data;
        //     if(root.indexOf(parentValue) < 0) {
        //         root.push(parentValue);
        //     }
        // }

        var type = obj.type;
        if(type == 'query'){
            menu.push(value);
            return true;
        }else if(type == 'menu'){
            return true;
        }else{
            children.push(value);
        }

    });

    var data = {};
    data.root = menu;
    // data.menu = menu;
    data.children = children;
    return data;
}

function getDataPriChecked(tree){
    var checked = tree.jstree().get_checked(true);
    var data = {};
    $.each(checked, function(index, obj){
        var value = obj.data;

        if(tree.jstree().is_parent(obj)){
        }else{
            var parent = obj.parent;
            var parentValue = tree.jstree().get_node(parent).data;

            if(!data[parentValue]){
                data[parentValue] = new Array();
            }
            data[parentValue].push(value);
        }
    });

    return data;
}

/**
 *
 * 根据后台拉取的权限数据展示到树状选项中
 * @param elementId 节点id String
 * @param menu 已设置的菜单权限 String 例如"MENU_INTENDED,MENU_PERSONAL,"
 * @param privileges 已设置的按钮权限 Array[]
 */
function setChecked(tree, privileges) {
    // $.each(privileges, function(index, obj){
    //     tree.jstree().check_node(getValue(obj));
    // });
    tree.jstree().check_node(privileges);
}

function setInitChecked(tree, childrenArray, rootStr, menuPrivilegeJson){

    if(isNullValue(childrenArray) && isNullValue(rootStr)){
    }else {
        $.each(menuPrivilegeJson, function (index, obj) {
            var root = obj;
            var children = root.children;
            root.state = {opened: false};
            $.each(children, function (mindex, level2Children) {
                if (level2Children) {
                    $.each(level2Children.children, function (cindex, child) {
                        if(childrenArray){
                            if (childrenArray.indexOf(child.id) > -1) {
                                child.state = {selected: true, opened: false};
                                rootStr.replace(root.id, '');
                            }
                        }
                        if(rootStr){
                            if (rootStr.indexOf(child.id) > -1) {
                                child.state = {selected: true, opened: false};
                                rootStr.replace(root.id, '');
                            }
                        }
                    })
                }
            })
        });
    }
    var jsTree = initTree(tree, menuPrivilegeJson);
    return jsTree;
}

// $(function () {
//     $("#tree")
//         .on("changed.jstree", function (e, data) {
//             console.log(data.changed.selected); // newly selected
//             console.log(data.changed.deselected); // newly deselected
//         })
//         .jstree({
//             "plugins" : [ "changed" ]
//         });
// });