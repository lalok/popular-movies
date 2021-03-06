package de.lokaizyk.popularmovies.util;

import android.content.Context;

import de.lokaizyk.popularmovies.R;

/**
 * Created by lars on 04.10.16.
 */

public class DeviceSettings {

    private static final String TAG = DeviceSettings.class.getSimpleName();

    private static final int DEFAULT_COLUMN_COUNT = 2;

    public static boolean isTablet(Context context) {
        if (context == null) {
            return false;
        }
        return context.getResources().getBoolean(R.bool.is_tablet);
    }

    public static boolean isLandscape(Context context) {
        if (context == null) {
            return false;
        }
        return context.getResources().getBoolean(R.bool.is_landscape);
    }

    public static int getColumnCount(Context context) {
        if (context == null) {
        return DEFAULT_COLUMN_COUNT;
        }
        return context.getResources().getInteger(R.integer.movies_column_count);
    }

}
