package com.ngtnl1.dmw.util;

import java.util.TimeZone;

public class TimeZoneUtils {
    public static class CommonTimeZones {
        public static final String UTC = "UTC";
        public static final String VIETNAM = "Asia/Ho_Chi_Minh";
        public static final String JAPAN = "Asia/Tokyo";
        public static final String SINGAPORE = "Asia/Singapore";
        public static final String CHINA = "Asia/Shanghai";
        public static final String KOREA = "Asia/Seoul";
        public static final String THAILAND = "Asia/Bangkok";
        public static final String INDONESIA = "Asia/Jakarta";
        public static final String PHILIPPINES = "Asia/Manila";

        // US Timezones
        public static final String US_EASTERN = "America/New_York";
        public static final String US_CENTRAL = "America/Chicago";
        public static final String US_MOUNTAIN = "America/Denver";
        public static final String US_PACIFIC = "America/Los_Angeles";

        // European Timezones
        public static final String UK = "Europe/London";
        public static final String GERMANY = "Europe/Berlin";
        public static final String FRANCE = "Europe/Paris";
    }

    public static boolean isValidTimeZone(String timeZoneId) {
        try {
            TimeZone.getTimeZone(timeZoneId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getCurrentSystemTimeZone() {
        return TimeZone.getDefault().getID();
    }
}