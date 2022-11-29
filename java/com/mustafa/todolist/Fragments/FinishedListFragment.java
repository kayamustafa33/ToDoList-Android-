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

import com.mustafa.todolist.Adapter.DoneAdapter;
import com.mustafa.todolist.Model.DoneList;
import com.mustafa.todolist.Model.List;
import com.mustafa.todolist.R;
import com.mustafa.todolist.databinding.FragmentFinishedListBinding;

import java.util.ArrayList;

public class FinishedListFragment extends Fragment {

    private FragmentFinishedListBinding binding;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<DoneList> arrayList;
    DoneAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFinishedListBinding.inflate(inflater,container,false);

        try {
            sqLiteDatabase = binding.getRoot().getContext().openOrCreateDatabase("ToDoList", Context.MODE_PRIVATE,null);
        }catch (Exception e){
            e.printStackTrace();
        }

        arrayList = new ArrayList<>();
        binding.RvDone.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        adapter = new DoneAdapter(arrayList,binding.getRoot().getContext());
        binding.RvDone.setAdapter(adapter);

        getData();
        return binding.getRoot();
    }

    private void getData(){
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM DoneList",null);
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                DoneList doneList = new DoneList(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                arrayList.add(doneList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(arrayList.isEmpty()){
            Toast.makeText(binding.getRoot().getContext(), "There is no Done List!", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();

    }
}