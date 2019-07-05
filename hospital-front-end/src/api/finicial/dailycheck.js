import request from '@/utils/request'

export default {
    // 查询门诊日结核对
    checkDailySummary(startDate, endDate, cashierId) {
        return request({
            url: '/finance/dailysummary',
            method: 'GET',
            params: {
                beginDateStr: startDate,
                endDateStr: endDate,
                cashierId: cashierId
            }
        })
    },

    // 核对通过
    checkDailySummary(dailySummaryParam) {
        return request({
            url: '/finance/confirm',
            method: 'PUT',
            params: {dailySummaryParam}
        })
    },
}