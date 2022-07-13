public class item {
    /**
     * Member Data
     */
    private String id;
    private String name;
    private double price;
    private int amount;


    /**
     * Constructor
     */

    public item() {
        id = "NOT SET";
        name = "NOTSET";
        price = 0;
        amount = 0;
    }

    /*member function*/
    public void resetCart() {
        amount = 0;
    }
    //takes a string converts it to int and saves it in the class variable id.

    /**
     * @param x
     */
    public void setId(String x) {
        this.id = x;
    }

    /**
     * @param x
     */
    public void setName(String x) {
        this.name = x;
    }

    /**
     * @param x
     */
    public void setPrice(String x) {
        this.price = Double.parseDouble(x);
    }

    /**
     * @param x
     */
    public void setAmount(String x) {
        this.amount = Integer.parseInt(x);
    }

    /**
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @return
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * @param x
     */
    public void addToAmount(int x) {
        this.amount = this.amount + x;
    }

    /*
     *This function will account for items that are not taxed.
     */

    /**
     * @return double (
     */
    public double getTotalPrice() {
        return this.amount * this.price;
    }
}
