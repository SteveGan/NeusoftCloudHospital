package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.ArrangementMapper;
import com.neuedu.hospitalbackend.model.dao.ArrangementRuleMapper;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.po.Arrangement;
import com.neuedu.hospitalbackend.model.po.ArrangementRule;
import com.neuedu.hospitalbackend.model.vo.ArrangementParam;
import com.neuedu.hospitalbackend.model.vo.ArrangementRuleParam;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.ArrangementManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.*;

@Service
public class ArrangementManagementServiceImpl implements ArrangementManagementService {

    @Resource
    ArrangementRuleMapper arrangementRuleMapper;
    @Resource
    ArrangementMapper arrangementMapper;
    @Resource
    RoleMapper roleMapper;


    /**
     * 设置排班规则
     * @param arrangementRuleParam VO:排班规则
     */
    @Override
    public CommonResult insertArrangementRule(ArrangementRuleParam arrangementRuleParam){
        int count = 0;
        Integer adminId = arrangementRuleParam.getAdminId();
        Integer departmentId = arrangementRuleParam.getDepartmentId();
        Integer id = arrangementRuleMapper.getLastId();
        if(id == null)
            id = 0;
        id = id + 1;

        List<ArrangementRule> arrangementRuleList = arrangementRuleParam.getArrangementRules();
        for(ArrangementRule arrangementRule : arrangementRuleList){
           arrangementRule.setId(id);
           arrangementRule.setAdminId(adminId);
           arrangementRule.setDepartmentId(departmentId);
           arrangementRule.setIsValid(true);//默认为有效
           Integer roleId = arrangementRule.getRoleId();
           Integer titleId = roleMapper.getTitleByRoleId(roleId);
           Byte levelId;
           Byte maxAppointment;
           arrangementRule.setTitleId(titleId); //职称
           if(titleId == 1 || titleId == 2) {//主任医师、副主任医师
               levelId = 2;
               maxAppointment = 20;
           }
           else if(titleId == 3 || titleId == 4) {//主治医师、住院医师
               levelId = 1;
               maxAppointment = 50;
           }
           else
               return CommonResult.fail(ResultCode.E_801);
           arrangementRule.setRegistrationLevelId(levelId); //1.普通 2.专家 3.急诊
           arrangementRule.setMaxAppointment(maxAppointment);//看诊人数上限
           // 插入数据库
           count = arrangementRuleMapper.insertSelective(arrangementRule);
           if(count <= 0)
               return CommonResult.fail(ResultCode.E_802);
        }


        return CommonResult.success(count);
    }


    /**
     * 生成排班规则
     * @param arrangementParam
     */
    public CommonResult insertArrangement(ArrangementParam arrangementParam){
        Date startDate = Date.valueOf(arrangementParam.getStartDate());
        Date endDate = Date.valueOf(arrangementParam.getEndDate());
        Integer departmentId = arrangementParam.getDepartmentId();
        Integer arrangementRuleId = arrangementParam.getId();//排班规则id

        //对时间范围内每一天排班
        Date today = startDate;
        while(today.compareTo(endDate) <= 0){
            //生成一天排班
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if(200 != saveArrangement(today, week, departmentId, arrangementRuleId).getCode())
                return CommonResult.fail(ResultCode.E_800);
            // 下一天
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            java.util.Date tomorrow = calendar.getTime();
            today = new Date(tomorrow.getTime());
        }
        return CommonResult.success("OK");
    }


    /**
     * 生成一天排班
     */
    public CommonResult saveArrangement(Date today, int week, Integer departmentId, Integer arrangementRuleId){
        int count = 0;

        //可用的排班规则信息
        List<HashMap> arrangementRules = arrangementRuleMapper.getValidById(arrangementRuleId);

        //生成一个人排班
        for(HashMap arrangementRule : arrangementRules) {
            Byte timeSlot = 1;
            String weekDate = "";
            switch (week) {
                case 1:
                    weekDate = "mon";
                    break;
                case 2:
                    weekDate = "tue";
                    break;
                case 3:
                    weekDate = "wed";
                    break;
                case 4:
                    weekDate = "thu";
                    break;
                case 5:
                    weekDate = "fri";
                    break;
                case 6:
                    weekDate = "sat";
                    break;
                case 7:
                    weekDate = "sun";
                    break;
            }

            Boolean am = (Boolean) arrangementRule.get(weekDate + "Am");
            Boolean pm = (Boolean) arrangementRule.get(weekDate + "Pm");

            //1.上午 2.下午 3.全天 0.无
            if(am == true || pm == false){
                timeSlot = 1;
            }
            else if(am == false || pm == true){
                timeSlot = 2;
            }
            else if (am == true && pm == true){
                timeSlot = 3;
            }

            //从排班规则表获取信息
            Integer roleId = (Integer)arrangementRule.get("roleId");
            String roleName = roleMapper.getUserNameByRoleId(roleId);
            Integer registrationLevelIdInt = (Integer)arrangementRule.get("registrationLevelId");
            Integer maxAppointmentInt = (Integer)arrangementRule.get("maxAppointment");
            Byte registrationLevelId = registrationLevelIdInt.byteValue();
            Byte maxAppointment = maxAppointmentInt.byteValue();

            if(timeSlot != 0) {
                //插入数据库
                Arrangement arrangement = new Arrangement();
                arrangement.setAppointmentDate(today);
                arrangement.setDepartmentId(departmentId);
                arrangement.setRoleId(roleId);//角色id
                arrangement.setUserName(roleName);//角色姓名
                arrangement.setRegistrationLevelId(registrationLevelId);//挂号级别
                arrangement.setIsValid(true);//默认有效
                arrangement.setTimeSlot(timeSlot);//午别
                arrangement.setMaxAppointment(maxAppointment);//看诊限额
                arrangement.setAppointmentLeft(maxAppointment);//剩余看诊限额
                count = arrangementMapper.insertSelective(arrangement);
                if(count <= 0)
                    return CommonResult.fail(ResultCode.E_800);
            }
        }
        return CommonResult.success(count);
    }


    /**
     * 查看某科室排班规则
     * @param departmentId 科室id
     */
    public CommonResult listArrangementRules(Integer departmentId){
        JSONObject returnJson = new JSONObject();

        List<HashMap> arrangementRuleList = arrangementRuleMapper.listArrangementRulesByDepartmentId(departmentId);

        HashMap<Integer, List<HashMap>> arrangementRules = new HashMap<>();// <规则id， 内容>
        for(HashMap arrangementRule : arrangementRuleList){
            System.out.println(arrangementRule);
            Long idLong = (Long)arrangementRule.get("id");
            Integer id = new Integer(String.valueOf(idLong));
            List<HashMap> info;
            if(!arrangementRules.containsKey(id))
                info = new ArrayList<>();
            else
                info = arrangementRules.get(id);
            arrangementRule.remove("id");
            arrangementRule.remove("departmentId");
            info.add(arrangementRule);
            arrangementRules.put(id, info);
        }
        //JSON格式
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<Integer, List<HashMap>> entry: arrangementRules.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ruleId", entry.getKey());
            jsonObject = formatJson(jsonObject, entry.getValue());
            jsonArray.add(jsonObject);
        }
        returnJson.put("arrangementRules", jsonArray);
        return CommonResult.success(returnJson);
    }

    /**
     * List转成JSON格式
     */
    public JSONObject formatJson(JSONObject jsonObject, List<HashMap> arrangementRule){
        JSONArray personalRulesArray = new JSONArray();
        //规则下每个role
        //转换成String
        //14位 每一位表示一个时间段 1.安排 0.不安排
        for(HashMap personalRule : arrangementRule){
            String timeStr = "";
            if((Boolean)personalRule.get("monAm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("monAm");
            if((Boolean)personalRule.get("monPm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("monPm");
            if((Boolean)personalRule.get("tueAm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("tueAm");
            if((Boolean)personalRule.get("tuePm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("tuePm");
            if((Boolean)personalRule.get("wedAm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("wedAm");
            if((Boolean)personalRule.get("wedPm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("wedPm");
            if((Boolean)personalRule.get("thuAm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("thuAm");
            if((Boolean)personalRule.get("thuPm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("thuPm");
            if((Boolean)personalRule.get("friAm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("friAm");
            if((Boolean)personalRule.get("friPm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("friPm");
            if((Boolean)personalRule.get("satAm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("satAm");
            if((Boolean)personalRule.get("satPm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("satPm");
            if((Boolean)personalRule.get("sunAm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("sunAm");
            if((Boolean)personalRule.get("sunPm") == true)
                timeStr = timeStr + 1;
            else
                timeStr = timeStr + 0;
            personalRule.remove("sunPm");
            personalRule.put("ruleTime", timeStr);
            personalRulesArray.add(personalRule);
        }

        jsonObject.put("arrangementRule", personalRulesArray);
        return jsonObject;
    }


    /**
     * 查看某科室排班结果信息
     * @param startDate
     * @param endDate
     * @param departmentId
     */
    @Override
    public CommonResult listArrangements(Date startDate, Date endDate, Integer departmentId){
       JSONObject returnJson = new JSONObject();
        List<Arrangement> arrangements = arrangementMapper.listByDepartmentIdAndDate(startDate, endDate, departmentId);
        returnJson.put("arrangements", arrangements);
        return null;

    }


}
