package com.chanaka.onlinelibrary.book;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import com.chanaka.onlinelibrary.R;
import com.chanaka.onlinelibrary.User.UserProfile;
import com.chanaka.onlinelibrary.dbhadler.BookModel;
import com.chanaka.onlinelibrary.dbhadler.BorrowbookModel;
import com.chanaka.onlinelibrary.dbhadler.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class View_book_list extends AppCompatActivity {
    private RecyclerView book_recycler;
    private  book_adapter adapter;
    private List<BookModel> booklist;
    private DatabaseReference mDatabaseReference,removeborrowbook;
    public  static BookModel bookModel;
    public static   String UserId;
    public SearchView searchView;

    public
    UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        removeBorrowbook();
        Intent intent = getIntent();
        UserId =intent.getStringExtra("user_id");
        searchView =(SearchView)findViewById(R.id.searchView) ;
        book_recycler =(RecyclerView)findViewById(R.id.book_recycler);
        book_recycler.setHasFixedSize(true);
        book_recycler.setLayoutManager(new LinearLayoutManager(this));
        booklist = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Book");




    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mDatabaseReference !=null){
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(!snapshot.exists())
                    {}
                    else{   for (DataSnapshot postsnapshot:snapshot.getChildren()){

                        bookModel   =postsnapshot.getValue(BookModel.class);
                        booklist.add(bookModel);
                    }

                        adapter = new book_adapter(View_book_list.this,booklist);
                        book_recycler.setAdapter(adapter);}

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(View_book_list.this,error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

        if(searchView !=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }

    }

    public void search(String searchingkey){
        List<BookModel> newbooklist = new ArrayList<>();
        for (BookModel object : booklist){
            //search by title,description or genre
            if(object.getTitle().toLowerCase().contains(searchingkey.toLowerCase()) || object.getDescription().toLowerCase().contains(searchingkey.toLowerCase())
            || object.getGenre().toLowerCase().contains(searchingkey.toLowerCase())){
                newbooklist.add(object);
            }

            book_adapter newadapter = new book_adapter(View_book_list.this,newbooklist);
            book_recycler.setAdapter(newadapter);
        }

    }



    public void openSinglebook(String title, String description, String genre, Double price, String imgeURL){
        Intent intent =new Intent(View_book_list.this,open_single_book.class);
        intent.putExtra("title",title);
        intent.putExtra("description", description);
        intent.putExtra("genre",genre);
       // intent.putExtra("price",price);
        intent.putExtra("imgeURL",imgeURL);

        startActivity(intent);
    }


    public void removeBorrowbook(){
        removeborrowbook  = FirebaseDatabase.getInstance().getReference("Member").child(userModel.getDupId()).child("borrowbook");
        removeborrowbook.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    System.out.println("notexist");
                }else{
                    for(DataSnapshot psDataSnapshot : snapshot.getChildren()){
                        BorrowbookModel borrowbookModel =psDataSnapshot.getValue(BorrowbookModel.class);
                        System.out.println(borrowbookModel.getBookid());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                            LocalDateTime now = LocalDateTime.now();
                            Date today= formatter.parse(dtf.format(now));
                            Date returnday = formatter.parse(borrowbookModel.getDate());

                            long diffInMillies = Math.abs(returnday.getTime() - today.getTime());
                            int diff = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                            if(diff>15){
                                psDataSnapshot.getRef().removeValue();
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void UserProfileopen(View view) {
        Intent intent = new Intent(View_book_list.this, UserProfile.class);
        startActivity(intent);
    }

}
