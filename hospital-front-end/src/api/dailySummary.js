import request from '@/utils/request'

export default {

    // 收费员日结统计
    conductDailyTransactionLogs(object) {
        return request({
            url: '/dailysummary/statistics',
            method: 'GET',
            params: object
        })
    }
}