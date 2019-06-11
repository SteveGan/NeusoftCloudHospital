import request from '@/utils/request'

export default {

    // 根据病历号或日期，查询开立的处方信息
    listAvailableRecipes(caseId, chargeDateStr) {
        return request({
            url: '/pharmacy/recipes',
            method: 'GET',
            params: {caseId, chargeDateStr}
        })
    },

    // 根据病历号或日期，查询开立的处方信息
    listRecipeInfo(caseId) {
        return request({
            url: '/pharmacy/recipe/' + caseId,
            method: 'GET'
        })
    },

    // 执行发药操作
    deliverMedicine(deliverSelection) {
        return request({
            url: '/pharmacy/delivery',
            method: 'POST',
            data: deliverSelection
        })
    },

    // 执行退药操作
    returnMedicine(returnSelection) {
        return request({
            url: '/pharmacy/return',
            method: 'POST',
            data: returnSelection
        })
    },    
}