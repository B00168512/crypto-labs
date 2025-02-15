package tud.b00168512.lab1c;

import java.math.BigInteger;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class ManyTimesPad {

    public static void main(String[] args) throws DecoderException {


        //m2
//        var msg1 = new BigInteger("0f351c71d96e59b742c34053a6853d9930664da237f24456874ae61198546b7ea6c8f7a34b581823ead8490c29568e618b68a929f3e6e6ea0ca35f1944c2ec70d686df3028c4f9a42a0720748cbb4e41a74c07773213bad56eefde922d44cdbcbf3e008be80870a5eb7c55ab907f18fcf347", 16);
        //m4
//        var msg1 = new BigInteger("0f351c71e76f5fbe548d4c54a69a2bcb3d2e4ceb3cfb5250c24dff419949683f8ed3a5f04f57116fe797410d2c138b60db6da534a7abe0f658b65b5c0ccbeb67d1c9d9216d8befeb35173f3596f40d4fa84e1e702818fbc06bec90d4315fceacfa7112c3e55d74aaf3394bb08f7504a8e501", 16);
        // m6
//        var msg1 = new BigInteger("1835183ce0614abc558d4c41a69521cc646f5ae77aee5247cc4ae506d752777ae8c8a5a5565e5f26fcd84f0c2b47877cdb71a426e9e6eea440be525c00cff166c19dc23b28e2eba4271c2e7a97e94c49af5252787b1dbacf27f4df923c4883baa26e158dfe416faebd7c4dba973004b9ff4a", 16);
//        //m8
        var msg1 = new BigInteger("12290a71f96d5dbd43de4c45ea896ecd2b2e45ed2cf81756c803e70881433f6ba79cb8a047441e3bead84c1d7f528c77db69a931e2aaaff345a35f1311dea56fc788db2066ccbff030132e7091bb4f47be52526a3e15b6c869e7dccb7e40c6beb4771a84e14d6ab8bd7f49be9e7d13b2e852", 16);
        //m10/
//        var msg1 = new BigInteger("127d0c22f5640da65f8d514fef822599306649f67afe4e40c251f81196457a3fbfdda4f0445f193bf6d8540c3e41912e9a72ad3ea791e7e558f77e5c10c2ea76c581d9697fcaeca4241b2b619bbb544bab5301393a07bad827f7d1c17e42cdb3a33e0086e30860aefc6b48ff986717a5bc60", 16);

        // m2
//        var msg1 = new BigInteger("12290a71f96d5dbd43de4c45ea896ecd2b2e45ed2cf81756c803e70881433f6ba79cb8a047441e3bead84c1d7f528c77db69a931e2aaaff345a35f1311dea56fc788db2066ccbff030132e7091bb4f47be52526a3e15b6c869e7dccb7e40c6beb4771a84e14d6ab8bd7f49be9e7d13b2e852dd4f3c839f06281a4ff20420f7", 16);



        //m6
//        var msg2 = new BigInteger("1835183ce0614abc558d4c41a69521cc646f5ae77aee5247cc4ae506d752777ae8c8a5a5565e5f26fcd84f0c2b47877cdb71a426e9e6eea440be525c00cff166c19dc23b28e2eba4271c2e7a97e94c49af5252787b1dbacf27f4df923c4883baa26e158dfe416faebd7c4dba973004b9ff4a", 16);
        // m13
        var msg2 = new BigInteger("0f351c71c7654ff251de056ea68920cf2d7d49e53ff9174bd303fc04d74e7e69ad9cb9bf56160c2aea960d002b139b6b8f25982fe2e6e9f158a2451944c3f623d19dc425648beceb621f38768abb4f47ad46176b7b04b3c069a0c4da3b0dd3bea96a54b7e4453989f86b55ba8b635b90f944", 16);

        System.out.println(msg1.toString(16));
        System.out.println(msg2.toString(16));


        var xoredPair = msg1.xor(msg2);

        var xoredAsString = new String(Hex.decodeHex(xoredPair.toString(16)));
        System.out.println(xoredAsString);


        var cribDragWord = "the Web as I envisaged it we have not seen it yet The future is still so much bigger than the past Tim Berners-Lee";
        var cribDragWordHex = new BigInteger(Hex.encodeHexString(cribDragWord.getBytes()), 16);


        for (int i = 0; i < xoredAsString.length(); i++) {
            if (i + cribDragWord.length() < xoredAsString.length()) {
                var s = xoredAsString.substring(i, i + cribDragWord.length());
                var sbi = new BigInteger(Hex.encodeHexString(s.getBytes()), 16);
                var res = sbi.xor(cribDragWordHex);
                if (res.toString(16).length() % 2 == 0) {
                    var printText = "idx: " + i + "; " + new String(Hex.decodeHex(res.toString(16)));
                    System.out.println(printText);
                }
            }
        }

    }

}
