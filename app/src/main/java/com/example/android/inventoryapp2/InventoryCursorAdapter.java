package com.example.android.inventoryapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp2.data.InventoryContract;
import com.example.android.inventoryapp2.data.InventoryContract.InventoryEntry;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * {@link InventoryCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of inventory data as its data source. This adapter knows
 * how to create list items for each row of inventory data in the {@link Cursor}.
 */
public class InventoryCursorAdapter extends CursorAdapter {

    /** Value for converting cents to dollars */
    final private static int CENT_TO_DOLLAR = 100;

    /** adjust quantity on sale click */
    private static int quantityCounter = 1;

    /**
     * Constructs a new {@link InventoryCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }
    /**
     * This method binds the inventory data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current prodocut can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView supplierTextView = (TextView) view.findViewById(R.id.supplier_name);
        TextView supplierPhoneTextView = (TextView) view.findViewById(R.id.supplier_phone);
        TextView priceTextView = (TextView) view.findViewById(R.id.produce_price);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.produce_quantity);

        // Find the columns of inventory details that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int supplierColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_NAME);
        int supplierPhoneColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_PHONE);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);


        // Read the product details from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        int supplierNameNum = cursor.getInt(supplierColumnIndex);
        String supplierName;
        String supplierPhone = cursor.getString(supplierPhoneColumnIndex);
        String productQuantity = cursor.getString(quantityColumnIndex);
        // Get product price convert to dollar
        String productPrice = cursor.getString(priceColumnIndex);
        double price = Double.valueOf(productPrice) / CENT_TO_DOLLAR;
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        /**
         * Convert the int value of supplier name to the actual name of the
         * supplier. Example 0 = "Pearson"
         */
        switch (supplierNameNum) {
            case InventoryEntry.SUPPLIER_PEARSON:
                supplierName = context.getString(R.string.supplier_pearson);
                break;
            case InventoryEntry.SUPPLIER_BROOK_TAYLOR:
                supplierName = context.getString(R.string.supplier_taylor_brook);
                break;
            default:
                supplierName = context.getString(R.string.supplier_american_book);
                break;
        }

        // Update the TextViews with the details for the current product
        nameTextView.setText(productName);
        supplierTextView.setText(supplierName);
        supplierPhoneTextView.setText(context.getString(R.string.phone_supplier_text) + supplierPhone);
        priceTextView.setText(nf.format(new BigDecimal(price)));
        quantityTextView.setText(productQuantity);

        // Change the quantity when you click the button
        // #Fix update the database with each sale
        Button sale = view.findViewById(R.id.sale_button);
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityCounter = Integer.parseInt(quantityTextView.getText().toString());
                String newValue;
                if (quantityCounter > 0) {
                    quantityCounter--;
                    newValue = String.valueOf(quantityCounter);
                    quantityTextView.setText(newValue);

                }
            }
        });

    }
}
