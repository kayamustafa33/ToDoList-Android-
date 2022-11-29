package com.mustafa.todolist.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.mustafa.todolist.databinding.FragmentAddItemBinding;

public class AddItemFragment extends Fragment {

    private FragmentAddItemBinding binding;
    private String text,authorName,date,title;
    private SQLiteDatabase sqLiteDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddItemBinding.inflate(inflater,container,false);

        try {
            sqLiteDatabase = binding.getRoot().getContext().openOrCreateDatabase("ToDoList", Context.MODE_PRIVATE,null);
        }catch (Exception e){
            e.printStackTrace();
        }



        binding.createBtn.setOnClickListener(item -> {
            text = binding.text.getText().toString();
            authorName = binding.authorName.getText().toString();
            date = binding.date.getText().toString();
            title = binding.title.getText().toString();

            if(!text.equals("") && !authorName.equals("") && !date.equals("") && !title.equals("")){
                try {
                    sqLiteDatabase.execSQL("INSERT INTO List(authorName,title,listText,date) VALUES('"+authorName+"','"+title+"','"+text+"','"+date+"');");
                    Toast.makeText(binding.getRoot().getContext(), "Successful", Toast.LENGTH_LONG).show();
                    clearAttr();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(binding.getRoot().getContext(), "Fill in the required fields!", Toast.LENGTH_LONG).show();
            }
        });

        return binding.getRoot();
    }

    private void clearAttr(){
        binding.text.setText("");
        binding.authorName.setText("");
        binding.date.setText("");
        binding.title.setText("");
    }
}