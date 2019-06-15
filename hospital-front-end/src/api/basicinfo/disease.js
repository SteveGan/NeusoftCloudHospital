import request from '@/utils/request'

export default {
    // 1.2.1 根据id获取科室信息
    getDiagnoselistById(id) {
      return request({
        url: '/basicinfo/disease/' + id,
        method: 'GET'
      })
    },

    // 1.2.2 新增科室信息
    addDiagnoselist(diagnoselist) {
      return request({
        url: '/basicinfo/disease',
        method: 'POST',
        data: diagnoselist
      })
    },

    // 1.2.3 修改科室信息
    updateDiagnoselist(diagnoselist) {
      return request({
        url: '/basicinfo/disease',
        method: 'PUT',
        data: diagnoselist
      })
    },

    // 1.2.4 删除科室信息
    deleteDiagnoselist(id) {
      return request({
        url: '/basicinfo/disease/' + id,
        method: 'DELETE'
      })
    },

    // 1.2.5 列出全部科室
    listAllDiagnoselists() {
      return request({
        url: '/basicinfo/diseases',
        method: 'GET'
      })
    }
}