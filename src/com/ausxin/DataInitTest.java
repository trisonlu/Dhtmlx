package com.ausxin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Random;

public class DataInitTest {

	public static void main(String[] args) {
		testCheckTableStatus();
	}

	public static void testCheckTableStatus() {
		String sqlEnd = "";
		StringBuilder custSql = new StringBuilder();
		custSql.append("insert into DB2ADMIN.f_customer (");
		custSql.append("dat_dte,");
		custSql.append("clt_nbr  ,");
		custSql.append("clt_nam  ,");
		custSql.append("ctf_typ  ,");
		custSql.append("ctf_cod  ,");
		custSql.append("grd_typ  ,");
		custSql.append("age_age  ,");
		custSql.append("mrt_sts  ,");
		custSql.append("cmp_nam  ,");
		custSql.append("lon_bal  ,");
		custSql.append("urg_rea  ,");
		custSql.append("is_vip   ,");
		custSql.append("is_cmb   ,");
		custSql.append("is_sen   ,");
		custSql.append("drf_num,CRE_DAT  ) values( ");
		custSql.append(" CURRENT DATE, ");
		custSql.append(" 'cust_code', ");
		custSql.append(" 'cust_name', ");
		custSql.append(" 'P01','123456789012345678','M',18,'B','cmp_name',123.11,'urgrea','Y','N','Y',3,CURRENT date)");
		custSql.append(sqlEnd);
		StringBuilder fcompanyinfoSql = new StringBuilder();
		fcompanyinfoSql.append("insert into DB2ADMIN.f_company_info (");
		fcompanyinfoSql.append("dat_dte,");
		fcompanyinfoSql.append("cmp_nbr,");
		fcompanyinfoSql.append("cmp_nam,");
		fcompanyinfoSql.append("est_dat,");
		fcompanyinfoSql.append("int_typ,");
		fcompanyinfoSql.append("org_nbr,");
		fcompanyinfoSql.append("reg_add,");
		fcompanyinfoSql.append("ctt_pho,");
		fcompanyinfoSql.append("mob_pho,");
		fcompanyinfoSql.append("lon_bal,");
		fcompanyinfoSql.append("urg_rea,CRE_DAT");
		fcompanyinfoSql.append(" ) values(  ");
		fcompanyinfoSql.append(" CURRENT DATE, 'cmp_code','cmp_name', CURRENT DATE ,'1','2','3','4','5',12.12,'urgrea',CURRENT date)");
		fcompanyinfoSql.append(sqlEnd);
		StringBuilder custExtSql = new StringBuilder();
		custExtSql.append("insert into DB2ADMIN.f_customer_info_ext (");
		custExtSql.append(" dat_dte,");
		custExtSql.append(" clt_nbr,");
		custExtSql.append(" crl_cnt,");
		custExtSql.append(" crl_amt,");
		custExtSql.append(" usd_amt,");
		custExtSql.append(" fam_cnt,");
		custExtSql.append(" fam_usd,");
		custExtSql.append(" fam_amt ");
		custExtSql.append(" ) values(  ");
		custExtSql.append(" CURRENT DATE, 'cust_code',3, 11.11,12.12,13,14.14,15.15)");
		custExtSql.append(sqlEnd);
		StringBuilder customerAddressSql = new StringBuilder();
		customerAddressSql.append("insert into DB2ADMIN.f_customer_address_info  (");
		customerAddressSql.append("dat_dte   ,");
		customerAddressSql.append("clt_nbr   ,");
		customerAddressSql.append("is_def    ,");
		customerAddressSql.append("is_org    ,");
		customerAddressSql.append("zip_cod   ,");
		customerAddressSql.append("cou_add   ,");
		customerAddressSql.append("pro_add   ,");
		customerAddressSql.append("cit_add   ,");
		customerAddressSql.append("dis_add   ,");
		customerAddressSql.append("det_add   ,");
		customerAddressSql.append("pho_nbr   ,");
		customerAddressSql.append("nat_nam   ,");
		customerAddressSql.append("pro_nam   ,");
		customerAddressSql.append("cit_nam   ,");
		customerAddressSql.append("dis_nam   ,");
		customerAddressSql.append("rmk_inf   ");
		customerAddressSql.append(" ) values(  ");
		customerAddressSql.append(" CURRENT DATE, 'cust_code','Y','Y','zipcod','','','','','detadd','','','','','','rmkinf')");
		customerAddressSql.append(sqlEnd);
		StringBuilder customerCmpAddressSql = new StringBuilder();
		customerCmpAddressSql.append("insert into DB2ADMIN.f_customer_company_addr_info  (");
		customerCmpAddressSql.append("dat_dte   ,");
		customerCmpAddressSql.append("clt_nbr   ,");
		customerCmpAddressSql.append("is_def    ,");
		customerCmpAddressSql.append("is_org    ,");
		customerCmpAddressSql.append("zip_cod   ,");
		customerCmpAddressSql.append("cmp_nam   ,");
		customerCmpAddressSql.append("cou_add   ,");
		customerCmpAddressSql.append("pro_add   ,");
		customerCmpAddressSql.append("cit_add   ,");
		customerCmpAddressSql.append("dis_add   ,");
		customerCmpAddressSql.append("det_add   ,");
		customerCmpAddressSql.append("pho_nbr   ,");
		customerCmpAddressSql.append("pro_nam   ,");
		customerCmpAddressSql.append("cit_nam   ,");
		customerCmpAddressSql.append("dis_nam   ,");
		customerCmpAddressSql.append("cou_nam   ,");
		customerCmpAddressSql.append("rml_inf   ");
		customerCmpAddressSql.append(" ) values(  ");
		customerCmpAddressSql.append(" CURRENT DATE, 'cust_code','Y','Y','zipcod','cmpnam','','','','','detadd','','','','','','rmlinf')");
		customerCmpAddressSql.append(sqlEnd);
		StringBuilder customerEmailSql = new StringBuilder();
		customerEmailSql.append("insert into DB2ADMIN.f_customer_email_info  (");
		customerEmailSql.append("dat_dte   ,");
		customerEmailSql.append("clt_nbr   ,");
		customerEmailSql.append("eml_add    ,");
		customerEmailSql.append("is_ava    ,");
		customerEmailSql.append("is_org   ,");
		customerEmailSql.append("rmk_inf   ");
		customerEmailSql.append(" ) values(  ");
		customerEmailSql.append(" CURRENT DATE, 'cust_code','email_rep','Y','Y','rmkinf')");
		customerEmailSql.append(sqlEnd);
		StringBuilder customerPhoneSql = new StringBuilder();
		customerPhoneSql.append("insert into DB2ADMIN.f_customer_phone_info  (");
		customerPhoneSql.append("dat_dte   ,");
		customerPhoneSql.append("clt_nbr   ,");
		customerPhoneSql.append("is_ava    ,");
		customerPhoneSql.append("is_org   ,");
		customerPhoneSql.append("ctt_tel   ,");
		customerPhoneSql.append("is_pho   ,");
		customerPhoneSql.append("pho_typ   ,");
		customerPhoneSql.append("rmk_inf   ");
		customerPhoneSql.append(" ) values(  ");
		customerPhoneSql.append(" CURRENT DATE, 'cust_code','Y','Y','phonum','ispho','photyp','rmkinf')");
		customerPhoneSql.append(sqlEnd);

		StringBuilder creditInfoSql = new StringBuilder();
		creditInfoSql.append("insert into DB2ADMIN.f_credit_info (");
		creditInfoSql.append("dat_dte,");
		creditInfoSql.append("crl_nbr,");
		creditInfoSql.append("clt_nbr,");
		creditInfoSql.append("apl_nbr,");
		creditInfoSql.append("prd_nbr,");
		creditInfoSql.append("prd_nam,");
		creditInfoSql.append("is_cyl ,");
		creditInfoSql.append("usd_pps,");
		creditInfoSql.append("crl_amt,");
		creditInfoSql.append("crl_ave,");
		creditInfoSql.append("tem_tem,");
		creditInfoSql.append("str_dte,");
		creditInfoSql.append("end_dte,");
		creditInfoSql.append("usd_end,");
		creditInfoSql.append("crl_sts,");
		creditInfoSql.append("grt_typ,");
		creditInfoSql.append("lon_cnt,");
		creditInfoSql.append("lon_bal,");
		creditInfoSql.append("apl_bbk,");
		creditInfoSql.append("bra_bbk,");
		creditInfoSql.append("dfr_flg,");
		creditInfoSql.append("acc_org,");
		creditInfoSql.append("is_plg ,");
		creditInfoSql.append("reg_nb1,");
		creditInfoSql.append("reg_nb2");
		creditInfoSql.append(" ) values(  ");
		creditInfoSql.append(" CURRENT DATE, 'creditcode','custcode','aplnbr',");
		creditInfoSql.append(" 'prdnbr','prdnam','Y','0101',null,12.32,12,CURRENT DATE,null,CURRENT DATE,");
		creditInfoSql.append(" 'PAUS','30205',12,22.12,'321','brabbk','1','accorg',null,'regnb1',''");
		creditInfoSql.append(")");
		creditInfoSql.append(sqlEnd);
		StringBuilder loanInfoSql = new StringBuilder();
		loanInfoSql.append("insert into DB2ADMIN.f_loan_info (");
		loanInfoSql.append("dat_dte ,bus_nbr ,clt_nbr ,crl_rot ,apl_nbr , ");
		loanInfoSql.append("prd_nbr ,crl_nbr ,bus_sts ,bal_amt ,tem_tem , ");
		loanInfoSql.append("ris_typ ,pln_acc ,apl_bbk ,bra_bbk ,rat_adj , ");
		loanInfoSql.append("drf_l12 ,pay_typ ,acc_org ,lon_pro ,crl_prd , ");
		loanInfoSql.append("lon_pps ,hig_l12 ,lon_dat ,shx_amt ,csc_ore , ");
		loanInfoSql.append("asc_ore ,nxt_dte ,end_dte ,adj_nam ,bbk_nbr , ");
		loanInfoSql.append("cpr_nam ");
		loanInfoSql.append(" ) values(  ");
		loanInfoSql.append("CURRENT DATE,'loancode','custcode','creditcode','aplnbr'");
		loanInfoSql.append(",'prdnbr','creditcode','DEFR',4.4,1");
		loanInfoSql.append(",'12345','plnacc','aplbbk','brabbk','1'");
		loanInfoSql.append(",'drfl12','paytyp','accorg','01','crlprd'");
		loanInfoSql.append(",'lops',7,CURRENT DATE,1.1,2.2");
		loanInfoSql.append(",3.3,CURRENT DATE,CURRENT DATE,'adjnam','789'");
		loanInfoSql.append(",'闪电贷' ");
		loanInfoSql.append(")");
		loanInfoSql.append(sqlEnd);
		StringBuilder overdueInfoSql = new StringBuilder();
		overdueInfoSql.append("insert into DB2ADMIN.f_overdue_info (");
		overdueInfoSql.append("dat_dte      ,");
		overdueInfoSql.append("bus_nbr      ,");
		overdueInfoSql.append("prd_nbr      ,");
		overdueInfoSql.append("clt_nbr      ,");
		overdueInfoSql.append("is_fst       ,");
		overdueInfoSql.append("drf_amt      ,");
		overdueInfoSql.append("drf_int      ,");
		overdueInfoSql.append("drf_inf      ,");
		overdueInfoSql.append("drf_pad      ,");
		overdueInfoSql.append("drf_day      ,");
		overdueInfoSql.append("tms_tsm      ,");
		overdueInfoSql.append("acc_tis      ,");
		overdueInfoSql.append("ser_tis      ,");
		overdueInfoSql.append("prd_nam      ,");
		overdueInfoSql.append("pln_acc      ,");
		overdueInfoSql.append("apl_bbk      ,");
		overdueInfoSql.append("bra_bbk      ,");
		overdueInfoSql.append("cur_amt      ,");
		overdueInfoSql.append("tot_amt      ,");
		overdueInfoSql.append("rat_adj      ,");
		overdueInfoSql.append("pay_typ      ,");
		overdueInfoSql.append("drf_tot_amt  ,");
		overdueInfoSql.append("drf_dat      ,");
		overdueInfoSql.append("acc_org      ,");
		overdueInfoSql.append("lon_pro      ");
		overdueInfoSql.append(" ) values(  ");
		overdueInfoSql.append("CURRENT DATE,'loancode','prdnbr','custcode','Y',");
		overdueInfoSql.append("1.1,2.2,3.3,4.4,5,6,null,8,'prdnam','C123456','aplbbk','brabbk',");
		overdueInfoSql.append("9.9,10.10,'1','py3',11.11,CURRENT DATE,'accorg','01')");
		overdueInfoSql.append(sqlEnd);

		StringBuilder collateralInfoSql = new StringBuilder();
		collateralInfoSql.append("insert into  DB2ADMIN.f_collateral_info (");
		collateralInfoSql.append("    dat_dte, ");
		collateralInfoSql.append("    crl_nbr, ");
		collateralInfoSql.append("    plg_nbr, ");
		collateralInfoSql.append("    plg_seq, ");
		collateralInfoSql.append("    man_plg,  ");
		collateralInfoSql.append("    plg_amt, ");
		collateralInfoSql.append("    plg_nam ");
		collateralInfoSql.append(" ) values(  ");
		collateralInfoSql.append("CURRENT DATE,'creditcode','plgnbr',");
		collateralInfoSql.append("'plgseq','manplg',plgamt,'plgnam' )");
		collateralInfoSql.append(sqlEnd);

		StringBuilder contactInfoSql = new StringBuilder();
		contactInfoSql.append("insert into DB2ADMIN.f_contact_info(dat_dte,clt_nbr,age_age,clt_rmk,nat_cod,ctt_nam,");  //6个
		contactInfoSql.append("grd_typ,mrt_sts,clt_rel,is_cor, clt_typ,crl_nbr,");  //6个
		contactInfoSql.append("ctt_nbr,fml_add,fml_add_def,fml_add_org,fml_rmk,"); //5
		contactInfoSql.append("fml_zip_cod,fml_cit_add,fml_cou_add,fml_pro_add,");//4
		contactInfoSql.append("fml_dis_add,fml_dis_nam,fml_cou_nam,fml_pro_nam,");//4
		contactInfoSql.append("fml_cit_nam,fml_pho_nbr,cmp_nam,cmp_det_add,cmp_add_def,");//5
		contactInfoSql.append("cmp_add_org,cmp_rmk,cmp_zip_cod,cmp_cit_add,cmp_cou_add,");//5
		contactInfoSql.append("cmp_pro_add,cmp_dis_add,cmp_dis_nam,cmp_cou_nam,cmp_pro_nam,");//5
		contactInfoSql.append("cmp_cit_nam,cmp_pho_nbr,eml_add,eml_add_def,eml_add_org,");//5
		contactInfoSql.append("eml_rmk,mob_nbr_def,mob_nbr_org,mob_nbr_rmk,mob_nbr,rlt_typ ");//6
		contactInfoSql.append(" ) values(  ");
		contactInfoSql.append("CURRENT DATE, 'custcode', null, '备注', '', '姓名', 'F', '01', '02',    ");
		contactInfoSql.append("'Y', 'CCTY', 'creditcode', 'cttnbr', '家庭地址', 'Y', 'Y', '备注',    ");
		contactInfoSql.append("'zipcod', '', '', '', '', '', '', '', '', 'fmlphone', '公司名称',     ");
		contactInfoSql.append("'公司地址', 'Y', 'Y', '备注', 'zipcod', '', '', '', '', '', '',        ");
		contactInfoSql.append("'', '', 'cmpphone', 'email', 'Y', 'Y', '备注', '', '', '', 'mobphone','02' )");
		contactInfoSql.append(sqlEnd);

		StringBuilder businessInfoSql = new StringBuilder();
		businessInfoSql.append("insert into DB2ADMIN.f_bus_info(");
		businessInfoSql.append("dat_dte,clt_nbr,clt_typ,clt_crl,clt_nam,");
		businessInfoSql.append("ctf_typ,ctf_cod,fud_amt,ins_amt,fin_amt,");
		businessInfoSql.append("exc_amt,deb_amt,now_bal,mon_bal,yea_bal,");
		businessInfoSql.append("hig_cad,all_one,hav_gld,hav_vip,hav_dim,");
		businessInfoSql.append("nrm_lvl,gld_lvl,vip_lvl,dim_lvl,gld_acc,");
		businessInfoSql.append("gld_int,pro_ver,eas_fin,aum_amt,hav_vis,");
		businessInfoSql.append("vis_typ,vis_sts,vis_ope_dta,vip_ope_dta,");//4
		businessInfoSql.append("vis_crl,crl_usd,bal_amt,rep_prc,rep_amt, ");
		businessInfoSql.append("drf_24m,rep_12m,drf_cur,drf_amt,hig_ind,");
		businessInfoSql.append("rmb_sav,rmb_lif,mon_aum,yer_aum");
		businessInfoSql.append(" ) values(  ");
		businessInfoSql.append("CURRENT DATE,'cltnbr','clttyp','creditcode','姓名','','',");
		businessInfoSql.append("1.01,2.02,3.03,4.04,5.05,6.06,7.07,8.08,'higcad','1', ");
		businessInfoSql.append("'2','3', '4','5','6','7','8','9','0','a','b',9.09,'c','vistyp',");
		businessInfoSql.append("'vissts',CURRENT DATE,CURRENT DATE,10.10,11.11,12.12,13.13,14.14,");
		businessInfoSql.append("15,16,17,18.18,19.19,20.20,21.21,22.22,23.23");
		businessInfoSql.append(")");
		businessInfoSql.append(sqlEnd);
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:db2://localhost:50000/sample:currentSchema=DB2ADMIN;", "db2admin", "111111");
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			int custNum = 1000;
			for (int custidx = 0; custidx < custNum; custidx++) {
				// f_customer
				String tmpCustSql = custSql.toString();
				String tmp = getRandomString(8);
				String tmpCustCode = tmp;
				String tmpCustCmpCode = tmp;
				tmpCustSql = tmpCustSql.replaceAll("cust_code", tmpCustCode);
				tmpCustSql = tmpCustSql.replaceAll("cust_name", "cust_name_t_"+custidx);
				if (custidx%2 ==0) {
					tmpCustSql = tmpCustSql.replaceAll("urgrea", "02");
				} else {
					tmpCustSql = tmpCustSql.replaceAll("urgrea", "03");
				}
				stmt.addBatch(tmpCustSql);
				String tmpCustCmpSql = fcompanyinfoSql.toString();
				tmpCustCmpSql = tmpCustCmpSql.replaceAll("cmp_code", tmpCustCmpCode);
				tmpCustCmpSql = tmpCustCmpSql.replaceAll("cmp_name", "cmp_name_t_"+custidx);
				if (custidx%2 ==0) {
					tmpCustCmpSql = tmpCustCmpSql.replaceAll("urgrea", "02");
				} else {
					tmpCustCmpSql = tmpCustCmpSql.replaceAll("urgrea", "03");
				}
				stmt.addBatch(tmpCustCmpSql);
				// f_customer_ext 1:1
				String tmpCustExtSql = custExtSql.toString();
				tmpCustExtSql = tmpCustExtSql.replaceAll("cust_code", tmpCustCode);
				stmt.addBatch(tmpCustExtSql);

				// f_bus_info
				String tmpBusSql = businessInfoSql.toString();
				tmpBusSql = tmpBusSql.replaceAll("cltnbr", tmpCustCode);
				tmpBusSql = tmpBusSql.replaceAll("clttyp", "");
				tmpBusSql = tmpBusSql.replaceAll("姓名", "CI姓名" + custidx);
				stmt.addBatch(tmpBusSql);

				// f_customer_address_info
				String tmpCustAddSql = customerAddressSql.toString();
				String tmpzipCod = "z"+custidx;
				String tmpdetAdd = "det_add_t_"+custidx;
				String tmprmkInf = "rmk_inf_t_"+custidx;
				tmpCustAddSql = tmpCustAddSql.replaceAll("cust_code", tmpCustCode);
				tmpCustAddSql = tmpCustAddSql.replaceAll("zipcod", tmpzipCod);
				tmpCustAddSql = tmpCustAddSql.replaceAll("detadd", tmpdetAdd);
				tmpCustAddSql = tmpCustAddSql.replaceAll("rmkinf", tmprmkInf);
				stmt.addBatch(tmpCustAddSql);
				// f_cmp_add
				String tmpCustCmpAddSql = customerCmpAddressSql.toString();
				String tmpcmpnam = "cmp_nam_t_"+custidx;
				tmpCustCmpAddSql = tmpCustCmpAddSql.replaceAll("cust_code", tmpCustCode);
				tmpCustCmpAddSql = tmpCustCmpAddSql.replaceAll("zipcod", tmpzipCod);
				tmpCustCmpAddSql = tmpCustCmpAddSql.replaceAll("detadd", tmpdetAdd);
				tmpCustCmpAddSql = tmpCustCmpAddSql.replaceAll("rmlinf", tmprmkInf);
				tmpCustCmpAddSql = tmpCustCmpAddSql.replaceAll("cmpnam", tmpcmpnam);
				stmt.addBatch(tmpCustCmpAddSql);

				String tmpCustEmailSql = customerEmailSql.toString();
				tmpCustEmailSql = tmpCustEmailSql.replaceAll("cust_code", tmpCustCode);
				tmpCustEmailSql = tmpCustEmailSql.replaceAll("email_rep", "email"+custidx+"@123.com");
				stmt.addBatch(tmpCustEmailSql);
				int phoneTypeCnt = 3;
				for (int phoneidx =0; phoneidx< phoneTypeCnt; phoneidx++) {
					String tmpCustPhoneSql = customerPhoneSql.toString();
					String tmpphonum = "pnum_"+ custidx +"_" +phoneidx;
					tmpCustPhoneSql = tmpCustPhoneSql.replaceAll("cust_code", tmpCustCode);
					tmpCustPhoneSql = tmpCustPhoneSql.replaceAll("rmkinf", tmprmkInf);
					tmpCustPhoneSql = tmpCustPhoneSql.replaceAll("phonum", tmpphonum);
					if (phoneidx ==0) {
						tmpCustPhoneSql = tmpCustPhoneSql.replaceAll("ispho", "Y");
						tmpCustPhoneSql = tmpCustPhoneSql.replaceAll("photyp", "01");
					}
					if (phoneidx ==1) {
						tmpCustPhoneSql = tmpCustPhoneSql.replaceAll("ispho", "N");
						tmpCustPhoneSql = tmpCustPhoneSql.replaceAll("photyp", "02");
					}
					if (phoneidx ==2) {
						tmpCustPhoneSql = tmpCustPhoneSql.replaceAll("ispho", "N");
						tmpCustPhoneSql = tmpCustPhoneSql.replaceAll("photyp", "03");
					}
					stmt.addBatch(tmpCustPhoneSql);
				}
				// 额度  1:2
				int creditNum = 2;
				for (int creditidx =0; creditidx< creditNum; creditidx++) {
					String tmpCreditSql = creditInfoSql.toString();
					String tmpcreditcode = "cr_"+custidx+"_"+creditidx;
					String tmpbrabbk = "b"+creditidx;
					String tmpbrappk = "a"+creditidx;
					String tmpaccorg = "ag"+"_"+creditidx;
					tmpCreditSql = tmpCreditSql.replaceAll("aplbbk", tmpbrappk);
					tmpCreditSql = tmpCreditSql.replaceAll("brabbk", tmpbrabbk);
					tmpCreditSql = tmpCreditSql.replaceAll("custcode", tmpCustCode);
					tmpCreditSql = tmpCreditSql.replaceAll("creditcode", tmpcreditcode);
					tmpCreditSql = tmpCreditSql.replaceAll("accorg", tmpaccorg);
					String tmpCollateralInfoSql = collateralInfoSql.toString();
					tmpCollateralInfoSql = tmpCollateralInfoSql.replaceAll("creditcode", tmpcreditcode);
					tmpCollateralInfoSql = tmpCollateralInfoSql.replaceAll("aplbbk", tmpbrappk);
					tmpCollateralInfoSql = tmpCollateralInfoSql.replaceAll("brabbk", tmpbrabbk);
					tmpCollateralInfoSql = tmpCollateralInfoSql.replaceAll("accorg", tmpaccorg);
					tmpCollateralInfoSql = tmpCollateralInfoSql.replaceAll("manplg", creditidx+"");
					tmpCollateralInfoSql = tmpCollateralInfoSql.replaceAll("plgamt", creditidx+"");
					tmpCollateralInfoSql = tmpCollateralInfoSql.replaceAll("plgseq", creditidx+"s");

					stmt.addBatch(tmpCollateralInfoSql);
					stmt.addBatch(tmpCreditSql);
					// loan_info 1 : 10
					for(int loanidx = 0; loanidx < 2; loanidx ++) {
						String tmploanSql = loanInfoSql.toString();
						String tmploancode = "loan_code_t_"+custidx+"_"+creditidx +"_" +loanidx;
						tmploanSql = tmploanSql.replaceAll("creditcode", tmpcreditcode);
						tmploanSql = tmploanSql.replaceAll("custcode", tmpCustCode);
						tmploanSql = tmploanSql.replaceAll("loancode", tmploancode);
						tmploanSql = tmploanSql.replaceAll("accorg", tmpaccorg);
						tmploanSql = tmploanSql.replaceAll("paytyp", "123");
						tmploanSql = tmploanSql.replaceAll("aplbbk", tmpbrappk);
						tmploanSql = tmploanSql.replaceAll("brabbk", tmpbrabbk);


						String tmpOverdueSql = overdueInfoSql.toString();
						tmpOverdueSql = tmpOverdueSql.replaceAll("loancode", tmploancode);
						tmpOverdueSql = tmpOverdueSql.replaceAll("custcode", tmpCustCode);
						tmpOverdueSql = tmpOverdueSql.replaceAll("aplbbk", tmpbrappk);
						tmpOverdueSql = tmpOverdueSql.replaceAll("brabbk", tmpbrabbk);
						tmpOverdueSql = tmpOverdueSql.replaceAll("accorg", tmpaccorg);

						if (loanidx<=4) {
							tmploanSql = tmploanSql.replaceAll("crlprd", "1122");
							tmploanSql = tmploanSql.replaceAll("prdnbr", "prdnbr"+loanidx);
						}
						if (loanidx >4 && loanidx%2==0) {
							tmploanSql = tmploanSql.replaceAll("prdnbr", "03620100");
							tmploanSql = tmploanSql.replaceAll("crlprd", "03620100");
						}
						if (loanidx >4 && loanidx%2==1) {
							tmploanSql = tmploanSql.replaceAll("prdnbr", "03620200");
							tmploanSql = tmploanSql.replaceAll("crlprd", "03620200");
						}
						stmt.addBatch(tmploanSql);
						stmt.addBatch(tmpOverdueSql);
					}
				}

				// contact 1:10
				for (int conidx = 0;conidx<1; conidx++) {
					String tmpContSql = contactInfoSql.toString();
					tmpContSql = tmpContSql.replaceAll("custcode", tmpCustCode);
					tmpContSql = tmpContSql.replaceAll("备注", "备注"+custidx+"_"+conidx);
					tmpContSql = tmpContSql.replaceAll("姓名", "姓名"+custidx+"_"+conidx);

					if(conidx ==0) {
						tmpContSql = tmpContSql.replaceAll("CCTY", "Y");
					} else if (conidx ==1 || conidx ==2) {
						tmpContSql = tmpContSql.replaceAll("CCTY", "N");
					}else if (conidx == 3) {
						tmpContSql = tmpContSql.replaceAll("CCTY", "B");
					} else {
						tmpContSql = tmpContSql.replaceAll("CCTY", "");
					}
					String tmpcreditcode = "cr_"+custidx+"_"+(conidx > 1 ? 1 : conidx);
					tmpContSql = tmpContSql.replaceAll("creditcode", tmpcreditcode);
					if (conidx < 6) {
						tmpContSql = tmpContSql.replaceAll("cttnbr", "concode_"+custidx+"_"+conidx);
					} else {
						tmpContSql = tmpContSql.replaceAll("cttnbr", "");
					}
					tmpContSql = tmpContSql.replaceAll("家庭地址", "家庭地址"+custidx +"_" +conidx);
					tmpContSql = tmpContSql.replaceAll("公司地址", "公司地址"+custidx +"_" +conidx);
					tmpContSql = tmpContSql.replaceAll("fmlphone", String.valueOf(custidx));
					tmpContSql = tmpContSql.replaceAll("cmpphone", String.valueOf(custidx));
					tmpContSql = tmpContSql.replaceAll("mobphone", String.valueOf(custidx));
					stmt.addBatch(tmpContSql);

					String tmpBusSql1 = businessInfoSql.toString();
					tmpBusSql1 = tmpBusSql1.replaceAll("cltnbr", "concode_"+custidx+"_"+conidx);
					tmpBusSql1 = tmpBusSql1.replaceAll("clttyp", "1");
					tmpBusSql1 = tmpBusSql1.replaceAll("姓名", "CO姓名" + custidx+"_"+conidx);
					stmt.addBatch(tmpBusSql1);
				}
				if (custidx >0 || (custidx % 100 == 0 || custidx == custNum -1)) {
					System.out.println(custidx);
					stmt.executeBatch();
					stmt.clearBatch();
					con.commit();
				}
			}
			stmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
	public static String getRandomString(int length) { //length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	//	@Test
	public void deleteTestData() {
		int batchNum = 10;
		int start = 100;
		int count = 300000;
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:db2://localhost:50000/sample:currentSchema=DB2ADMIN;", "db2admin", "111111");
			con.setAutoCommit(false);
			PreparedStatement pst1 = con.prepareStatement("delete from cmbcdev.t_business_info where id =? ");
			PreparedStatement pst2 = con.prepareStatement("delete from cmbcdev.t_contact_phone_info where id =? ");
			for (int i = start; i<count; i++) {
				pst1.setLong(1, i);
				pst1.addBatch();
				pst2.setLong(1, i);
				pst2.addBatch();
				if (i%batchNum ==0 || i == (count -1)) {
					pst1.executeBatch();
					pst2.executeBatch();
					System.out.println(i);
					con.commit();
					pst2.clearBatch();
				}
			}
			System.out.println("D");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
