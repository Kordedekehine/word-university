package com.cbt.cbtapp.Utils;

import java.time.Instant;

public class Utils {

    public static String timeAgo(Instant then) {
        long secondsSinceThen = Instant.now().minusMillis(then.toEpochMilli()).toEpochMilli() / 1000;
        long interval = secondsSinceThen / 31536000;

        if (interval > 1)
            return (int)Math.floor(interval) + " years ago";

        interval = secondsSinceThen / 2592000;
        if (interval > 1)
            return (int)Math.floor(interval) + " months ago";

        interval = secondsSinceThen / 86400;
        if (interval > 1)
            return (int)Math.floor(interval) + " days ago";

        interval = secondsSinceThen / 3600;
        if (interval > 1)
            return (int)Math.floor(interval) + " hours ago";

        interval = secondsSinceThen / 60;
        if (interval > 1)
            return (int)Math.floor(interval) + " minutes ago";

        return (int)Math.floor(secondsSinceThen) + " seconds ago";
    }
}
