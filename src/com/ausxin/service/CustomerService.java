package com.ausxin.service;


import com.ausxin.dao.CustomerDao;
import com.ausxin.domain.TreeNode;
import com.ausxin.util.AusxinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.ausxin.domain.PageInfo;

@Service
public class CustomerService{
	
	@Autowired
	private CustomerDao customerDao;
	

    public List<TreeNode> setCustomerTreeRootData(){
        List<TreeNode> rootList=new ArrayList<TreeNode>();
        TreeNode rootNode=new TreeNode();
        rootNode.setNodeId("root");
        rootNode.setTreeId("root");
        rootNode.setText("催收作业");
        rootList.add(rootNode);
        return rootList;
    }


	public List<TreeNode> setRouteTreeFirstData(String rootId){
		List<TreeNode> firstList=new ArrayList<TreeNode>();
		TreeNode firstNode=new TreeNode();
		firstNode.setParentId(rootId);
		firstNode.setNodeId("fi_unit");
		firstNode.setText("催收机构");
		firstNode.setChecked("1");
		firstList.add(firstNode);
		return firstList;
	}

	public List<TreeNode> setRouteTreeSecondData(String firstId){
		List<TreeNode> secondList=new ArrayList<TreeNode>();
		List<Map<String,Object>> list=customerDao.getAllCollectionUnit();
		for (Map<String,Object> unit:list){
			TreeNode secondNode=new TreeNode();
			secondNode.setParentId(firstId);
			secondNode.setNodeId("se_"+AusxinUtil.convertString(unit.get("id")));
			secondNode.setText(AusxinUtil.convertString(unit.get("unit_name")));
			secondNode.setLeaf(true);
			secondList.add(secondNode);
		}
		return secondList;
	}

	public List<List<String>> getCustomer(String collectionUnitId,String customerName,String customerCode,String segmentName,PageInfo page) throws  ParseException, UnsupportedEncodingException{
		List<List<String>> list=new ArrayList<List<String>>();
		List<Map<String, Object>> listResult=customerDao.getCustomer(collectionUnitId,customerCode,customerName,segmentName,page);

		for (int i = 0; i < listResult.size(); i++) {
			List<String> al = new ArrayList<String>();
			al.add(AusxinUtil
					.convertString(listResult.get(i).get("customer_code")));
			al.add(AusxinUtil
					.convertString(listResult.get(i).get("name")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("login_name")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("loan_status")));
			al.add(AusxinUtil.toDisplayStr(AusxinUtil.convertString(listResult.get(i).get("collection_date"))));
			al.add(AusxinUtil.convertString(listResult.get(i).get("overdue_status")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("overdue_balance")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("bill_not_pay")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("due_to_pay")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("payback_amt_today")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("segment_name")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("last_overdue_resaon_a")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("last_overdue_resaon_b")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("last_operation_result")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("isWorkingToday")));
            al.add(AusxinUtil.toDisplayStr(AusxinUtil.convertString(listResult.get(i).get("out_assign_date"))));
            al.add(AusxinUtil.toDisplayStr(AusxinUtil.convertString(listResult.get(i).get("out_assign_end_date"))));
			al.add(AusxinUtil.convertString(listResult.get(i).get("out_assign_num")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("out_assign_code")));
			al.add(AusxinUtil.convertString(listResult.get(i).get("last_unit_name")));
            al.add(AusxinUtil.convertString(listResult.get(i).get("isAssign")));
			list.add(al);
		}
		return list;
	}

}
