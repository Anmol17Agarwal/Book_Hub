package com.example.bookhub.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.R
import com.example.bookhub.activity.DescriptiveActivity
import com.example.bookhub.model.Book
import com.squareup.picasso.Picasso

class DashboardRecyclerAdaptor(val context:Context,val itemList :ArrayList<Book>) :RecyclerView.Adapter<DashboardRecyclerAdaptor.DashboardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return (DashboardViewHolder(view))
    }

    override fun getItemCount(): Int {
            return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtBookName.text = book.bookName
        holder.textBookAuthor.text = book.bookAuthor
        holder.txtBookCost.text = book.bookPrice
        holder.txtBookRating.text = book.bookRating
        //holder.imgBookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookImage)
        holder.llContext.setOnClickListener {
            Toast.makeText(context,"Clicked on ${holder.txtBookName.text}",Toast.LENGTH_SHORT)
            val intent = Intent(context,DescriptiveActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }
            }

    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtBookName:TextView = view.findViewById(R.id.txtBookName)
        val textBookAuthor:TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookCost:TextView = view.findViewById(R.id.txtBookPrice)
        val txtBookRating:TextView = view.findViewById(R.id.txtBookRating)
        val imgBookImage:ImageView = view.findViewById(R.id.imgBookImage)
        val llContext:LinearLayout = view.findViewById(R.id.llcontent)
    }


}