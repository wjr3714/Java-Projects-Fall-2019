/* 
 * MyStock.java
 * provided starter lab file
 */

package investing;

import java.util.*;

/**
 * A class that represents a stock.
 * @author William J. Reid (wjr3714)
 */

public class MyStock implements Comparable<MyStock> {

    /** the ticker symbol is the unique stock name. */
    private String tickerSymbol;

    /** the 'current' price per share */
    private int pricePerShare;

    /** total number of shares held */
    private int sharesHeld;

    /**
     * Create a new MyStock object
     *
     * @param tickerSymbol String representing the unique stock name
     * @param pricePerShare integer price per share
     * @param sharesHeld integer total shares held
     */
    public MyStock(String tickerSymbol, int pricePerShare, int sharesHeld) {
        this.tickerSymbol = tickerSymbol;
        this.pricePerShare = pricePerShare;
        this.sharesHeld = sharesHeld;
    }

    /**
     * @return stock ticker symbol
     */
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    @Override
    public String toString() {
        return "MyStock{"
                    + "tickerSymbol='" + tickerSymbol
                    + '\'' + ", pricePerShare=" + pricePerShare
                    + ", sharesHeld=" + sharesHeld
                    + "}";
    }

    /**
     * Two MyStock objects are 'equal' if they have the same ticker symbol.
     * @param o MyStock instance to compare for equivalence
     * @return true if equal and false otherwise
     */
    @Override
    public boolean equals(Object o) { // Activity 1
        if (o instanceof MyStock) {
            MyStock a = (MyStock) o;
            return this.tickerSymbol.equals(a.tickerSymbol);
        }
        return false;
    }

    /**
     * The hash code of a MyStock object is the hash code of the ticker symbol.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return this.tickerSymbol.hashCode();
    }

    /**
     * The natural order comparison of MyStock object is that an instance
     * with a higher price per share comes before one with a lower price. 
     * If two MyStock objects have the same price, the tiebreaker
     * is alphabetical by ticker symbol.
     *
     * @param o MyStock instance to compare
     * @return less than 0 if this instance price is higher than the other,
     * 0 if the two are equal, and greater than 0 if the other's price
     * is higher than this instance price.
     */
    @Override
    public int compareTo(MyStock o) { // Activity 2
        // Avoid printing duplicates
        if(this.tickerSymbol.equals(o.tickerSymbol) && this.pricePerShare == o.pricePerShare){
            return 0;
        }
        else if(this.pricePerShare < o.pricePerShare){
            return 1;
        }
        else{
            return -1;
        }
    }

    /**
     * The main method runs tests on MyStock to verify method implementations
     * that affect or determine collection ordering.
     *
     * @param args unused String array, the command line argument required for main
     */
    public static void main(String[] args) {

        // create an assortment of MyStock objects
        MyStock apple = new MyStock("AAPL", 114, 5);
        MyStock google = new MyStock("GOOG", 768, 10);
        MyStock msft = new MyStock("MSFT", 31, 7);
        MyStock amazon = new MyStock("AMZN", 778, 4);
        MyStock ebay = new MyStock("EBAY", 31, 100);

        // Activity 1: make the list contains() method work
        System.out.println("Activity 1: list contains");
        ArrayList<MyStock> stockList = new ArrayList<>();
        stockList.add(apple);
        stockList.add(google);
        stockList.add(msft);
        stockList.add(amazon);
        System.out.println("stockList.contains(apple)? "
                            + stockList.contains(apple));
        System.out.println("stockList.contains(amazon)? "
                            + stockList.contains(amazon));
        System.out.println("stockList.contains(ebay)? "
                            + stockList.contains(ebay));
        System.out.println("stockList.contains(\"XXX\")? "
                            + stockList.contains("XXX"));

        // Activity 2: print tree stocks by decreasing price per share
        System.out.println("\nActivity 2: print tree of stocks by decreasing price per share");
        TreeSet<MyStock> stockTree1 = new TreeSet<>();
        stockTree1.add(apple);
        stockTree1.add(google);
        stockTree1.add(msft);
        stockTree1.add(amazon);
        stockTree1.add(ebay);
        stockTree1.add(apple);  // intentionally add a duplicate
        for (MyStock stock : stockTree1) {
            System.out.println(stock);
        }

        // Activity 3: print the stocks alphabetically by name
        System.out.println("\nActivity 3: print tree of stocks alphabetically by name");
        TreeSet<MyStock> stockTree2 = new TreeSet<>(new MyStockComparator());
        stockTree2.add(apple);
        stockTree2.add(google);
        stockTree2.add(msft);
        stockTree2.add(amazon);
        stockTree2.add(ebay);
        for (MyStock stock : stockTree2) {
            System.out.println(stock);
        }

        // Activity 4: print the stocks in map with a 4 rating
        System.out.println("\nActivity 4: print stocks in map with a 4 rating");
        HashMap<MyStock, Integer> stockMap = new HashMap<>();
        stockMap.put(apple, 4);
        stockMap.put(google, 5);
        stockMap.put(msft, 4);
        stockMap.put(amazon, 4);
        stockMap.put(ebay, 2);

        // loop over the map and print out the matching stocks here
         Iterator cursorstockMapKeys = stockMap.entrySet().iterator();
         while (cursorstockMapKeys.hasNext()) {
             Map.Entry mapElement = (Map.Entry) cursorstockMapKeys.next();
             if (((int) mapElement.getValue() == 4)) {
                 System.out.println(mapElement.getKey());
             }
         }

         // A more condensed form for Activity 4 is shown directly below (commented out).
//        for (Map.Entry<MyStock, Integer> myStockIntegerEntry : stockMap.entrySet()) {
//            if (((int) ((Map.Entry) myStockIntegerEntry).getValue() == 4)) {
//                System.out.println(((Map.Entry) myStockIntegerEntry).getKey());
//            }
//        }

    }
}


/**
 * A class that overrides the natural order comparison of MyStock objects
 * and compares them alphabetically by name.
 */
class MyStockComparator implements Comparator<MyStock> { // Activity 3
    @Override
    public int compare(MyStock o1, MyStock o2) {

        for (int j = 0; j < o1.getTickerSymbol().length(); j++){
            char o1Character = o1.getTickerSymbol().charAt(j);
            char o2Character = o2.getTickerSymbol().charAt(j);
            if(o1.getTickerSymbol().equals(o2.getTickerSymbol())){
                return 0;
            }
            else if(o1Character == o2Character){ // Jump to the next iteration of the loop.
                continue;
            }
            else if (o1Character - o2Character < 0){
                return -1;
            }
            else { // if (o1Character - o2Character > 0)
                return 1;
            }

        }
        return -2; // This is never reached, but a return statement is required.
    }
}


/*
 * Activity 3: Another way of implementing the compare method using the Comparator interface is shown below. Uncommenting the lines
 * below (220 through 247) and replacing lines 193 through 214 with these yield the same result (alphabetical sorting).
 */
//class MyStockComparator implements Comparator<MyStock> { // Activity 3
//    @Override
//    public int compare(MyStock o1, MyStock o2) { // Look into other ways of implementing the compare() method to sort alphabetically.
//        // Possibly using the charAt() method in the String class and subtracting them in a for loop for the length of the String.

//        // String to denote the natural order of letters. There are better ways of doing this... ? Though this was clever!
//        String alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
//        // Avoid duplicates
//        if(o1.getTickerSymbol().equals(o2.getTickerSymbol())){
//            return 0;
//        }
//        else{
//            for (int i = 0; i < o1.getTickerSymbol().length(); i++){
//                char o1Character = o1.getTickerSymbol().charAt(i);
//                char o2Character = o2.getTickerSymbol().charAt(i);
//                int o1Index = alphabet.indexOf(o1Character);
//                int o2Index = alphabet.indexOf(o2Character);
//                if(o1Index < o2Index){
//                    return -1;
//                }
//                else if(o1Index > o2Index){
//                    return 1;
//                }
//            }
//
//            return -2; // This is never reached.
//
//        }




/* OUTPUT:
Activity 1: list contains
stockList.contains(apple)? true
stockList.contains(amazon)? true
stockList.contains(ebay)? false
stockList.contains("XXX")? false

Activity 2: print tree of stocks by decreasing price per share
MyStock{tickerSymbol='AMZN', pricePerShare=778, sharesHeld=4}
MyStock{tickerSymbol='GOOG', pricePerShare=768, sharesHeld=10}
MyStock{tickerSymbol='AAPL', pricePerShare=114, sharesHeld=5}
MyStock{tickerSymbol='EBAY', pricePerShare=31, sharesHeld=100}
MyStock{tickerSymbol='MSFT', pricePerShare=31, sharesHeld=7}

Activity 3: print tree of stocks alphabetically by name
MyStock{tickerSymbol='AAPL', pricePerShare=114, sharesHeld=5}
MyStock{tickerSymbol='AMZN', pricePerShare=778, sharesHeld=4}
MyStock{tickerSymbol='EBAY', pricePerShare=31, sharesHeld=100}
MyStock{tickerSymbol='GOOG', pricePerShare=768, sharesHeld=10}
MyStock{tickerSymbol='MSFT', pricePerShare=31, sharesHeld=7}

Activity 4: print stocks in map with a 4 rating
MyStock{tickerSymbol='MSFT', pricePerShare=31, sharesHeld=7}
MyStock{tickerSymbol='AAPL', pricePerShare=114, sharesHeld=5}
MyStock{tickerSymbol='AMZN', pricePerShare=778, sharesHeld=4}
*/
