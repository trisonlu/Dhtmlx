package com.ausxin.domain;

/**
 * Created by lushuqin on 2016/8/8.
 */
public class TreeNode {

    private String treeId;
    private String nodeId;
    private String text;
    private String parentId;
    private String im0;//叶子图标
    private String im1;//父节点打开图标
    private String im2;//父节点闭合图标
    private boolean open=false;
    private boolean leaf=false;
    private String checked;

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIm0() {
        return im0;
    }

    public void setIm0(String im0) {
        this.im0 = im0;
    }

    public String getIm1() {
        return im1;
    }

    public void setIm1(String im1) {
        this.im1 = im1;
    }

    public String getIm2() {
        return im2;
    }

    public void setIm2(String im2) {
        this.im2 = im2;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
