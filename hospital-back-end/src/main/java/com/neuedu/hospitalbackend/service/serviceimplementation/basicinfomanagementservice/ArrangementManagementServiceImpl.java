package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.po.Arrangement;
import com.neuedu.hospitalbackend.model.po.ArrangementRule;
import com.neuedu.hospitalbackend.model.po.Constant;
import com.neuedu.hospitalbackend.model.vo.ArrangementConflictParam;
import com.neuedu.hospitalbackend.model.vo.ArrangementParam;
import com.neuedu.hospitalbackend.model.vo.ArrangementRuleParam;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.ArrangementManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ConstantMap;
import com.neuedu.hospitalbackend.util.ResultCode;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ArrangementManagementServiceImpl implements ArrangementManagementService {

    @Resource
    ArrangementRuleMapper arrangementRuleMapper;
    @Resource
    DepartmentMapper departmentMapper;
    @Resource
    ArrangementMapper arrangementMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserMapper userMapper;


    private List<Arrangement> toInsertArrangements;


    /**
     * 设置排班规则
     * @param arrangementRuleParam VO:排班规则
     */
    @Override
    public CommonResult insertArrangementRule(ArrangementRuleParam arrangementRuleParam){
        int count = 0;
        Integer adminId = arrangementRuleParam.getAdminId();
        Integer departmentId = arrangementRuleParam.getDepartmentId();
        String ruleName = arrangementRuleParam.getRuleName();
        //排班规则名称是否重复
        if(0 != arrangementRuleMapper.listByRuleName(ruleName).size()){
            return CommonResult.fail(ResultCode.E_806); //名称已存在
        }
        //参数检验
        List<ArrangementRule> arrangementRuleList = arrangementRuleParam.getArrangementRules();
        if(0 == arrangementRuleList.size())
            return CommonResult.fail(ResultCode.E_801);

        //排班规则编号
        Integer id = arrangementRuleMapper.getLastId();
        if(id == null)
            id = 0;
        id = id + 1;

        for(ArrangementRule arrangementRule : arrangementRuleList){
           arrangementRule.setId(id);
           arrangementRule.setRuleName(ruleName);
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
     * 查看某科室排班规则
     * @param departmentId 科室id
     */
    @Override
    public CommonResult listArrangementRules(Integer departmentId){
        JSONObject returnJson = new JSONObject();

        List<HashMap> arrangementRuleList = arrangementRuleMapper.listArrangementRulesByDepartmentId(departmentId);
        // JSON格式 v2.0
//        returnJson = formatJson(returnJson, arrangementRuleList);
//         JSON格式 v1.0
        HashMap<Integer, List<HashMap>> arrangementRules = new HashMap<>();// <规则id， 内容>
        for(HashMap arrangementRule : arrangementRuleList){
            Long idLong = (Long)arrangementRule.get("id");
            Integer id = new Integer(String.valueOf(idLong));
            List<HashMap> info;
            if(!arrangementRules.containsKey(id))
                info = new ArrayList<>();
            else
                info = arrangementRules.get(id);
            arrangementRule.remove("id");
            arrangementRule.remove("departmentId");
            Byte levelId = Byte.valueOf(String.valueOf(arrangementRule.get("registrationLevelId")));
            arrangementRule.put("registrationLevel", ConstantMap.convert("挂号级别", levelId));
            arrangementRule.remove("registrationLevelId");
            Byte positionId = Byte.valueOf(String.valueOf(arrangementRule.get("titleId")));
            arrangementRule.put("title", ConstantMap.convert("职称", positionId));
            arrangementRule.remove("titleId");
            arrangementRule.put("isValid", getStatusStr((Boolean)arrangementRule.get("isValid")));

            info.add(arrangementRule);
            arrangementRules.put(id, info);
        }
        //JSON格式
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<Integer, List<HashMap>> entry: arrangementRules.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ruleId", entry.getKey());
            jsonObject.put("ruleName", entry.getValue().get(0).get("ruleName"));
            Integer adminId = (Integer)entry.getValue().get(0).get("adminId");
            jsonObject.put("adminName", roleMapper.getUserNameByRoleId(adminId));
            jsonObject = formatJson(jsonObject, entry.getValue());
            jsonArray.add(jsonObject);
        }
        returnJson.put("arrangementRules", jsonArray);
        return CommonResult.success(returnJson);
    }


    /**
     * 生成排班结果
     * @param arrangementParam
     */
    @Override
    public CommonResult insertArrangement(ArrangementParam arrangementParam){
        Boolean toInsert = true;
        toInsertArrangements = new ArrayList<>();//待插入数据库排班结果
        Date startDate = Date.valueOf(arrangementParam.getStartDate());
        Date endDate = Date.valueOf(arrangementParam.getEndDate());
        Integer departmentId = arrangementParam.getDepartmentId();
        Integer arrangementRuleId = arrangementParam.getId();//排班规则id

        //参数检验
        if(startDate.compareTo(endDate) > 0)
            return CommonResult.fail(ResultCode.E_809);
        if(arrangementRuleId == null)
            return CommonResult.fail(ResultCode.E_801);//排班规则为空
        //已存在排班结果
        if(0!= arrangementMapper.listByDepartmentIdAndDatePeriod(startDate, endDate, departmentId).size()){
            return CommonResult.fail(ResultCode.E_810);
        }

        //时间段内现有所有排班有效信息
        List<Arrangement> arrangements = arrangementMapper.listByUserIdAndDatePeriod(startDate, endDate);

        //对时间范围内每一天排班
        Date today = startDate;
        List<ArrangementConflictParam> conflict = new ArrayList<>();
        while(today.compareTo(endDate) <= 0){
            //生成一天排班
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            conflict = saveArrangement(today, week, departmentId, arrangementRuleId, arrangements, conflict, toInsert,
                                    toInsertArrangements);
            if(0 != conflict.size())
                toInsert = false;
            // 下一天
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            java.util.Date tomorrow = calendar.getTime();
            today = new Date(tomorrow.getTime());
        }
        // 确定为可插入，最后一起插入
        if(toInsert.equals(true)){
            for(Arrangement arrangement : toInsertArrangements)
                arrangementMapper.insertSelective(arrangement);
        }
        if(0 != conflict.size())
            return CommonResult.fail(ResultCode.E_808, conflict);
        else
            return CommonResult.success("OK");
    }


    /**
     * 生成一天排班
     */
    public List<ArrangementConflictParam> saveArrangement(Date today, int week, Integer departmentId,
                                                          Integer arrangementRuleId, List<Arrangement> arrangements,
                                                          List<ArrangementConflictParam> conflict, Boolean toInsert,
                                                          List<Arrangement> toInsertArrangements){
        //当日现有排班有效信息 key: roleId    value: Arrangement
        HashMap<Integer, Arrangement> arrangementsToday = new HashMap<>();
        for(Arrangement arrangement : arrangements){
            if(0 == arrangement.getAppointmentDate().compareTo(today)){
                arrangementsToday.put(arrangement.getRoleId(), arrangement);
            }
        }

        //可用的排班规则信息
        List<HashMap> arrangementRules = arrangementRuleMapper.getValidById(arrangementRuleId);

        //生成一个人排班
        for(HashMap arrangementRule : arrangementRules) {
            Byte timeSlot = 0;
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
            if(am == true && pm == false){
                timeSlot = 1;
            }
            else if(am == false && pm == true){
                timeSlot = 2;
            }
            else if (am == true && pm == true){
                timeSlot = 3;
            }
            //若与现有排班信息冲突，则生成失败，返回冲突提示
            Integer roleId = (Integer)arrangementRule.get("roleId");
            //当日该用户冲突排班信息
            Integer userId = roleMapper.getUserIdByRoleId(roleId);
            for (Map.Entry<Integer, Arrangement> entry : arrangementsToday.entrySet()) {
                Integer arrangedRoleId = entry.getKey();
                //用户user今日有其他排班
                Integer theUserId = roleMapper.getUserIdByRoleId(arrangedRoleId);
                if(userId.equals(theUserId)){
                    //是否存在冲突
                    Arrangement arrangement = entry.getValue();
                    //时间安排存在冲突
                    if((timeSlot != 0) && (timeSlot == arrangement.getTimeSlot() || timeSlot == 3 || arrangement.getTimeSlot() == 3)){
                        ArrangementConflictParam arrangementConflictParam = new ArrangementConflictParam();
                        arrangementConflictParam.setDate(today);
                        String departmentName = departmentMapper.getNameById(departmentId);
                        arrangementConflictParam.setDepartmentName(departmentName);
                        arrangementConflictParam.setUserName(arrangement.getUserName());
                        conflict.add(arrangementConflictParam);
                    }
                }
            }

            //从排班规则表获取信息
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
//                arrangementMapper.insertSelective(arrangement);
                toInsertArrangements.add(arrangement);//待插入排班结果
            }
        }
        return conflict;
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
//        JSONArray arrangementResultsArray = new JSONArray();

//        List<HashMap<String, List<HashMap>>> arrangementLogs = new ArrayList<>();

        //时间范围内的每一天排班结果
        Date today = startDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        while(today.compareTo(endDate) <= 0){
            //当日排班信息
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);

            List<HashMap> arrangementLogInfo = new ArrayList<>();
            List<HashMap> arrangements = arrangementMapper.listByDepartmentIdAndDatePeriod(today, today, departmentId);
            for(HashMap arrangement : arrangements){
                arrangement.remove("appointmentDate");
                arrangement.put("timeSlot", ConstantMap.convert("看诊时间段",
                        Byte.valueOf(String.valueOf(arrangement.get("timeSlot")))));
                arrangement.put("registrationLevel", ConstantMap.convert("挂号级别",
                        Byte.valueOf(String.valueOf(arrangement.get("registrationLevelId")))));
                arrangement.put("isValid", getStatusStr((Boolean)arrangement.get("isValid")));
                arrangementLogInfo.add(arrangement);
            }
            returnJson.put(formatter.format(today), arrangementLogInfo);

            // 下一天
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            java.util.Date tomorrow = calendar.getTime();
            today = new Date(tomorrow.getTime());
        }

        return CommonResult.success(returnJson);


//        JSONArray arrangementResultsArray = new JSONArray();
//
//        JSONArray arrangementsArray = new JSONArray();
//        List<HashMap> arrangements = arrangementMapper.listByDepartmentIdAndDatePeriod(startDate, endDate, departmentId);
//
//        for(HashMap arrangement : arrangements) {
//            JSONObject arrangementResultJson = new JSONObject();
//
//            Date appointmentDate = (Date)arrangement.get("appointmentDate");
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            arrangement.put("appointmentDate", formatter.format(appointmentDate));
//            arrangement.put("timeSlot", ConstantMap.convert("看诊时间段",
//                    Byte.valueOf(String.valueOf(arrangement.get("timeSlot")))));
//            arrangement.put("registrationLevel", ConstantMap.convert("挂号级别",
//                    Byte.valueOf(String.valueOf(arrangement.get("registrationLevelId")))));
//            arrangement.put("isValid", getStatusStr((Boolean)arrangement.get("isValid")));
//
//            arrangementsArray.add(arrangement);
//        }
//
//        returnJson.put("arrangementResults", arrangementResultsArray);
//        return CommonResult.success(returnJson);
    }

    public String getStatusStr(Boolean isValid){
        if(isValid.equals(true))
            return "有效";
        else
            return "无效";
    }




    /**
     * 修改排班结果v1.0
     * 批量修改
     * @param arrangementParam
     */
    @Override
    public CommonResult modifyArrangements(ArrangementParam arrangementParam){
        int count = 0;
        List<Arrangement> arrangements = arrangementParam.getArrangements();
        Date startDate = Date.valueOf(arrangementParam.getStartDate());
        Date endDate = Date.valueOf(arrangementParam.getEndDate());
        Integer departmentId = arrangementParam.getDepartmentId();

        //参数检验
        if(startDate.compareTo(endDate) > 0)
            return CommonResult.fail(ResultCode.E_809);
        if(departmentId == null)
            return CommonResult.fail(ResultCode.E_801);

        //删除时间段内的排班结果
        arrangementMapper.deleteByDepartmentIdAndDatePeriod(startDate, endDate, departmentId);
        //新增修改后的排班结果
        for(Arrangement arrangement : arrangements){
            arrangement.setDepartmentId(departmentId);
            count += arrangementMapper.insertSelective(arrangement);
        }

        return CommonResult.success(count);

    }

    /**
     * 修改排班结果v2.0
     * 修改午别
     * @param arrangementId,timeSlot
     */
    @Override
    public CommonResult modifyArrangement(Integer arrangementId, Byte timeSlot){

        arrangementMapper.updateTimeSlot(arrangementId, timeSlot);

        return null;
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



}
