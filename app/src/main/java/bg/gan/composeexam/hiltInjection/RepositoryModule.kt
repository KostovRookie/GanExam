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
    //advantage of using @Binds is that it reduces the amount of code generated (such as Module Factory classes).
    // Less code to generate means the Kapt plugin has less work to do which can speed up build times in larger projects.

}