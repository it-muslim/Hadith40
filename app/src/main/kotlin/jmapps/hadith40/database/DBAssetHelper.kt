package jmapps.hadith40.database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import jmapps.hadith40.R

const val DBVersion = 1

class DBAssetHelper(context: Context?) :
        SQLiteAssetHelper(context, context!!.getString(R.string.db_name), null, DBVersion) {

    override fun setForcedUpgrade(version: Int) {
        super.setForcedUpgrade(DBVersion)
    }
}