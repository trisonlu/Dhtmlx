package com.ausxin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by lushuqin on 2016/10/9.
 */
public class JimuBoxImportSql {

    public static void main(String[] args){
        importData();
    }

    public static void importData(){
        try {
            StringBuilder customerBuilder=new StringBuilder();
            StringBuilder loanBuilder=new StringBuilder();
            StringBuilder contactBuilder=new StringBuilder();
            int custNum = 2000;
            for (int custidx = 1; custidx <= custNum; custidx++) {
                String customerId=JimuBoxImport.getRandomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ",3)+JimuBoxImport.getRandomString("0123456789",8);
                String customerName=JimuBoxImport.getRandomString("张李唐王宋卢高",1)+JimuBoxImport.getRandomString("了解啥地链接撒点附近垃圾发动机阿里的叫法是陪我今儿就爱上对方圣诞节佛案",2);
                String Marriage=JimuBoxImport.getRandomArrayString("10,90");
                String houSt=JimuBoxImport.getRandomArrayString("20,30,50,70");
                String acdSt=JimuBoxImport.getRandomArrayString("20,30");
                String fmlPhoNum="0"+JimuBoxImport.getRandomString("0123456789",9);
                String mobPhoNum1="1"+JimuBoxImport.getRandomString("0123456789",10);
                String mobPhoNum2="1"+JimuBoxImport.getRandomString("0123456789",10);
                String cmpPhoNum="0"+JimuBoxImport.getRandomString("0123456789",9);
                String weChat=JimuBoxImport.getRandomString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",8);
                String qqNum=JimuBoxImport.getRandomString("123456789",1)+JimuBoxImport.getRandomString("0123456789",10);
                String Source=JimuBoxImport.getRandomArrayString("02,01");
                String Channel=JimuBoxImport.getRandomArrayString("2701000,1007030");
                String riskLevel=JimuBoxImport.getRandomArrayString("0,1,2,3,4,5");
                String maxOverdueHis=JimuBoxImport.getRandomArrayString("11,22,6,19,17,16,14,13,9,7,20,18,15,12,10,8,21,23,24,25,26,-16673,2,1,27,28,3,29,4,30,31,5,32,33,34,-16681,35,36,37,38,39,40,41,45,43,42,46,44,47,48,50,51,49,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,76,74,77,75,78,0,79,80,82,83,81,84,87,85,88,86,89,90,91,92,93,94,95,167,104,180,139,259,285,221,253,176,208,277,290,198,121,288,287,284,153,223,128,265,155,117,120,137");
                String maxOverdueThree=JimuBoxImport.getRandomArrayString("6,19,17,16,14,13,11,9,7,20,18,15,12,10,8,21,22,23,24,25,26,-16673,2,1,27,28,3,29,4,30,31,5,32,33,34,-16681,35,36,37,38,39,40,41,45,43,42,46,44,47,48,50,51,49,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,76,74,77,75,78,0,79,80,82,83,81,84,87,85,88,86,89,90,91,92,93,94,95,167,104,180,139,259,285,221,253,176,208,277,290,198,121,288,287,284,153,223,128,265,155,117,120,137");
                String customerType=JimuBoxImport.getRandomArrayString("0310,03100,0320,0370,0390");

                String customerSql=JimuBoxImport.createImportMtCustomerQuerySql()+";";
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
                customerBuilder.append(customerSql).append("\r\n");

                String contactId=customerId+"_"+JimuBoxImport.getRandomString("abcdefghijklmnopqrstuvwxyz",1)+JimuBoxImport.getRandomString("0123456789",7);
                String contactName=JimuBoxImport.getRandomString("班陈章丁赵潘",1)+JimuBoxImport.getRandomString("了解啥地链接撒点附近垃圾发动机阿里的叫法是陪我今儿就爱上对方圣诞节佛案",2);
                String Relation=JimuBoxImport.getRandomArrayString("0301,0302,0303,07020,07030");
                String mobPhoNum="1"+JimuBoxImport.getRandomString("0123456789",10);
                String famPhoNum="0"+JimuBoxImport.getRandomString("0123456789",9);
                String cmpPhoNumContact="0"+JimuBoxImport.getRandomString("0123456789",9);

                String contactSql=JimuBoxImport.createImportMtContactQuerySql()+";";
                contactSql=contactSql.replaceAll("contactId",contactId);
                contactSql=contactSql.replaceAll("customerId",customerId);
                contactSql=contactSql.replaceAll("contactName",contactName);
                contactSql=contactSql.replaceAll("Relation",Relation);
                contactSql=contactSql.replaceAll("mobPhoNum",mobPhoNum);
                contactSql=contactSql.replaceAll("famPhoNum",famPhoNum);
                contactSql=contactSql.replaceAll("cmpPhoNumContact",cmpPhoNumContact);
                contactBuilder.append(contactSql).append("\r\n");

                String accountId=JimuBoxImport.getRandomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ",3)+JimuBoxImport.getRandomString("0123456789",10);
                String deductAccount=JimuBoxImport.getRandomString("6789",1)+JimuBoxImport.getRandomString("0123456789",16);
                String plnAcc=JimuBoxImport.getRandomString("6789",1)+JimuBoxImport.getRandomString("0123456789",16);
                String prodName=JimuBoxImport.getRandomArrayString("2701000010,1007000220,1007000180,1007000402,1007030240,1007000110,1007000120,1007000190,1007020220,1007010220");
                String usdPps=JimuBoxImport.getRandomArrayString("个人借款,个人消费，现金业务,个人消费（购车周转）,个人经营用途,企业经营用途.企业经营流动性资金,个人消费,个人消费用途,经营:短期资金周转,消费:购物,消费:日常消费");
                String conAmount=JimuBoxImport.getRandomAmount();
                String loanAmount=JimuBoxImport.getRandomAmount();
                String loanPeriod=JimuBoxImport.getRandomArrayString("24,6,1,3,2,12,18,13,36,10,8,15,9,0");
                String repDate=JimuBoxImport.getRandomArrayString("5,13,19,1,18,21,24,3,4,9,10,11,12,14,15,16,17,20,26,6,7,25,27,28,29,2,8,22,23,30,31");
                String repType=JimuBoxImport.getRandomArrayString(",3,#6,1");
                String overdueDays=JimuBoxImport.getRandomString("9876",1);
                String firstPayOverDays=JimuBoxImport.getRandomString("12",2);
                String curPayOverDays=JimuBoxImport.getRandomString("12345",1);
                String overdueTimes=JimuBoxImport.getRandomString("123",2);


                String creditAccoutSql=JimuBoxImport.createImportMtCreditAccoutQuerySql()+";";
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
                loanBuilder.append(creditAccoutSql).append("\r\n");

                if (custidx % 999 == 0 || custidx == custNum) {
                    WirteToFile.writeToFile("c:/ausxin/testdata.sql",customerBuilder.toString());
                    WirteToFile.writeToFile("c:/ausxin/testdata.sql",contactBuilder.toString());
                    WirteToFile.writeToFile("c:/ausxin/testdata.sql",loanBuilder.toString());
                    customerBuilder=new StringBuilder();
                    loanBuilder=new StringBuilder();
                    contactBuilder=new StringBuilder();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
