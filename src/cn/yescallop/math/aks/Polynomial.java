package cn.yescallop.math.aks;

import java.math.BigInteger;

public class Polynomial {

    private BigInteger[] coef;
    private int degree;

    public Polynomial(int degree) {
        coef = new BigInteger[degree];
        this.degree = degree;
    }
}
