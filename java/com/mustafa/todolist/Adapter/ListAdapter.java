package com.mustafa.todolist.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.mustafa.todolist.Model.List;
import com.mustafa.todolist.R;
import com.mustafa.todolist.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    ArrayList<List> listArrayList;
    Context context;
    SQLiteDatabase sqLiteDatabase;
    TextView titleText,label;
    ImageView closeLayout;

    public ListAdapter(ArrayList<List> listArrayList,Context context) {
        this.listArrayList = listArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.authorNameText.setText("Author Name: "+listArrayList.get(position).authorName);
        holder.binding.titleText.setText("Title: "+listArrayList.get(position).title);
        holder.binding.dateText.setText("Date: "+listArrayList.get(position).date);

        try {
            sqLiteDatabase = context.openOrCreateDatabase("ToDoList",Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS DoneList(id INTEGER PRIMARY KEY,authorName VARCHAR,title VARCHAR,listText VARCHAR,date VARCHAR);");
        }catch (Exception e){
            e.printStackTrace();
        }

        Dialog dialog = new Dialog(context);

        holder.binding.trashImage.setOnClickListener(item -> {
            sqLiteDatabase.execSQL("DELETE FROM List WHERE title='"+listArrayList.get(position).title+"';");
            Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        });

        holder.itemView.setOnClickListener(item -> {
            dialog.setContentView(R.layout.text_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);

            titleText = dialog.findViewById(R.id.titleLayout);
            label = dialog.findViewById(R.id.textLayout);
            closeLayout = dialog.findViewById(R.id.closeLayout);
            titleText.setText(listArrayList.get(position).title);
            label.setText(listArrayList.get(position).text);


            closeLayout.setOnClickListener(item2 -> {
                dialog.dismiss();
            });

            dialog.show();
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.longclick_menu,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.done:
                                try {
                                    sqLiteDatabase.execSQL("INSERT INTO DoneList(authorName,title,listText,date) VALUES('"+listArrayList.get(position).authorName+"'," +
                                            "'"+listArrayList.get(position).title+"'," +
                                            "'"+listArrayList.get(position).text+"'," +
                                            "'"+listArrayList.get(position).date+"');");

                                    sqLiteDatabase.execSQL("DELETE FROM List WHERE title='"+listArrayList.get(position).title+"';");
                                    Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show();

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                break;

                        }
                        return true;
                    }
                });
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerRowBinding binding;

        public ViewHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
