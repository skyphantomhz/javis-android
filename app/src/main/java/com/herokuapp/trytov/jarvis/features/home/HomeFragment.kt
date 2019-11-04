package com.herokuapp.trytov.jarvis.features.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognizerIntent
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import com.herokuapp.trytov.jarvis.R
import com.herokuapp.trytov.jarvis.data.model.PackageResponse
import com.herokuapp.trytov.jarvis.extensions.setHideKeyBoardListener
import com.herokuapp.trytov.jarvis.util.TextReader
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import android.view.inputmethod.InputMethodManager


class HomeFragment : Fragment(), HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter
    lateinit var mContext: Context
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.herokuapp.trytov.jarvis.R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextInputAction()
        btn_speak.setOnClickListener {
            tv_text_output?.text = ""
            promptSpeechInput()
        }
        layout_backgroud.setHideKeyBoardListener()
    }

    private fun initTextInputAction() {
        tv_text_input.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (event?.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyBoard()
                    tv_text_output?.text = ""
                    presenter.sendResultAfterResolveVoice(tv_text_input.text.toString())
                }
                return false
            }
        })
        tv_text_input.setOnClickListener{detachChange = true}
    }

    private fun hideKeyBoard(){
        val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun dialogWarningDeviceNotSupport(){
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle(R.string.dialog_warning)
        builder.setIcon(android.R.drawable.stat_sys_warning)
        builder.setCancelable(false)
        builder.setMessage(R.string.dialog_message_device_not_support_speed_to_text)
                .setPositiveButton(R.string.ok, { _, _ ->
                    presenter.directToGooglePlayDownloadAppSupport()
                })
                .setNegativeButton(R.string.cancel, { _, _ ->
                    activity!!.finish()
                })
        builder.create().show()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun gotoSetting(){
        startActivityForResult(Intent(Settings.ACTION_VOICE_INPUT_SETTINGS), 0)
    }

    override fun setTextInput(textInput: String) {
        tv_text_input.setText(textInput, TextView.BufferType.EDITABLE)
        tv_text_input.post({
            if(!detachChange){
                presenter.sendResultAfterResolveVoice(textInput)
            }
        })
    }

    /**
     * Showing google speech input dialog
     */
    private fun promptSpeechInput() {
        detachChange = false
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt))
        try {
            startActivityForResult(intent, presenter.getReqCodeSpeechInput())
        } catch (a: ActivityNotFoundException) {
            presenter.deviceNotsupport()
            Toast.makeText(activity,
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun getContextSource() = context

    override fun showErrorByToast(messegeError: String) {
        Toast.makeText(activity, messegeError, Toast.LENGTH_SHORT).show()
    }


    override fun directToGooglePlayFullLink(appPackageName: String){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)))
    }

    override fun directToGooglePlayShortLink(appPackageName: String){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)))
    }

    /**
     * Receiving speech input
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.resolveTextInput(requestCode,resultCode,data)
    }

    override fun outputText(data: PackageResponse) {
        tv_text_output.text = data.textResponse
        outputVoice(data)
    }

    private fun outputVoice(data: PackageResponse){
        TextReader.getInstance().speak(data.textResponse)
    }

    companion object {
        fun newInstance() = HomeFragment()
        private var detachChange: Boolean = false
    }
}
