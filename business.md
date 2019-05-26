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