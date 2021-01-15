package com.example.bookhub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insertBook(bookEntities: BookEntities)

    @Delete
    fun deleteBook(bookEntities: BookEntities)

    @Query("Select * from books")
    fun getAllBooks():List<BookEntities>

    @Query("select * from books where book_id = :bookId")
    fun getBookById(bookId:String):BookEntities
}
