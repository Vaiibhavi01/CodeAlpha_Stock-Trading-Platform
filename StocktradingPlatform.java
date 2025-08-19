import java.util.*;
class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Transaction {
    String type; // BUY or SELL
    Stock stock;
    int quantity;
    double totalCost;

    Transaction(String type, Stock stock, int quantity, double totalCost) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    List<Transaction> transactions = new ArrayList<>();
    double balance;

    Portfolio(double initialBalance) {
        this.balance = initialBalance;
    }

    void buyStock(Stock stock, int quantity) {
        double cost = stock.price * quantity;
        if (cost <= balance) {
            balance -= cost;
            holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + quantity);
            transactions.add(new Transaction("BUY", stock, quantity, cost));
            System.out.println("Bought " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough balance to buy.");
        }
    }

    void sellStock(Stock stock, int quantity) {
        int owned = holdings.getOrDefault(stock.symbol, 0);
        if (quantity <= owned) {
            double earnings = stock.price * quantity;
            balance += earnings;
            holdings.put(stock.symbol, owned - quantity);
            transactions.add(new Transaction("SELL", stock, quantity, earnings));
            System.out.println("Sold " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    void displayPortfolio(Map<String, Stock> market) {
        System.out.println("\n===== Portfolio =====");
        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);
            double currentValue = qty * market.get(symbol).price;
            System.out.println(symbol + " - " + qty + " shares - Value: $" + currentValue);
        }
        System.out.println("Balance: $" + balance);
    }

    void displayTransactions() {
        System.out.println("\n===== Transaction History =====");
        for (Transaction t : transactions) {
            System.out.println(t.type + " " + t.quantity + " of " + t.stock.symbol + " @ $" + t.stock.price + " | Total: $" + t.totalCost);
        }
    }
}

public class StocktradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Sample market data
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 150.0));
        market.put("GOOG", new Stock("GOOG", 2800.0));
        market.put("TSLA", new Stock("TSLA", 750.0));
        market.put("AMZN", new Stock("AMZN", 3400.0));

        Portfolio portfolio = new Portfolio(10000.0);

        while (true) {
            System.out.println("\n===== Stock Trading Platform =====");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n===== Market Data =====");
                    for (Stock stock : market.values()) {
                        System.out.println(stock.symbol + " - $" + stock.price);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next().toUpperCase();
                    if (market.containsKey(buySymbol)) {
                        System.out.print("Enter quantity: ");
                        int buyQty = sc.nextInt();
                        portfolio.buyStock(market.get(buySymbol), buyQty);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next().toUpperCase();
                    if (market.containsKey(sellSymbol)) {
                        System.out.print("Enter quantity: ");
                        int sellQty = sc.nextInt();
                        portfolio.sellStock(market.get(sellSymbol), sellQty);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;

                case 4:
                    portfolio.displayPortfolio(market);
                    break;

                case 5:
                    portfolio.displayTransactions();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

