/**
 * TODO rsa工具(pkcs1)
 *
 * @author Mr.He
 * 2021/4/15 14:41
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
import JSEncrypt from 'jsencrypt'

const instance = new JSEncrypt()
const settings = require('../../settings')
const publicKey = settings.publicKey;
// const privateKey = settings.privateKey;

class JSEncryptUtils {
    static encrypt(data) {
        instance.setPublicKey(publicKey)
        return instance.encrypt(data);
    }

    // static decrypt(data) {
    //     instance.setPrivateKey(privateKey)
    //     return instance.decrypt(data);
    // }
}

export default JSEncryptUtils