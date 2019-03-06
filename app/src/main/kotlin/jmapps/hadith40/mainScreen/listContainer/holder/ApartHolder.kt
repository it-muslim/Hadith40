package jmapps.hadith40.mainScreen.listContainer.holder

import android.content.SharedPreferences
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import jmapps.hadith40.mainScreen.listContainer.apater.ApartAdapter
import kotlinx.android.synthetic.main.item_apart.view.*

class ApartHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvContentArabicApart = itemView.tv_content_arabic_apart!!
    val tvContentTranslationApart = itemView.tv_content_translation_apart!!
    private val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)

    fun findOnItemClick(onItemClick: ApartAdapter.OnItemClicksApart, apartHolder: ApartHolder, position: Int) {
        itemView.setOnClickListener {
            onItemClick.onItemClickApart(apartHolder, position)
        }
    }

    fun setTextSize() {
        val textSize = mPreferences.getInt("key_text_size", 16)
        tvContentArabicApart.textSize = textSize.toFloat()
        tvContentTranslationApart.textSize = textSize.toFloat()
    }

    fun setArabicTextColor() {
        val arabicColor = mPreferences.getInt("key_arabic_color", Color.argb(255, 0, 0, 0))
        tvContentArabicApart.setTextColor(arabicColor)
    }

    fun setTranslationTextColor() {
        val translationColor = mPreferences.getInt("key_translation_color", Color.argb(255, 0, 0, 0))
        tvContentTranslationApart.setTextColor(translationColor)
    }

    fun isTextTranslate() {
        val state = mPreferences.getBoolean("key_is_text_translation", false)

        if (state) {
            tvContentTranslationApart.visibility = View.GONE
        } else {
            tvContentTranslationApart.visibility = View.VISIBLE
        }
    }
}