package com.ausxin.web;


import com.ausxin.service.CustomerService;
import com.ausxin.util.AusxinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import  com.ausxin.domain.PageInfo;

@Controller("/customer.spr")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

	
    @RequestMapping(params = "method=toCustomer")
    public String toCustomer() {
        return "customer";
    }

    @RequestMapping(params = "method=loadCustomer")
    public void loadCustomer(HttpServletRequest request,
                               HttpServletResponse response) throws UnsupportedEncodingException, NumberFormatException, ParseException {
        String collectionUnitId = request.getParameter("collectionUnitId");
        String customerName = request.getParameter("customerName");
        String customerCode = request.getParameter("customerCode");
        String segmentName = request.getParameter("segmentName");
		PageInfo page = new PageInfo();
		page.setCurrentPage(Integer.parseInt(request.getParameter("curr")
				.toString()));// 当前页
		page.setPageSize(Integer.parseInt(request.getParameter("len")
				.toString()));// 页面展示的总条数
		page.setRowStart((page.getCurrentPage() - 1) * page.getPageSize());
		page.setRowEnd(page.getCurrentPage() * page.getPageSize());
        AusxinUtil.outputXML(response, AusxinUtil.parsListStringListToXML(page,
                customerService.getCustomer(collectionUnitId, customerName, customerCode,segmentName, page), true,0,false,true,false,null),"UTF-8");
    }

    @RequestMapping(params="method=showQueryTree")
    public void showQueryTree(HttpServletRequest request,HttpServletResponse response)
    {
        try {
            String id = request.getParameter("id");
            if (id == null) {
                AusxinUtil.outputTreeXml(request, response, customerService.setCustomerTreeRootData());
            } else {
                if (id.contains("root")) {//动态加载根节点下的子节点
                    AusxinUtil.outputTreeXml(request, response,customerService.setRouteTreeFirstData(id));
                }else if(id.startsWith("fi_")){
                    AusxinUtil.outputTreeXml(request,response,customerService.setRouteTreeSecondData(id));
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
