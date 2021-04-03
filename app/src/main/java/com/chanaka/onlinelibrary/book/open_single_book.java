package com.chanaka.onlinelibrary.book;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chanaka.onlinelibrary.R;
import com.chanaka.onlinelibrary.dbhadler.BookModel;
import com.chanaka.onlinelibrary.dbhadler.BookModelduplicate;
import com.chanaka.onlinelibrary.dbhadler.BorrowbookModel;
import com.chanaka.onlinelibrary.dbhadler.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import android.graphics.Color;
public class open_single_book extends AppCompatActivity {
    public TextView titles,descriptions,genres,prices,borrowed_done;
    public ImageView bookImage;
    public Button borrow_done_button;
    RadioGroup  paymentgroupRadio;
    UserModel userModel= new UserModel();
    BookModelduplicate  bookModel =new BookModelduplicate();
    BorrowbookModel borrowbookModel = new BorrowbookModel();
    public DatabaseReference databaseReference  ;
    public static  boolean alreadybookexit =true ,
            paymentpaypal =false,
            paymentdebitcard =false,
            paymentskrill =false,
            paymentcompleteStatus=false;
    AlertDialog alertDialog ;

    EditText paypal_username,paypal_password,cardHolder_name,card_cvv,card_expiredate,skrill_username,skrill_password;
    LinearLayout paypal_board,debitcard_board,skrill_board;
    RadioButton paypal_radiobtn, cardpayment_radiobtn,skril_radiobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_single_book);

              alertDialog = new AlertDialog.Builder(open_single_book.this).create();
              alertDialog.setTitle("Alert");
              setTitle("Borrow Book");
              titles  =(TextView)findViewById(R.id.single_book_title);
              descriptions  =(TextView)findViewById(R.id.single_book_description);
              genres  =(TextView)findViewById(R.id.single_book_genre);
              prices  =(TextView)findViewById(R.id.single_book_price);
              borrowed_done= (TextView)findViewById(R.id.borrowedAlert);
              borrow_done_button=(Button)findViewById(R.id.borrow_done_button);
              bookImage =(ImageView)findViewById(R.id.single_book_image);
              prices.setText(String.valueOf(bookModel.getPrice()));
              titles.setText(bookModel.getTitle());
              descriptions.setText(bookModel.getDescription());
              genres.setText(bookModel.getGenre());
              paypal_username=(EditText)findViewById(R.id.paypal_username);
              paypal_password=(EditText)findViewById(R.id.paypal_password);
              cardHolder_name=(EditText)findViewById(R.id.cardHolder_name);
              card_cvv=(EditText)findViewById(R.id.card_cvv);
              card_expiredate=(EditText)findViewById(R.id.card_expiredate);
              skrill_username=(EditText)findViewById(R.id.skrill_username);
              skrill_password=(EditText)findViewById(R.id.skrill_password);
             paypal_radiobtn= (RadioButton)findViewById(R.id.paypal);
             cardpayment_radiobtn= (RadioButton)findViewById(R.id.cardpayment);
             skril_radiobtn= (RadioButton)findViewById(R.id.skrill);


              paypal_board=( LinearLayout)findViewById(R.id.paypal_board);
              debitcard_board=( LinearLayout)findViewById(R.id.debitcard_board);
              skrill_board=( LinearLayout)findViewById(R.id.skrill_board);

              paymentgroupRadio=(RadioGroup)findViewById(R.id. paymentgroupRadio);
              Picasso.with(this).load(bookModel.getBooktImageUrk())
                .fit()
                .centerCrop()
                .into(bookImage);

        databaseReference  = FirebaseDatabase.getInstance().getReference("Member").child(userModel.getDupId()).child("borrowbook");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    alreadybookexit = false;

                    System.out.println("alreadybrrow 23:"+alreadybookexit);
                }
                else{
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        BorrowbookModel borrowbookModel =dataSnapshot.getValue(BorrowbookModel.class);
                        if(!bookModel.getId().equalsIgnoreCase(borrowbookModel.getBookid())){

                            alreadybookexit = false;
                            System.out.println("alreadybrrow 24:"+alreadybookexit);
                        }
                        else{
                            alreadybookexit = true;
                            System.out.println("alreadybrrow 25:"+alreadybookexit);
                            break;

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void borrow_book(View view) {

        if(paymentpaypal ||paymentdebitcard || paymentskrill  )
        {

            if(paymentpaypal)
            {

                if(paypal_username.getText().toString().length() !=0 && paypal_password.getText().toString().length() !=0)
                {
                    paymentcompleteStatus = true;
                }
                else{
                    paypal_username.setError("check username");
                    paypal_password.setError("check password");
                    }

            }

            else if(paymentdebitcard)
            {
                if (cardHolder_name.getText().toString().length() != 0 && card_cvv.getText().toString().length() != 0 && card_expiredate.getText().toString().length() != 0)
                {
                    paymentcompleteStatus = true;
                } else {
                    cardHolder_name.setError("check username");
                    card_cvv.setError("check password");
                    card_expiredate.setError("check expiredate");
                }
            }
            else if(paymentskrill)
            {
                if(skrill_username.getText().toString().length() !=0 && skrill_password.getText().toString().length() !=0)
                {
                    paymentcompleteStatus = true;
                }else{
                    skrill_username.setError("check username");
                    skrill_password.setError("check password");
                }
            }
        }
        else {
            showpopupalert("Select a  payment method");
            System.out.println("Select a  payment method23");
            Toast.makeText(open_single_book.this,"Select a  payment method",Toast.LENGTH_LONG).show();
        }

        if(paymentcompleteStatus)
        {

            if(!alreadybookexit)
            {
                LocalDateTime datetime = LocalDateTime.now();
                datetime = datetime.plusDays(15);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formatDateTime =  datetime.format(formatter);
                // System.out.println("Return date" +formatDateTime);
                Toast.makeText(open_single_book.this,formatDateTime,Toast.LENGTH_LONG).show();
                borrowbookModel.setBorrowid(databaseReference.push().getKey());
                borrowbookModel.setBookid(bookModel.getId());
                borrowbookModel.setBookTitle(bookModel.getTitle());
                borrowbookModel.setBookimageUrl(bookModel. getBooktImageUrk());
                borrowbookModel.setDate(String.valueOf(formatDateTime));
                databaseReference.push().setValue(borrowbookModel);
                Toast.makeText(open_single_book.this,"Successfully borrowede",Toast.LENGTH_SHORT).show();
                /*findViewById(R.id.borrwed).setVisibility(View.INVISIBLE);*/
                findViewById(R.id.borrwed).setEnabled(false);
                findViewById(R.id.borrow_done_button).setVisibility(View.VISIBLE);


                borrowed_done.setTextColor(Color.GREEN);
                borrowed_done.setVisibility(View.VISIBLE);
                borrowed_done.setText("Successfully borrowed");
                borrow_done_button.setVisibility(View.VISIBLE);
                borrow_done_button.setBackground(getDrawable(R.drawable.borrowed_done_green));

            }
            else{
                System.out.println("already borrowed 3");
                System.out.println("already borrowed1 :"+bookModel.getId());
                System.out.println("already borrowed2 :"+borrowbookModel.getBookid());

                borrowed_done.setTextColor(Color.RED);
                borrowed_done.setVisibility(View.VISIBLE);
                borrowed_done.setText("Already Borrowed");
                borrow_done_button.setVisibility(View.VISIBLE);
                borrow_done_button.setBackground(getDrawable(R.drawable.borrowed_done_red));


            }
            paymentcompleteStatus =false;
            paypal_board.setVisibility(View.GONE);
            debitcard_board.setVisibility(View.GONE);
            skrill_board.setVisibility(View.GONE);
            makeEditText_empty();
        }

        System.out.println("already exist uis last :" +alreadybookexit);

    }

    public void makepaymentmethode(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.paypal:
                if(checked)
                    paypal_board.setVisibility(View.VISIBLE);
                    debitcard_board.setVisibility(View.GONE);
                    skrill_board.setVisibility(View.GONE);
                    paymentpaypal =true;
                    paymentdebitcard =false;
                    paymentskrill =false ;
                break;
            case R.id.cardpayment:
                if(checked)
                    paypal_board.setVisibility(View.GONE);
                    debitcard_board.setVisibility(View.VISIBLE);
                    skrill_board.setVisibility(View.GONE);
                    paymentpaypal =false;
                    paymentdebitcard =true;
                    paymentskrill =false ;
                break;
            case R.id.skrill:
                if (checked)
                    paypal_board.setVisibility(View.GONE);
                    debitcard_board.setVisibility(View.GONE);
                    skrill_board.setVisibility(View.VISIBLE);
                    paymentpaypal =false;
                    paymentdebitcard =false;
                    paymentskrill =true ;
                    break;

        }
    }

    public void showpopupalert(String alert){
        alertDialog.setMessage(alert);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }
    public void makeEditText_empty(){
        paypal_username.setText("");
        paypal_password.setText("");
        cardHolder_name.setText("");
        card_cvv.setText("");
        card_expiredate.setText("");
        skrill_username.setText("");
        skrill_password.setText("");

        paypal_radiobtn.setChecked(false);
        cardpayment_radiobtn.setChecked(false);
        skril_radiobtn.setChecked(false);

    }
}
