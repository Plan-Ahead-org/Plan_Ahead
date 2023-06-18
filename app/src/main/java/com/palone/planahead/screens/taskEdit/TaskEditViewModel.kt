package com.palone.planahead.screens.taskEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskPriority
import com.palone.planahead.data.database.task.properties.TaskType
import com.palone.planahead.domain.useCase.DateFeaturesUseCase
import com.palone.planahead.screens.taskEdit.data.AlertProperty
import com.palone.planahead.screens.taskEdit.data.TaskEditUIState
import com.palone.planahead.screens.taskEdit.data.TaskRepeatPeriod
import com.palone.planahead.screens.taskEdit.data.typeFieldProperties.ChoreProperties
import com.palone.planahead.screens.taskEdit.data.typeFieldProperties.CronProperties
import com.palone.planahead.screens.taskEdit.data.typeFieldProperties.OneTimeProperties
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alertRepository: AlertRepository,
    private val dateFeaturesUseCase: DateFeaturesUseCase
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(TaskEditUIState())
    val uiState: StateFlow<TaskEditUIState> = _uiState.asStateFlow()

    private val _selectedTaskType =
        MutableStateFlow(TaskType.ONE_TIME)
    val selectedTaskType: StateFlow<TaskType> = _selectedTaskType.asStateFlow()

    private val _oneTimeProperties =
        MutableStateFlow(OneTimeProperties())
    val oneTimeProperties: StateFlow<OneTimeProperties> = _oneTimeProperties.asStateFlow()

    private val _choreProperties =
        MutableStateFlow(ChoreProperties())
    val choreProperties: StateFlow<ChoreProperties> = _choreProperties.asStateFlow()

    private val _cronProperties =
        MutableStateFlow(CronProperties())
    val cronProperties: StateFlow<CronProperties> = _cronProperties.asStateFlow()

    private val _alertProperties =
        MutableStateFlow(listOf<AlertProperty>())
    val alertProperties: StateFlow<List<AlertProperty>> = _alertProperties.asStateFlow()
    var databaseJob: Job? = null


    fun resetProperties() {
        viewModelScope.launch {
            if ((databaseJob == null) || (databaseJob!!.isCompleted)) { // make sure that this operation won't affect database operations
                _alertProperties.update { emptyList() }
                _oneTimeProperties.update { OneTimeProperties() }
                _choreProperties.update { ChoreProperties() }
                _cronProperties.update { CronProperties() }
            } else {
                delay(500)
                resetProperties() // check if database operations are completed
            }
        }
    }

    fun createDatabaseEntry(type: TaskType = _selectedTaskType.value, name: String) {
        databaseJob = viewModelScope.launch {
            when (type) {
                TaskType.ONE_TIME -> createOneTimeDatabaseEntry(name = name)
                TaskType.CRON -> createCronDatabaseEntry(name = name)
                TaskType.CHORE -> createChoreDatabaseEntry(name = name)
            }
        }
    }

    private suspend fun createOneTimeDatabaseEntry(
        typeProperties: OneTimeProperties = _oneTimeProperties.value,
        alertProperty: List<AlertProperty> = _alertProperties.value,
        name: String
    ) {
        val millisToTaskEvent = dateFeaturesUseCase.getEpochMillisFromLocalDate(typeProperties.date)
        val task = Task(
            taskId = null,
            taskType = TaskType.ONE_TIME,
            description = name,
            LocalDateTime.now().toString(),
            isCompleted = false,
            priority = TaskPriority.LOW
        )
        val taskId = taskRepository.upsert(task)
        addAlertsToDatabase(alertProperty, millisToTaskEvent, taskId)
    }

    private suspend fun addAlertsToDatabase(
        alertProperty: List<AlertProperty>,
        millisToTaskEvent: Long,
        taskId: Long,
        interval: Long = 0,
    ) {
        alertProperty.forEach {
            val alertMillisDuration =
                Duration.of(
                    it.offsetValue.toLong(),
                    it.offsetUnit
                ).toMillis()
            val millisToNotifyBeforeTaskEvent = millisToTaskEvent - alertMillisDuration
            val alert = Alert(
                taskId = taskId.toInt(),
                alertTypeName = it.type,
                alertTriggerName = AlertTrigger.TIME,
                eventMillisInEpoch = millisToNotifyBeforeTaskEvent,
                interval = interval
            )
            alertRepository.upsert(alert)
        }
    }

    private suspend fun createCronDatabaseEntry(
        typeProperties: CronProperties = _cronProperties.value,
        alertProperty: List<AlertProperty> = _alertProperties.value,
        name: String
    ) {
        val todayDateTime = LocalDateTime.of(LocalDate.now(), typeProperties.dayTime)
        val millisToTaskEvent = dateFeaturesUseCase.getEpochMillisFromLocalDate(todayDateTime)
        val task = Task(
            taskId = null,
            taskType = TaskType.CRON,
            description = name,
            LocalDateTime.now().toString(),
            isCompleted = false,
            priority = TaskPriority.LOW
        )
        val taskId = taskRepository.upsert(task)
        when (typeProperties.repeatPeriod) {
            TaskRepeatPeriod.DAILY -> addAlertsToDatabase(
                alertProperty,
                millisToTaskEvent,
                taskId,
                Duration.of(1, ChronoUnit.DAYS).toMillis()
            )

            TaskRepeatPeriod.WEEKLY -> addWeeklyAlertsToDatabaseByDays(
                taskId,
                typeProperties.daysOfWeek,
                typeProperties.dayTime,
                alertProperty
            )

            TaskRepeatPeriod.MONTHLY -> TODO()
        }
    }

    private suspend fun addWeeklyAlertsToDatabaseByDays(
        taskId: Long,
        daysOfWeek: List<DayOfWeek>,
        dayTime: LocalTime,
        alertProperty: List<AlertProperty>
    ) {
        daysOfWeek.forEach {
            val wantedDay =
                LocalDate.now().plusDays(dateFeaturesUseCase.getDaysToNextDayOfWeek(it).toLong())
            val wantedDayTime = LocalDateTime.of(wantedDay, dayTime)
            addAlertsToDatabase(
                alertProperty,
                dateFeaturesUseCase.getEpochMillisFromLocalDate(wantedDayTime),
                taskId,
                Duration.of(7, ChronoUnit.DAYS).toMillis()
            ) // Weekly
        }
    }

    private fun createChoreDatabaseEntry(
        data: ChoreProperties = _choreProperties.value,
        name: String
    ) {

    }

    fun insertAlertProperty(alert: AlertProperty) {
        _alertProperties.update { _alertProperties.value + listOf(alert) }
    }

    fun deleteAlertProperty(alert: AlertProperty) {
        _alertProperties.update { _alertProperties.value.filter { it != alert } }
    }

    fun deleteAlertProperty(index: Int) {
        _alertProperties.update { _alertProperties.value.filterIndexed { i, _ -> i != index } }
    }

    fun editAlertProperty(index: Int, alert: AlertProperty) {
        val mList = _alertProperties.value.toMutableList()
        mList[index] = alert
        _alertProperties.update { mList }
    }


    fun updateSelectedTaskType(type: TaskType) {
        _selectedTaskType.update { type }
    }

    fun updateIntervalValue(value: Int) {
        _choreProperties.update { _choreProperties.value.copy(intervalValue = value) }
    }

    fun updateIntervalUnit(type: ChronoUnit) {
        _choreProperties.update { _choreProperties.value.copy(intervalType = type) }
    }

    fun updateTaskRepeatMode(mode: TaskRepeatPeriod) {
        _cronProperties.update { _cronProperties.value.copy(repeatPeriod = mode) }
    }

    fun updateDaysOfWeek(days: List<DayOfWeek>) {
        _cronProperties.update { _cronProperties.value.copy(daysOfWeek = days) }
    }

    fun updateRepeatTime(time: LocalTime) {
        _cronProperties.update { _cronProperties.value.copy(dayTime = time) }
    }

    fun updateDateAndTime(dateAndTime: LocalDateTime) {
        _oneTimeProperties.update { _oneTimeProperties.value.copy(date = dateAndTime) }
        _choreProperties.update { _choreProperties.value.copy(date = dateAndTime) }
    }


}