package com.example.paragonapp;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.WeakReference;

public class SendEmail implements Runnable {
    private WeakReference<Activity> activityCopy;
    private String email, password;

    public SendEmail(Activity activity, String email, String password) {
        this.activityCopy = new WeakReference<Activity>(activity);
        this.email = email;
        this.password = password;
    }
    public void run() {

        try {
            GMailSender sender = new GMailSender(email, password);
            sender.sendMail("This is Subject",
                    "This is Body",
                    email,
                    "naters@byui.edu");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        final Activity activity = activityCopy.get();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
}
