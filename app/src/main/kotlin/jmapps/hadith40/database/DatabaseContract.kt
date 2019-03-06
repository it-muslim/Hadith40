package jmapps.hadith40.database

import android.content.SharedPreferences
import jmapps.hadith40.mainScreen.chapters.model.ChapterListModel
import jmapps.hadith40.mainScreen.favorites.model.FavoriteListModel

interface DatabaseContract {

    interface MainContentView {

        fun setHadeethNumber(hadeethNumber: String)

        fun setHadeethTitle(hadeethTitle: String)

        fun setContentArabic(contentArabic: String)

        fun setContentTranslation(contentTranslation: String)

        fun setBookmarkState(state: Boolean)
    }

    interface DatabasePresenter {

        fun getMainContent(sectionNumber: Int?)

        val getFavoriteList: List<FavoriteListModel>

        val getChapterList: List<ChapterListModel>

        fun addRemoveFavorite(isChecked: Boolean, sectionNumber: Int?, mEditor: SharedPreferences.Editor)

        fun shareContent()
    }
}