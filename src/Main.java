import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.text.NumberFormat;

// Homework 2: Sales Register Program 2.0
// Course: CIS357
// Due date: 7/12/2022
// Name: Cody Syring
// GitHub: https://github.com/syrin1cr/Hw2
// Instructor: Il-Hyung Cho
// Program description: This program will act like a cash register. It will add items to cart as you ask
// Admin options are available for adding, removing, and modifying items.

/*
Program features:
Change the item code type to String: Y
Provide the input in CSV format. Ask the user to enter the input file name: Y
Implement exception handling for
    File input: Y
    Checking wrong input data type: Y
    Checking invalid data value: Y
    Tendered amount less than the total amount: y
Use ArrayList for the items data: Y
Adding item data: Y
Deleting item data: Y
Modifying item data: Y
*/

public class Main {
    private static double totalCostForDay = 0; //holds total expense on register

    public static void main(String[] args) throws IOException {
        //Controls if we are done with the program or not.
        Boolean newSale = true;
        String userData;
        char tempData;
        //Defines the List that we use for all items held in our store
        List<item> allItems = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Prints the welcome screen
        System.out.println("Welcome to Syring cash register system!\n");

        allItems = getData(); //Stores information in text file as a List of item
        // printList(allItems); //This is for testing the output. It spits out all possible items
        //This is the start of the Register. Will continue till newSale is false
        //Only when the user types N/n
        do {
            System.out.print("Beginning a new sale (Y/N) ");
            userData = reader.readLine();
            tempData = userData.charAt(0);
            System.out.println("\n---------------------------");
            if (tempData == 'Y' || tempData == 'y') {
                addItemToCart(allItems);
            } else if (tempData == 'N' || tempData == 'n') {
                newSale = false;
            }
            resetShop(allItems);
        } while (newSale);

        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(); //Creates the format for the currency

        System.out.println("The total sale for the day: " + defaultFormat.format(totalCostForDay));
        System.out.println("Thanks for using POST system. Goodbye.");
    }

    /* Gets Linked list of items and returns a Linked list of items
     *This function will get the list of all items on the register and reset the 'cart'
     *portion of the register. This will not remove the ID, Price, or Name. Only the
     *amount to buy
     */

    /**
     * @param itemList
     * @return
     */
    static List<item> resetShop(List<item> itemList) {
        for (item x : itemList) {
            x.resetCart();
        }
        return itemList;
    }

    /*Gets the List of items
     *This function gets every item on the register as itemsList. It handles all the
     *tabbing for the output along with calling the required functions to output the
     *end sale information. It will also add the end sale total to the total register
     *sales.
     */

    /**
     * @param itemsList
     * @throws IOException
     */
    static void printReceipt(List<item> itemsList) throws IOException {
        //These base formats what used from java Lanuage Tutorials. Slightly modified after.
        String column1Format = "%-3.3s";  // fixed size 3 characters, left aligned
        String column2Format = "%-8.8s";  // fixed size 8 characters, left aligned
        String column3Format = "%6.6s";   // fixed size 6 characters, right aligned
        String formatInfo = column1Format + " " + column2Format + " " + column3Format + " " + column1Format;


        column1Format = "%-9.9s";
        column2Format = "%13.2s";
        column3Format = "%-5.4s";
        String formatInfo1 = column1Format + " " + column2Format + " " + column3Format;

        column1Format = "%-9.9s";
        column2Format = "%14.3s";
        String formatInfo2 = column1Format + " " + column2Format;

        cash total = new cash();

        System.out.println("---------------------------");
        System.out.println("Items list:");
        for (item x : itemsList) {
            if (x.getAmount() != 0) {
                //------------------------------------------
                //This section of output is for showing the list of items in the cust cart
                //It will also add the total coast of the cart into
                System.out.print("\t");
                System.out.format(formatInfo, x.getAmount(), x.getName(), "$", (x.getPrice() * x.getAmount()));
                System.out.println();
                total.addToTotal(x.getTotalPrice());
            }
        }

        //-------------------------------------------
        //This is to show the total expense of the cart
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String getCash = "";
        Double change = 0.0;
        System.out.format(formatInfo1, "Subtotal", "$", total.getTotalCost());
        System.out.println();
        System.out.format(formatInfo1, "With Tax", "$", total.getTax());
        System.out.println();
        System.out.format(formatInfo2, "Amount", "$ ");

        getCash = reader.readLine();
        change = Double.parseDouble(getCash) - total.getTax();

        while (change < 0) {
            System.out.println("Please enter more cash than total cost");
            System.out.format(formatInfo2, "Amount", "$ ");
            getCash = reader.readLine();
            change = Double.parseDouble(getCash) - total.getTax();
        }
        System.out.format(formatInfo1, "Change", "$", change);
        totalCostForDay = totalCostForDay + total.getTax();
        System.out.println("\n---------------------------\n");
    }

    /*Gets the list of items and returns nothing.
     *This is to print a list of every item in the "store" if you will.
     */

    /**
     * @param itemsList
     */
    static void printList(List<item> itemsList) {
        //These base formats what used from java Lanuage Tutorials. Slightly modified after.
        String column1Format = "%-4.4s";  // fixed size 3 characters, left aligned
        String column2Format = "%-15.15s";  // fixed size 8 characters, left aligned
        String column3Format = "%-6.6s";   // fixed size 6 characters, right aligned
        String formatInfo = column1Format + " " + column2Format + " " + column3Format;

        System.out.println("\n---------------------------");
        System.out.format(formatInfo, "ID ", "Item Name  ", "Price\n");
        for (item x : itemsList) {
            System.out.format(formatInfo, x.getId(), x.getName(), x.getPrice());
            System.out.println();
        }
        System.out.println("---------------------------\n");
    }

    /*
    addItemToCart function gets the entire item list. It will get a variable from the user
    will than ask the user how many of that item they want. It will call itself again
    incase the user wants to buy another item.
     */

    /**
     * @param itemsList
     * @throws IOException
     */
    static void addItemToCart(List<item> itemsList) throws IOException {
        String addToCart = "";
        Boolean foundItem = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter product Code:\t");
            addToCart = reader.readLine();

            if (addToCart.equals("-1")) {
                printReceipt(itemsList);
            } else if (addToCart.equals("0000")) {
                printList(itemsList);
            } else if (addToCart.equals("admin")) {
                bootAdmin(itemsList);
            } else {
                for (item x : itemsList) {
                    if (addToCart.equals(x.getId())) {
                        foundItem = true;
                        System.out.println("\t item name: " + x.getName());
                        System.out.print("Enter Quantity: \t");
                        addToCart = reader.readLine();
                        x.addToAmount(Integer.parseInt(addToCart));
                        System.out.println("\t" + "item total: \t" + (x.getAmount() * x.getPrice()));
                    }
                }
                if (foundItem == false) {
                    System.out.println("Item not found please try again.");
                }
                addItemToCart(itemsList);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid input please try again");
        }
    }

    /*
     *This function handles the choices in the "admin" screen.
     * options are add, delete, modify or quit. It will also get the option from user
     */

    /**
     * @param itemsList
     * @return
     * @throws IOException
     */
    static List<item> bootAdmin(List<item> itemsList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";

        System.out.print("Admin options: \n" +
                "Add: A\n" +
                "Delete: D\n" +
                "Modify: M\n" +
                "Quit: Q\n" +
                "Select Option: ");

        userInput = reader.readLine();

        switch (userInput) {
            case "A":
            case "a":
                itemsList = addItem(itemsList);
                bootAdmin(itemsList);
                break;
            case "D":
            case "d":
                itemsList = deleteItem(itemsList);
                bootAdmin(itemsList);
                break;
            case "M":
            case "m":
                itemsList = modifyList(itemsList);
                bootAdmin(itemsList);
                break;
            case "Q":
            case "q":
                System.out.println("Leaving admin console");
                return itemsList;

        }

        return itemsList;
    }
    /*Gets the List of items and returns a List of items
     * This method handles all editing of an existing item.
     */

    /**
     * @param itemList
     * @return
     * @throws IOException
     */
    static List<item> modifyList(List<item> itemList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        Boolean itemFound = false;
        int cnt = 0;
        boolean priceSet = false;

        System.out.println("\n---------------------------");
        printList(itemList);

        System.out.print("Select item to Modify(ID): ");
        userInput = reader.readLine();

        for (item x : itemList) {
            if (userInput.equals(x.getId())) {
                itemFound = true;
                System.out.println("modifying " + x.getName());

                System.out.print("What would you like to edit?\n" +
                        "ID: 1\n" +
                        "Name: 2\n" +
                        "Price: 3\n" +
                        "Enter Field: ");
                userInput = reader.readLine();

                switch (userInput) {
                    case "1":
                        System.out.print("Change " + x.getId() + " to: ");
                        userInput = reader.readLine();
                        x.setId(userInput);
                        break;
                    case "2":
                        System.out.print("Change " + x.getName() + " to: ");
                        userInput = reader.readLine();
                        x.setName(userInput);
                        break;
                    case "3":
                        System.out.print("Item Price: ");
                        userInput = reader.readLine();
                        do {
                            try {
                                x.setPrice(userInput);
                                priceSet = false;
                            } catch (NumberFormatException nfe) {
                                System.out.println("Invalid price please try again");
                                System.out.print("Item Price: ");
                                userInput = reader.readLine();
                            }
                        } while (priceSet);
                        break;
                }
                itemList.set(cnt, x);
                return itemList;
            }
            cnt++;
        }
        if (!itemFound) {
            System.out.println("Item not found");
        }
        System.out.println("---------------------------\n");
        return itemList;
    }

    /*
     *this will print only the Id and name
     */

    /**
     * @param itemList
     */
    static void printIDandName(List<item> itemList) {
        System.out.println("ID\t Name");
        for (item x : itemList) {
            System.out.println(x.getId() + "\t" + x.getName());
        }
    }

    static List<item> deleteItem(List<item> itemList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        Boolean itemFound = false;
        int cnt = 0;

        printIDandName(itemList);

        System.out.println("\n---------------------------");
        System.out.print("Enter item ID to remove: ");
        userInput = reader.readLine();

        for (item x : itemList) {
            if (userInput.equals(x.getId())) {
                itemFound = true;
                System.out.println("Item " + x.getId() + " removed");
                itemList.remove(cnt);
                return itemList;
            }
            cnt++;
        }
        if (itemFound == false) {
            System.out.println("Item not found");
        }
        System.out.println("---------------------------\n");
        return itemList;
    }

    /*
     *This will check to see if theirs a Douplicate Item when adding or modifying items
     */

    /**
     * @param itemList
     * @param userInput
     * @return
     */
    static boolean checkDouplicate(List<item> itemList, String userInput) {
        for (item x : itemList) {
            if (userInput.equals(x.getId())) {
                return true;
            }
        }
        return false;
    }
    /*
     *This function will add new items to the list of items and send it back.
     */

    /**
     * @param itemsList
     * @return
     * @throws IOException
     */
    static List<item> addItem(List<item> itemsList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        boolean priceSet = true;
        item tempItem = new item();

        System.out.println("Enter New Item Information");
        System.out.print("Item ID: ");
        userInput = reader.readLine();
        while (checkDouplicate(itemsList, userInput)) {
            System.out.println("Item ID taken, enter new ID");
            System.out.print("Item ID: ");
            userInput = reader.readLine();
        }
        tempItem.setId(userInput);
        System.out.print("Item Name: ");
        userInput = reader.readLine();
        tempItem.setName(userInput);
        System.out.print("Item Price: ");
        userInput = reader.readLine();

        do {
            try {
                tempItem.setPrice(userInput);
                priceSet = false;
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid price please try again");
                System.out.print("Item Price: ");
                userInput = reader.readLine();
            }
        } while (priceSet);
        itemsList.add(tempItem);

        return itemsList;
    }

    /*
     * splitString gets a comma delimited string and will break it into a list.
     * It will than create an item and place the information in its home, sending
     * that single item back.
     */

    /**
     * @param tempList
     * @return
     */
    public static item splitString(String tempList) {
        item tempItem = new item();
        List<String> list = Arrays.asList(tempList.split(","));
        tempItem.setId(list.get(0));
        tempItem.setName(list.get(1));
        tempItem.setPrice(list.get(2));

        return tempItem;
    }

    /*
    getData will grab all the information from the text file and send it back
    as a list to the main function.
     */
    static List<item> getData() {
        List<item> allItems = new ArrayList<>();
        try {
            File inFile = new File("src/dataCSV.csv");
            Scanner inReader = new Scanner(inFile);
            while (inReader.hasNextLine()) {
                String data = inReader.nextLine();
                allItems.add(splitString(data));
            }
            inReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return allItems;
    }
}
