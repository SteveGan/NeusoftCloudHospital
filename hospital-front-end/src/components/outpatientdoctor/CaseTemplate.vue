<template>
  <div>
    <!-- 一个标签页：个人模版，科室模版（包括个人的模版），全院模版 -->
    <el-tabs :stretch="true">
      <el-tab-pane label="个人" name="first">
        <!-- 顶部操作按钮区：增加模版按钮，删除模版按钮，编辑模版按钮 -->
        <div class="button-bar">
          <el-button type="text" icon="el-icon-plus" circle>添加</el-button>
          <el-button type="text" icon="el-icon-edit" circle>编辑</el-button>
          <el-button type="text" icon="el-icon-delete" circle>删除</el-button>
        </div>
        <!-- 表格区 -->
        <div>
          <el-table>
            <el-table-column type="selection"></el-table-column>
            <el-table-column label="模版名称"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini">使用</el-button>
              </template>
            </el-table-column>
            <el-table-column type="expand">
              <template slot-scope="props">
                <p>haha</p>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="科室" name="second">
        <!-- 表格区 -->
        <div>
          <el-table>
            <el-table-column type="selection"></el-table-column>
            <el-table-column label="模版名称"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini">使用</el-button>
              </template>
            </el-table-column>
            <el-table-column type="expand">
              <template slot-scope="props">
                <p>haha</p>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="全院" name="third">
        <!-- 表格区 -->
        <div>
          <el-table>
            <el-table-column type="selection"></el-table-column>
            <el-table-column label="模版名称"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini">使用</el-button>
              </template>
            </el-table-column>
            <el-table-column type="expand">
              <template slot-scope="props">
                <p>haha</p>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 每个菜单下都有一个模版列表（其中个人模版可以勾选，并进行操作），有下拉功能展示详情-->
  </div>
</template>
<script>
export default {
  name: "CaseTemplate",
  methods: {}
};
</script>
<style>
.button-bar {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}
</style>
