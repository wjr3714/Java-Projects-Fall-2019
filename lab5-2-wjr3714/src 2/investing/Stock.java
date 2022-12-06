package investing;


/**
 * This class represents a stock. Each stock has a ticker symbol, a number of shares, and a price per share.
 *
 * @see Comparable<Stock></Stock>
 * @see Brokerage
 *
 * @author William J. Reid (wjr3714)
 */
public class Stock implements Comparable<Stock> {

    /** The ticker symbol is the unique stock name. */
    private String tickerSymbol;

    /** The 'current' price per share. */
    private int pricePerShare;

    /** Total number of shares owned of the stock. */
    private int sharesHeld;

    /**
     * Create a new Stock object
     *
     * @param tickerSymbol String representing the unique stock name
     * @param pricePerShare integer price per share
     * @param sharesHeld integer total shares held
     */
    public Stock(String tickerSymbol, int pricePerShare, int sharesHeld) {
        this.tickerSymbol = tickerSymbol;
        this.pricePerShare = pricePerShare;
        this.sharesHeld = sharesHeld;
    }

    /**
     * Add a new stock to holding.
     * Precondition: The input Stock object ticker symbol is the same as this ticker symbol.
     * @param s The stock to add to holding.
     */
    public void addToHolding(Stock s) {
        assert this.getTickerSymbol().equals(s.getTickerSymbol());
            this.sharesHeld +=s.sharesHeld;
    }

    /**
     * Compare two Stock objects based on ticker symbol (sort in alphabetical order).
     * @param o The stock object to compare to.
     * @return Result of the comparison.
     */
    @Override
    public int compareTo(Stock o) {
        // Avoid printing duplicates
        if(this.tickerSymbol.equals(o.tickerSymbol)){
            return 0;
        }
        else if(this.tickerSymbol.compareTo(o.tickerSymbol) < 0){
            return -1;
        }
        else {
            return 1;
        }

    }

    /**
     * Two Stock objects are 'equal' if they have the same ticker symbol.
     * @param o Stock instance to compare for equivalence
     * @return True if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Stock) {
            Stock aStock = (Stock) o;
            return this.tickerSymbol.equals(aStock.tickerSymbol);
        }
        return false;
    }

    /**
     * Get the price per share.
     * @return The price per share.
     */
    public int getPricePerShare() {
        return pricePerShare;
    }

    /**
     * Get total number of shares held.
     * @return Number of shares held.
     */
    public int getSharesHeld() {
        return sharesHeld;
    }

    /**
     * Get the Stock's ticker symbol.
     * @return Stock's ticker symbol.
     */
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * Sell shares of a stock.
     * Preconditions:
     * (1) The input Stock object ticker symbol is the same as this ticker symbol
     * (2) The number of shares to be sold does not exceed or match the number currently held.
     * @param s The stock to sell from holding.
     */
    public void sellFromHolding(Stock s) {
        assert this.getTickerSymbol().equals(s.getTickerSymbol()) && this.getSharesHeld() > s.getSharesHeld();
            this.sharesHeld -=s.sharesHeld;
    }

    /**
     * String representation of a Stock.
     * @return A string representation of a Stock.
     */
    @Override
    public String toString() {
        return "MyStock{"
                + "tickerSymbol='" + tickerSymbol
                + '\'' + ", pricePerShare=" + pricePerShare
                + ", sharesHeld=" + sharesHeld
                + "}";
    }

    /**
     * The hash code of a Stock object is the hash code of the ticker symbol.
     * @return hash code of stock's ticker symbol.
     */
    @Override
    public int hashCode() {
        return this.tickerSymbol.hashCode();
    }

    /**
     * Update the price of the stock.
     * @param price The price of the stock to update.
     */
    public void updatePrice(int price) {
        this.pricePerShare = price;
    }
}
