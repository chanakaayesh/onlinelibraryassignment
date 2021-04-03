package com.chanaka.onlinelibrary.User;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.chanaka.onlinelibrary.R;
import com.chanaka.onlinelibrary.dbhadler.BorrowbookModel;
import com.chanaka.onlinelibrary.dbhadler.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserProfile extends AppCompatActivity {
    private RecyclerView book_recycler;
    private user_adapter adapter;
    private List<BorrowbookModel> booklist;
    private DatabaseReference mDatabaseReference;
    private UserModel userModel;
    private  BorrowbookModel borrowbookModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle("Profile");
        book_recycler = findViewById(R.id.borrowedbookrecycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        book_recycler.setLayoutManager(manager);
        book_recycler.setHasFixedSize(true);

        booklist = new ArrayList<>();

        userModel =new UserModel();
        borrowbookModel =new BorrowbookModel();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Member").child(userModel.getDupId()).child("borrowbook");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot nesnapshot : snapshot.getChildren()){
                    borrowbookModel =nesnapshot.getValue(BorrowbookModel.class);


                    System.out.println(borrowbookModel.getBookid());
                    System.out.println(borrowbookModel.getBookimageUrl());
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    ;
                    try {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDateTime now = LocalDateTime.now();
                        Date today= formatter.parse(dtf.format(now));
                        Date returnday = formatter.parse(borrowbookModel.getDate());
                        long diffInMillies = Math.abs(returnday.getTime() - today.getTime());
                        int diff = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                        borrowbookModel.setReminingdays(diff);

                     System.out.println("time differnence" + diff);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    booklist.add(borrowbookModel);
                }
                adapter = new user_adapter(UserProfile.this,booklist);
                book_recycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}