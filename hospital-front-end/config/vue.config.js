module.exports = {
    // 修改的配置
    // 将baseUrl: '/api',改为baseUrl: '/'
    devServer: {
        port: 8888,
        proxy: {
            '/api': {
                target: 'https://sm.ms/api',
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                  '^/api': ''
                }
            }
        }
    }
}