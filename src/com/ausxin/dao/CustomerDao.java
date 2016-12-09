package com.ausxin.dao;

import com.ausxin.domain.PageInfo;
import com.ausxin.util.AusxinUtil;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerDao extends PageDao {

	public List<Map<String,Object>> getAllCollectionUnit(){
		String sql="select id,unit_name from t_collection_unit ";
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=this.getJdbcTemplate().queryForList(sql);
		}catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}


	public List<Map<String, Object>> getCustomer(String collectionUnitId,String customerName,String customerCode,String segmentName,PageInfo page) throws  UnsupportedEncodingException{
		List<Object> objs=new ArrayList<Object>();
		StringBuilder extSql=new StringBuilder();
		extSql.append(",ciext.login_name ");
        extSql.append(" , concat(ciext.account_cnt,'/',ciext.trans_cnt,'/',ciext.late_day_trans_cnt,'/',ciext.overdue_trans_cnt) loan_status ");
        extSql.append(" , concat(dlsc.content_lang1,'/',ciext.delay_days) overdue_status ");
		extSql.append(",ciext.bill_not_pay,ciext.due_to_pay ,lorasc.content_lang1 last_overdue_resaon_a,lorbsc.content_lang1 last_overdue_resaon_b ");
		extSql.append(",ciext.payback_amt_today,orsc.content_lang1 last_operation_result ");

		StringBuilder sql = new StringBuilder("select ci.id, ci.customer_code, ci.collection_date, "
				+ " max(oi.overdue_period) as max_overdue_period, "
				+ " ciext.overdue_balance, si.segment_name, ci.overdue_count, ci.segment_id, ci.age, ci.name, "
				+ " ci.out_assign_date, ci.out_assign_end_date, "
				+ " ci.out_assign_num, ci.out_assign_code "
				+ " ,case when tai_assign.assigncnt >0 then 'Y' else 'N' end isAssign "
				+ ", wt.isWorkingToday , last_unit.unit_name last_unit_name "
				+ extSql.toString()
				+ " from t_customer ci "
				+ " left join (select ov.customer_id customer_id, ov.overdue_principal overdue_total_amount, ov.overdue_period overdue_period "
				+ " from t_overdue_info ov where ov.overdue_status = 'Y' ) oi"
				+ " on ci.id = oi.customer_id "
				+ " left join "
				+ " (  select count(*) assigncnt,tai.customer_id "
				+ " from t_customer_attach_info tai   where tai.attach_type =(select id from t_sys_code where code_category='CTAT' and code='01') "   // -- 指派
				+ "  group by tai.customer_id) tai_assign   on tai_assign.customer_id = ci.id "
				+ " left join t_segment_info si on ci.segment_id = si.id "
				//查询前手委外公司名称
				+ " left join t_customer_info_ext ciext on ci.id = ciext.cust_id "
				+ " left join (select id,content_lang1 from t_sys_code where code_category='ACTA') dlsc on ciext.delay_level=dlsc.id "
				+ " left join (select id,content_lang1 from t_sys_code where code_category='OVRS') lorasc on ciext.last_overdue_reason_a=lorasc.id "
				+ " left join (select id,content_lang1 from t_sys_code where code_category='ORMS') lorbsc on ciext.last_overdue_reason_b=lorbsc.id "
				+ " left join (select id,content_lang1 from t_sys_code where code_category='OPRS') orsc on ci.operation_result_id=orsc.id "
				+ " left join t_collection_unit last_unit on ciext.last_assign_company = last_unit.id "
				+ " left join ( select count(*) isWorkingToday,tct.cust_id from t_collection_task tct where "
				+ "  tct.TASK_OPT_STATUS "
				+ " in (select id from t_sys_code where code_category='TAOP' and code in ('01','02','03') ) "
				+ " group by tct.cust_id) wt on wt.cust_id = ci.id, "
				+ "  ");

		sql.append(" t_segment_info si_2, t_segment_tree st, t_collection_unit tcu ");
		sql.append(" where ci.segment_id = si_2.id and si_2.tree_id = st.id and st.create_unit_id = tcu.id  ");

        if(!AusxinUtil.isEmpty(collectionUnitId)){
            sql.append(" and tcu.id=? ");
            objs.add(Integer.parseInt(collectionUnitId));
        }

		sql.append(" and ci.collection_status = (select id from t_sys_code where code_category='CCST' and code='02') ");

		StringBuilder groupBySql = new StringBuilder(" group by ci.id, ci.customer_code, ci.collection_date, si.segment_name, ci.name, "
				+ " ci.out_assign_date, ci.out_assign_end_date,ci.out_assign_num, ci.out_assign_code ,"
				+ " ci.overdue_count, ci.segment_id, ci.age ,tai_assign.assigncnt, wt.isWorkingToday, last_unit.unit_name "
				+ " having 1=1 ");
		if (!AusxinUtil.isEmpty(customerCode)) {
			sql.append(" and ci.customer_code like ? ");
			customerCode = URLDecoder.decode(customerCode,"UTF-8");
			objs.add(customerCode+"%");
		}

		if (!AusxinUtil.isEmpty(customerName)) {
			sql.append(" and ci.name  like ? ");
			customerName = URLDecoder.decode(customerName,"UTF-8");
			objs.add(customerName+"%");
		}


		if ( !AusxinUtil.isEmpty(segmentName )) {
			sql.append(" and si.segment_name like ? ");
			segmentName = URLDecoder.decode(segmentName,"UTF-8");
			objs.add(segmentName+"%");
		}

		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();



		try {
			if (page!=null) {
				page.setRowCount(this.getCountSql(sql.append(groupBySql).toString(),objs.toArray()));
				//进行数据查询
				list = this.findPageListByJDBCSqlMy(sql.toString(),objs.toArray(),page.getRowStart(), page.getCurrentPage()*page.getPageSize());
			}else {
				list = this.getJdbcTemplate().queryForList(sql.append(groupBySql).toString(),objs.toArray());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
            e.printStackTrace();
		}
		return list;
	}

}
