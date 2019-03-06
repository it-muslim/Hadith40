package jmapps.hadith40.mainScreen.player

interface PlayerContract {

    interface PlayerView {

        fun playButtonState(state: Boolean)

        fun loopButtonState(state: Boolean)

        fun currentTrackTime(trackTime: String)

        fun totalTrackTime(trackTime: String)
    }

    interface PlayerPresenter {

        fun initPlayer()

        fun playTrack(playState: Boolean)

        fun loopTrack(loopState: Boolean)

        fun clearPlayer()
    }
}