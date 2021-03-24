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