package com.szzc.common.utils.eth;

import java.math.BigInteger;

/**
 * Created by lx on 17-4-1.
 */
public class EthAddressUtils {
    public static BigInteger toBigInt(String hexValue) {
        String cleanValue = cleanHexPrefix(hexValue);
        return new BigInteger(cleanValue, 16);
    }

    public static String cleanHexPrefix(String input) {
        return containsHexPrefix(input)?input.substring(2):input;
    }

    public static boolean containsHexPrefix(String input) {
        return input.length() > 1 && input.charAt(0) == 48 && input.charAt(1) == 120;
    }

    public static String toHexStringWithPrefixZeroPadded(BigInteger value) {
        return toHexStringWithPrefixZeroPadded(value, 40);
    }
    public static String toHexStringWithPrefixZeroPadded(BigInteger value, int size) {
        return toHexStringZeroPadded(value, size, true);
    }

    public static String toHexStringNoPrefix(BigInteger value) {
        return value.toString(16);
    }

    private static String toHexStringZeroPadded(BigInteger value, int size, boolean withPrefix) {
        String result = toHexStringNoPrefix(value);
        int length = result.length();
        if(length > size) {
            throw new UnsupportedOperationException("Value " + result + "is larger then length " + size);
        } else if(value.signum() < 0) {
            throw new UnsupportedOperationException("Value cannot be negative");
        } else {
            if(length < size) {
                result = zeros(size - length) + result;
            }

            return withPrefix?"0x" + result:result;
        }
    }

    public static String zeros(int n) {
        return repeat('0', n);
    }

    public static String repeat(char value, int n) {
        return (new String(new char[n])).replace("\u0000", String.valueOf(value));
    }

    public static void main(String[] args) {
       /* BigInteger value = toBigInt("000000000000000000000000608f52a1aefbe6021c843387a2e56126f723c87b");
        String info = toHexStringWithPrefixZeroPadded(value);
        System.three.println("info = " + info);*/
        double value = parseAmount("000000000000000000000000000000000000000000000002b9bf569da1170000");
        System.out.println("info = " + value);
    }

    public static double parseAmount(String hexval){
        BigInteger intVal = new BigInteger(hexval,16) ;
        return intVal.doubleValue()/Math.pow(10, 18) ;
    }
}
