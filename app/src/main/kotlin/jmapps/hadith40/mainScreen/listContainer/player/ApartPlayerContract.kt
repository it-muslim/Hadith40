package jmapps.hadith40.mainScreen.listContainer.player

import jmapps.hadith40.mainScreen.listContainer.model.ApartModel

interface ApartPlayerContract {

    interface Presenter {

        fun playOnlyTrack(position: Int)

        fun playAllTracks(position: Int)

        fun clearPlayer()
    }
}