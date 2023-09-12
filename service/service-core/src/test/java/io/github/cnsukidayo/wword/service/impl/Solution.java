package io.github.cnsukidayo.wword.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Solution {

    public BigDecimal quickMul(BigDecimal m, BigDecimal n) {
        if (n.compareTo(new BigDecimal("0")) == 0) {
            return new BigDecimal("1");
        }
        BigDecimal y = quickMul(m, n.divide(new BigDecimal(2), RoundingMode.FLOOR));
        return n.divideAndRemainder(new BigDecimal(2))[1].compareTo(new BigDecimal("0")) == 0 ? y.multiply(y) : y.multiply(y).multiply(m);
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        BigDecimal m = new BigDecimal("12345");
        BigDecimal n = new BigDecimal("12345");
        System.out.println(solution.quickMul(m, n));
    }
}