package com.mustafa.todolist.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.mustafa.todolist.Fragments.AddItemFragment;
import com.mustafa.todolist.Fragments.AllListFragment;
import com.mustafa.todolist.Fragments.FinishedListFragment;
import com.mustafa.todolist.R;
import com.mustafa.todolist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            sqLiteDatabase = this.openOrCreateDatabase("ToDoList",MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS List(id INTEGER PRIMARY KEY,authorName VARCHAR,title VARCHAR,listText VARCHAR,date VARCHAR);");
        }catch (SQLException e){
            e.printStackTrace();
        }


        binding.appBarLayout.setVisibility(View.VISIBLE);
        replaceFragment(new AllListFragment());
        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.allList:
                    binding.appBarLayout.setVisibility(View.VISIBLE);
                    replaceFragment(new AllListFragment());
                    break;
                case R.id.addItem:
                    binding.appBarLayout.setVisibility(View.GONE);
                    replaceFragment(new AddItemFragment());
                    break;
                case R.id.finishedList:
                    binding.appBarLayout.setVisibility(View.VISIBLE);
                    replaceFragment(new FinishedListFragment());
                    break;
            }
            return true;
        });

        binding.toolbar.inflateMenu(R.menu.info_menu);

        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.info_item:
                    startActivity(new Intent(MainActivity.this,InformationActivity.class));
                    finish();
                    return true;
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}