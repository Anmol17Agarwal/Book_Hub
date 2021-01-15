package com.example.bookhub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.bookhub.R
import com.example.bookhub.adaptor.FavouriteRecyclerAdaptor
import com.example.bookhub.database.BookDatabase
import com.example.bookhub.database.BookEntities


class FavouriteFragment : Fragment() {

    lateinit var recyclerFavourite:RecyclerView
    lateinit var progressLayout:RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdaptor: FavouriteRecyclerAdaptor
    var dbBookList = listOf<BookEntities>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_favourite, container, false)


       recyclerFavourite =view.findViewById(R.id.recylcerFavourite)

        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        //layoutManager = GridLayoutManager(activity as Context,2)
        dbBookList = RetrieveFavourite(activity as Context).execute().get()

        if (activity != null)
        {
            layoutManager = GridLayoutManager(activity as Context,2)
            progressLayout.visibility = View.GONE
            recyclerAdaptor = FavouriteRecyclerAdaptor(activity as Context,dbBookList)
            recyclerFavourite.adapter = recyclerAdaptor
            recyclerFavourite.layoutManager =layoutManager
        }

        return view

    }

    class RetrieveFavourite(val context: Context):AsyncTask<Void,Void,List<BookEntities>>(){
        override fun doInBackground(vararg params: Void?): List<BookEntities> {

        val db = Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
            return db.bookDao().getAllBooks()
        }

    }
}