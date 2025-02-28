
var crypto = require('node:crypto');
const ByteBuffer = require("bytebuffer");

var keyname = "napier";
var plaintext = "testing";

// var args = process.argv;
// if (args.length > 1) plaintext = args[2];
// if (args.length > 2) keyname = args[3];
var iv = Buffer.from(crypto.randomBytes(16)).toString('hex')
var key = crypto.createHash('shake256', {outputLength: 128}).update(keyname).digest();

var cipher = crypto.createCipheriv('rc4', key, '');
var ciphertext = cipher.update(plaintext, 'utf8', 'hex');
// var ciphertext = ByteBuffer.fromHex("8907deba").buffer
console.log("Ciphertext:\t", ciphertext);
var decipher = crypto.createDecipheriv('rc4', key, '');
var text = decipher.update(ciphertext, 'hex', 'utf8');
console.log("Decipher:\t", text);