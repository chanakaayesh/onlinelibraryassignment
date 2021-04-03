package com.chanaka.onlinelibrary.book;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chanaka.onlinelibrary.dbhadler.BookModel;

import java.util.List;

import  com.chanaka.onlinelibrary.R;
import com.chanaka.onlinelibrary.dbhadler.BookModelduplicate;
import com.chanaka.onlinelibrary.dbhadler.UserModel;
import com.squareup.picasso.Picasso;

public class book_adapter extends RecyclerView.Adapter<book_adapter.book_image> {
    public  static View_book_list view_book_list = new View_book_list();
    private Context context;
    private List<BookModel> list;

    public book_adapter(Context context, List<BookModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public book_image onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_imageresouce,parent,false);
        return new book_image(v);
    }

    @Override
    public void onBindViewHolder(@NonNull book_image holder, int position) {
        BookModel bookModel = list.get(position);
        holder.book_resouce_title.setText(bookModel.getTitle());
        holder.book_resouce_description.setText(bookModel.getDescription());
        holder.book_resouce_genre.setText(bookModel.getGenre());
        holder.book_resouce_price.setText(String.valueOf(bookModel.getPrice()));
        Picasso.with(context).load(bookModel.getBooktImageUrk())
                .fit()
                .centerCrop()
                .into(holder.book_image);
        holder.book_viewclicker.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                View_book_list book_list = new View_book_list();
             //   UserModel userModel = new UserModel();
                BookModel bookModel1 = new BookModel();
                BookModelduplicate dup_book =new BookModelduplicate();

                dup_book.setId(bookModel.getId());
                dup_book.setTitle(bookModel.getTitle());
                dup_book.setDescription(bookModel.getDescription());
                dup_book.setGenre(bookModel.getGenre());
                dup_book.setPrice(bookModel.getPrice());
                dup_book.setBooktImageUrk(bookModel.getBooktImageUrk());
                Intent intent = new Intent(context , open_single_book.class);
             context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class  book_image extends RecyclerView.ViewHolder{
        public ImageView book_image;
        public RelativeLayout book_viewclicker;
        public TextView book_resouce_title,book_resouce_description,book_resouce_genre,book_resouce_price;
        public book_image(@NonNull View itemView) {
            super(itemView);

            book_resouce_title=(TextView) itemView.findViewById(R.id.book_resouce_title);
            book_resouce_description=(TextView)itemView.findViewById(R.id.book_resouce_description);
            book_resouce_genre=(TextView)itemView.findViewById(R.id.book_resouce_genre);
            book_resouce_price=(TextView)itemView.findViewById(R.id.book_resouce_price);
            book_image =(ImageView)itemView.findViewById(R.id.book_image);
            book_viewclicker=(RelativeLayout)itemView.findViewById(R.id.book_viewclicker);

        }
    }

}
