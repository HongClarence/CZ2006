package com.example.cz2006.classes;

public class Summary {
    private String total, used, remaining, cost;

    public Summary(String total, String used, String remaining, String cost) {
        this.total = total;
        this.used = used;
        this.remaining = remaining;
        this.cost = cost;
    }

    public String getTotal() {
        return total;
    }

    public String getUsed() {
        return used;
    }

    public String getRemaining() {
        return remaining;
    }

    public String getCost() {
        return cost;
    }
}
