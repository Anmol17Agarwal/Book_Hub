package com.example.bookhub.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.R
import com.example.bookhub.database.BookEntities
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdaptor(val context:Context,val bookList:List<BookEntities>):RecyclerView.Adapter<FavouriteRecyclerAdaptor.FavouriteViewHolder>() {
    class FavouriteViewHolder(view:View):RecyclerView.ViewHolder(view){
        val txtBookName: TextView = view.findViewById(R.id.txtBookName)
        val textBookAuthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookCost: TextView = view.findViewById(R.id.txtBookPrice)
        val txtBookRating: TextView = view.findViewById(R.id.txtBookRating)
        val imgBookImage: ImageView = view.findViewById(R.id.imgBookImage)
        val llContext: LinearLayout = view.findViewById(R.id.llcontent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {

        val view =LayoutInflater.from(parent.context).inflate(R.layout.recycler_favourite_single_row,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {

        return bookList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {

        val book = bookList[position]
        holder.txtBookName.text = book.bookName
        holder.textBookAuthor.text = book.bookAuthor
        holder.txtBookCost.text = book.bookCost
        holder.txtBookRating.text = book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookImage)
    }

}