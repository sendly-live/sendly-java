package com.sendly.models;

import java.util.List;

public class VerificationListResponse {
    private List<Verification> verifications;
    private Pagination pagination;

    public List<Verification> getVerifications() { return verifications; }
    public Pagination getPagination() { return pagination; }

    public static class Pagination {
        private int limit;
        private boolean hasMore;

        public int getLimit() { return limit; }
        public boolean isHasMore() { return hasMore; }
    }
}
