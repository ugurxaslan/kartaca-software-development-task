package org.example;

public class Marker {
    private double[] position;
    private String text;

    public Marker(double[] position, String text) {
        this.position = position;
        this.text = text;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}