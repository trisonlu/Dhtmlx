<%@page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<%=com.ausxin.util.JsUtils.include(request,"jquery,json,layout,grid,toolbar,menu,dataProcessor")%>
<link rel="STYLESHEET" type="text/css" href="<%=request.getContextPath()%>/component/common/queryCondition.css">

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>客户信息</title>
    
</head>
<body onload="init()" style="overflow: auto;">
<div id="dataProcessor"></div>
<div id="conditionDiv" >
    <div  style="width: 100%; height: 95%; background:#E4EFFF;overflow:auto;">
        <table class="table_data" style="width:100%;" border="0" cellspacing="1" cellpadding="2" align="center" >
            <tr>
                <td class="td_search_txt" >客户姓名： </td>
                <td class="td"><input class="td_input"  type="text" id="customerName"
                                      value=""  /></td>
                <td class="td_search_txt" >客户号： </td>
                <td class="td"><input class="td_input"  type="text" id="customerCode"
                                      value=""  /></td>
            </tr>
            <tr>
                <td class="td_search_txt" >客群名称： </td>
                <td class="td"><input class="td_input"  type="text" id="segmentName"
                                      value=""  /></td>
            </tr>

        </table>
    </div>
</div>
</body>
<script type="text/javascript">
    var toolbar;
    var statusBar;
    var gridMenu;
    var mygrid;
    var layout;
    var dataProcessor;
    var collectionUnitId=GetQueryString('collectionUnitId');
    var $D = function(id) {
        return document.getElementById(id);
    }

    function initGridMenu() {
        gridMenu = new dhtmlXMenuObject();
        gridMenu.renderAsContextMenu();
        gridMenu.addNewChild(null, 1, "rowId", "行ID");
        gridMenu.addNewChild(null, 2, "allowMultiSelect", "允许全选");
        gridMenu.addNewChild(null, 3, "disAllowMultiSelect", "不允许全选");
        gridMenu.attachEvent("onClick", onGridRightMenuClick);
    }
    var onGridRightMenuClick = function(id) {
        switch (id) {
            case "rowId":
                var id=mygrid.getSelectedRowId();
                if(id==null){
                    alert("请先选择一条记录");
                }else{
                    alert(id);
                }
                break;
            case "allowMultiSelect":
                mygrid.enableMultiselect(true);
                alert("操作成功");
                break;
            case "disAllowMultiSelect":
                mygrid.enableMultiselect(false);
                alert("操作成功");
                break;
            default :
                break;
        }
    }

    function init() {

        initLayout();
        initToolbar();
        initGridMenu();
        initGrid();
        mygrid.enableContextMenu(gridMenu);
        loadData();

    }

    function initLayout() {
        layout = new dhtmlXLayoutObject(document.body, "2E", dhtmlxSkinName);
        layout.setImagePath(dhtmlxLayoutImagePath);
        layout.cells('a').setText("查询条件");
        layout.cells('a').attachObject('conditionDiv');
        layout.cells('a').setHeight(90);
        layout.cells('b').hideHeader();
    }
    function initToolbar() {
        toolbar = layout.cells('b').attachToolbar();
        toolbar.setIconsPath(dhtmlxIconPath);
        toolbar.addButton('btnQuery', 1, '查询', "search.png", "search.png");
        toolbar.attachEvent("onClick", onToolbarButtonClick);
    }
    function initGrid() {
        mygrid = layout.cells('b').attachGrid();
        mygrid.setSkin(dhtmlxSkinName);
        mygrid.setImagePath(dhtmlxGridImagePath);
        mygrid.setHeader("#master_checkbox,客户号,姓名,注册号,贷款状况,入催日期,逾期状况,总逾期金额,总到期应还金额,总到期应收金额,当前还款金额汇总,客群,逾期原因A,逾期原因B,结果代码,是否当天计划催收,委外日期,委外收回日期,委外手数,委外批次号,前手委外单位,是否指派", null);
        mygrid.setInitWidths("50,100,80,100,80,150,80,100,100,100,110,100,100,100,100,110,100,100,100,100,100,100");
        mygrid.setColAlign("center,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left");
        mygrid.setColTypes("ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
        mygrid.enableStableSorting(true);
        mygrid.setColSorting("str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str");
        mygrid.enableMultiselect(true);
        statusBar = layout.cells('b').attachStatusBar();
        statusBar.setText("<div id='pageDiv'></div>");
        mygrid.enablePaging(true, 40, 0, "pageDiv");
        mygrid.setPagingSkin("toolbar");
        mygrid.init();
        mygrid.attachEvent("onXLS", function (grid_obj) {
            layout.cells('b').progressOn();
        });
        mygrid.attachEvent("onXLE", function () {
            layout.cells('b').progressOff();
        });
        mygrid.attachEvent("onRowSelect", function(id,ind){
            var obj = mygrid.cells(id,0);
            if(obj.isChecked()){
                obj.setChecked(false);
            }else{
                obj.setChecked(true);
            }
        });
        mygrid.attachEvent("onCheck", function(rId,cInd,state){
            if(state){
                mygrid.selectRowById(rId,true);
            }else{
                mygrid.removeSelectionByRowId(rId);
            }
        });


    }


    function onToolbarButtonClick(id) {
        switch (id) {
            case "btnQuery":
                loadData();
                break;
            default :
                break;
        }
    }

    function loadData() {
        if(mygrid.getRowsNum()>0)
            mygrid.clearAll();
        var autoUrl = WebContext+"/customer.spr?method=loadCustomer&collectionUnitId="+collectionUnitId+"&customerName="+encodeURIComponent(encodeURI($D("customerName").value))
                +"&customerCode="+$D("customerCode").value+"&segmentName="+encodeURIComponent(encodeURI($D("segmentName").value))+"&a="+new Date;
        var url=autoUrl+"&curr=1&len=50";
        mygrid.setXMLAutoLoading(autoUrl+"&");
        mygrid.loadXML(url,function(){
        });

    }

    function GetQueryString(name)

    {

        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");

        var r = window.location.search.substr(1).match(reg);

        if(r!=null){
            return  r[2];
        }

        return null;

    }

</script>
</html>

