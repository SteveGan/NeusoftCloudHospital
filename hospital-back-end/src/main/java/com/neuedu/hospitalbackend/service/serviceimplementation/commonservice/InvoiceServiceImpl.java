package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.InvoiceMapper;
import com.neuedu.hospitalbackend.model.po.Invoice;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_713;

/**
 * @author Polaris
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Override
    public synchronized CommonResult getNextInvoiceCode() {
        String nextInvoiceCode = invoiceMapper.getAvailableInvoiceCode();
        if(nextInvoiceCode == null)
            return CommonResult.fail(E_713);
        invoiceMapper.updateInvoiceStatusById((byte)2, nextInvoiceCode);
        System.out.println("[INFO]正在使用发票号: " + nextInvoiceCode);
        return CommonResult.success(nextInvoiceCode);
    }

    @Override
    public CommonResult updateStatus(Byte status, String invoiceCode){
        int count = invoiceMapper.updateInvoiceStatusById(status, invoiceCode);
        if (count > 0)
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }

    @Override
    public CommonResult insertInvoices(String beginInvoiceCode, String endInvoiceCode){
        BigDecimal begin = new BigDecimal(beginInvoiceCode);
        BigDecimal end = new BigDecimal(endInvoiceCode);
        int count = 0;
        while(begin.compareTo(end) != 1){
            count += invoiceMapper.insert(begin.toString());
            begin = begin.add(BigDecimal.valueOf(1));
        }
        if(count > 0)
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }

    @Override
    public CommonResult getInvoiceInfo() {
        HashMap<Integer, String> statusMap = new HashMap<>();
        JSONObject result = new JSONObject();
        statusMap.put(1, "未使用");
        statusMap.put(2, "使用中");
        statusMap.put(3, "已使用");
        List<HashMap> temp = invoiceMapper.getInvoiceInfo();
        for (HashMap map : temp) {
            map.put("statusName", statusMap.get(map.get("status")));
            result.put((String) map.get("statusName"), map.get("count"));
        }
        return CommonResult.success(result);
    }

    @Override
    public CommonResult list() {
        HashMap<Byte, String> statusMap = new HashMap<>();

        JSONArray result = new JSONArray();
        statusMap.put((byte) 1, "未使用");
        statusMap.put((byte) 2, "使用中");
        statusMap.put((byte) 3, "已使用");
        List<Invoice> temp = invoiceMapper.list();
        for (Invoice item: temp) {
            JSONObject res = new JSONObject();
            res.put("id", item.getId());
            res.put("status", statusMap.get(item.getStatus()));
            result.add(res);
        }

        return CommonResult.success(result);
    }
}
