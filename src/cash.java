public class cash {
    private final double SALESTAX = 1.06;
    private double taxedTotalCost;
    private double notTaxedTotalCost;

    public cash() {
        notTaxedTotalCost = 0;
        taxedTotalCost = 0;
    }

    /**
     * @return
     */
    public double getTotalCost() {
        return this.notTaxedTotalCost + taxedTotalCost;
    }

    /**
     * @param x
     */
    public void addToTotalTaxed(double x) {
        this.taxedTotalCost = this.taxedTotalCost + x;
    }

    public void addToTotalNotTaxed(double x) {
        this.notTaxedTotalCost = this.notTaxedTotalCost + x;
    }

    /**
     * @return
     */
    public double getTax() {
        return taxedTotalCost * SALESTAX + notTaxedTotalCost;
    }

    /**
     * @param cashIn
     * @return
     */
    public double getChange(double cashIn) {
        return cashIn - getTax();
    }
}
