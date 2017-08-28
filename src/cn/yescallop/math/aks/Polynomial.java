package cn.yescallop.math.aks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static cn.yescallop.math.MathUtil.getBinomialCoef;

public class Polynomial {

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
        return p;
    }

    public void addTerm(Monomial term) {
        terms.add(term);
    }

    public Polynomial collect() {
        Map<Long, Monomial> res = new HashMap<>();
        terms.forEach(term -> res.compute(
                ((long) term.expX << 32) | term.expA,
                (i, m) -> m == null ? term : m.addCoef(term.coef)
        ));
        this.terms = new HashSet<>(res.values());
        return this;
    }
}
