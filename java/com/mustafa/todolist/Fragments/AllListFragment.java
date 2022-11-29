package com.mustafa.todolist.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mustafa.todolist.Adapter.ListAdapter;
import com.mustafa.todolist.Model.List;
import com.mustafa.todolist.databinding.FragmentAllListBinding;
import java.util.ArrayList;

public class AllListFragment extends Fragment {

    ArrayList<List> arrayList;
    ListAdapter adapter;
    FragmentAllListBinding binding;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllListBinding.inflate(inflater,container,false);


        sqLiteDatabase = binding.getRoot().getContext().openOrCreateDatabase("ToDoList", Context.MODE_PRIVATE,null);


        arrayList = new ArrayList<>();
        binding.recyclerList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        adapter = new ListAdapter(arrayList,binding.getRoot().getContext());
        binding.recyclerList.setAdapter(adapter);

        getData();
        return binding.getRoot();
    }

    private void getData(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM List",null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            List list = new List(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            arrayList.add(list);
        }

        if(arrayList.isEmpty()){
            Toast.makeText(binding.getRoot().getContext(), "There is no To Do List!", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();

    }

}