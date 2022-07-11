public class cash {
    private final double SALESTAX = 1.06;
    private double totalCost;

    public cash(){
        totalCost =0;
    }
    public double getTotalCost(){
        return this.totalCost;
    }
    public void addToTotal(double x){
        this.totalCost = this.totalCost + x;
    }
    public double getTax(){
        return totalCost * SALESTAX;
    }
    public double getChange(double cashIn){
        return cashIn - totalCost;
    }
}
