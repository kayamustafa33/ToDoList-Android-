package com.mustafa.todolist.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mustafa.todolist.databinding.ActivityInformationBinding;

public class InformationActivity extends AppCompatActivity {

    private ActivityInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void goBackBtn(View view){
        startActivity(new Intent(InformationActivity.this,MainActivity.class));
        finish();
    }
}