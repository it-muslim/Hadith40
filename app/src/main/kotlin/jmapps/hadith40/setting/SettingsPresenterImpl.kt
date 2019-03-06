package jmapps.hadith40.setting

import android.content.SharedPreferences
import android.graphics.Color

class SettingsPresenterImpl(
    private val settingsView: SettingsContract.SettingsView?,
    private var editor: SharedPreferences.Editor
) : SettingsContract.SettingsPresenter {

    override fun setTextSize(size: Int) {
        settingsView!!.textSize(size)
        editor.putInt("key_text_size", size).apply()
    }

    override fun setArabicTextColor(progress: Int) {

        var redColor = 0
        var greenColor = 0
        var blueColor = 0

        when {
            progress < 256 -> {
                blueColor = progress
            }
            progress < 256 * 2 -> {
                greenColor = progress % 256
                blueColor = 256 - progress % 256
            }
            progress < 256 * 3 -> {
                greenColor = 255
                blueColor = progress % 256
            }
            progress < 256 * 4 -> {
                redColor = progress % 256
                greenColor = 256 - progress % 256
                blueColor = 256 - progress % 256
            }
            progress < 256 * 5 -> {
                redColor = 255
                greenColor = 0
                blueColor = progress % 256
            }
            progress < 256 * 6 -> {
                redColor = 255
                greenColor = progress % 256
                blueColor = 256 - progress % 256
            }
            progress < 256 * 7 -> {
                redColor = 255
                greenColor = 255
                blueColor = progress % 256
            }
        }

        settingsView!!.arabicTextColor(Color.argb(255, redColor, greenColor, blueColor))
        editor.putInt("key_progress_arabic", progress).apply()
        editor.putInt("key_arabic_color", Color.argb(255, redColor, greenColor, blueColor)).apply()
    }

    override fun setTranslationTextColor(progress: Int) {

        var redColor = 0
        var greenColor = 0
        var blueColor = 0

        when {
            progress < 256 -> {
                blueColor = progress
            }
            progress < 256 * 2 -> {
                greenColor = progress % 256
                blueColor = 256 - progress % 256
            }
            progress < 256 * 3 -> {
                greenColor = 255
                blueColor = progress % 256
            }
            progress < 256 * 4 -> {
                redColor = progress % 256
                greenColor = 256 - progress % 256
                blueColor = 256 - progress % 256
            }
            progress < 256 * 5 -> {
                redColor = 255
                greenColor = 0
                blueColor = progress % 256
            }
            progress < 256 * 6 -> {
                redColor = 255
                greenColor = progress % 256
                blueColor = 256 - progress % 256
            }
            progress < 256 * 7 -> {
                redColor = 255
                greenColor = 255
                blueColor = progress % 256
            }
        }

        settingsView!!.translationTextColor(Color.argb(255, redColor, greenColor, blueColor))
        editor.putInt("key_progress_translation", progress).apply()
        editor.putInt("key_translation_color", Color.argb(255, redColor, greenColor, blueColor)).apply()
    }

    override fun isTextTranslationShowing(state: Boolean) {
        if (state) {
            settingsView!!.hideTextTranslation()
        } else {
            settingsView!!.showTextTranslation()
        }
        editor.putBoolean("key_is_text_translation", state).apply()
    }
}