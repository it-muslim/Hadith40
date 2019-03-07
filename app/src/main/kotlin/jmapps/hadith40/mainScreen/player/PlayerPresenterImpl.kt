package jmapps.hadith40.mainScreen.player

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.widget.SeekBar

class PlayerPresenterImpl(
    private var context: Context?,
    private var playerView: PlayerContract.PlayerView?,
    private var sectionNumber: Int?,
    private var sbAudioProgress: SeekBar?) : SeekBar.OnSeekBarChangeListener,
    PlayerContract.PlayerPresenter, MediaPlayer.OnCompletionListener {

    private var player: MediaPlayer? = null
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

    override fun initPlayer() {
        val resID = context!!.resources.getIdentifier(
            "hadeeth_$sectionNumber", "raw", "jmapps.hadith40"
        )
        player = MediaPlayer.create(context, resID)
        initAudioProgress()
        totalTrackTime()
        sbAudioProgress!!.setOnSeekBarChangeListener(this)
        player!!.setOnCompletionListener(this)
    }

    override fun clearPlayer() {
        if (player != null) {
            player!!.stop()
            player!!.reset()
        }
    }

    private fun initAudioProgress() {
        sbAudioProgress!!.max = player!!.seconds
        runnable = Runnable {
            sbAudioProgress!!.progress = player!!.currentSeconds
            currentTrackTime()
            handler.postDelayed(runnable, 100)
        }
        handler.postDelayed(runnable, 100)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            player!!.seekTo(progress * 1000)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun playTrack(playState: Boolean) {
        if (playState) {
            player!!.start()
        } else {
            player!!.pause()
        }
        playerView!!.playButtonState(playState)
    }

    override fun loopTrack(loopState: Boolean) {
        player!!.isLooping = loopState
        playerView!!.loopButtonState(loopState)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (!player!!.isLooping) {
            playerView!!.playButtonState(false)
            initPlayer()
        }
    }

    private val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
        }

    private val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / 1000
        }

    private fun currentTrackTime() {
        val duration = player!!.currentSeconds
        val hours = duration / 3600
        val minutes = (duration - hours * 3600) / 60
        val seconds = duration - (hours * 3600 + minutes * 60)
        val time = String.format("%02d:%02d", minutes, seconds)
        playerView!!.currentTrackTime(time)
    }

    private fun totalTrackTime() {
        val duration = player!!.seconds
        val hours = duration / 3600
        val minutes = (duration - hours * 3600) / 60
        val seconds = duration - (hours * 3600 + minutes * 60)
        val time = String.format("%02d:%02d", minutes, seconds)
        playerView!!.totalTrackTime(time)
    }
}