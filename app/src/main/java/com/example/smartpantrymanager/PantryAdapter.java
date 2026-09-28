package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryItems;
    private DatabaseHelper databaseHelper;

    public PantryAdapter(List<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        PantryItem item = pantryItems.get(position);

        holder.tvItemName.setText(item.getName());

        holder.tvCategory.setText(
                "Category: " + item.getCategory()
        );

        holder.tvQuantity.setText(
                "Quantity: " + item.getQuantity() + " " + item.getUnit()
        );

        if (item.getExpiryDate() == null || item.getExpiryDate().isEmpty()) {
            holder.tvExpiryDate.setText("Expiry date: Not provided");
        } else {
            holder.tvExpiryDate.setText(
                    "Expiry date: " + item.getExpiryDate()
            );
        }

        holder.btnEditItem.setOnClickListener(v -> {

            Context context = v.getContext();

            Intent intent = new Intent(context, EditItemActivity.class);
            intent.putExtra("item_id", item.getId());

            context.startActivity(intent);
        });

        holder.btnDeleteItem.setOnClickListener(v -> {

            Context context = v.getContext();

            new AlertDialog.Builder(context)
                    .setTitle("Delete Pantry Item")
                    .setMessage(
                            "Are you sure you want to delete " +
                                    item.getName() + "?"
                    )
                    .setPositiveButton("Delete", (dialog, which) -> {

                        databaseHelper = new DatabaseHelper(context);

                        SQLiteDatabase db =
                                databaseHelper.getWritableDatabase();

                        int rowsDeleted = db.delete(
                                "pantry_items",
                                "id = ?",
                                new String[]{String.valueOf(item.getId())}
                        );

                        db.close();

                        if (rowsDeleted > 0) {

                            int currentPosition =
                                    holder.getBindingAdapterPosition();

                            if (currentPosition != RecyclerView.NO_POSITION) {

                                pantryItems.remove(currentPosition);

                                notifyItemRemoved(currentPosition);
                            }

                            Toast.makeText(
                                    context,
                                    "Pantry item deleted",
                                    Toast.LENGTH_SHORT
                            ).show();
                        } else {

                            Toast.makeText(
                                    context,
                                    "Failed to delete pantry item",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName;
        TextView tvCategory;
        TextView tvQuantity;
        TextView tvExpiryDate;

        android.widget.Button btnEditItem;
        android.widget.Button btnDeleteItem;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);

            btnEditItem = itemView.findViewById(R.id.btnEditItem);
            btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);
        }
    }
}