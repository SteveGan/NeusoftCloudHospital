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

Q1. 对于挂号操作来说，是否应该是先缴费才会在数据库registration表中插入记录 



- 检查患者是否已在本系统中 

  - [ ] 从前端得到患者的身份证号

  - [ ] select patient_id from **patient** where id_card = #{id_card}

    - Field/Object

    如返回值不为空，传patient_id到前端

    如返回值为空

    - [ ] insert into **patient** values (值1, 值2,....) 

- 从前端得到姓名(必填）、性别(必填)、身份证号、出生日期（通过身份证号得到）、年龄、结算类别（必填）、家庭住址、挂号级别（必填）、挂号科室（必填）、<u>看诊医生（必填）</u>、是否要病历本

  - [ ] 对看诊医生挂号限额的判断

  - [ ] 根据看诊医生和挂号级别，是否需要病历本，算出应收金额

- 向缴费表中添加新的缴费记录  --已缴费
  - [ ] insert into **transaction_log** (...type, status...) values (...挂号费, 2...)

- 向挂号表中添加新的挂号记录 --默认正常
  - [ ] insert into **registration** values (值1, 值2,....) 

- 向病历表中添加新的病历记录 --默认待诊
  - [ ] insert into **case** (registration_id, patient_id, user_id) values (值1, 值2,....)  

Note: 1.病历号自动递增

​	   2.通过指定挂号科室，返回看科室所有当天看诊医生列表 （排班表）

​	   3.看诊医生：若患者未指定，应由收费人员从该科室当前空闲看诊医生中随意选择一个

​				  若患者有指定，应先在某个与**医生及其限号人数有关的表**中查询他当前的余号数量

​						若该医生已满，则询问患者更换医生/由收费人员指定

### 2.1.2 清空

### 2.1.3 更新发票号

## 2.2 退号

- 通过患者病历号，显示患者挂号信息

  select * from **registration** where registration_id = #{registration_id} 

  - Object

- 通过患者病历号，确定患者挂号状态是否是待诊状态

  - [ ] select * from **case** where registration_id = #{registration_id} and status = 1 

    - Field/Object

    如返回值不为空，可以执行退号操作，此操作可以得到原缴费记录的流水号和金额

    - [ ] select * from **transaction_log** where registration_id = #{registration_id} and type = “挂号费”
    - Object (id, invoice_code, total_money)

    如返回值为空，不可以执行退号操作

- 向缴费表中添加新的缴费记录  --冲正，并且返回该记录的发票号

  - [ ] insert into **transaction_log** (...type, total_money, status...) values (..., 挂号费, -#{total_money}, 4...)  
    - Field (invoice_code)

- 将原有缴费记录状态更改为已退费 --已退费

  - [ ] update **transaction_log** set status = 3 where id = #{id}  

- 向异常表中添加新的记录

  - [ ] insert into **transaction_exception_log** (original_invoice_code, new_invoice_code, reverse_invoice_code, user_id, reason) values (#{original_invoice_code}, null, #{reverse_invoice_code}, #{user_id}, ‘挂号退费’)

- 在挂号表中更新该病历号的状态 --已退号

  - [ ] update **registration** set status = 0 where registration_id = #{registration_id} 

- 从门诊病历首页移除该病历号，删除医生端的病历记录

  - [ ] delete from **case** where registration_id = #{registration_id}  

## 2.3 收费

Pre-condition: 医生开完检查/检验/处置/处方后，会首先加入一条相关的待缴费记录到 **transaction_log**

Q1. 从实际情况来看，transaction_log存的应该就是具体的每个条目的价钱

Q2. transaction_log需不需要录入结算方式？



- 通过患者病历号，显示患者挂号信息
  - [ ] select *  from **registration** where registration_id = #{registration_id} 
    - Object
- 查询未缴费状态的收费项目
  - [ ] select * from **transaction_log** where registration_id = #{registration_id} and status = 1 
    - List
- 更新收费项目的缴费状态 --已缴费
  - [ ] update **transaction_log** set status = 2 where registration_id = #{registration_id} and collection_id = #{collection_id} and project_id = #{project_id} and item_id = #{item_id}

## 2.4 退费

情景假设：一个病历号下有分别有检查单A（内含3个project)，检验单B\C （每张各内含3个project），处置单D （内含3个project)，处方E\F\G （每张各内含5个project）

假设想退掉 检验单B中的项目1 和 处方F中的药品2总量的3/5



Note: 1. 只有已开立的项目才会在**transaction_log**表中有对应记录

​	   2. 只有处方类项目允许退掉部分数量

​	   3. 在同一张检验单/检查单/处置单/处方上的项目如果有一个退了，都需要给患者新的发票

​           4. 属于同一张检查/检验/处置/处方单上的项目对应流水号/发票号相同



- 通过患者病历号，显示患者信息

  - [ ] select *  from **registration** where registration_id = #{registration_id} 
    - Object

- 通过患者病历号，显示所有已开立的项目及当前状态 （这里的状态指的是未缴费/已缴费/已退/冲正）

  - [ ] select * from **transaction_log**  where registration_id = #{registration_id} and type <> '挂号费'

    - List (包含流水号、第一层级id、第二层级id、第三层级id)

    只有当前状态为已缴费的项目可以执行下一步操作

- 1.对于检查/检验/处置项目来说，只有缴费了但未登记的项目才可以退费

  - 1.1 在检查/检验/处置表中查找想要退费的项目状态

    - [ ] select * from  **examination/inspection/treatment** where id = #{collection_id} and project_id = #{project_id} 

      - Object (status, collection_id, project_id)

      只有处当前状态为开立的项目可以执行下一步操作

  - 1.2 在检查/检验/处置表中更改想要退费的项目状态 （即检验单B项目1）--已作废

    - [ ] update  **examination/inspection/treatment**  set status = 3 where id = #{collection_id} and project_id = #{project_id} 

  - 1.3 更改 退费的项目所在第一层级的 相关缴费记录状态（即检验单B中的所有项目）--已退费

    - [ ] update **transaction_log** set status = 3 where id = #{id} and collection_id = #{collection_id} 

  - 1.4 向缴费表中添加 退费的项目所在第一层级的 冲正记录（即检验单B中的所有项目） --冲正，并且返回该记录的发票号

    - [ ] insert into **transaction_log** (...status...) values (...4...)  
      - Field (invoice_code)

  - 1.5 新增与 退费的项目所在相同第一层级的未退费项目 的缴费记录（即检验单B中的项目1、3）--已缴费

    - [ ] insert into **transaction_log** values (值1, 值2,....) 

  - 1.6 向异常表中添加新的记录

    - [ ] insert into **transaction_exception_log** (original_invoice_code, new_invoice_code, reverse_invoice_code, user_id, reason) values (#{original_invoice_code}, #{new_invoice_code}, #{reverse_invoice_code}, #{user_id}, ‘退费’)

    注：如果退掉检验单B中的所有项目，则不需要1.5操作，且1.6中 new_invoice_code = null

- 2.对于处方来说，只要是已缴费的药品都可以退费

  - 2.1 在处方表中查询想要退费的药品状态 （开立/已取药）

    - [ ] select * from **recipe** where id = #{collection_id} and medicine_code = #{project_id} 

      只有当前状态为开立或已退药才可以执行下一步操作

      - Object (return_amount)

  - 2.2 在处方表中更改想要退费的药品状态 （即处方F药品2）--已作废
    - [ ] update  **recipe** set status = 3 where id = #{collection_id} and medicine_code = #{project_id} 
  - 2.3 更改 退费的药品所在处方的 相关缴费记录状态（即处方F中的所有药品）--已退费
    - [ ] update **transaction_log** set status = 3 where id = #{id} and collection_id = #{collection_id} 

  - 2.4 向缴费表中添加 退费的药品所在处方的 冲正记录（即处方F中的所有药品） --冲正，并且返回该记录的发票号

    - [ ] insert into **transaction_log** (...status...) values (...4...)  
      - Field (invoice_code)

  - 2.5 新增与 退费的药品所在相同处方的的未退费药品 的缴费记录（即处方F中的药1,2(未退部分）, 3,4,5）--已缴费

    - [ ] insert into **transaction_log** values (值1, 值2,....) 

  - 2.6 向异常表中添加新的记录

    - [ ] insert into **transaction_exception_log** (original_invoice_code, new_invoice_code, reverse_invoice_code, user_id, reason) values (#{original_invoice_code}, #{new_invoice_code}, #{reverse_invoice_code}, #{user_id}, ‘退费’)

    注：如果退掉处方F中的所有药品，则不需要2.5操作，且2.6中 new_invoice_code = null

## 2.5 发票补打

Note: 由于打印机没有正常走纸，用原有发票进行补打

- 根据原发票号，查询缴费信息
  - [ ] select * from **transaction_log** where invoice_code = #{invoice_code}
    - List

## 2.6 发票重打

Note: 由于发票上的信息可能有误或者模糊不清，用新的发票号进行重打

Q1.重打发票时，原发票要收回并作废，流水号用重新分配吗？

- 根据原发票号，查询缴费信息
  - [ ] select * from **transaction_log** where invoice_code = #{invoice_code}
    - List

- 分配新的发票号，但流水号与原有的相同
  - [ ] insert into  **transaction_log** values (值1, 值2,....) 
- 更改原有的发票号对应的缴费状态
  - [ ] update  **transaction_log**  set status = 5 where id = #{id}
- 在异常表里进行记录
  - [ ] insert into  **transaction_exception_log **(original_invoice_code, new_invoice_code, user_id, reason) values (#{original_invoice_code}, #{new_invoice_code}, #{user_id}, "重打") 

## 2.7 患者费用查询

## 2.8 收费员日结

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

### 4.1.2 执行确认

### 4.1.3 取消执行

### 4.1.4 填写结果

## 4.2 患者检验
### 4.2.1 患者查询

### 4.2.2 执行确认

### 4.2.3 取消执行

### 4.2.4 填写结果

## 4.3 患者处置
### 4.3.1 患者查询

### 4.3.2 执行确认

### 4.3.3 取消执行

### 4.3.4 填写结果

## 4.4 医技管理（基础信息管理）
### 4.4.1 查询

### 4.4.2 新增

### 4.4.3 修改

### 4.4.4 删除

### 4.4.5 导入

### 4.4.6 导出

# 5. 门诊药房工作站
## 5.1 门诊发药

### 5.1.1 查询

根据患者的病历号，查询相应的已缴费尚未发放的药品信息

- [ ] select * from **transaction_log** T, **recipe** R where T.collection_id = R.id and T.status = 2 and R.status = 2
  - List 

### 5.1.2 发药

更新对应药品状态

- [ ] update recipe set status = 4 where id = #{id} and medicine_code = #{medicine_code}

## 5.2 门诊退药

### 5.2.1 查询

根据患者的病历号，查询想要退药的药品信息

- [ ] select * from **recipe** where case_id = #{case_id} and medicine_code = #{medicine_code}

  - Object 

  如果药品状态为已取药，则可执行下一步

### 5.2.2 退药

输入退药的数量 return_amount		

- 更新对应的recipe记录
  - [ ] update **recipe** set status = 5,  return_amount = #{return_amount} where case_id = #{case_id} and medicine_code = #{medicine_code}
- 更新对应药品的库存
  - [ ] update **inventory** set remaining_amount = remaining_amount + #{return_amount} where medicine_code = #{medicine_code}

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