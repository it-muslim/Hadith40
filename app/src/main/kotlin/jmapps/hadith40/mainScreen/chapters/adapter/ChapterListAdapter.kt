package jmapps.hadith40.mainScreen.chapters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.hadith40.R
import jmapps.hadith40.mainScreen.chapters.holder.ChapterHolder
import jmapps.hadith40.mainScreen.chapters.model.ChapterListModel

class ChapterListAdapter(
    private val context: Context?,
    private val chapterModel: List<ChapterListModel>,
    private val onItemClicksChapter: OnItemClicksChapter
) : RecyclerView.Adapter<ChapterHolder>() {

    interface OnItemClicksChapter {
        fun onItemClickChapter(chapterHolder: ChapterHolder, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
        return ChapterHolder(LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false))
    }

    override fun onBindViewHolder(holder: ChapterHolder, position: Int) {

        val strHadeethNumber: String = chapterModel[position].hadeethNumber!!
        val strHadeethTitle: String = chapterModel[position].hadeethTitle!!

        holder.tvHadeethNumber.text = strHadeethNumber
        holder.tvHadeethTitle.text = strHadeethTitle

        holder.findOnItemClick(onItemClicksChapter, holder, position)
    }

    override fun getItemCount(): Int {
        return chapterModel.size
    }
}