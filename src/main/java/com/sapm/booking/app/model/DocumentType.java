package com.sapm.booking.app.model;

public enum DocumentType {
    PASSPORT("Passport"),
    CEDULA("Cedula"),
    DRIVER_LICENSE("Driver License");

    private String displayName;

    DocumentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

