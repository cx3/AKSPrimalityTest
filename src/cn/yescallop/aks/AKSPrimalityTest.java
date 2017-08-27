package cn.yescallop.aks;

import java.math.BigInteger;

public class AKSPrimalityTest {

    private static final BigInteger BI_TWO = BigInteger.valueOf(2);
    private static final BigInteger BI_THREE = BigInteger.valueOf(3);
    private static final BigInteger BI_FOUR = BigInteger.valueOf(4);
    private static final BigInteger BI_NINE = BigInteger.valueOf(9);

    private static final double LOG2 = Math.log(2);
    private static final double LOG3 = Math.log(3);

    private AKSPrimalityTest() {

    }

    public static boolean isPrime(BigInteger n) {
        n = n.abs();
        if (n.bitLength() < 64)
            return isPrime(n.longValue());
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

        int amax = (int) (Math.sqrt(euler(r)) * log2(n));
        for (int a = 1; a <= amax; a++) {
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

    /* --- INTERNAL PART --- */

    private static int euler(int n) {
        if (n == 1)
            return 1;
        int res = 1;
        for (int i = 2; i < n; i++) {
            if (gcd(n, i) == 1)
                res++;
        }
        return res;
    }

    private static int gcd(int a, int b) {
        while (true) {
            if (b == 0)
                return a;
            int temp = a;
            a = b;
            b = temp % b;
        }
    }

    private static int ord(BigInteger a, int n) {
        BigInteger _n = BigInteger.valueOf(n);
        for (int k = 0; ; k++) {
            if (a.pow(k).mod(_n).equals(BigInteger.ONE))
                return k;
        }
    }

    private static boolean isPerfectPower(BigInteger a) {
        if (isPerfectSquare(a))
            return true;
        double maxi = log2(a);
        for (int i = 3; i <= maxi; i += 2) {
            if (isPerfectPower(a, i))
                return true;
        }
        return false;
    }

    private static boolean isPerfectPower(long a) {
        if (isPerfectSquare(a))
            return true;
        double maxi = log2(a);
        for (int i = 3; i <= maxi; i += 2) {
            if (isPerfectPower(a, i))
                return true;
        }
        return false;
    }

    private static boolean isPerfectPower(BigInteger a, int n) {
        int k = (int) Math.ceil(log2(a) / n);
        int r = (int) Math.ceil(log2(k));
        if (r < 1) return false;
        BigInteger g = BigInteger.ONE;
        BigInteger s = BigInteger.ONE;
        BigInteger m = BI_FOUR;
        BigInteger t = BigInteger.ONE;
        BigInteger _n = BigInteger.valueOf(n);
        for (int i = 1; i < r; i++) {
            g = g.subtract(
                    g.multiply(t)
                            .subtract(a)
                            .multiply(s)
            ).mod(m);
            BigInteger mSquare = m.pow(2);
            t = g.pow(n - 1).remainder(mSquare);
            s = s.shiftLeft(1)
                    .subtract(
                            _n.multiply(t).multiply(s.pow(2))
                    ).mod(m);
            m = mSquare;
        }
        g = g.subtract(
                g.multiply(t)
                        .subtract(a)
                        .multiply(s)
        ).mod(BI_TWO.pow(k));
        return g.pow(n).equals(a);
    }

    private static boolean isPerfectPower(long a, int n) {
        int k = (int) Math.ceil(log2(a) / n);
        int r = (int) Math.ceil(log2(k));
        if (r < 1) return false;
        long g = 1;
        long s = 1;
        long m = 4;
        long t = 1;
        for (int i = 1; i < r; i++) {
            g = mod(g - (g * t - a) * s, m);
            long mSquare = m * m;
            t = (long) Math.pow(g, n - 1) % mSquare;
            s = mod((s << 1) - n * t * s * s, m);
            m = mSquare;
        }
        g = mod(g - (g * t - a) * s, m);
        return (long) Math.pow(g, n) == a;
    }

    private static boolean isPerfectSquare(BigInteger a) {
        if (a.mod(BI_THREE).equals(BI_TWO))
            return false;
        BigInteger[] dr;
        while ((dr = a.divideAndRemainder(BI_NINE))[1].equals(BigInteger.ZERO)) {
            a = dr[0];
        }
        int k = (int) Math.ceil(log3(a) / 2);
        int r = (int) Math.ceil(log2(k));
        if (r < 1) return false;
        BigInteger g = BigInteger.ONE;
        BigInteger s = BI_TWO;
        BigInteger m = BI_NINE;
        for (int i = 1; i < r; i++) {
            g = g.subtract(g.pow(2).subtract(a).multiply(s))
                    .mod(m);
            s = s.subtract(g.multiply(s.pow(2)))
                    .shiftLeft(1)
                    .mod(m);
            m = m.pow(2);
        }
        m = BI_THREE.pow(k);
        g = g.subtract(
                g.pow(2)
                        .subtract(a)
                        .multiply(s)
        ).mod(m);
        return g.pow(2).equals(a) || m.subtract(g).pow(2).equals(a);
    }

    private static boolean isPerfectSquare(long a) {
        if (mod(a, 3) == 2)
            return false;
        if (a == 9)
            return true;
        long[] dr;
        while ((dr = divideAndRemainder(a, 9))[1] == 0) {
            a = dr[0];
        }
        while (mod(a, 9) == 0) {
            a = a / 9;
        }
        int k = (int) Math.ceil(log3(a) / 2);
        int r = (int) Math.ceil(log2(k));
        if (r < 1) return false;
        long g = 1;
        long s = 2;
        long m = 9;
        for (int i = 1; i < r; i++) {
            g = mod(g - (g * g - a) * s, m);
            s = mod((s - (g * s * s)) << 1, m);
            m = m * m;
        }
        m = (long) Math.pow(3, k);
        g = mod(g - (g * g - a) * s, m);
        return g * g == a || (m - g) * (m - g) == a;
    }

    private static long[] divideAndRemainder(long a, long b) {
        long[] res = new long[2];
        res[0] = a / b;
        res[1] = a - res[0] * b;
        return res;
    }

    private static long mod(long n, long m) {
        long res = n % m;
        return (res >= 0 ? res : res + m);
    }

    private static int mod(int n, int m) {
        int res = n % m;
        return (res >= 0 ? res : res + m);
    }

    private static double log2(BigInteger n) {
        return log(n) / LOG2;
    }

    private static double log2(double n) {
        return Math.log(n) / LOG2;
    }

    private static double log3(BigInteger n) {
        return log(n) / LOG3;
    }

    private static double log3(double n) {
        return Math.log(n) / LOG3;
    }

    private static double log(BigInteger n) {
        int b = n.bitLength() - 1022;
        if (b > 0)
            n = n.shiftRight(b);
        double res = Math.log(n.doubleValue());
        return b > 0 ? res + b * LOG2 : res;
    }
}
