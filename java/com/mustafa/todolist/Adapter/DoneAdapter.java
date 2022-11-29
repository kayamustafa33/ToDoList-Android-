package com.mustafa.todolist.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mustafa.todolist.Model.DoneList;
import com.mustafa.todolist.databinding.RecyclerDoneBinding;

import java.util.ArrayList;

public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.ViewHolder> {

    ArrayList<DoneList> arrayList;
    Context context;
    SQLiteDatabase sqLiteDatabase;

    public DoneAdapter(ArrayList<DoneList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerDoneBinding recyclerDoneBinding = RecyclerDoneBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(recyclerDoneBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DoneAdapter.ViewHolder holder, int position) {
        holder.binding.authorNameText.setText("Author Name: "+arrayList.get(position).authorName);
        holder.binding.titleText.setText("Title: "+arrayList.get(position).title);
        holder.binding.dateText.setText("Date: "+arrayList.get(position).date);

        try {
            sqLiteDatabase = context.openOrCreateDatabase("ToDoList",Context.MODE_PRIVATE,null);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.binding.trashImage.setOnClickListener(item -> {
            sqLiteDatabase.execSQL("DELETE FROM DoneList WHERE title='"+arrayList.get(position).title+"';");
            Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerDoneBinding binding;
        public ViewHolder(RecyclerDoneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
