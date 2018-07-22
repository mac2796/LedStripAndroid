package xyz.mateusztarnowski.homecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac2796 on 02.01.18.
 */

public class SleepAsAndroidReceiver extends BroadcastReceiver {
    private final String SLEEP_TRACKING_STARTED = "com.urbandroid.sleep.alarmclock.SLEEP_TRACKING_STARTED";
    private final String LUCID_CUE = "com.urbandroid.sleep.LUCID_CUE_ACTION";
    private final String SMART_WAKE_IN_45_MIN = "com.urbandroid.sleep.alarmclock.AUTO_START_SLEEP_TRACK";
    private final String ALARM_START = "com.urbandroid.sleep.alarmclock.ALARM_ALERT_START";
    private final String ALARM_SNOOZE = "com.urbandroid.sleep.alarmclock.ALARM_SNOOZE_CLICKED_ACTION";
    private final String ALARM_STOP = "com.urbandroid.sleep.alarmclock.ALARM_ALERT_DISMISS";

    @Override
    public void onReceive(Context context, Intent intent) {
        LedStripController ledStripController = new LedStripController(context, "http://192.168.0.76");

        switch (intent.getAction()) {
            case SLEEP_TRACKING_STARTED:
                onSleepTrackingStarted(ledStripController);
                break;
            case LUCID_CUE:
                onLucidCue(ledStripController);
                break;
            case SMART_WAKE_IN_45_MIN:
                onSmartWakeIn45Min(ledStripController);
                break;
            case ALARM_START:
                ledStripController.changeColor(80, 80, 80);
                //TODO Implement
                break;
            case ALARM_SNOOZE:
                ledStripController.changeColor(10, 10, 10);
                //TODO Implement
                break;
            case ALARM_STOP:
                ledStripController.changeColor(80, 80, 80);
                //TODO Implement
                break;
        }
    }

    private void onSleepTrackingStarted(LedStripController ledStripController) {
        LightSequenceGenerator.generateFadeOutSequence(ledStripController).start();
    }

    private void onLucidCue(LedStripController ledStripController) {
        LightSequenceGenerator.generateLucidDreamSequence(ledStripController).start();
    }

    private void onSmartWakeIn45Min(final LedStripController ledStripController) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LightSequenceGenerator.generateSunriseLightSequence(ledStripController).start();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 45 * 60000);
    }
}
