package jmapps.hadith40.mainScreen.listContainer.player

import jmapps.hadith40.mainScreen.listContainer.model.ApartModel

interface ApartPlayerContract {

    interface Presenter {

        fun playOnlyTrack(apartModel: List<ApartModel>, idPosition: Int)

        fun playAllTracks(apartModel: List<ApartModel>, idPosition: Int)

        fun clearPlayer()
    }
}