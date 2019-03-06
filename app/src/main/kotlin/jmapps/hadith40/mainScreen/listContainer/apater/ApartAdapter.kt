package jmapps.hadith40.mainScreen.listContainer.apater

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.hadith40.R
import jmapps.hadith40.mainScreen.listContainer.holder.ApartHolder
import jmapps.hadith40.mainScreen.listContainer.model.ApartModel

class ApartAdapter(
    private val context: Context?,
    private val apartModel: List<ApartModel>,
    private val onItemClicksApart: OnItemClicksApart
) : RecyclerView.Adapter<ApartHolder>() {

    interface OnItemClicksApart {
        fun onItemClickApart(apartHolder: ApartHolder, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartHolder {
        return ApartHolder(LayoutInflater.from(context).inflate(R.layout.item_apart, parent, false))
    }

    override fun onBindViewHolder(holder: ApartHolder, position: Int) {

        val strContentArabic: String = apartModel[position].contentArabic!!
        holder.tvContentArabicApart.text = strContentArabic

        if (apartModel[position].contentTranslation != null) {
            val strContentTranslation: String? = apartModel[position].contentTranslation!!
            holder.tvContentTranslationApart.visibility = View.VISIBLE
            holder.tvContentTranslationApart.text = Html.fromHtml(strContentTranslation)
        } else {
            holder.tvContentTranslationApart.visibility = View.GONE
        }

        holder.setTextSize()
        holder.setArabicTextColor()
        holder.setTranslationTextColor()
        holder.isTextTranslate()

        holder.findOnItemClick(onItemClicksApart, holder, position)
    }

    override fun getItemCount(): Int {
        return apartModel.size
    }
}