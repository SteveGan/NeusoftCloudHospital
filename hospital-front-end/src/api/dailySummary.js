import request from '@/utils/request'

export default {
    // 查询收费员上次日结截止时间
    getLastEndDate(cashierId) {
        return request({
            url: '/dailysummary/lastdate',
            method: 'GET',
            params: {cashierId}
        })
    },

    // 收费员日结统计
    conductDailyTransactionLogs(object) {
        return request({
            url: '/dailysummary/statistics',
            method: 'GET',
            params: object
        })
    },

    // 收费员结算报账
    freezeDailyTransactionLogs(object) {
        return request({
            url: '/dailysummary/settlement',
            method: 'GET',
            params: object
        })
    }
}