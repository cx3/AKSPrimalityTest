package cn.yescallop.math.aks;

import java.math.BigInteger;

import static cn.yescallop.math.MathUtil.*;

public class AKSPrimalityTest {

    private AKSPrimalityTest() {

    }

    public static boolean isPrime(BigInteger n) {
        n = n.abs();
        //if (n.bitLength() < 64)
        //    return isPrime(n.longValue());
        if (n.equals(BI_TWO))
            return true;
        if (!n.testBit(0) || n.equals(BigInteger.ONE))
            return false;

        if (isPerfectPower(n)) return false;

        int mr = (int) Math.pow(log2(n), 2);
        int r = 2;
        while (true) {
            if (!n.gcd(BigInteger.valueOf(r)).equals(BigInteger.ONE)) {
                r++;
                continue;
            }
            if (ord(n, r) > mr)
                break;
            r++;
        }

        r = Math.min(r, n.intValue() - 1);
        for (int a = 2; a <= r; a++) {
            if (n.remainder(BigInteger.valueOf(a)).equals(BigInteger.ZERO))
                return false;
        }

        if (n.compareTo(BigInteger.valueOf(r)) <= 0)
            return true;

        int ma = (int) (Math.sqrt(euler(r)) * log2(n));
        for (int a = 1; a <= ma; a++) {
            //TODO
        }
        return true;
    }

    public static boolean isPrime(long n) {
        n = Math.abs(n);
        if (n == 2)
            return true;
        if ((n & 0b1) == 0 || n == 1)
            return false;
        if (isPerfectPower(n)) return false;
        return true;
    }
}
