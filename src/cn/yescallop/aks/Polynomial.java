package cn.yescallop.aks;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

class Polynomial {

    private Set<Monomial> terms;

    public Polynomial() {
        terms = new HashSet<>();
    }

    public Polynomial(Set<Monomial> terms) {
        this.terms = terms;
    }

    public static Polynomial ofBinomialExpansion(int exp) {
        Polynomial p = new Polynomial();
        for (int k = 0; k <= exp; k++) {
            p.addTerm(new Monomial(
                    getBinomialCoef(exp, k),
                    exp - k,
                    k
            ));
        }
        return p.collect();
    }

    public void addTerm(Monomial term) {
        terms.add(term);
    }

    public Polynomial collect() {
        //TODO
        return this;
    }

    private static BigInteger getBinomialCoef(int n, int k) {
        return factorial(n).divide(factorial(k))
                .divide(factorial(n - k));
    }

    private static BigInteger factorial(int n) {
        BigInteger res = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }
}
