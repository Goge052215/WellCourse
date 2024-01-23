
module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://122.51.27.122:8080',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': '',
                },
            }
        }
    }
}