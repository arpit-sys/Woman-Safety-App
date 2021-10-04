package com.learning.safetyapp.Contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.learning.safetyapp.R;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<ContactModel> {

    Context context;
    List<ContactModel> contacts;

    public CustomAdapter(@NonNull Context context, List<ContactModel>contacts){
        super(context, 0, contacts);
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Create a database helper object to handle the database manipulations
        DbHelper db = new DbHelper(context);

        // Get the data item for this position
        ContactModel c = getItem(position);
        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        LinearLayout linearLayout = convertView.findViewById(R.id.linear);

        //Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);

        // Populate the data into the template view using the data object
        tvName.setText(c.getName());
        tvPhone.setText(c.getName());

        linearLayout.setOnLongClickListener(view -> {

            //generate an MaterialAlertDialog Box
            new MaterialAlertDialogBuilder(context)
                    .setTitle("Remove Contact")
                    .setMessage("Are you sure want to remove this contact?")
                    .setPositiveButton("YES", (dialogInterface, i) -> {
                        // delete the specified contact from the database
                        db.deleteContact(c);
                        //remove the item from the list
                        contacts.remove(c);
                        //notify the listview that dataset has been changed
                        notifyDataSetChanged();
                        Toast.makeText(context, "Contact removed!", Toast.LENGTH_SHORT).show();
                    }).setNegativeButton("NO", (dialogInterface, i) -> {}).show();
            return false;
        });
        return convertView;
    }

    //this method will update the ListView
    public  void refresh(List<ContactModel> list) {
        contacts.clear();
        contacts.addAll(list);
        notifyDataSetChanged();
    }
}
