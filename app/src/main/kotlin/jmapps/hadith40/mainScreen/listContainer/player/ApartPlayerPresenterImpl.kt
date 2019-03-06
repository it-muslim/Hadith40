package jmapps.hadith40.mainScreen.listContainer.player

import android.content.Context
import android.media.MediaPlayer
import jmapps.hadith40.mainScreen.listContainer.model.ApartModel

class ApartPlayerPresenterImpl(private val context: Context?) :
    ApartPlayerContract.Presenter {

    private var player: MediaPlayer? = null

    override fun playOnlyTrack(apartModel: List<ApartModel>, idPosition: Int) {
        clearPlayer()
        val resID = context!!.resources.getIdentifier(
            "${apartModel[idPosition].nameAudio}", "raw", "jmapps.hadith40"
        )
        player = MediaPlayer.create(context, resID)
        player!!.start()
    }

    override fun playAllTracks(apartModel: List<ApartModel>, idPosition: Int) {
    }

    override fun clearPlayer() {
        if (player != null) {
            player!!.stop()
            player!!.reset()
        }
    }
}