package controller;

import java.math.BigDecimal;

public class ChargerRatePower {
    private final BigDecimal costPerKWH;
    private final BigDecimal kw;

    public ChargerRatePower(BigDecimal costPerKWH, BigDecimal kw) {
        this.costPerKWH = costPerKWH;
        this.kw = kw;
    }

    public BigDecimal getCostPerKWH() {
        return costPerKWH;
    }

    public BigDecimal getKw() {
        return kw;
    }
}
