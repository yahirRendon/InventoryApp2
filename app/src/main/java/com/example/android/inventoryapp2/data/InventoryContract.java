package com.example.android.inventoryapp2.data;

import android.content.ContentResolver;
import android.provider.BaseColumns;
import android.net.Uri;

/**
 * API Contract for the Inventory App.
 *
 * Created by yahir on 10/13/2018.
 */

public final class InventoryContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private InventoryContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.inventory";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.inventoryapp2/inventory/ is a valid path for
     * looking at inventory data. content://com.example.android.inventoryapp2/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_INVENTORY = "inventory";

    /**
     * Inner class that defines constant values for the inventory database table.
     * Each entry in the table represents a single product.
     */
    public static final class InventoryEntry implements BaseColumns {

        /** The content URI to access the inventory data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /** Name of database table for inventory */
        public final static String TABLE_NAME = "inventory";

        /**
         * Unique ID number for the product (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the product.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME ="name";

        /**
         * Price of the product.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_PRICE = "price";

        /**
         * Quantity of the product.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";

        /**
         * Supplier name of the product.
         *
         * The only possible values are {@link #SUPPLIER_PEARSON}, {@link #SUPPLIER_BROOK_TAYLOR},
         * or {@link #SUPPLIER_AMERICAN_BOOK}.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_SUPPLIER_NAME = "supplierName";

        /**
         * Supplier phone number of the product.
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_PHONE = "supplierPhone";

        /**
         * Possible values for the supplier name.
         */
        public static final int SUPPLIER_PEARSON = 0;
        public static final int SUPPLIER_BROOK_TAYLOR = 1;
        public static final int SUPPLIER_AMERICAN_BOOK = 2;

        /**
         * Returns whether or not the given supplier is {@link #SUPPLIER_PEARSON}, {@link #SUPPLIER_BROOK_TAYLOR},
         * or {@link #SUPPLIER_AMERICAN_BOOK}.
         */
        public static boolean isValidSupplier(int gender) {
            if (gender == SUPPLIER_PEARSON || gender == SUPPLIER_BROOK_TAYLOR || gender == SUPPLIER_AMERICAN_BOOK) {
                return true;
            }
            return false;
        }
    }

}
