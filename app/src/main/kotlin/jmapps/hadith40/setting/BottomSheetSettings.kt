package jmapps.hadith40.setting

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.hadith40.R
import kotlinx.android.synthetic.main.bottom_sheet_settings.*
import kotlinx.android.synthetic.main.bottom_sheet_settings.view.*

class BottomSheetSettings : BottomSheetDialogFragment(), View.OnClickListener, CompoundButton.OnCheckedChangeListener,
    SettingsContract.SettingsView, SeekBar.OnSeekBarChangeListener {

    private lateinit var mPreferences: SharedPreferences
    private lateinit var mEditor: SharedPreferences.Editor

    private var textSize: Int? = null

    private lateinit var settingsPresenter: SettingsPresenterImpl

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootSettings: View = inflater.inflate(R.layout.bottom_sheet_settings, container, false)

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mEditor = mPreferences.edit()

        settingsPresenter = SettingsPresenterImpl(this, mEditor)

        rootSettings.tv_text_size_counter.text = mPreferences.getInt("key_text_size", 16).toString()

        rootSettings.sb_text_color_arabic.progress = mPreferences.getInt("key_progress_arabic", 0)
        rootSettings.sb_text_color_translation.progress = mPreferences.getInt("key_progress_translation", 0)

        rootSettings.cb_showing_text_translation.isChecked = mPreferences.getBoolean("key_is_text_translation", false)

        rootSettings.tv_container_arabic_color.setBackgroundColor(
            mPreferences.getInt("key_arabic_color", Color.argb(255, 0, 0, 0))
        )

        rootSettings.tv_container_translation_color.setBackgroundColor(
            mPreferences.getInt("key_translation_color", Color.argb(255, 0, 0, 0))
        )

        rootSettings.btn_text_size_minus.setOnClickListener(this)
        rootSettings.btn_text_size_plus.setOnClickListener(this)

        rootSettings.sb_text_color_arabic.setOnSeekBarChangeListener(this)
        rootSettings.sb_text_color_translation.setOnSeekBarChangeListener(this)

        rootSettings.cb_showing_text_translation.setOnCheckedChangeListener(this)

        return rootSettings
    }

    override fun onClick(v: View?) {

        textSize = mPreferences.getInt("key_text_size", 16)
        tv_text_size_counter.text = textSize.toString()

        when (v!!.id) {
            R.id.btn_text_size_minus -> {
                if (textSize!! > 12) {
                    settingsPresenter.setTextSize(textSize!! - 1)
                }
            }

            R.id.btn_text_size_plus -> {
                if (textSize!! < 60) {
                    settingsPresenter.setTextSize(textSize!! + 1)
                }
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar!!.id) {
            R.id.sb_text_color_arabic -> {
                settingsPresenter.setArabicTextColor(progress)
            }
            R.id.sb_text_color_translation -> {
                settingsPresenter.setTranslationTextColor(progress)
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView!!.id) {
            R.id.cb_showing_text_translation -> {
                settingsPresenter.isTextTranslationShowing(isChecked)
            }
        }
    }

    override fun textSize(size: Int) {
        tv_text_size_counter.text = size.toString()
    }

    override fun arabicTextColor(color: Int) {
        tv_container_arabic_color.setBackgroundColor(color)
    }

    override fun translationTextColor(color: Int) {
        tv_container_translation_color.setBackgroundColor(color)
    }

    override fun hideTextTranslation() {
        Toast.makeText(context, R.string.settings_text_hide, Toast.LENGTH_LONG).show()
    }

    override fun showTextTranslation() {
        Toast.makeText(context, R.string.settings_text_show, Toast.LENGTH_LONG).show()
    }
}