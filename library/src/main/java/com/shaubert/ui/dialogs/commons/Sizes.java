
package com.shaubert.ui.dialogs.commons;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Sizes {

    /**
     * This method converts dp unit to equivalent device specific value in
     * pixels.
     * 
     * @param dp A value in dp(Device independent pixels) unit. Which we need to
     *            convert into pixels
     * @param context Context to get resources and device specific display
     *            metrics
     * @return A float value to represent Pixels equivalent to dp according to
     *         device
     */
    public static int dpToPx(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int)(px + 0.5f);
    }

    /**
     * This method converts device specific pixels to device independent pixels.
     * 
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display
     *            metrics
     * @return A float value to represent db equivalent to px value
     */
    public static float pxToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;

    }

    public static int spToPx(float sp, Context context) {
        Resources r = context.getResources();
        return (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp, r.getDisplayMetrics()) + 0.5f);
    }

}
