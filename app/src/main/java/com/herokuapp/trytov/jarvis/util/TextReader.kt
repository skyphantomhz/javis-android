package com.herokuapp.trytov.jarvis.util

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import java.util.*

class TextReader private constructor() {

    init {
        mTextToSpeech = TextToSpeech(mContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                mTextToSpeech.language = Locale.UK
            }
        })
    }

    fun speak(word: String) {
        if (!mTextToSpeech.isSpeaking) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ttsGreater21(word)
            } else {
                ttsUnder20(word)
            }
        }
    }

    fun setSpeechRate(rate: Float) {
        mTextToSpeech.setSpeechRate(rate)
    }

    //-------------------
    private fun ttsUnder20(text: String) {
        val map = HashMap<String, String>()
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId")
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, map)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun ttsGreater21(text: String) {
        val utteranceId = this.hashCode().toString() + ""
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    companion object {

        private var sInstance: TextReader? = null

        private var mContext: Context? = null
        private lateinit var mTextToSpeech: TextToSpeech

        fun getInstance(): TextReader{
            return sInstance?: TextReader().apply { sInstance = this }
        }
        fun initSpeaker(context: Context) {
            mContext = context
        }
    }
}
