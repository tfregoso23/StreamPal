package com.example.myapplication;

/**
 * Sets up Streaming platform enum as well as a way to get the name as a string
 */

public enum StreamingPlatform {

    NETFLIX("Netflix"),
    HULU("Hulu"),
    PRIME_VIDEO("Prime Video"),
    DISNEY_PLUS("Disney+"),
    HBO_MAX("HBO Max"),
    TUBI("Tubi"),
    PEACOCK("Peacock"),
    PARAMOUNT_PLUS("Paramount+"),
    OTHER("Other"),
    NONE("Not available");


    private final String displayName;

    StreamingPlatform(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


    @Override
    public String toString() {
        return displayName;
    }
}