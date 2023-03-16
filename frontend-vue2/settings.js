const settings = {
    baseUrl: 'http://localhost:9093/', //测试环境
    url: function (path) {
        return this.baseUrl;
    },
    isDebug: false,
    isCrypt: false, //接口加密
    publicKey: '-----BEGIN PUBLIC KEY-----\n' +
        'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtEdgPwBkPuulvFSr0ESP\n' +
        'jRFUG6x/oNL52rNEDamf1Qr2d8viNsLbYHJpSnpmis1IIIQq9xufFMtexj9eDhtA\n' +
        'w8sW9kwHsQ7PVfP3pGz1IhPA/y7wlegXx1s+zTesypXgxDlEJIzGOTnQeqwflBwP\n' +
        'Yn9tkEqi+DPFgAw2aZywsqU3cqMzdtOdhc4jk+Xir/2JobWFB7uW9uVbu3ypU5n7\n' +
        '0o74hxPKVjj85OzaQndP3dnpT/u4Hez/v08vXlXK/R505HonHROGXVpgqKpMh38k\n' +
        '9H4y/BtWJvNJ2LzUykJLbfaXyqw8MLpNUyor3um0FKtuFdCrb0DKH6yzEM+z8W7n\n' +
        'ZwIDAQAB\n' +
        '-----END PUBLIC KEY-----\n',
    isEny: 1, //密码RSA加密,暂未实现
    customBaseUrl: false, //自定义baseUrl
};

//重写方法后栈溢出，需要修复
export function log(...e) {
    if (settings.isDebug) {
        console.log(...e)
    }
}
export function error(...e) {
    if (settings.isDebug) {
        console.error(...e)
    }
}
export function warn(...e) {
    if (settings.isDebug) {
        console.warn(...e)
    }
}

export default {settings}
