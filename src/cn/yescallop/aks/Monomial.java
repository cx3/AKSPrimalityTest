package cn.yescallop.aks;

import java.math.BigInteger;

class Monomial {
    private BigInteger coef;
    private int expX;
    private int expA;

    public Monomial(BigInteger coef, int expX, int expA) {
        this.coef = coef;
        this.expA = expA;
        this.expX = expX;
    }
}
