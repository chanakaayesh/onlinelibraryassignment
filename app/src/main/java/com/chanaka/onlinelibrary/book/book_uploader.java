package com.chanaka.onlinelibrary.book;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chanaka.onlinelibrary.R;
import com.chanaka.onlinelibrary.dbhadler.BookModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class book_uploader extends AppCompatActivity {

    private Button insert_book;
    private ImageView imageView;
    private EditText title,price,genre,description;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    BookModel bookModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_uploader);

        setTitle("Insert book");
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Insert book");
        String titlen = actionBar.getTitle().toString();
        actionBar.hide();
        insert_book=(Button)findViewById(R.id.book_insert);
        title=(EditText)findViewById(R.id.book_title);
        price=(EditText)findViewById(R.id.book_price);
        genre=(EditText)findViewById(R.id.book_Genre);
        description=(EditText)findViewById(R.id.book_description);
        imageView=(ImageView)findViewById(R.id.book_image);

        storageReference = FirebaseStorage.getInstance().getReference("BookImage");
        databaseReference = FirebaseDatabase.getInstance().getReference("Book");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Image_Request_Code && resultCode == RESULT_OK && data !=null && data.getData() !=null){

            FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),FilePathUri);
                imageView.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public String GetFileExtension(Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void insertbook(View view) {

        if(FilePathUri != null && title.getText().toString().length() !=0 &&description.getText().toString().length() !=0
                && genre.getText().toString().length() !=0 && price.getText().toString().length() !=0) {
            final StorageReference storageReference1 =storageReference.child(System.currentTimeMillis() + "."+GetFileExtension(FilePathUri));
            storageReference1.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Image Uploaded SuccessFully",Toast.LENGTH_LONG).show();
                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String key = databaseReference.push().getKey();
                                    bookModel =new BookModel();
                                    bookModel.setId(key);
                                    bookModel.setTitle(title.getText().toString());
                                    bookModel.setDescription(description.getText().toString());
                                    bookModel.setGenre(genre.getText().toString());
                                    bookModel.setPrice(Double.parseDouble(price.getText().toString()));
                                    bookModel.setBooktImageUrk(uri.toString());
                                    databaseReference.push().setValue(bookModel);
                                    Toast.makeText(getApplicationContext(),"Success fully added",Toast.LENGTH_LONG).show();
                                    title.setText("");
                                    description.setText("");
                                    genre.setText("");
                                    price.setText("");


                                }
                            });

                        }
                    });

        }

        else{

            title.setError("!");
            description.setError("!");
            genre.setError("!");
            price.setError("!");
        }
    }
}