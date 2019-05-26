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
通过患者病历号或姓名快速查询患者：直接在case表中查询病历号，或者先从patient表中用患者病历号查到case_id；

```sql
-- 1. 通过患者病历号查询患者，返回case
select * from case where patient_id = #{patient_id};
-- 2. 通过患者病历号查询出预诊信息, 返回 List<diagnose>
select * from diagnose where case_id = #{caseID};

```

### 3.1.2 暂存病历首页
暂存病历：将当前病历信息存在case中， 初诊的diagnose存在diagnose表中，status设置为暂存。

```sql
--将基本信息存在case中
update case
set
  status=#{status},
  narrate=#{narrate},
  cur_disease=#{cur_disease},
  cur_treat_condition=#{curTreatConditoin},
  past_disease=#{past_disease},
  allergy=#{allergy},
  physical_condition=#{physicalCondition},
  advice=#{advice},
  attention=#{attention}
where case_id=#{caseID};

--将病历中已有的初步诊断存储在diagnose中，每次暂存操作应该包括两个步骤：
-- 1. 删除当前与该case相关联的初步诊断记录；
delete from diagnose where case_id = #{caseID} and type=#{type};
-- 2. 新添加当前case中的初步诊断记录(下面的属性非必须存在，应做相应调整，因为暂存的时候不是所有属性医生都填了)
insert into diagnose
  values(#{caseID}, #{diseaseID}, #{startTime}, #{type});

```

### 3.1.3 提交病历首页

提交病历首页：case状态变为变为已提交；

```sql
--将case的状态变为已诊
update case set status=#{status} where case_id = #{caseID};
```

### 3.1.4 清屏

清屏：数据库端也应当清除当前一存储的相关信息；

```sql
-- 1. 将数据库中case里记录的信息修改为原先的默认值
同 3.1.2

-- 2. 删除diagnose中与该病历相关的初步诊断记录, type = 初诊
delete from diagnose where case_id = #{caseID} and type=#{type};

```

### 3.1.5 存为模板
存为模版：将当前病历信息存在case_template中，初诊信息存在diagnose中，status无; **【注意：这里的操作和暂存病历有很大不同，因为template并不是在挂号的时候就创建，而是后建立的。而且模版可以有多个，病历只有一个。因此寸为模版时我们要考虑此模版是新建的（在template中新增数据）还是已经存在的（更新模版的数据）**

```sql
-- 判断模版是否存在(其实我感觉可以改成一个存储函数，但这里就简单的查一下啦,如果有查到那就是存在)
select * from case_template where id=#{caseTemplateID};


-- 如果该模版已经存在，那就更新模版的内容
update case_template
set
  name=#{name};
  scope=#{scope};
  narrate=#{narrate};
  cur_disease=#{curDisease};
  physical_condition=#{physicalCondition};
where case_id=#{caseID};

-- 模版已经存在，如果也修改了模版中的初诊信息，那需要：
-- 1. 删除diagnose中相关的记录：
delete from diagnose where case_id = "当前的case_template_id";
-- 2. 添加新的diagnose记录：
insert into diagnose
  values("case_template_id", "disease_id", "start_time", "(空，不关心是初诊还是确诊)");


-- 如果是新建的模版， 那就加入新的记录
insert into case_template
  values(该传的东西);

-- 如果是新建的模版，那就直接在diagnose中加入新的记录：
insert into diagnose
  values(#{caseTemplateID}, #{diseaseID}, #{startTime}, null);

```

### 3.1.6 引用病历模板

引用病理模版: 因为模版是有权限的，因此需要在user表中查询到该用户所属的department_id。接着在case_template中查询该用户专属的模版，和部门拥有的模版。通过template的id在diagnose中查询相应的疾病; 返回值：caseTemplate

```sql
-- 查询该用户有权利使用的所有模版：
-- 具体的分类显示在前端实现

-- 个人模版
select <对应po的属性>
from case_template
where  user_id = "312312";
union
-- 部门模版：未完待续
select <对应po的属性>
from case_template and role
where
  case_template.department_id = role.department_id,
  and case_template.user_id = role.user_id,
  and case_template.scope="部门",
  and role.department_id.
-- 全院模版
union
select <对应的po属性>
from case_template
where
  scope = "全院"

```

### 3.1.7 常用诊断管理

常用诊断管理：在diagnose_template表中通过医生的id来查询常用的诊断,

```sql

-- 查询中医常用诊断，返回值： List<diagnose>
select *
from diagnose_template, traditional_disease
where
  diagnose_template.disease_id = traditional_disease.id,
  and user_id=#{id}''

```

### 3.1.8 查看历史病历

查看历史病历：历史病历是针对一个患者的历次的就诊的病历信息，主要是给医生查看，能够综合的了解病人的疾病史及就诊记录，辅助医生对于患者的诊治。选择 “历史病历”，通过历史病历的查看，为本次写病历提供参考。

```sql
-- 查询所有病历，返回值 List<case>
select *
from case
where
  case.patient_id = #{patient_id};

-- 根据病历号查询具体内容同上

```

## 3.2 检查申请
### 3.2.1 新增项目

新增项目：点击“新增项目”，系统显示出当前所有的检查项目，项目可以支持快速检索，选中相应的项目后，输入项目检查的目的和要求，完成新增项目。

```sql
-- 返回 null
insert into inspection
  values (对应的值)
```

### 3.2.2 删除项目

暂存项目：点击“暂存”，将申请的项目暂存，暂存的项目可以删除或编辑。

```sql
-- 同样需要注意是否该暂存的项目是否已经存在
-- 如果已经存在则更新原暂存的内容：
update inspection
  set ..
  set ...
where
-- 如果不存在则新加记录：
```

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