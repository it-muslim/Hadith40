package jmapps.hadith40.database

import jmapps.hadith40.mainScreen.listContainer.model.ApartModel

interface ApartContract {

    interface ListContentView {

        fun setHadeethNumber(hadeethNumber: String)

        fun setHadeethTitle(hadeethTitle: String)
    }

    interface DatabasePresenter {

        fun getMainContent()

        val getApartList: List<ApartModel>
    }
}