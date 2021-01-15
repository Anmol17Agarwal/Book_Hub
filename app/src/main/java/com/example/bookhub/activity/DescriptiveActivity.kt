package com.example.bookhub.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.database.BookDatabase
import com.example.bookhub.database.BookEntities
import com.example.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_descriptive.*
import org.json.JSONObject
import java.lang.Exception

class DescriptiveActivity : AppCompatActivity() {

    lateinit var txtBookName:TextView
    lateinit var txtBookAuthor:TextView
    lateinit var txtBookPrice:TextView
    lateinit var txtBookRating:TextView
    lateinit var txtBookDescription:TextView
    lateinit var txtBookContent:TextView
    lateinit var btnAddToFavourite:Button
    lateinit var imgBookImage:ImageView
    lateinit var progressBar:ProgressBar
    lateinit var progressLayout:RelativeLayout
    lateinit var toolbar: Toolbar

    var bookId:String? = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descriptive)


        txtBookName =findViewById(R.id.txtBookName)
        txtBookAuthor =findViewById(R.id.txtBookAuthor)
        txtBookPrice =findViewById(R.id.txtBookPrice)
        txtBookRating=findViewById(R.id.txtBookRating)
        txtBookDescription =findViewById(R.id.txtBookDescription)
        txtBookContent =findViewById(R.id.txtAboutTheBookStatic)
        imgBookImage =findViewById(R.id.imgBookImage)
        btnAddToFavourite = findViewById(R.id.btnAddToFav)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"

        if(intent!=null)
        {
            bookId =intent.getStringExtra("book_id")
        }
        else{
            finish()
            Toast.makeText(this@DescriptiveActivity,"Some Error has Occured",Toast.LENGTH_SHORT).show()
        }
        if(bookId=="100"){
            finish()
            Toast.makeText(this@DescriptiveActivity,"Some Error has Occured",Toast.LENGTH_SHORT).show()
        }
            val queue = Volley.newRequestQueue(this@DescriptiveActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id",bookId)
        if(ConnectionManager().checkConnectivity(this@DescriptiveActivity)){
            val jsonRequest = object:JsonObjectRequest(Request.Method.POST,url,jsonParams, Response.Listener {
                try{
                    val success = it.getBoolean("success")
                    if(success)
                    {
                        val bookJsonObject = it.getJSONObject("book_data")
                        progressLayout.visibility = View.GONE

                        val bookImageUrl = bookJsonObject.getString("image")
                        Picasso.get().load(bookJsonObject.getString("image"))
                            .error(R.drawable.default_book_cover).into(imgBookImage)
                        txtBookName.text = bookJsonObject.getString("name")
                        txtBookAuthor.text = bookJsonObject.getString("author")
                        txtBookPrice.text = bookJsonObject.getString("price")
                        txtBookRating.text = bookJsonObject.getString("rating")
                        txtBookDescription.text = bookJsonObject.getString("description")

                        val bookEntities =  BookEntities(
                            bookId?.toInt() as Int,
                            txtBookName.text.toString(),
                            txtBookAuthor.text.toString(),
                            txtBookPrice.text.toString(),
                            txtBookRating.text.toString(),
                            txtBookDescription.text.toString(),bookImageUrl)

                        val checkFav = DBAsyncTask(applicationContext,bookEntities,1).execute()
                        val isFav = checkFav.get()
                        if(isFav)
                        {
                            btnAddToFav.text = "Remove from favourites"
                        val favColor = ContextCompat.getColor(applicationContext,R.color.colorfavourite)
                        btnAddToFav.setBackgroundColor(favColor)
                        }
                        else {

                                btnAddToFav.text = "Add to favourites"
                                val noFavColor = ContextCompat.getColor(applicationContext,R.color.colorPrimary)
                            btnAddToFav.setBackgroundColor(noFavColor)
                            }
                        btnAddToFav.setOnClickListener {
                            if (!DBAsyncTask(applicationContext,bookEntities,1).execute().get()) {
                                val async =
                                    DBAsyncTask(applicationContext, bookEntities, 2).execute()
                                val result = async.get()
                                if (result) {
                                    Toast.makeText(
                                        this@DescriptiveActivity, "Book added to favourites",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    btnAddToFav.text = "Remove from favourites"
                                    val favColor = ContextCompat.getColor(
                                        applicationContext,
                                        R.color.colorfavourite
                                    )
                                    btnAddToFav.setBackgroundColor(favColor)
                                } else {
                                    Toast.makeText(
                                        this@DescriptiveActivity,
                                        "Some Error has Occured",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                                else{
                                    val async = DBAsyncTask(applicationContext,bookEntities,3).execute()
                                    val result = async.get()
                                    if(result)
                                    {
                                        Toast.makeText(this@DescriptiveActivity,"Book remove from favourites",Toast.LENGTH_SHORT).show()
                                    btnAddToFav.text = "Add to favourites"
                                        val noFavColor = ContextCompat.getColor(applicationContext,R.color.colorPrimary)
                                        btnAddToFav.setBackgroundColor(noFavColor)
                                    }else{
                                        Toast.makeText(this@DescriptiveActivity,"Some Error has Occured",Toast.LENGTH_SHORT).show()
                                    }

                                }
                            }
                        }





                    else{
                        Toast.makeText(this@DescriptiveActivity,"Some Error has Occured",Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    Toast.makeText(this@DescriptiveActivity,"Some Error has Occured",Toast.LENGTH_SHORT).show()
                }
            },Response.ErrorListener {

                Toast.makeText(this@DescriptiveActivity,"Volley Error $it",Toast.LENGTH_SHORT).show()
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "90a9452f5e2c91"
                    return headers

                }
            }
            queue.add(jsonRequest)
        }
        else{
            val dialog = AlertDialog.Builder(this@DescriptiveActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Setting"){text,Listener ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                finish()
            }
            dialog.setNegativeButton("Exit"){text,Listener ->
                ActivityCompat.finishAffinity(this@DescriptiveActivity)
            }

            dialog.create()
            dialog.show()
        }

    }

    class DBAsyncTask(val context: Context,val bookEntities: BookEntities,val mode:Int): AsyncTask<Void,Void,Boolean>(){
        /*
        Mode1 -> Check DB if the book is favourite or not
        Mode2 ->Save the book into the favourites
        Mode3 -> Remove the favourite book
        * */
        override fun doInBackground(vararg params: Void?): Boolean {
            val db = Room.databaseBuilder(context,BookDatabase::class.java,"book-db").build()
            when(mode)
            {
                1->{
                     //Mode1 -> Check DB if the book is favourite or not

                    val book:BookEntities? = db.bookDao().getBookById(bookEntities.book_id.toString())
                    db.close()
                    return book!=null
                }
                2 ->{
                    //Mode2 ->Save the book into the favourites
                    db.bookDao().insertBook(bookEntities)
                    db.close()
                    return true
                }
                3->{
                    //Mode3 -> Remove the favourite book
                    db.bookDao().deleteBook(bookEntities)
                    db.close()
                    return true
                }



            }


            return false
        }


    }
}
