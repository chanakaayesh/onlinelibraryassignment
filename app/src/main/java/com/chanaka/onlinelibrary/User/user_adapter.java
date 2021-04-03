package com.chanaka.onlinelibrary.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chanaka.onlinelibrary.R;
import com.chanaka.onlinelibrary.book.book_adapter;
import com.chanaka.onlinelibrary.dbhadler.BookModel;
import com.chanaka.onlinelibrary.dbhadler.BorrowbookModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class user_adapter extends RecyclerView.Adapter<user_adapter.borrrowbook_image> {

    private Context context;
    private List<BorrowbookModel> borrowlist;

    public user_adapter(Context context, List<BorrowbookModel> borrowlist) {
        this.context = context;
        this.borrowlist = borrowlist;
    }
    @NonNull
    @Override
    public borrrowbook_image onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.borrowbook_imageresource,parent,false);
        return new user_adapter.borrrowbook_image(v);
    }

    @Override
    public void onBindViewHolder(@NonNull borrrowbook_image holder, int position) {
       BorrowbookModel borrowbookModel = borrowlist.get(position);
     holder.borrowbooktitle.setText(borrowbookModel.getBookTitle());
        holder.date.setText(borrowbookModel.getDate());
        holder. reminingdays.setText(String.valueOf(borrowbookModel.getReminingdays()));
        Picasso.with(context).load(borrowbookModel.getBookimageUrl())
                .fit()
                .centerCrop()
                .into(holder. borrowimage);
    }

    @Override
    public int getItemCount() {
        return borrowlist.size();
    }



    public class borrrowbook_image extends RecyclerView.ViewHolder {
        public ImageView borrowimage;
        public TextView borrowbooktitle,date,reminingdays;
        public borrrowbook_image(@NonNull View itemView) {
            super(itemView);

            borrowimage=(ImageView)itemView.findViewById(R.id.borrowbook_image);
            borrowbooktitle=(TextView)itemView.findViewById(R.id.borrowbook_title);
            date=(TextView)itemView.findViewById(R.id.borrowbook_Date);
            reminingdays=(TextView)itemView.findViewById(R.id.borrowbook_reminingdays);
        }
    }
}

