/**
 * TODO rsa工具(pkcs8)
 *
 * @author Mr.He
 * 2021/4/15 14:41
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
import jsrsasign from 'jsrsasign'

const settings = require('../../settings')
const publicKey = settings.publicKey;

// const privateKey = settings.privateKey;

export function encrypt(data) {
    let key = jsrsasign.KEYUTIL.getKey(publicKey);
    let enc = jsrsasign.KJUR.crypto.Cipher.encrypt(data, key);
    return jsrsasign.hextob64(enc);
}
