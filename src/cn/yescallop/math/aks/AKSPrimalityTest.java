package cn.yescallop.math.aks;

import java.math.BigInteger;

import static cn.yescallop.math.MathUtil.*;

public class AKSPrimalityTest {

    private AKSPrimalityTest() {

    }

    /**
     * Returns if integer n is prime.
     * Uses the AKS primality test algorithm.
     */
    public static boolean isPrime(BigInteger n) {
        if (n.signum() <= 0)
            return false;
        if (n.equals(BI_TWO)) // two is prime
            return true;
        if (!n.testBit(0) || n.equals(BigInteger.ONE)) //composite or one
            return false;

        // Step 1: Check if n is a perfect power.
        if (isPerfectPower(n)) return false;

        double log2n = log2(n);
        // Step 2: Find the smallest value of r such that ord(n, r) > (log2 n)^2.
        int r = findSmallestOrdValue(n, log2n);

        // Step 3: If a|n for some 2 ≤ a ≤ min(r, n−1), output composite.
        if (!checkDivision(n, r))
            return false;

        // Step 4: If n ≤ r, output prime.
        if (n.compareTo(BigInteger.valueOf(r)) <= 0)
            return true;

        // Step 5: For 1≤ a ≤ ⌊sqrt(euler(r)*(log2 n)⌋, if (x+a)^n ≠ x^n+a (mod x^r−1, n), output composite.
        return checkPolynomialModulo(n, log2n, r);
    }

    /**
     * Returns the smallest value of r such that ord(n, r) > (log2 n)^2.
     */
    private static int findSmallestOrdValue(BigInteger n, double log2n) {
        int mr = (int) Math.pow(log2n, 2);
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
        return r;
    }

    /**
     * Returns if a|n for all 2 ≤ a ≤ min(r, n−1).
     */
    private static boolean checkDivision(BigInteger n, int r) {
        r = Math.min(r, n.intValue() - 1);
        for (int a = 2; a <= r; a++) {
            if (n.remainder(BigInteger.valueOf(a)).equals(BigInteger.ZERO))
                return false;
        }
        return true;
    }

    /**
     * Returns if (x+a)^n ≡ x^n+a (mod x^r−1, n) for 1≤ a ≤ ⌊sqrt(euler(r))*(log2 n)⌋.
     */
    private static boolean checkPolynomialModulo(BigInteger n, double log2n, int r) {
        int ma = (int) (Math.sqrt(euler(r)) * log2n);
        for (int a = 1; a <= ma; a++) {
            //TODO
        }
        return true;
    }
}
