package com.ausxin;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by lushuqin on 2016/10/9.
 */
public class JimuBoxImport {

    public static void main(String[] args){
        importData();
    }

    public static void importData(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/jimubox?useUnicode=true&characterEncoding=UTF-8", "root", "1234");
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            int custNum = 100;
            for (int custidx = 1; custidx <= custNum; custidx++) {
                String customerId=getRandomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ",3)+getRandomString("0123456789",8);
                String customerName=getRandomString("张李唐王宋卢高",1)+getRandomString("了解啥地链接撒点附近垃圾发动机阿里的叫法是陪我今儿就爱上对方圣诞节佛案",2);
                String Marriage=getRandomArrayString("10,90");
                String houSt=getRandomArrayString("20,30,50,70");
                String acdSt=getRandomArrayString("20,30");
                String fmlPhoNum="0"+getRandomString("0123456789",9);
                String mobPhoNum1="1"+getRandomString("0123456789",10);
                String mobPhoNum2="1"+getRandomString("0123456789",10);
                String cmpPhoNum="0"+getRandomString("0123456789",9);
                String weChat=getRandomString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",8);
                String qqNum=getRandomString("123456789",1)+getRandomString("0123456789",10);
                String Source=getRandomArrayString("02,01");
                String Channel=getRandomArrayString("2701000,1007030");
                String riskLevel=getRandomArrayString("0,1,2,3,4,5");
                String maxOverdueHis=getRandomArrayString("11,22,6,19,17,16,14,13,9,7,20,18,15,12,10,8,21,23,24,25,26,-16673,2,1,27,28,3,29,4,30,31,5,32,33,34,-16681,35,36,37,38,39,40,41,45,43,42,46,44,47,48,50,51,49,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,76,74,77,75,78,0,79,80,82,83,81,84,87,85,88,86,89,90,91,92,93,94,95,167,104,180,139,259,285,221,253,176,208,277,290,198,121,288,287,284,153,223,128,265,155,117,120,137");
                String maxOverdueThree=getRandomArrayString("6,19,17,16,14,13,11,9,7,20,18,15,12,10,8,21,22,23,24,25,26,-16673,2,1,27,28,3,29,4,30,31,5,32,33,34,-16681,35,36,37,38,39,40,41,45,43,42,46,44,47,48,50,51,49,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,76,74,77,75,78,0,79,80,82,83,81,84,87,85,88,86,89,90,91,92,93,94,95,167,104,180,139,259,285,221,253,176,208,277,290,198,121,288,287,284,153,223,128,265,155,117,120,137");
                String customerType=getRandomArrayString("0310,03100,0320,0370,0390");

                String customerSql=createImportMtCustomerQuerySql();
                customerSql=customerSql.replaceAll("customerId",customerId);
                customerSql=customerSql.replaceAll("customerName",customerName);
                customerSql=customerSql.replaceAll("Marriage",Marriage);
                customerSql=customerSql.replaceAll("houSt",houSt);
                customerSql=customerSql.replaceAll("acdSt",acdSt);
                customerSql=customerSql.replaceAll("fmlPhoNum",fmlPhoNum);
                customerSql=customerSql.replaceAll("mobPhoNum1",mobPhoNum1);
                customerSql=customerSql.replaceAll("mobPhoNum2",mobPhoNum2);
                customerSql=customerSql.replaceAll("cmpPhoNum",cmpPhoNum);
                customerSql=customerSql.replaceAll("weChat",weChat);
                customerSql=customerSql.replaceAll("qqNum",qqNum);
                customerSql=customerSql.replaceAll("Source",Source);
                customerSql=customerSql.replaceAll("Channel",Channel);
                customerSql=customerSql.replaceAll("riskLevel",riskLevel);
                customerSql=customerSql.replaceAll("maxOverdueHis",maxOverdueHis);
                customerSql=customerSql.replaceAll("maxOverdueThree",maxOverdueThree);
                customerSql=customerSql.replaceAll("customerType",customerType);
                stmt.addBatch(customerSql);

                String contactId=customerId+"_"+getRandomString("abcdefghijklmnopqrstuvwxyz",1)+getRandomString("0123456789",7);
                String contactName=getRandomString("班陈章丁赵潘",1)+getRandomString("了解啥地链接撒点附近垃圾发动机阿里的叫法是陪我今儿就爱上对方圣诞节佛案",2);
                String Relation=getRandomArrayString("0301,0302,0303,07020,07030");
                String mobPhoNum="1"+getRandomString("0123456789",10);
                String famPhoNum="0"+getRandomString("0123456789",9);
                String cmpPhoNumContact="0"+getRandomString("0123456789",9);

                String contactSql=createImportMtContactQuerySql();
                contactSql=contactSql.replaceAll("contactId",contactId);
                contactSql=contactSql.replaceAll("customerId",customerId);
                contactSql=contactSql.replaceAll("contactName",contactName);
                contactSql=contactSql.replaceAll("Relation",Relation);
                contactSql=contactSql.replaceAll("mobPhoNum",mobPhoNum);
                contactSql=contactSql.replaceAll("famPhoNum",famPhoNum);
                contactSql=contactSql.replaceAll("cmpPhoNumContact",cmpPhoNumContact);
                stmt.addBatch(contactSql);

                String accountId=getRandomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ",3)+getRandomString("0123456789",10);
                String deductAccount=getRandomString("6789",1)+getRandomString("0123456789",16);
                String plnAcc=getRandomString("6789",1)+getRandomString("0123456789",16);
                String prodName=getRandomArrayString("2701000010,1007000220,1007000180,1007000402,1007030240,1007000110,1007000120,1007000190,1007020220,1007010220");
                String usdPps=getRandomArrayString("个人借款,个人消费，现金业务,个人消费（购车周转）,个人经营用途,企业经营用途.企业经营流动性资金,个人消费,个人消费用途,经营:短期资金周转,消费:购物,消费:日常消费");
                String conAmount=getRandomAmount();
                String loanAmount=getRandomAmount();
                String loanPeriod=getRandomArrayString("24,6,1,3,2,12,18,13,36,10,8,15,9,0");
                String repDate=getRandomArrayString("5,13,19,1,18,21,24,3,4,9,10,11,12,14,15,16,17,20,26,6,7,25,27,28,29,2,8,22,23,30,31");
                String repType=getRandomArrayString(",3,#6,1");
                String overdueDays=getRandomString("9876",1);
                String firstPayOverDays=getRandomString("12",2);
                String curPayOverDays=getRandomString("12345",1);
                String overdueTimes=getRandomString("123",2);


                String creditAccoutSql=createImportMtCreditAccoutQuerySql();
                creditAccoutSql=creditAccoutSql.replaceAll("accountId",accountId);
                creditAccoutSql=creditAccoutSql.replaceAll("customerId",customerId);
                creditAccoutSql=creditAccoutSql.replaceAll("deductAccount",deductAccount);
                creditAccoutSql=creditAccoutSql.replaceAll("plnAcc",plnAcc);
                creditAccoutSql=creditAccoutSql.replaceAll("prodName",prodName);
                creditAccoutSql=creditAccoutSql.replaceAll("usdPps",usdPps);
                creditAccoutSql=creditAccoutSql.replaceAll("conAmount",conAmount);
                creditAccoutSql=creditAccoutSql.replaceAll("loanAmount",loanAmount);
                creditAccoutSql=creditAccoutSql.replaceAll("loanPeriod",loanPeriod);
                creditAccoutSql=creditAccoutSql.replaceAll("repDate",repDate);
                creditAccoutSql=creditAccoutSql.replaceAll("repType",repType);
                creditAccoutSql=creditAccoutSql.replaceAll("overdueDays",overdueDays);
                creditAccoutSql=creditAccoutSql.replaceAll("firstPayOverDays",firstPayOverDays);
                creditAccoutSql=creditAccoutSql.replaceAll("curPayOverDays",curPayOverDays);
                creditAccoutSql=creditAccoutSql.replaceAll("overdueTimes",overdueTimes);
                stmt.addBatch(creditAccoutSql);

                if (custidx % 999 == 0 || custidx == custNum) {
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



    public static String createImportMtCustomerQuerySql(){

        StringBuilder sqlBuilder=new StringBuilder();
        sqlBuilder.append(" INSERT INTO mt_customer(customer_id,name,id_no,marriage,hou_st,acd_st,fml_pho_num,mob_pho_num1,mob_pho_num2, ");
        sqlBuilder.append(" res_pro_add,res_city_add,res_dis_add,res_detail_add,res_zip_code,cen_res_pro_add,cen_res_city_add,cen_res_dis_add,cen_res_detail_add,cmp_pho_num, ");
        sqlBuilder.append(" cmp_name,cmp_pro_add,cmp_city_add,cmp_dis_add,cmp_detail_add,post_date,dept_name,int_type,unit_type,post_type,sch_name,sch_date,sch_len,email_add, ");
        sqlBuilder.append(" we_chat,qq_num,loan_num,first_overdeu_date,source,channel,risk_level,max_over_days_his,max_over_days_three,customer_type,bsn_dt,tms,tbl_nm,sp_nm) ");
        sqlBuilder.append(" VALUES ('customerId','customerName','NDUwMTIxMTk4NzA2MjM0ODQz','Marriage','houSt','acdSt','fmlPhoNum','mobPhoNum1','mobPhoNum2', ");
        sqlBuilder.append(" '130000','130100','130101','河北省石家庄市长安区***路100号','000001','130000','130100','130101','河北省石家庄市长安区***路100号','cmpPhoNum', ");
        sqlBuilder.append(" '长丰电器有限公司','130000','130100','130101','河北省石家庄市长安区',current_date(),'开发部','A02','040','3','上海复旦大学',current_date(),4,'ddadf@126.com', ");
        sqlBuilder.append(" 'weChat','qqNum',1,current_date(),'Source','Channel','riskLevel',maxOverdueHis,maxOverdueThree,'customerType',current_date(),now(),'mt_customer','p_mt_customer' ) ");

        return sqlBuilder.toString();

    }

    public static String createImportMtContactQuerySql(){

        StringBuilder sqlBuilder=new StringBuilder();
        sqlBuilder.append(" INSERT INTO mt_contact(contact_id,customer_id,contact_name,relation,mob_pho_num,fam_pho_num,reg_pro_add,reg_city_add, ");
        sqlBuilder.append(" reg_dis_add,reg_detail_add,cmp_pho_num,cmp_pro_add,cmp_city_add,cmp_dis_add,cmp_detail_add,is_available,is_checked,bsn_dt,tms,tbl_nm,sp_nm) ");
        sqlBuilder.append(" VALUES ('contactId','customerId','concactName','Relation','mobPhoNum','famPhoNum','320000','320400', ");
        sqlBuilder.append(" '320402','江苏省常州市天宁区**弄**号','cmpPhoNumContact','320000','320400','320402','江苏省常州市天宁区**弄**号','Y','Y',current_date(),now(),'mt_contact','p_mt_contact' ) ");

        return sqlBuilder.toString();

    }

    public static String createImportMtCreditAccoutQuerySql(){

        StringBuilder sqlBuilder=new StringBuilder();
        sqlBuilder.append(" INSERT INTO mt_credit_accout(account_id,customer_id,start_date,deduct_bank,deduct_account,pl_bank,pln_acc,pln_name,prod_name, ");
        sqlBuilder.append(" usd_pps,con_mer,con_branch,con_amount,loan_amount,loan_period,first_pay_date,rep_date,rep_type,rep_period,nor_rep_period, ");
        sqlBuilder.append(" receivable_inst,received_inst,accrued_inst,actual_amount, ");
        sqlBuilder.append(" man_amount,ser_amount,overdue_penalty,late_fees,overdue_principal,overdue_inst,over_due_fee_managment,pay_amount,overdue_amount,overdue_date, ");
        sqlBuilder.append(" cancel_staging,app_pay_channel,last_pay_channel,overdue_days,first_pay_over_days,cur_pay_over_days,overdue_times,contract_st,bsn_dt,tms, ");
        sqlBuilder.append(" tbl_nm,sp_nm,minsheng_account,minsheng_amount) ");
        sqlBuilder.append(" VALUES ('accountId','customerId',current_date(),'5','deductAccount','5','plnAcc','','prodName', ");
        sqlBuilder.append(" 'usdPps','100','100007001',conAmount,loanAmount,loanPeriod,current_date(),repDate,'repType',loanPeriod-1,loanPeriod-1, ");
        sqlBuilder.append(" loanAmount+2333.33,2000.00,loanAmount+333.33,loanAmount, ");
        sqlBuilder.append(" 334.333,432.56,2322.55,332.22,loanAmount,241.33,0.00,115.77,3269.77,current_date(), ");
        sqlBuilder.append(" 'N','1','2',overdueDays,firstPayOverDays,curPayOverDays,overdueTimes,'8',current_date(),now(), ");
        sqlBuilder.append(" 'mt_credit_accout','p_mt_credit_accout','ms001',123.00 ) ");

        return sqlBuilder.toString();

    }

    public static String createImportMtOutCustomerQuerySql(){

        StringBuilder sqlBuilder=new StringBuilder();
        sqlBuilder.append(" INSERT INTO mt_out_customer(customer_id,account_id,out_date,out_res_type ");
        sqlBuilder.append(" ,bsn_dt,tms,tbl_nm,sp_nm) ");
        sqlBuilder.append(" VALUES ('customerId','accountId',current_date(),null ");
        sqlBuilder.append(" ,current_date(),now(),'mt_out_customer','p_mt_out_customer' ) ");

        return sqlBuilder.toString();

    }



    public static String getRandomString(String base,int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomArrayString(String base) {
        String[] bases=base.split(",");
        Random random = new Random();
        int number = random.nextInt(bases.length);
        return bases[number];
    }

    public static String getRandomAmount() {
        Random random = new Random();
        int preLength = random.nextInt(6);
        while(preLength==0){
            preLength = random.nextInt(6);
        }
        return getRandomString("123456",1)+getRandomString("0123456789",preLength-1)+"."+getRandomString("0123456789",2);
    }


}
