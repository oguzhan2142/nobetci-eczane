package com.oguzhan.nobetcieczane.utils;

enum FetchingStatusType {initial, error, loading, success}

public class FetchingStatus {
    public FetchingStatusType type;
    public String message;


    public FetchingStatus(FetchingStatusType type, String message) {
        this.type = type;
        this.message = message;
    }

    public static FetchingStatus idleStatus() {
        return new FetchingStatus(
                FetchingStatusType.initial,
                null);
    }

    public static FetchingStatus errorStatus(String message) {
        return new FetchingStatus(FetchingStatusType.initial, message);
    }

    public static FetchingStatus successStatus(String message) {
        return new FetchingStatus(FetchingStatusType.success, message);
    }
    public static FetchingStatus loadingStatus() {
        return new FetchingStatus(FetchingStatusType.loading, null);
    }
}
