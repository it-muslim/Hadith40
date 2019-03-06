package jmapps.hadith40.mainScreen.chapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.hadith40.R
import jmapps.hadith40.database.DatabasePresenterImpl
import jmapps.hadith40.mainScreen.chapters.adapter.ChapterListAdapter
import jmapps.hadith40.mainScreen.chapters.holder.ChapterHolder
import kotlinx.android.synthetic.main.fragment_chapters.view.*

class ChaptersFragment : DialogFragment(), ChapterListAdapter.OnItemClicksChapter {

    private lateinit var clickToCurrentPosition: ClickToCurrentPosition

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootChapters: View = inflater.inflate(R.layout.fragment_chapters, container, false)

        val databasePresenter = DatabasePresenterImpl(context, null)
        rootChapters.rv_chapter_list.layoutManager = LinearLayoutManager(context)
        val chapterAdapter = ChapterListAdapter(context, databasePresenter.getChapterList, this)
        rootChapters.rv_chapter_list.adapter = chapterAdapter

        return rootChapters
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            clickToCurrentPosition = context as ClickToCurrentPosition
        } catch (e: ClassCastException) {
            throw ClassCastException("$context implements interface")
        }
    }

    interface ClickToCurrentPosition {
        fun toCurrentPosition(position: Int)
    }

    override fun onItemClickChapter(chapterHolder: ChapterHolder, position: Int) {
        clickToCurrentPosition.toCurrentPosition(position)
        dialog!!.dismiss()
    }
}