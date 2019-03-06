package jmapps.hadith40.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import jmapps.hadith40.mainScreen.listContainer.model.ApartModel

class ApartPresenterImpl(
    private var context: Context?,
    private var listContentView: ApartContract.ListContentView?,
    private var sectionNumber: Int?
) : ApartContract.DatabasePresenter {

    private lateinit var sqLiteDatabase: SQLiteDatabase

    private var strHadeethNumber: String? = null
    private var strHadeethTitle: String? = null

    // Apart head
    @SuppressLint("Recycle")
    override fun getMainContent() {

        sqLiteDatabase = DBAssetHelper(context).readableDatabase

        try {

            val cursor: Cursor = sqLiteDatabase.query(
                "Table_of_chapters",
                arrayOf("NumberHadeeth", "TitleHadeeth"),
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

                listContentView!!.setHadeethNumber(strHadeethNumber!!)
                listContentView!!.setHadeethTitle(strHadeethTitle!!)
            }

            if (cursor.isClosed) {
                cursor.close()
            }

        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    // Apart list
    override val getApartList: List<ApartModel>
        @SuppressLint("Recycle") get() {

            sqLiteDatabase = DBAssetHelper(context).readableDatabase

            val cursor: Cursor = sqLiteDatabase.query(
                "Table_of_cut", null, "ContentPosition = $sectionNumber",
                null, null, null, null, null
            )

            val allApartList = ArrayList<ApartModel>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val apart = ApartModel()
                    apart.positionId = cursor.getString(cursor.getColumnIndex("_id"))
                    apart.contentArabic = cursor.getString(cursor.getColumnIndex("ContentArabic"))
                    apart.contentTranslation = cursor.getString(cursor.getColumnIndex("ContentTranslation"))
                    apart.nameAudio = cursor.getString(cursor.getColumnIndex("NameAudio"))
                    allApartList.add(apart)
                    cursor.moveToNext()

                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return allApartList
        }
}