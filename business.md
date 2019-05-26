# 基于SQL的业务流描述文档
# 1. 基础信息维护
## 1.1 常数类别管理
## 1.2 科室管理
### 1.2.1 查询科室byID
```sql
select id, classification, code, name, type from department where id = #{id}
```
### 1.2.2 新增科室byID
```sql
insert into department (classification, code, name, type) values (#{classification}, #{code}, #{name}, #{type})
```
### 1.2.3 修改科室byAll
```sql
update emp
    <set>
        <if test="classification != null">
            classification = #{classification},
        </if>
        <if test="code != null">
            code = #{code},
        </if>
        <if test="name != null">
            name = #{name},
        </if>
        <if test="type != null">
            type = #{type},
        </if>
    </set>
where id = #{id}
```
### 1.2.4 删除科室byID
```sql
delete from department where id = #{id}
```
### 1.2.5 导入科室
java读取Excel实现

## 1.3 用户管理
### 1.3.1 查询用户
```sql
select * from user join role on user.id = role.user_id join where id = #{id}
```
### 1.3.2 新增用户
```sql
insert into department (class, code, name, type) values (#{class}, #{code}, #{name}, #{type})
```
### 1.3.3 修改用户
```sql
update emp
    <set>
        <if test="class != null">
            class = #{class},
        </if>
        <if test="code != null">
            code = #{code},
        </if>
        <if test="name != null">
            name = #{name},
        </if>
        <if test="type != null">
            type = #{type},
        </if>
    </set>
where id = #{id}
```
### 1.3.4 删除用户
```sql
delete from department where id = #{id}
```

## 1.4 挂号级别管理
### 1.4.1 查询挂号级别

### 1.4.2 新增挂号级别

### 1.4.3 修改挂号级别

### 1.4.4 删除挂号级别

## 1.5 诊断目录管理
### 1.5.1 查询疾病byID
```sql
select id, icd_code, code, name, type from modern_disease where id = #{id}
```
### 1.5.2 新增疾病byID
```sql
insert into modern_disease (icd_code, code, name, type) values (#{icd_code}, #{code}, #{name}, #{type})
```
### 1.5.3 修改疾病byAll
```sql
update emp
    <set>
        <if test="icd_code != null">
            icd_code = #{icd_code},
        </if>
        <if test="code != null">
            code = #{code},
        </if>
        <if test="name != null">
            name = #{name},
        </if>
        <if test="type != null">
            type = #{type},
        </if>
    </set>
where id = #{id}
```
### 1.5.4 删除疾病byID
```sql
delete from modern_disease where id = #{id}
```

## 1.6 非药品收费项目管理
### 1.6.1 查询非药品收费项目
```sql
select id, code, name, specification, unit_price, cost_type, executive_department from non_medicine where id = #{id}
```
### 1.6.2 新增非药品收费项目
```sql
insert into non_medicine (code, name, specification, unit_price, cost_type, executive_department) values (#{code}, #{name}, #{specification}, #{unit_price}, #{cost_type}, #{executive_department})
```
### 1.6.3 修改非药品收费项目
```sql
update non_medicine
    <set>
        <if test="code != null">
            code = #{code},
        </if>
        <if test="name != null">
            name = #{name},
        </if>
        <if test="specification != null">
            specification = #{specification},
        </if>
        <if test="unit_price != null">
            unit_price = #{unit_price},
        </if>
        <if test="cost_type != null">
            cost_type = #{cost_type},
        </if>
        <if test="executive_department != null">
            executive_department = #{executive_department},
        </if>        
    </set>
where id = #{id}
```
### 1.6.4 删除非药品收费项目
```sql
delete from non_medicine where id = #{id}
```
### 1.6.5 导入非药品收费项目
java读取Excel实现

### 1.6.6 导出非药品收费项目
java导出Excel实现

## 1.7 医生排班管理
### 1.7.1 设置排班规则

### 1.7.2 生成排班计划

# 2. 门诊挂号收费
## 2.1 现场挂号
### 2.1.1 挂号

### 2.1.2 情空

### 2.1.3 更新发票号

## 2.2 退号

## 2.3 收费

## 2.4 退费

## 2.5 发票补打

## 2.6 发票重打

## 2.7 患者费用查询
 输入患者病历号（必输）、开始时间和结束时间选填。查询该患者的所有收费项目列表，默认按收费时间降序排序。

input：patientId, begineDate, endDate

output：患者收费项目列表 药品信息

SELECT type, item_id

FROM transaction_log t, medicine m

WHERE t.item_id = m.id

<choose>

	<when test = "begineDate != null">

			and #{begineDate} >= t.gmt_create

	<when test = "begineDate != null">

			and #{endDate} <= t.gmt_create

</choose>

ORDER BY t.gmt_create DECS

## 2.8 收费员日结
日结表daily_junction：id, casher_id, total_money, invoice_code_begin, invoice_code_end,  date

问题：属于某个casher的发票号不连续

			不像一个对账表，是一个总结表
【日结】

    收费员录入统计时间（起始时间为上次日结的截止时间），只需录入截止时间，默认为当前时间，不能录入晚于当前时间的时间。点击“日结统计”按钮，统计上次日结截止时间到本次日结之间的收费金额以及对应的发票信息。点击“结算报账”按钮，对统计时间段的收费记录，进行冻结状态。

    日结后，操作员持日结单、对应发票、以及对应金额或划卡小票到财务科报账。

   业务逻辑：查询该casher从上次日结到本次日结时间内所有未结清单

   input： casherId

   output：上一次日结日期

   SQL:

SELECT date 

FROM daily_junction

WHERE casher_id = #{casherId}



input：beginDate, endDate, casherId

output： sum总金额

SQL:

SELECT sum(total_money) 

FROM transaction

WHERE casher_id = #{casherId}

	  AND #{begin_Date} <= gmt_create AND #{endDate} >= gmt_create



input：beginDate, endDate, casherId

output：开始发票号

SQL:

SELECT invoice_code 

FROM transaction

WHERE casher_id = #{casherId}

AND #{beginDate} < gmt_create AND #{endDate} >= gmt_create

ORDER BY invoice_code ASC LIMIT 1



input：beginDate, endDate, casherId, totalMoney, invoiceCodeStart, invoiceCodeEnd

output： 最后发票号

SQL: 

SELECT TOP1 invoice_code 

FROM transaction

WHERE casher_id = #{casherId}

AND #{begin_date} <= gmt_create AND #{end_date} >= gmt_create

ORDER BY invoice_code DECS LIMIT 1



input：casherId, totalMoney, invoiceCodeBegin, invoiceCodeEnd

SQL：插入日结信息

INSERT INTO daily_junction

VALUES(null, casherId, totalMoney, invoiceCodeBegin, invoiceCodeEnd )



【日结历史查询】

    指定查询起始及终止时间。点击“查询”按钮，系统显示出指定条件范围内的日结信息。点击其中的一条日结信息，会显示其对应的信息，包括日结汇总以及其对应的发票信息。

业务逻辑：显示范围内所有日结信息，点击某一条信息看详情

input：beginDate, endDate

SQL：查询所有日结记录

SELECT id

FROM daily_junction

WHERE #{begineDate} <= gmt_create AND #{endDate} >= gmt_create

ORDER BY invoice_code DECS

# 3. 门诊医生工作站
## 3.1 门诊病历首页
### 3.1.1 患者选择

### 3.1.2 暂存病历首页

### 3.1.3 提交病历首页

### 3.1.4 清屏

### 3.1.5 存为模板

### 3.1.6 引用病历模板

### 3.1.7 常用诊断管理

### 3.1.8 查看历史病历

## 3.2 检查申请
### 3.2.1 新增项目

### 3.2.2 删除项目

### 3.2.3 开立项目

### 3.2.4 作废项目

### 3.2.5 存为组套

### 3.2.6 引用组套

### 3.2.7 查看检查结果

## 3.3 检验申请
### 3.3.1 新增项目

### 3.3.2 删除项目

### 3.3.3 开立项目

### 3.3.4 作废项目

### 3.3.5 存为组套

### 3.3.6 引用组套

### 3.3.7 查看检验结果

## 3.4 门诊确诊

## 3.5 处置申请
### 3.5.1 新增项目

### 3.5.2 删除项目

### 3.5.3 开立项目

### 3.5.4 作废项目

### 3.5.5 存为组套

### 3.5.6 引用组套

### 3.5.7 查看处置结果

## 3.6 成药处方
### 3.6.1 开立处方

### 3.6.2 删除处方

### 3.6.3 发送处方

### 3.6.4 作废处方

### 3.6.5 增药

### 3.6.6 删药

### 3.6.7 引用组套

## 3.7 草药处方
### 3.7.1 开立处方

### 3.7.2 删除处方

### 3.7.3 发送处方

### 3.7.4 作废处方

### 3.7.5 增药

### 3.7.6 删药

### 3.7.7 引用组套

## 3.8 诊毕

## 3.9 患者费用明细查询

## 3.10 医技模板管理
### 3.10.1 新增模板

### 3.10.2 修改模板

### 3.10.3 删除模板

### 3.10.4 查询模板

### 3.10.5 增加项目

## 3.11 西药处方模板管理
### 3.11.1 新增模板

### 3.11.2 修改模板

### 3.11.3 删除模板

### 3.11.4 查询模板

### 3.11.5 增加项目

## 3.12 中药处方模板管理
### 3.12.1 新增模板

### 3.12.2 修改模板

### 3.12.3 删除模板

### 3.12.4 查询模板

### 3.12.5 增加项目

# 4. 门诊医技工作站
## 4.1 患者检查
### 4.1.1 患者查询
输入患者病历号或姓名，可以查询到本科室的待诊患者，选择患者可以看到患者信息及申请的项目明细  （状态：1.暂存 2.开立 3.作废 4.已登记）

   input：patient_id 或 patient_name

   output：患者信息（患者基本信息）, 申请项目明细

   SQL:

SELECT p.patient_id, p.registration_id, p.patient_name, inspection.*, 

FROM patient p, case c, inspection i

WHERE i.case_id = c.case_id AND c.patient_id = p.patient_id

	AND c.status = 2 AND i_status = 2 <!--case状态 已诊；检查项目状态 开立-->

	 <choose>

		<when test="patientId != null">

			AND p.patient_id = #{patientId}

		</when>

		<when test="patientName != null">

			AND p.patient_name = #{patientName}

		</when>

	</choose>
建议: 在inspetcion表中存放patient_id

### 4.1.2 执行确认
 选中相应的患者，点击“执行确认”按钮，进行登记操作。注意：只有已缴费的项目，才可以进行登记

业务逻辑：上一操作后，从页面获取用户caseId，传至后端，根据caseId查询出所有目前可以登记的项目列表; 选中列表中项目开始登记，登记时后端更新项目申请信息。

input：case_id 

SQL：查询出所有可登记（已缴费&未登记）项目

SELECT collection_id, project_id

FROM transaction_log t, inspection i

WHERE t.collection_id = i.id AND t.item_id = i.project_id

	 AND t.status = 2 AND i.status = 2 AND t.case_id = #{case_id} <!--trasaction_log状态 2.已缴；检查项目状态 2.开立--> 



input：collectionId，projectId，医技id 

SQL：更新项目申请信息：状态、医技id

UPDATE inspection

SET status = 4, examinor_id = #{examinorId}<!--检查项目状态 4.已登记--> 

WHERE id = #{collectionId} AND project_id = #{projectId}  

### 4.1.3 取消执行
选中相应的患者，点击“取消执行”按钮，进行取消操作。注意：一般情况不会进行取消操作

业务逻辑：根据页面上显示该患者的所有可登记项目，点击选择取消执行的项目，取消时后端更新项目申请信息

input：医技医生id

SQL：更新项目申请信息：状态、医技id

UPDATE inspection

SET status = 3, examinor_id = #{examinorId}<!--检查项目状态 3.作废--> 

 WHERE id = #{collectionId} AND project_id = #{projectId}

### 4.1.4 填写结果
选中相应的患者和项目后，点击“结果录入”按钮，录入检查结果，如果检查项目有图片，上传检查结果图片

业务逻辑：显示所有已登记但未录入结果的项目，录入结果（即数据库更新申请项目信息）

input：caseId

output：该病历号下已登记&未录入结果的项目清单

SQL：查询需要登记结果的数据

SELECT collection_id, project_id

FROM inspection

WHERE caseId = #{caseId} AND status = 4 AND result_description != null<!--检查项目状态 4.已登记--> 



input：collectionId, projectId, resultDescription, resultPicture, advice

SQL：录入结果、图片（可选）、医技医生建议

UPDATE inspection

SET result_description = #{resultDescription}, result_picture = #{resultPicture}, advice = #{advice}

WHERE id = #{collectionId} AND project_id = #{projectId}  




## 4.2 患者检验
同上，换成表examination
### 4.2.1 患者查询

### 4.2.2 执行确认

### 4.2.3 取消执行

### 4.2.4 填写结果

## 4.3 患者处置
同上，换成表examination
但是没有【结果录入】
### 4.3.1 患者查询

### 4.3.2 执行确认

### 4.3.3 取消执行

### 4.3.4 填写结果

## 4.4 医技管理（基础信息管理）
用于医院管理员维护医院使用的医技收费项目

数据存储在non_medicine表中

### 4.4.1 查询
input：检查/检验/处置 Object   （动态查询）
SQL：SELECT语句

### 4.4.2 新增
input：检查/检验/处置 Object  （ id -> auto increase )
INSERT语句

### 4.4.3 修改
input：检查/检验/处置 Object   （动态update）
SQL：UPDATE语句

### 4.4.4 删除
DELETE语句

### 4.4.5 导入

### 4.4.6 导出

# 5. 门诊药房工作站
## 5.1 门诊发药
### 5.1.1 查询

### 5.1.2 发药

## 5.2 门诊退药
### 5.2.1 查询

### 5.2.2 退药

## 5.3 药品管理（基础信息管理）
### 5.3.1 查询

### 5.3.2 新增

### 5.3.3 修改

### 5.3.4 删除

### 5.3.5 导入

# 6. 门诊财务管理
## 6.1 费用科目管理（基础信息管理）
### 6.1.1 查询

### 6.1.2 新增

### 6.1.3 修改

### 6.1.4 删除

## 6.2 门诊日结核对

## 6.3 门诊科室工作量统计
### 6.3.1 临床科室工作量统计

### 6.3.2 医技科室工作量统计

## 6.4 门诊医生工作量统计
### 6.4.1 操作员输入统计起始和终止时间

### 6.4.2 点击查询按钮

### 6.4.3 查询看诊人次

### 6.4.4 发票数量

### 6.4.5 各分项收入及总收入情况

### 6.4.4 通过点击导出按钮

### 6.4.7 对统计结果进行导出