import request from '@/utils/request'

export default {

    // 根据病历号或日期，查询开立的处方信息
    listConstantsTree() {
        return request({
            url: '/basicinfo/constants/tree',
            method: 'GET'
        })
    }
}