package cn.yescallop.math.aks;

import java.math.BigInteger;

class Monomial {
    BigInteger coef;
    int expX;
    int expA;

    public Monomial(BigInteger coef, int expX, int expA) {
        this.coef = coef;
        this.expA = expA;
        this.expX = expX;
    }

    public Monomial addCoef(BigInteger coef) {
        this.coef = this.coef.add(coef);
        return this;
    }
}
