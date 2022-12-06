/*
 * Brokerage.java
 * Provided code for completion by student
 */

package investing;

import java.util.*;

/**
 * Implementation of the Brokerage class.  In this simplified simulation
 * the brokerage will manage a single client's investments.  It will
 * also track the movement of the market as a whole.
 *
 * @see Stock
 * @see Comparator<Stock></Stock>
 * @see Investor
 *
 * @author atd: Aaron T Deever
 * @author William J. Reid (wjr3714)
 *
 */
public class Brokerage {

    /** The investor's available cash. */
    private int funds;

    /** List of the stocks owned by the investor. */
    private List<Stock> portfolio;

    /** Map containing stocks available and their current price per share. */
    private Map<String, Integer> market = new HashMap<String, Integer>();
    
    /**
     * Constructor.  Initializes the investor and the market as a whole. In this simplified simulation there is just a
     * single investor and the whole market is tracked by the brokerage.
     * @param initialInvestment The initial investment.
     */
    public Brokerage(int initialInvestment) {
        portfolio = new ArrayList<Stock>();
        funds = initialInvestment;

        /* initialize the market */
        market.put("GOOG", 1239);
        market.put("AMZN", 1820);
        market.put("AAPL", 220);
        market.put("INTC", 51);
        market.put("MSFT", 140);
        market.put("ADBE", 280);
        market.put("AMD",  30);
        market.put("FB",   192);
        market.put("NFLX", 277);
    }
    
    /**
     * Add to Investor's holding.  This function should error-check to ensure the ticker symbol exists, the number of
     * shares requested is a positive value, and that the client has sufficient funds.
     * @param tickerSymbol the particular stock to buy
     * @param shares the number of shares requested
     * @return True if transaction is completed, false otherwise.
     */
    public boolean increaseHolding(String tickerSymbol, int shares) {

        // Allow the user to enter lower & upper case commands as command line arguments
        tickerSymbol = tickerSymbol.toUpperCase();

        // Check if ticker symbol exists.
        if(!market.containsKey(tickerSymbol)) {
            System.out.println("\nTicker symbol '" + tickerSymbol + "' does not exist.");
            return false;
        }

        // Create the stock to buy.
        Stock newStock = new Stock(tickerSymbol, market.get(tickerSymbol), shares);

        // Verify the requested shares > 0
        if(shares <= 0) {
            System.out.println("\nShares to buy must be ≥ 1.");
            return false;
        }
        // Verify the client has sufficient funds to purchase the requested shares.
        else if(funds < market.get(tickerSymbol)*shares) {
            int maximumSharesCanBuy = funds/market.get(tickerSymbol);
            System.out.println("\nYou don't have enough funds to buy " + shares + " shares of stock " + tickerSymbol + ".\n" +
                    "With $" + funds + ", you can buy AT MOST " + maximumSharesCanBuy + " shares of stock " + tickerSymbol + ".");
            return false;
        }
        // Process transaction. Update shares owned of an already owned stock in portfolio.
       else if(portfolio.contains(newStock)) {

            // Update shares owned.
            portfolio.get(portfolio.indexOf(newStock)).addToHolding(newStock);

            // Update the investor's funds after the purchase.
            funds -= market.get(tickerSymbol) * shares;

            return true;
       }
       else {

            // If no shares of the stock were previously owned, add the stock to the portfolio.
            portfolio.add(newStock);

            // Subtract the money spent on the purchase from the total available funds.
            funds -= market.get(tickerSymbol) * shares;

            return true;
        }
    }
    
    /**
     * Reduce Investor's holding.  This function should error-check to ensure the ticker symbol exists, and the
     * number of shares to reduce is a positive value no greater than the number currently held.
     * @param tickerSymbol The particular stock to sell
     * @param shares The number of shares to sell
     * @return True if transaction is completed, false otherwise.
     */
    public boolean reduceHolding(String tickerSymbol, int shares) {

        // Allow the user to enter lower & upper case commands as command line arguments
        tickerSymbol = tickerSymbol.toUpperCase();

        // Check if ticker symbol exists.
        if(!market.containsKey(tickerSymbol)) {
            System.out.println("\nTicker symbol '" + tickerSymbol + "' does not exist.");
            return false;
        }

        // Create the stock to sell.
        Stock sellStock = new Stock(tickerSymbol, market.get(tickerSymbol), shares);

        // Verify the user owns this stock.
        if(!portfolio.contains(sellStock)){
            System.out.println("\nYou do NOT own any shares of stock " + tickerSymbol + ".");
            return false;
        }
        // Verify the shares to sell > 0.
        else if (shares <= 0){
            System.out.println("\nShares to sell must be ≥ 1.");
            return false;
        }
        // Verify the user does not sell more shares than are owned
        else if(shares > portfolio.get(portfolio.indexOf(sellStock)).getSharesHeld()){
            System.out.println("\nYou only have " + portfolio.get(portfolio.indexOf(sellStock)).getSharesHeld() +
                    " shares of stock " + sellStock.getTickerSymbol() + " to sell, but you requested to sell " +
                    shares + ".\nYou can sell at most " + portfolio.get(portfolio.indexOf(sellStock)).getSharesHeld() +
                    " shares of stock " + sellStock.getTickerSymbol() + " at this time.");
            return false;
        }

        /* Process transaction. */
        else if(portfolio.get(portfolio.indexOf(sellStock)).getSharesHeld() > sellStock.getSharesHeld()) {

            // Sell shares of stock from portfolio (without selling all of the shares)
            portfolio.get(portfolio.indexOf(sellStock)).sellFromHolding(sellStock);

            // Cash-in value of shares of stock sold.
            funds += market.get(tickerSymbol) * shares;

            return true;

        }

        /* Process transaction. */
        else{
            // Executes only when all of the shares of the stock owned are being sold.

            // Remove stock from portfolio.
            portfolio.remove(sellStock);

            // Cash-in value of shares of stock sold.
            funds += market.get(tickerSymbol) * shares;
            return true;
        }
    }
    
    /**
     * Generates a string to represent the investor's portfolio.  Can be requested in alphabetical order, or in
     * decreasing order of the value of the holdings (shares * price per share).
     * @param choice "N" for by name, "V" for by value
     * @return String representing the portfolio.  This string must include the name, number of shares, price per
     * share, and total value for each stock in the portfolio. The entries must be sorted according to the input request.
     */
    public String accessPortfolio(String choice) {

        boolean flag = true;
        while (flag) {
            // Sort alphabetically by ticker symbol.
            if (choice.toUpperCase().equals("N")) {
                Collections.sort(portfolio); // Using the compareTo() method [Comparable]
                flag = false;
            }
            // Sort using the compare() method [Comparator]
            else if (choice.toUpperCase().equals("V")) {
                SortPortfolioByValue stockValue = new SortPortfolioByValue();
                portfolio.sort(stockValue);
                flag = false;
            }
            else {
                // If we could replace line 216 in Investor.java with what's contained between
                // the quotes --> "output = brokerage.accessPortfolio(choice);" the line below would be executed
                // when an a command line argument other than N, n, V, or V was entered. But the file Investor.java
                // strictly says at the top of the file that student's shouldn't modify it.
                System.out.println("Please enter 'N' to sort by ticker symbol or 'V' to sort by total value for each stock in the portfolio.");
            }
        }

        StringBuilder output = new StringBuilder();
        output.append("CURRENT PORTFOLIO\n");
        output.append("Cash Available: ").append(funds).append("\n");
        output.append("SYMBOL  |  SHARES  |  PRICE  |  TOTAL VALUE\n");
        output.append("===========================================\n");

        // Right-justified table of stocks in portfolio.
        for (Stock stock : portfolio) {
            output.append(String.format("%6s", stock.getTickerSymbol()));
            output.append(String.format("%11d", stock.getSharesHeld()));
            output.append(String.format("%10s","$" + stock.getPricePerShare()));
            output.append(String.format("%16s", "$" + (stock.getPricePerShare() * stock.getSharesHeld())));
            output.append("\n");
        }

        return output.toString();
    }
    
    /**
     * Update the price per share of each stock using a random value to
     * determine the change.  A multiplier is applied to the stock price and
     * the result is rounded to the nearest integer.  A minimum price of $1 is
     * required. (For the given inputs, this constraint will always hold
     * without checking). This method can also be used to update the price of
     * a stock inside any stock object that contains that information.
     * @return A string "ticker" that indicates the ticker symbols and their prices.
     */
    public String tickerUpdate() { 
        
        String output = "";
        
        for(String str : market.keySet()) { 
            int currVal = market.get(str);
            int num = (int)(Math.random() * 5);
            int newVal;
            switch(num) { 
            case 0:
                newVal = (int)(currVal * .9 + 0.5);
                break;
            case 1:
                newVal = (int)(currVal * .95 + 0.5);
                break;
            case 2:
                newVal = currVal;
                break;
            case 3:
                newVal = (int)(currVal * 1.1 + 0.5);
                break;
            case 4:
            default:
                newVal = (int)(currVal * 1.2 + 0.5);
                break;
            }
            
            market.put(str, newVal);
            output += str + " " + newVal + "      ";
        }

        // Update the price of each stock in user's portfolio
        for (Stock stock : portfolio){
            if (market.containsKey(stock.getTickerSymbol())){
                stock.updatePrice(market.get(stock.getTickerSymbol()));
            }
        }

        return output;
    }
    
    /**
     * Sell all remaining stocks in the portfolio. Calling this method will also terminate the simulation according to Investor.java.
     * @return The cash value of the portfolio.
     */
    public int closeAccount() {
        while (portfolio.size() > 0){
            Stock stockSold = portfolio.remove(0);
            funds += market.get(stockSold.getTickerSymbol()) * stockSold.getSharesHeld();
        }
        return funds;
    }
}

/**
 * A class to sort the stocks in the portfolio by value.
 */
class SortPortfolioByValue implements Comparator<Stock> {

    /**
     * Compares the stocks by value in the portfolio.
     * @param stock1 The first stock to compare.
     * @param stock2 The second stock to compare.
     * @return The stocks in descending order by value.
     */
    @Override
    public int compare(Stock stock1, Stock stock2) {

        int valueStock1 = stock1.getSharesHeld() * stock1.getPricePerShare();
        int valueStock2 = stock2.getSharesHeld() * stock2.getPricePerShare();

        if(valueStock1 < valueStock2) {
            return 1;
        }
        else if(valueStock1 == valueStock2) {
            return 0;
        }
        else {
            return -1;
        }
    }
}
