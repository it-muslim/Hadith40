package jmapps.hadith40.mainScreen.chapters.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import jmapps.hadith40.mainScreen.chapters.adapter.ChapterListAdapter
import kotlinx.android.synthetic.main.item_chapter.view.*

class ChapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvHadeethNumber = itemView.tv_hadeeth_number_chapter!!
    val tvHadeethTitle = itemView.tv_hadeeth_title_chapter!!

    fun findOnItemClick(
        onItemClicks: ChapterListAdapter.OnItemClicksChapter,
        chapterHolder: ChapterHolder,
        position: Int) {
        itemView.setOnClickListener {
            onItemClicks.onItemClickChapter(chapterHolder, position)
        }
    }
}