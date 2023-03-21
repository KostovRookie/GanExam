package bg.gan.composeexam.hiltInjection

import bg.gan.composeexam.model.repository.MainRepository
import bg.gan.composeexam.model.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

}