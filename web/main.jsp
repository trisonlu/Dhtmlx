<%--
  Created by IntelliJ IDEA.
  User: lushuqin
  Date: 2016/8/8
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%=com.ausxin.util.JsUtils.include(request,"jquery,json,layout,tree,tabbar,menu")%>
<html>
<head>
    <title>Dhtmlx范例</title>
</head>
<body onload="init()">



<script type="text/javascript">
    
    var layout;
    var tree;
    var treeMenu;
    var tabbar;
    var tabCount = 0;
    var tabbarMenu;
    var tabMax=2;


    function init(){

        initLayout();

        initTreeMenu();
        initTree();
        tree.enableContextMenu(treeMenu);

        initTabbarMenu();
        initTabbar();
        tabbar._tabZone.id="theader";
        tabbarMenu.addContextZone("theader");

        tree.attachEvent('onClick',function (id) {
            if(id.substring(0,2)=="se"){
                var unitId=id.split('_')[1];
                var unitName=tree.getSelectedItemText();
                //createTab(unitId+"_"+Math.random(),unitName,WebContext+"/customer.spr?method=toCustomer&collectionUnitId="+unitId,true);
                createTab(unitId,unitName,WebContext+"/customer.spr?method=toCustomer&collectionUnitId="+unitId,true);
            }
        });
    }

    function initLayout() {
        layout = new dhtmlXLayoutObject(document.body, "2U", dhtmlxSkinName);
        layout.setImagePath(dhtmlxLayoutImagePath);
        //layout.cells('a').hideHeader();
        layout.cells('a').setText('催收机构');
        layout.cells('a').setWidth(200);
    }

    function initTreeMenu() {
        treeMenu = new dhtmlXMenuObject();
        treeMenu.renderAsContextMenu();
        treeMenu.addNewChild(null, 1, "nodeId", "节点id");
        treeMenu.addNewChild(null, 2, "nodeText", "节点文本");
        treeMenu.attachEvent("onClick", onTreeRightMenuClick);
    }

    function initTree() {
        tree = layout.cells('a').attachTree();
        tree.setSkin(dhtmlxSkinName);
        tree.setImagePath(dhtmlxTreeImagePath + "csh_bluebooks/");
        tree.setXMLAutoLoading(WebContext + "/customer.spr?method=showQueryTree");
        tree.loadXML(WebContext + "/customer.spr?method=showQueryTree");
        tree.enableCheckBoxes(true, false);
        tree.enableThreeStateCheckboxes(true);
        tree.attachEvent("onRightClick", function(id, object){
            tree.selectItem(id);
        });
    }

    var onTreeRightMenuClick = function(id) {
        switch (id) {
            case "nodeId":
                alert(tree.getSelectedItemId());
                break;
            case "nodeText":
                alert(tree.getSelectedItemText());
                break;
            default :
                break;
        }
    }

    function createTab(id, text, url, isRefresh) {
        if(!tabbar.cells(id) && tabCount >= tabMax){
            alert("您打开的页面过多,请关闭其他页面再打开");
            return;
        }
        if (!tabbar.cells(id)) {
            tabbar.addTab(id, text, "100%");
            tabCount++;
            tabbar.cells(id).attachURL(url);
        }
        //添加第四个参数是否需要重新刷新页面
        if(isRefresh){
            tabbar.cells(id).attachURL(url);
        }
        tabbar.setTabActive(id);
    }


    function setCurrentTab(id){
        tabbar.setTabActive(id);
    }

    function closeTab(id, mode) {
        if (tabbar.cells(id)) {
            tabbar.removeTab(id,mode);
            tabCount--;
        }
    }

    function initTabbarMenu(){
        tabbarMenu = new dhtmlXMenuObject();
        tabbarMenu.setIconsPath(dhtmlxMenuImagePath);
        tabbarMenu.renderAsContextMenu();
        tabbarMenu.attachEvent("onClick", closeAll);
        tabbarMenu.addNewChild(null, 0, "closeAll", "关闭全部");
    }

    function closeAll(){
        tabbar.clearAll();
        tabCount = 0;
    }

    function initTabbar() {
        tabbar = layout.cells('b').attachTabbar();
        tabbar.setSkin(dhtmlxSkinName);
        tabbar.setImagePath(dhtmlxTabImagePath);
        tabbar.setHrefMode("iframes-on-demand");
        tabbar.enableTabCloseButton(true);
        tabbar.attachEvent("onTabClose", function (id) {
            tabCount--;
            return true;
        });
    }

</script>

</body>
</html>
