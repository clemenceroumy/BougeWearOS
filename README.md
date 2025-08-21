## MOCK WATCH
Starting a walk : 
```bash
adb shell am broadcast -a "whs.synthetic.user.START_WALKING" com.google.android.wearable.healthservices
```

Stopping a walk : 
```bash
adb shell am broadcast -a "whs.synthetic.user.STOP_EXERCISE" com.google.android.wearable.healthservices
```

## HEALTH SERVICES
onNewDataPointsReceived() method delay emitting value :
- With Service (allowing to retrieve value on Background) : at least 1 minute 
- With Callback (only retrieve when app is in foreground) : almost immediate (for a walk, get step instantly)