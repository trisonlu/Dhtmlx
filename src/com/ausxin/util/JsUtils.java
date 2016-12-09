package com.ausxin.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class JsUtils {

    /**
     * 用于辅助js的依赖库的导入
     *
     * @param request
     * @param dhtmlxList
     * @return
     */
    public static String include(HttpServletRequest request, String dhtmlxList) {
        boolean hasDhtmlxComp = false;

        String contextPath = request.getContextPath();
        String[] list = dhtmlxList.split(",");
        Set<String> compSet = new HashSet();
        for (String compName : list) {
            compName = compName.toLowerCase();
            compSet.add(compName);

            // 包含库

            if (compName.equals("layout")
                    || compName.equals("grid")
                    || compName.equals("toolbar")
                    || (compName.equals("tab") || compName.equals("tabbar"))
                    || compName.equals("tree")
                    || compName.equals("menu")) {
                hasDhtmlxComp = true;
            }
        }

        StringBuffer buf = new StringBuffer();
        buf.append("<script language='javascript'>").append("\n");
        // web上下文

        buf.append("var WebContext = '" + contextPath + "';// web上下文").append("\n");
        // 皮肤
        buf.append("var dhtmlxSkinName = 'dhx_skyblue';// 皮肤").append("\n");
        // layout 图片路径
        buf.append("var dhtmlxLayoutImagePath = '" + contextPath + "/dhtmlx/dhtmlxLayout/imgs/'; // layout 图片路径").append("\n");
        // tab 图片路径
        buf.append("var dhtmlxTabImagePath = '" + contextPath + "/dhtmlx/dhtmlxTabbar/imgs/'; // tab 图片路径").append("\n");
        // grid 图片路径
        buf.append("var dhtmlxGridImagePath = '" + contextPath + "/dhtmlx/dhtmlxGrid/imgs/'; // grid 图片路径").append("\n");
        // tree 图片路径
        buf.append("var dhtmlxTreeImagePath = '" + contextPath + "/dhtmlx/dhtmlxTree/imgs/'; // tree 图片路径").append("\n");
        // menu 图片路径
        buf.append("var dhtmlxMenuImagePath = '" + contextPath + "/dhtmlx/dhtmlxMenu/imgs/'; // tree 图片路径").append("\n");
        // dhtmlx 的图片路径， 如工具栏上的图标
        buf.append("var dhtmlxIconPath = '" + contextPath + "/dhtmlx/icon/';// dhtmlx 的图片路径， 如工具栏上的图标").append("\n");
        // 动态查询toolbar图标
        buf.append("</script>").append("\n");

        // jquery 组件
        if (compSet.contains("jquery")) {
            buf.append("<!-- jquery -->").append("\n");
            buf.append("<script src='" + contextPath + "/js/jquery/jquery-1.5.1.js'></script>").append("\n");
        }
        // json 组件
        if (compSet.contains("json")) {
            buf.append("<!-- json -->").append("\n");
            buf.append("<script src='" + contextPath + "/js/common/json.js'></script>").append("\n");
        }
        // dhtmlx 组件
        if (hasDhtmlxComp) {
            buf.append("<!-- dhtmlx共用组件 -->").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxLayout/dhtmlxcommon.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxLayout/dhtmlxcontainer.js'></script>").append("\n");
        }
        if (compSet.contains("layout")) {
            buf.append("<!-- layout -->").append("\n");
            buf.append("<link href='" + contextPath + "/dhtmlx/dhtmlxLayout/dhtmlxlayout.css' rel='stylesheet' type='text/css'/>").append("\n");
            buf.append("<link href='" + contextPath + "/dhtmlx/dhtmlxLayout/skins/dhtmlxlayout_dhx_skyblue.css' rel='stylesheet' type='text/css'/>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxLayout/dhtmlxlayout.js'></script>").append("\n");
        }
        if (compSet.contains("tab") || compSet.contains("tabbar")) {
            buf.append("<!-- tabbar -->").append("\n");
            buf.append("<link href='" + contextPath + "/dhtmlx/dhtmlxTabbar/dhtmlxtabbar.css' rel='stylesheet' type='text/css'/>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxTabbar/dhtmlxtabbar_start.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxTabbar/dhtmlxtabbar.js'></script>").append("\n");

        }
        if (compSet.contains("grid")) {
            buf.append("<!-- grid -->").append("\n");
            buf.append("<link href='" + contextPath + "/dhtmlx/dhtmlxGrid/dhtmlxgrid.css' rel='stylesheet' type='text/css'/>").append("\n");
            buf.append("<link href='" + contextPath + "/dhtmlx/dhtmlxGrid/skins/dhtmlxgrid_dhx_skyblue.css' rel='stylesheet' type='text/css'/>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxGrid/dhtmlxgrid.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxGrid/dhtmlxgridcell.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxGrid/ext/dhtmlxgrid_drag.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxGrid/dhtmlxtreegrid.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxGrid/dhtmlxgrid_pgn.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxGrid/excells/dhtmlxgrid_excell_link.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxGrid/ext/dhtmlxgrid_nxml.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxGrid/ext/dhtmlxgrid_filter.js'></script>").append("\n");
        }
        if (compSet.contains("toolbar")) {
            buf.append("<!-- toolbar -->").append("\n");
            buf.append("<link href='" + contextPath + "/dhtmlx/dhtmlxToolbar/skins/dhtmlxtoolbar_dhx_skyblue.css' rel='stylesheet' type='text/css'/>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxToolbar/dhtmlxtoolbar.js'></script>").append("\n");
        }
        if (compSet.contains("tree")) {
            buf.append("<!-- tree -->").append("\n");
            buf.append("<link href='" + contextPath + "/dhtmlx/dhtmlxTree/dhtmlxtree.css' rel='stylesheet' type='text/css'/>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxTree/dhtmlxtree.js'></script>").append("\n");
        }
        if (compSet.contains("menu")) {
            buf.append("<!-- menu -->").append("\n");
            buf.append("<link href='" + contextPath + "/dhtmlx/dhtmlxMenu/skins/dhtmlxmenu_dhx_skyblue.css' rel='stylesheet' type='text/css'/>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxMenu/dhtmlxmenu.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxMenu/ext/dhtmlxmenu_ext.js'></script>").append("\n");
        }
        if (compSet.contains("dataProcessor")) {
            buf.append("<!-- dataProcessor -->").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxDataProcessor/dhtmlxdataprocessor.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxDataProcessor/dhtmlxdataprocessor_debug.js'></script>").append("\n");
            buf.append("<script src='" + contextPath + "/dhtmlx/dhtmlxDataProcessor/dhtmlxdataprocessor_deprecated.js'></script>").append("\n");
        }


        return buf.toString();
    }


}
