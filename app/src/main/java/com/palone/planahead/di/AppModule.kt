package com.palone.planahead.di

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.PlanAheadDatabase
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.domain.alertEngine.AlertEngine
import com.palone.planahead.domain.taskEngine.TaskEngine
import com.palone.planahead.domain.useCase.DateFeaturesUseCase
import com.palone.planahead.services.alarms.AlarmsHandler
import com.palone.planahead.services.ringtone.RingtoneHandler
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
    fun provideRingtoneHandler(): RingtoneHandler {
        Log.i("Injecting", "Task Engine")
        return RingtoneHandler()
    }

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
    fun provideAlarmManager(app: Application): AlarmManager {
        Log.i("Injecting", "Alarm Manager")
        return app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Provides
    @Singleton
    fun provideAlarmsHandler(app: Application, alarmManager: AlarmManager): AlarmsHandler {
        Log.i("Injecting", "Alarm Handler")
        return AlarmsHandler(app.applicationContext, alarmManager)
    }

    @Provides
    @Singleton
    fun provideDateFeaturesUseCase(): DateFeaturesUseCase = DateFeaturesUseCase()

    @Provides
    @Singleton
    fun provideTaskRepository(db: PlanAheadDatabase) = TaskRepository(db.taskDao)

    @Provides
    @Singleton
    fun provideAlertRepository(db: PlanAheadDatabase) = AlertRepository(db.alertDao)

    @Provides
    @Singleton
    fun provideTaskEngine(
        alarmsHandler: AlarmsHandler,
        alertRepository: AlertRepository,
        taskRepository: TaskRepository
    ): TaskEngine {
        Log.i("Injecting", "Task Engine")
        return TaskEngine(taskRepository, alarmsHandler, alertRepository)
    }

    @Provides
    @Singleton
    fun provideAlertEngine(
        alarmsHandler: AlarmsHandler,
        alertRepository: AlertRepository,
        taskRepository: TaskRepository
    ): AlertEngine {
        Log.i("Injecting", "Task Engine")
        return AlertEngine(taskRepository, alarmsHandler, alertRepository)
    }
}