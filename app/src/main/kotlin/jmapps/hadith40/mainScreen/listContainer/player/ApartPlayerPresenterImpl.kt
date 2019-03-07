package jmapps.hadith40.mainScreen.listContainer.player

import android.content.Context
import android.media.MediaPlayer
import androidx.recyclerview.widget.RecyclerView
import jmapps.hadith40.mainScreen.listContainer.apater.ApartAdapter
import jmapps.hadith40.mainScreen.listContainer.model.ApartModel

class ApartPlayerPresenterImpl(
    private val context: Context?,
    private var apartModel: List<ApartModel>,
    private val rvApartList: RecyclerView,
    private var apartAdapter: ApartAdapter
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
        rvApartList.smoothScrollToPosition(position)
        apartAdapter.onItemSelected(position)
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
            player!!.setOnCompletionListener(this)
        } else {
            audioIndex = 0
            rvApartList.smoothScrollToPosition(audioIndex)
            apartAdapter.onItemSelected(-1)
        }
    }

    override fun clearPlayer() {
        if (player != null) {
            player!!.stop()
            player!!.reset()
        }
    }
}