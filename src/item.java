public class item {
    /**Member Data*/
    private String id;
    private String name;
    private double price;
    private int amount;


    /**Constructor*/
    public item(){
        id = "NOT SET";
        name = "NOTSET";
        price = 0;
        amount = 0;
    }
    /**member function*/
    public void resetCart(){
        amount = 0;
    }
    //takes a string converts it to int and saves it in the class variable id.
    public void setId(String x){
        this.id = x;
    }
    public void setName(String x){
        this.name = x;
    }
    public void setPrice(String x){
        this.price = Double.parseDouble(x);
    }
    public void setAmount(String x){
        this.amount = Integer.parseInt(x);
    }

    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public int getAmount(){
        return this.amount;
    }
    public void addToAmount(int x){
        this.amount = this.amount+x;
    }
    public double getTotalPrice(){
        return this.amount*this.price;
    }
}
