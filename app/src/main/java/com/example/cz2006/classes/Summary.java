package com.example.cz2006.classes;

public class Summary {
    private int total, used, remaining, cost;

    public Summary(int total, int used, int remaining, int cost) {
        this.total = total;
        this.used = used;
        this.remaining = remaining;
        this.cost = cost;
    }

    public int getTotal() {
        return total;
    }

    public int getUsed() {
        return used;
    }

    public int getRemaining() {
        return remaining;
    }

    public int getCost() {
        return cost;
    }
}
