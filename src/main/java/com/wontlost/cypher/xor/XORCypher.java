package com.wontlost.cypher.xor;

import java.util.Base64;

public class XORCypher {

    public static String encode(String s, String key, String innerKey) {
        return base64Encode(xorWithKey(s.getBytes(), key.getBytes(), innerKey.getBytes()));
    }

    public static String decode(String s, String key, String innerKey) {
        return new String(xorWithKey(base64Decode(s), key.getBytes(), innerKey.getBytes()));
    }

    private static byte[] base64Decode(String s) {
        return Base64.getDecoder().decode(s);
    }

    private static String base64Encode(byte[] bytes) {
        byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
        return new String(encodeBase64);
    }

    private static byte[] xorWithKey(byte[] a, byte[] key, byte[] innerKey) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i % key.length] ^ innerKey[i % innerKey.length]);
        }
        return out;
    }

}
