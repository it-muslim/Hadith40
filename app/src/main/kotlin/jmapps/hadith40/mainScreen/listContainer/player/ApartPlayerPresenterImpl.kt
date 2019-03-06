package jmapps.hadith40.mainScreen.listContainer.player

import android.content.Context
import android.media.MediaPlayer
import androidx.recyclerview.widget.RecyclerView
import jmapps.hadith40.mainScreen.listContainer.model.ApartModel

class ApartPlayerPresenterImpl(
    private val context: Context?,
    private var apartModel: List<ApartModel>,
    private val rvApart: RecyclerView
) :
    ApartPlayerContract.Presenter, MediaPlayer.OnCompletionListener {

    private var player: MediaPlayer? = null
    private var audioIndex: Int = 0

    override fun playOnlyTrack(position: Int) {
        clearPlayer()
        val resID = context!!.resources.getIdentifier(
            "${apartModel[position].nameAudio}", "raw", "jmapps.hadith40"
        )
        player = MediaPlayer.create(context, resID)
        player!!.start()
    }

    override fun playAllTracks(position: Int) {
        playOnlyTrack(audioIndex)
        player!!.setOnCompletionListener(this)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (audioIndex < apartModel.size - 1) {
            playOnlyTrack(audioIndex++)
            playOnlyTrack(audioIndex)
            rvApart.smoothScrollToPosition(audioIndex + 1)
            player!!.setOnCompletionListener(this)
        }
    }

    override fun clearPlayer() {
        if (player != null) {
            player!!.stop()
            player!!.reset()
        }
    }
}