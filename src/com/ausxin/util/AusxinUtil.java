package com.ausxin.util;

import com.ausxin.domain.PageInfo;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.ausxin.domain.TreeNode;
/**
 * Created by lushuqin on 2016/8/8.
 */
public class AusxinUtil {

    public static boolean isEmpty(String str) {
        return (null == str || "".equals(str.trim())) ? true : false;
    }

    public static String convertString(Object obj)
    {
        return obj==null?"":obj.toString();
    }

    public static String toDisplayStr(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.trim().equals("")) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=fmt.parse(dateStr);
        return fmt.format(date);
    }

    /**
     *
     * @param dataList
     *            数据结构为List<List<String>>
     *
     * @param isNeedCheckBox
     *            是否需要选择框
     *
     * @return
     */
    public static String parsListStringListToXML(PageInfo page,
                                                 List<List<String>> dataList, boolean isNeedCheckBox, int startIndex, boolean treeFlag, boolean isKeyRowId, boolean isChild, String parentId) {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("rows");
        if (isChild) {
            if(!isEmpty(parentId))
                root.addAttribute("parent", parentId);
        }
        if (page!=null) {
            root.addAttribute("currentPage", "" + page.getCurrentPage());
            root.addAttribute("rowCount", "" + page.getRowCount());
            root.addAttribute("pageSize", "" + page.getPageSize());
        }

        try {
            for (int i = 0; i < dataList.size(); i++) {
                Element row = root.addElement("row");
                List<String> list = dataList.get(i);
                if (isKeyRowId) {
                    row.addAttribute("id", list.get(0));
                }
                if (treeFlag) {
                    row.addAttribute("xmlkids", "1");
                }
                if (list != null && !list.isEmpty()) {
                    // 是否需要选择框

                    if (isNeedCheckBox) {
                        row.addElement("cell").addText("0");
                    }

                    for (int j = startIndex; j < list.size(); j++) {
                        row.addElement("cell").addText(list.get(j));
                    }
                }
            }
            doc.setRootElement(root);
        } catch (Exception e) {
        }
        return doc.asXML();
    }

    public static void outputXML(HttpServletResponse response, String xml, String encoding) {
        PrintWriter out = null;
        try {
            response.setContentType("text/xml; charset=" + encoding);
            out = response.getWriter();
            out.write(xml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            out.flush();
            out.close();
        }
    }

    public static void outputTreeXml(HttpServletRequest request, HttpServletResponse response, List<TreeNode> treeNodeList) throws IOException {
        response.setContentType("text/xml;charset=utf-8");
        PrintWriter pw = response.getWriter();
        try {
            String xml = createTreeXml(request.getParameter("id") == null ? "0" : request.getParameter("id"), treeNodeList);
            pw.write(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回树节点数据Document
     *
     * @param treeId
     * @param list
     * @return
     */
    public static String createTreeXml(String treeId, List<TreeNode> list) {

        if (list == null || list.size() == 0) {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><tree id=\"" + treeId + "\"></tree>";
        } else {
            Document document = DocumentHelper.createDocument();
            // document.setXMLEncoding("utf-8");
            Element root = document.addElement("tree");
            root.addAttribute("id", treeId);

            for (int i = 0; i < list.size(); i++) {
                TreeNode branch = list.get(i);
                String pid = branch.getParentId();
                if (pid == null || pid.equals(""))
                    branch.setParentId(treeId);
                document = createTreeElement(treeId, document, branch);
            }
            return document.asXML();
        }

    }

    private static Document createTreeElement(String treeId, Document document, TreeNode branch) {
        String parentId = branch.getParentId();
        // 配置父节点


        Element parentElement = null;
        Node parentNode = document.selectSingleNode("//item[@id='" + parentId + "']");
        if (!treeId.equals(parentId)) {
            if (parentNode == null) {
                parentElement = document.getRootElement().addElement("item");
                parentElement.addAttribute("id", parentId);
            } else {
                parentElement = (Element) parentNode;
            }
        }
        String id = branch.getNodeId();

        // 配置本节点


        Element element = null;
        Node node = document.selectSingleNode("//item[@id='" + id + "']");
        if (node == null) {
            if (parentElement == null) {
                element = document.getRootElement().addElement("item");
            } else {
                element = parentElement.addElement("item");
            }
        } else {
            element = (Element) node;
            Element oldParent = element.getParent();
            oldParent.remove(element);
            parentElement.add(element);
        }
        setElementAttribute(element, branch);
        return document;
    }

    /**
     * 设置节点元素属性

     *
     *
     *
     *
     *
     * @param element
     * @param node
     */
    private static void setElementAttribute(Element element, TreeNode node) {
        element.addAttribute("id", node.getNodeId());
        if (!isEmpty(node.getText())) {
            element.addAttribute("text", node.getText().trim());
        }
        if (!isEmpty(node.getText())) {
            element.addAttribute("parentId", node.getParentId());
        }
        if (node.isOpen()) {
            element.addAttribute("open", "1");
        }
        if(!node.isLeaf()){
            element.addAttribute("child", "1");
        }
        if(!isEmpty(node.getChecked())){
            element.addAttribute("checked",node.getChecked().trim());
        }
        if (!isEmpty(node.getIm0())) {
            element.addAttribute("im0", node.getIm0().trim());
        }
        if (!isEmpty(node.getIm1())) {
            element.addAttribute("im1", node.getIm1().trim());
        }
        if (!isEmpty(node.getIm2())) {
            element.addAttribute("im2", node.getIm2().trim());
        }

    }

}
