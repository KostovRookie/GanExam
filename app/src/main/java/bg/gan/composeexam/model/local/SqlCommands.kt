package bg.gan.composeexam.model.local

import androidx.lifecycle.LiveData
import androidx.room.*


@Database(entities = [Bookmarks::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookmarksDao(): BookmarksDao
}

@Dao
interface BookmarksDao {

    //simple SQL commands to work with Room database, to order, delete, add users to table

    @Query("SELECT * FROM bookmarks_table ORDER BY id ASC")
    fun getBookmarks(): LiveData<List<Bookmarks>>

    @Query("SELECT * FROM bookmarks_table WHERE id = :id")
    fun getBookmarksById(id: Int): LiveData<Bookmarks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmarks: Bookmarks)

    @Query("DELETE FROM bookmarks_table")
    suspend fun deleteTable()

    @Query("DELETE FROM bookmarks_table WHERE login = :login")
    suspend fun deleteUser(login: String)

    @Query("SELECT EXISTS(SELECT * FROM bookmarks_table WHERE login = :login)")
    fun contains(login: String): LiveData<Boolean>
}