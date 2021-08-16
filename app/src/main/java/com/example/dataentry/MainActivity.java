package com.example.dataentry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements Entry_Adapter.ItemSelected {
    TextView client_name, client_email_id;
    ImageView client_img;
    Button sub_btn;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton floating_btn_add;
    Uri uri, backup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client_name = findViewById(R.id.client_name);
        client_email_id = findViewById(R.id.client_email_id);
        client_img = findViewById(R.id.client_img);
        sub_btn = findViewById(R.id.sub_btn);
        floating_btn_add = findViewById(R.id.floating_btn_add);
        backup = Uri.parse("android.resource://your.package.here/drawable/ic_baseline_person_24.xml");
        uri = Uri.parse("android.resource://your.package.here/drawable/ic_baseline_person_24.xml");

        recyclerView=findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Entry_Adapter  myadapter=new Entry_Adapter(MainActivity.this, ApplicationClass.items);
        recyclerView.setAdapter(myadapter);

        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (client_name.getText().toString().isEmpty())
                    client_name.setError("This field cannot be empty");
                else if (client_email_id.getText().toString().isEmpty())
                    client_email_id.setError("This field cannot be empty");
                else if (!(Patterns.EMAIL_ADDRESS.matcher(client_email_id.getText().toString()).matches()))
                    client_email_id.setError("Invalid mail id");
                else{
                    database_store db = new database_store(MainActivity.this);
                    try {
                        db.open();
                        db.createEntry(client_name.getText().toString().trim(), client_email_id.getText().toString().trim(),uri.toString());
                        ApplicationClass.items.add(new data_items(client_name.getText().toString(),client_email_id.getText().toString(),uri.toString()));
                        myadapter.notifyDataSetChanged();
                        db.close();
                        client_name.setText("");
                        client_email_id.setText("");
                        client_img.setImageURI(backup);
                        uri = Uri.parse("android.resource://your.package.here/drawable/ic_baseline_person_24.xml");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        floating_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(MainActivity.this)
                        .crop(200l,200l)
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        client_img.setImageURI(uri);
    }

    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int index) {
        /*ApplicationClass.items.remove(index);
        database_store db = new database_store(this);
        try {
            db.open();
            db.deleteEntry(Integer.toString(index));
            db.close();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/
    }
}
