package bg.gan.composeexam.hiltInjection

import android.content.Context
import androidx.room.Room
import bg.gan.composeexam.model.local.AppDatabase
import bg.gan.composeexam.model.local.BookmarksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

//database builder injected with Hilt in repository
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideDao(appDatabase: AppDatabase): BookmarksDao {
        return appDatabase.bookmarksDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase{
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "stupid_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context{
        return appContext
    }

    @Provides
    @Singleton
    fun getCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }
}