package com.yuexue.tifenapp.wxapi;

import android.os.Build;

import org.apache.http.NameValuePair;

import java.util.Iterator;

/**
 * Created by haoxiqiang on 15/3/13.
 */
public class AndroidMileage {

    final static int[] versionCodes;
    final static String[] versionMileages;
    final static int size;

    static {
        versionCodes = new int[]{
                Build.VERSION_CODES.BASE, Build.VERSION_CODES.BASE_1_1, Build.VERSION_CODES.CUPCAKE,
                Build.VERSION_CODES.DONUT, Build.VERSION_CODES.ECLAIR, Build.VERSION_CODES.ECLAIR_0_1,
                Build.VERSION_CODES.ECLAIR_MR1, Build.VERSION_CODES.FROYO, Build.VERSION_CODES.GINGERBREAD,
                Build.VERSION_CODES.GINGERBREAD_MR1, Build.VERSION_CODES.HONEYCOMB, Build.VERSION_CODES.HONEYCOMB_MR1,
                Build.VERSION_CODES.HONEYCOMB_MR2, Build.VERSION_CODES.ICE_CREAM_SANDWICH, Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1,
                Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.JELLY_BEAN_MR1, Build.VERSION_CODES.JELLY_BEAN_MR2,
                Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.KITKAT_WATCH, Build.VERSION_CODES.LOLLIPOP
        };
        versionMileages = new String[]{
                "October 2008: The original, first, version of Android.  Yay!",
                "February 2009: First Android update, officially called 1.1.",
                " May 2009: Android 1.5.",
                "September 2009: Android 1.6.",
                "November 2009: Android 2.0",
                "December 2009: Android 2.0.1",
                "January 2010: Android 2.1",
                "June 2010: Android 2.2",
                "November 2010: Android 2.3",
                "February 2011: Android 2.3.3.",
                "February 2011: Android 3.0.",
                "May 2011: Android 3.1.",
                "June 2011: Android 3.2.",
                "October 2011: Android 4.0.",
                "December 2011: Android 4.0.3.",
                "June 2012: Android 4.1.",
                "November 2012: Android 4.2, Moar jelly beans!",
                "July 2013: Android 4.3, the revenge of the beans.",
                "October 2013: Android 4.4, KitKat, another tasty treat.",
                "Android 4.4W: KitKat for watches, snacks on the run.",
                "Lollipop.  A flat one with beautiful shadows.  But still tasty.",
        };

        size =  Math.min(versionCodes.length, versionMileages.length);
    }

    public static class Mileage implements NameValuePair {

        public String name;
        public String value;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "versionCode:" + name + " desc:" + value;
        }
    }


    public Iterator<Mileage> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Mileage> {
        /**
         * Number of elements remaining in this iteration
         */
        private int remaining = size;

        /**
         * Index of element that remove() would remove, or -1 if no such elt
         */
        private int removalIndex = -1;

        @Override
        public boolean hasNext() {
            return remaining != 0;
        }

        @Override
        public Mileage next() {
            Mileage mileage = new Mileage();
            removalIndex = size-remaining;
            mileage.name = String.valueOf(versionCodes[removalIndex]);
            mileage.value = versionMileages[removalIndex];
            remaining-=1;
            return mileage;
        }

        @Override
        public void remove() {
            versionCodes[removalIndex]=-1;
            versionMileages[removalIndex]="It was set null";
        }
    }
}
