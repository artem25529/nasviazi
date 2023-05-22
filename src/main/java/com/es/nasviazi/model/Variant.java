package com.es.nasviazi.model;

public class Variant {
    private String header;
    private String description;

    public Variant(String header, String description) {
        this.header = header;
        this.description = description;
    }

    public Variant() {
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
