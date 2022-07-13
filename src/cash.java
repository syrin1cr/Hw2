public class cash {
    private final double SALESTAX = 1.06;
    private double totalCost;

    public cash() {
        totalCost = 0;
    }

    /**
     * @return
     */
    public double getTotalCost() {
        return this.totalCost;
    }

    /**
     * @param x
     */
    public void addToTotal(double x) {
        this.totalCost = this.totalCost + x;
    }

    /**
     * @return
     */
    public double getTax() {
        return totalCost * SALESTAX;
    }

    /**
     * @param cashIn
     * @return
     */
    public double getChange(double cashIn) {
        return cashIn - totalCost;
    }
}
