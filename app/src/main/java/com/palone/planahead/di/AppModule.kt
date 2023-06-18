package com.palone.planahead.di

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.PlanAheadDatabase
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.domain.useCase.DateFeaturesUseCase
import com.palone.planahead.services.alarms.AlarmsHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDb(app: Application) =
        Room.databaseBuilder(
            app.applicationContext,
            PlanAheadDatabase::class.java,
            "tasks.db"
        ).build()

    @Provides
    @Singleton
    fun alarmManager(app: Application): AlarmManager =
        app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @Provides
    @Singleton
    fun alarmsHandler(app: Application, alarmManager: AlarmManager) =
        AlarmsHandler(app.applicationContext, alarmManager)

    @Provides
    @Singleton
    fun provideDateFeaturesUseCase(): DateFeaturesUseCase = DateFeaturesUseCase()

    @Provides
    @Singleton
    fun provideTaskRepository(db: PlanAheadDatabase) = TaskRepository(db.taskDao)

    @Provides
    @Singleton
    fun provideAlertRepository(db: PlanAheadDatabase) = AlertRepository(db.alertDao)
}