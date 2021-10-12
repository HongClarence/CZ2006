package com.example.cz2006.classes;

public class Limit {
    private double limit;
    private double reminder;

    public Limit(double limit, double reminder) {
        this.limit = limit;
        this.reminder = reminder;
    }

    public double getLimit() {
        return limit;
    }

    public double getReminder() {
        return reminder;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public void setReminder(double reminder) {
        this.reminder = reminder;
    }
}
