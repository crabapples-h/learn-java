module.exports = {
    baseUrl: 'http://localhost:9093/', //测试环境
    // fileServBaseUrl: 'http://www.gz.10086.cn/queue/', //文件服务器前缀
    fileServBaseUrl: 'http://118.31.40.95:10372/', //开发文件服务器前缀
    url: function (path) {
        return this.baseUrl;
    },
    fileurl: function (path) {
        return this.fileServBaseUrl + path;
    },
    isDebug: true,
    crypt: false, //接口加密
    publicKey: '-----BEGIN PUBLIC KEY-----\n' +
        'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtEdgPwBkPuulvFSr0ESP\n' +
        'jRFUG6x/oNL52rNEDamf1Qr2d8viNsLbYHJpSnpmis1IIIQq9xufFMtexj9eDhtA\n' +
        'w8sW9kwHsQ7PVfP3pGz1IhPA/y7wlegXx1s+zTesypXgxDlEJIzGOTnQeqwflBwP\n' +
        'Yn9tkEqi+DPFgAw2aZywsqU3cqMzdtOdhc4jk+Xir/2JobWFB7uW9uVbu3ypU5n7\n' +
        '0o74hxPKVjj85OzaQndP3dnpT/u4Hez/v08vXlXK/R505HonHROGXVpgqKpMh38k\n' +
        '9H4y/BtWJvNJ2LzUykJLbfaXyqw8MLpNUyor3um0FKtuFdCrb0DKH6yzEM+z8W7n\n' +
        'ZwIDAQAB\n' +
        '-----END PUBLIC KEY-----\n',
    isEny: 1, //密码RSA加密
    customBaseUrl: false, //自定义baseUrl
};
