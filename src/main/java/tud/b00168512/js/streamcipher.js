var crypto = require('node:crypto');
var ByteBuffer = require("bytebuffer");
var chacha20 = require("chacha20");

var keyname = "qwerty";
var plaintext = "testing";
// var args = process.argv;
// if (args.length > 1) plaintext = args[2];
// if (args.length > 2) keyname = args[3];
var key = crypto.createHash('sha256').update(keyname).digest();
var nonce = Buffer.alloc(8);
nonce.fill(0);

// var cipherte
// xt = chacha20.encrypt(key, nonce, Buffer.from(plaintext));
var ciphertext = ByteBuffer.fromHex("e96924f16d6e").buffer
console.log("Ciphertext:\t", ciphertext.toString("hex"));
console.log("Decipher\t", chacha20.decrypt(key, nonce, ciphertext).toString());