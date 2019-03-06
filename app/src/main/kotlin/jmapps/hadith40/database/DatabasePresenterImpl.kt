package jmapps.hadith40.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.Html
import android.widget.Toast
import jmapps.hadith40.R
import jmapps.hadith40.mainScreen.chapters.model.ChapterListModel
import jmapps.hadith40.mainScreen.favorites.model.FavoriteListModel

class DatabasePresenterImpl(
    private var context: Context?,
    private var mainContentView: DatabaseContract.MainContentView?
) :
    DatabaseContract.DatabasePresenter {

    private lateinit var sqLiteDatabase: SQLiteDatabase

    private var strHadeethNumber: String? = null
    private var strHadeethTitle: String? = null
    private var strContentArabic: String? = null
    private var strContentTranslation: String? = null

    // Main content
    @SuppressLint("Recycle")
    override fun getMainContent(sectionNumber: Int?) {

        sqLiteDatabase = DBAssetHelper(context).readableDatabase

        try {

            val cursor: Cursor = sqLiteDatabase.query(
                "Table_of_chapters",
                arrayOf("NumberHadeeth", "TitleHadeeth", "ContentArabic", "ContentTranslation"),
                "_id = ?",
                arrayOf(sectionNumber.toString()),
                null,
                null,
                null,
                null
            )

            if (cursor.moveToFirst()) run {

                strHadeethNumber = cursor.getString(0)
                strHadeethTitle = cursor.getString(1)
                strContentArabic = cursor.getString(2)
                strContentTranslation = cursor.getString(3)

                mainContentView!!.setHadeethNumber(strHadeethNumber!!)
                mainContentView!!.setHadeethTitle(strHadeethTitle!!)
                mainContentView!!.setContentArabic(strContentArabic!!)
                mainContentView!!.setContentTranslation(strContentTranslation!!)
            }

            if (cursor.isClosed) {
                cursor.close()
            }

        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    // Favorite list
    override val getFavoriteList: List<FavoriteListModel>
        @SuppressLint("Recycle") get() {

            sqLiteDatabase = DBAssetHelper(context).readableDatabase

            val cursor: Cursor = sqLiteDatabase.query(
                "Table_of_chapters", null,
                "Favorite = 1", null, null, null, null, null
            )

            val allFavoriteList = ArrayList<FavoriteListModel>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val favorites = FavoriteListModel()
                    favorites.positionId = cursor.getInt(cursor.getColumnIndex("_id"))
                    favorites.hadeethNumber = cursor.getString(cursor.getColumnIndex("NumberHadeeth"))
                    favorites.hadeethTitle = cursor.getString(cursor.getColumnIndex("TitleHadeeth"))
                    allFavoriteList.add(favorites)
                    cursor.moveToNext()

                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return allFavoriteList
        }

    // Chapter list
    override val getChapterList: List<ChapterListModel>
        @SuppressLint("Recycle") get() {

            sqLiteDatabase = DBAssetHelper(context).readableDatabase

            val cursor: Cursor = sqLiteDatabase.query(
                "Table_of_chapters", null, null, null, null, null, null, null
            )

            val allChapterList = ArrayList<ChapterListModel>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val chapters = ChapterListModel()
                    chapters.hadeethNumber = cursor.getString(cursor.getColumnIndex("NumberHadeeth"))
                    chapters.hadeethTitle = cursor.getString(cursor.getColumnIndex("TitleHadeeth"))
                    allChapterList.add(chapters)
                    cursor.moveToNext()

                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return allChapterList
        }

    // Add and Remove favorite
    override fun addRemoveFavorite(isChecked: Boolean, sectionNumber: Int?, mEditor: SharedPreferences.Editor) {
        val favoriteState = ContentValues()
        favoriteState.put("Favorite", isChecked)

        mainContentView!!.setBookmarkState(isChecked)
        mEditor.putBoolean("key_favorite_chapter_$sectionNumber", isChecked).apply()

        try {
            sqLiteDatabase = DBAssetHelper(context).readableDatabase
            sqLiteDatabase.update(
                "Table_of_chapters", favoriteState, "_id = ?", arrayOf(sectionNumber.toString())
            )
        } catch (e: java.lang.Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    // Share Hadith
    override fun shareContent() {
        val shareContent = Intent()
        shareContent.action = Intent.ACTION_SEND
        shareContent.type = "text/*"
        shareContent.putExtra(
            Intent.EXTRA_TEXT,
            Html.fromHtml("$strHadeethNumber <br/> $strHadeethTitle <p/> $strContentArabic <p/> $strContentTranslation")
        )
        context!!.startActivity(Intent.createChooser(shareContent, context!!.getString(R.string.share_content_to)))
    }
}
