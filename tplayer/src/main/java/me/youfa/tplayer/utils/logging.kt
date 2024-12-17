package me.youfa.tplayer.utils

// use android Log class for logging
import android.util.Log

class Logging {


    companion object {
        private const val TAG = "me.youfa.Logging"
        fun log(message: String) {
            Log.d(TAG, message)
        }

        // log a message with a tag
        fun log(tag: String, message: String) {
            Log.d(tag, message)
        }

        // log a message with a tag and a throwable
        fun log(tag: String, message: String, throwable: Throwable) {
            Log.d(tag, message, throwable)
        }
    }
}