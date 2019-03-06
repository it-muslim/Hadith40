package jmapps.hadith40.setting

interface SettingsContract {

    interface SettingsView {

        fun textSize(size: Int)

        fun arabicTextColor(color: Int)

        fun translationTextColor(color: Int)

        fun hideTextTranslation()

        fun showTextTranslation()
    }

    interface SettingsPresenter {

        fun setTextSize(size: Int)

        fun setArabicTextColor(progress: Int)

        fun setTranslationTextColor(progress: Int)

        fun isTextTranslationShowing(state: Boolean)
    }
}