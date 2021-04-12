import jsrsasign from 'jsrsasign'

const settings = require('../../settings')
const publicKey = settings.publicKey;

// const privateKey = settings.privateKey;

export function encrypt(data) {
    let key = jsrsasign.KEYUTIL.getKey(publicKey);
    let enc = jsrsasign.KJUR.crypto.Cipher.encrypt(data, key);
    return jsrsasign.hextob64(enc);
}
