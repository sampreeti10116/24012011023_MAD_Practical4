# Practical-4: Android Alarm Application using Service & BroadcastReceiver

## Aim

To create an Android Alarm application using **Service** and **BroadcastReceiver**. The application allows the user to select an alarm time and plays an alarm sound when the scheduled time is reached.

## Objectives

1. Create the MainActivity according to the given UI design.
2. Create an `AlarmBroadcastReceiver` class.
3. Create an `AlarmService` class.
4. Add the `SCHEDULE_EXACT_ALARM` permission in the Manifest file.
5. Schedule an alarm using `AlarmManager`.
6. Use `PendingIntent` to trigger the BroadcastReceiver.
7. Start an Alarm Service when the alarm is triggered.
8. Play the alarm sound using `MediaPlayer`.
9. Stop the Alarm Service when required.

## Concepts Covered

- BroadcastReceiver
- Service
- TextClock
- TimePickerDialog
- Calendar Class
- SimpleDateFormat Class
- PendingIntent
- AlarmManager
- `getSystemService()`
- `sendBroadcast()`
- MediaPlayer
- `startService()`
- `stopService()`
- `Intent.getStringExtra()`
- `Intent.putStringExtra()`
- MaterialCardView
- Android Manifest permissions
- Exact alarms
- Alarm scheduling

## 1. MainActivity

`MainActivity` provides the user interface for the alarm application.

The UI can contain:

- Current time display using `TextClock`
- Alarm time display
- Button/card to select alarm time
- Set Alarm button
- Stop/Cancel Alarm button
- MaterialCardView components for organizing the interface

The `TimePickerDialog` is used to allow the user to select the desired hour and minute.

## 2. TextClock

`TextClock` is an Android widget used to display the current time.

Example:

```xml
<TextClock
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:format12Hour="hh:mm:ss a"
    android:format24Hour="HH:mm:ss" />
```

It automatically updates as the system time changes.

## 3. TimePickerDialog

`TimePickerDialog` allows the user to select an hour and minute.

Example:

```kotlin
val timePickerDialog = TimePickerDialog(
    this,
    { _, hour, minute ->
        // Store selected time
    },
    hour,
    minute,
    false
)

timePickerDialog.show()
```

## 4. Calendar Class

The `Calendar` class can be used to create and manipulate the date and time at which an alarm should trigger.

Example:

```kotlin
val calendar = Calendar.getInstance()

calendar.set(Calendar.HOUR_OF_DAY, hour)
calendar.set(Calendar.MINUTE, minute)
calendar.set(Calendar.SECOND, 0)
```

The calculated time can then be supplied to `AlarmManager`.

## 5. SimpleDateFormat

`SimpleDateFormat` can be used to format a selected alarm time for displaying it to the user.

Example:

```kotlin
val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
val formattedTime = format.format(calendar.time)
```

## 6. AlarmManager

`AlarmManager` is an Android system service used to schedule operations for a particular time.

Example:

```kotlin
val alarmManager =
    getSystemService(Context.ALARM_SERVICE) as AlarmManager
```

An exact alarm can be scheduled using an appropriate `AlarmManager` API.

## 7. PendingIntent

A `PendingIntent` allows the Android system to execute a specified Intent at a later time.

Example:

```kotlin
val pendingIntent = PendingIntent.getBroadcast(
    this,
    0,
    intent,
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
)
```

The PendingIntent is associated with the alarm and is triggered when the scheduled time is reached.

## 8. BroadcastReceiver

`AlarmBroadcastReceiver` receives the broadcast generated when the alarm is triggered.

Example structure:

```kotlin
class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Start alarm service
    }
}
```

The BroadcastReceiver acts as the connection between `AlarmManager` and `AlarmService`.

## 9. AlarmService

The `AlarmService` performs the alarm-related work after the BroadcastReceiver receives the alarm broadcast.

Example:

```kotlin
class AlarmService : Service() {

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        // Play alarm sound
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
```

## 10. MediaPlayer

`MediaPlayer` is used to play the alarm sound.

The alarm audio file should be placed in:

```text
app/src/main/res/raw/
```

For example:

```text
alarm.mp3
```

The sound can be loaded using:

```kotlin
val mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
mediaPlayer.start()
```

When the alarm is stopped, the MediaPlayer should be released properly.

## 11. Starting and Stopping Service

The Alarm Service can be started using:

```kotlin
startService(Intent(this, AlarmService::class.java))
```

It can be stopped using:

```kotlin
stopService(Intent(this, AlarmService::class.java))
```

For modern Android versions, background execution restrictions should be considered when starting services.

## 12. Intent.putStringExtra()

Data can be passed between Android components using Intent extras.

Example:

```kotlin
intent.putStringExtra("alarmTime", "07:30 AM")
```

This stores the alarm time inside the Intent.

## 13. Intent.getStringExtra()

The stored value can be retrieved using:

```kotlin
val alarmTime = intent.getStringExtra("alarmTime")
```

This allows the BroadcastReceiver or Service to receive information from the sending component.

## 14. sendBroadcast()

`sendBroadcast()` is used to send a broadcast Intent to interested BroadcastReceivers.

Example:

```kotlin
sendBroadcast(intent)
```

For this practical, `AlarmManager` and `PendingIntent` are used to trigger the alarm BroadcastReceiver at the scheduled time.

## 15. getSystemService()

Android provides system-level services through `getSystemService()`.

Example:

```kotlin
val alarmManager =
    getSystemService(Context.ALARM_SERVICE) as AlarmManager
```

It can be used to access services such as:

- AlarmManager
- NotificationManager
- PowerManager
- ConnectivityManager

## 16. MaterialCardView

`MaterialCardView` is used to create visually structured cards in the application's interface.

Example:

```xml
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <!-- Alarm UI -->

</com.google.android.material.card.MaterialCardView>
```

It can be used to group the current time, selected alarm time, and controls.

## 17. Manifest Permission

The application requires the following permission for exact alarms:

```xml
<uses-permission
    android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```

The permission should be declared in `AndroidManifest.xml`.

Depending on the Android version and application target, exact-alarm access may also need to be enabled/checked through the system settings before scheduling an exact alarm.

## 18. Android Manifest Components

The BroadcastReceiver and Service should be declared in the application manifest.

Example:

```xml
<receiver
    android:name=".AlarmBroadcastReceiver"
    android:exported="false" />

<service
    android:name=".AlarmService"
    android:exported="false" />
```

## 19. Application Flow

```text
                  MainActivity
                       |
                       v
               Select Alarm Time
                       |
                       v
                TimePickerDialog
                       |
                       v
                  Calendar
                       |
                       v
                  AlarmManager
                       |
                       v
                PendingIntent
                       |
             Scheduled Time Reached
                       |
                       v
             AlarmBroadcastReceiver
                       |
                       v
                  AlarmService
                       |
                       v
                  MediaPlayer
                       |
                       v
                 Alarm Sound
```

## 20. Alarm Audio Resource

The provided `alarm.mp3` file should be added to:

```text
app/src/main/res/raw/alarm.mp3
```

It can then be accessed in Kotlin using:

```kotlin
R.raw.alarm
```

## 21. Expected Application Behavior

1. The application opens and displays the current time.
2. The user selects an alarm time.
3. The selected time is displayed on the screen.
4. The user sets the alarm.
5. `AlarmManager` schedules the alarm.
6. At the scheduled time, the `PendingIntent` triggers `AlarmBroadcastReceiver`.
7. The BroadcastReceiver starts `AlarmService`.
8. `AlarmService` uses `MediaPlayer` to play `alarm.mp3`.
9. The user can stop the alarm, causing the service and audio playback to stop.

## 22. Learning Outcomes

After completing this practical, the student will be able to:

- Understand Android Services.
- Understand BroadcastReceivers.
- Schedule tasks using AlarmManager.
- Create and use PendingIntent.
- Work with Calendar and SimpleDateFormat.
- Use TimePickerDialog for time selection.
- Display current time using TextClock.
- Start and stop an Android Service.
- Play audio using MediaPlayer.
- Pass data using Intent extras.
- Use `putStringExtra()` and `getStringExtra()`.
- Understand `getSystemService()`.
- Use MaterialCardView in an Android UI.
- Configure exact-alarm permissions in the Manifest.
- Understand communication between Activity, AlarmManager, BroadcastReceiver, and Service.

## Conclusion

Practical-4 demonstrates the development of an Android Alarm application using **AlarmManager, PendingIntent, BroadcastReceiver, and Service**. The practical also introduces time and date handling, audio playback using MediaPlayer, Intent data transfer, system services, MaterialCardView, and exact-alarm permissions. These concepts provide a foundation for developing Android applications that perform scheduled background operations.
