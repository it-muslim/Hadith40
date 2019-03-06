package jmapps.hadith40.mainScreen.mainContainer

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import jmapps.hadith40.R
import jmapps.hadith40.database.DatabaseContract
import jmapps.hadith40.database.DatabasePresenterImpl
import jmapps.hadith40.mainScreen.player.PlayerContract
import jmapps.hadith40.mainScreen.player.PlayerPresenterImpl
import kotlinx.android.synthetic.main.fragment_main.view.*

@Suppress("DEPRECATION")
class PlaceholderFragment : Fragment(), DatabaseContract.MainContentView, CompoundButton.OnCheckedChangeListener,
    View.OnClickListener, PlayerContract.PlayerView, SharedPreferences.OnSharedPreferenceChangeListener {

    private var rootView: View? = null
    private var sectionNumber: Int? = null
    private lateinit var databasePresenter: DatabasePresenterImpl
    private lateinit var playerPresenter: PlayerPresenterImpl
    private lateinit var mPreferences: SharedPreferences
    private lateinit var mEditor: SharedPreferences.Editor

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("ResourceType", "CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_main, container, false)

        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this)

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mEditor = mPreferences.edit()

        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)
        databasePresenter = DatabasePresenterImpl(context, this)
        databasePresenter.getMainContent(sectionNumber)

        val favoriteState = mPreferences.getBoolean("key_favorite_chapter_$sectionNumber", false)
        rootView!!.tb_add_favorite.isChecked = favoriteState

        playerPresenter = PlayerPresenterImpl(context, this, sectionNumber!!, rootView!!.sb_audio_progress)
        playerPresenter.initPlayer()

        setTextSize()
        setArabicTextColor()
        setTranslationTextColor()
        isTextTranslate()

        rootView!!.tb_add_favorite.setOnCheckedChangeListener(this)
        rootView!!.btn_share_content.setOnClickListener(this)
        rootView!!.tb_play_pause.setOnCheckedChangeListener(this)
        rootView!!.tb_loop_on_off.setOnCheckedChangeListener(this)

        return rootView
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView!!.id) {

            R.id.tb_add_favorite -> databasePresenter.addRemoveFavorite(isChecked, sectionNumber, mEditor)

            R.id.tb_play_pause -> playerPresenter.playTrack(isChecked)

            R.id.tb_loop_on_off -> playerPresenter.loopTrack(isChecked)
        }
    }

    override fun onClick(v: View?) {
        databasePresenter.shareContent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playerPresenter.clearPlayer()
    }

    // Database
    override fun setHadeethNumber(hadeethNumber: String) {
        rootView!!.tv_hadeeth_number.text = Html.fromHtml(hadeethNumber)
    }

    override fun setHadeethTitle(hadeethTitle: String) {
        rootView!!.tv_hadeeth_title.text = Html.fromHtml(hadeethTitle)
    }

    override fun setContentArabic(contentArabic: String) {
        rootView!!.tv_content_arabic.text = Html.fromHtml(contentArabic)
    }

    override fun setContentTranslation(contentTranslation: String) {
        rootView!!.tv_content_translation.text = Html.fromHtml(contentTranslation)
    }

    override fun setBookmarkState(state: Boolean) {
        if (state) {
            Toast.makeText(context, R.string.favorite_added, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, R.string.favorite_removed, Toast.LENGTH_LONG).show()
        }
    }

    // Player
    override fun playButtonState(state: Boolean) {
        rootView!!.tb_play_pause.isChecked = state
    }

    override fun loopButtonState(state: Boolean) {
        if (state) {
            Toast.makeText(context, R.string.player_loop_on, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, R.string.player_loop_off, Toast.LENGTH_LONG).show()
        }
        rootView!!.tb_loop_on_off.isChecked = state
    }

    override fun currentTrackTime(trackTime: String) {
        rootView!!.tv_current_track_time.text = trackTime
    }

    override fun totalTrackTime(trackTime: String) {
        rootView!!.tv_total_track_time.text = trackTime
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        setArabicTextColor()
        setTranslationTextColor()
        isTextTranslate()
        setTextSize()
    }

    private fun setTextSize() {
        val textSize = mPreferences.getInt("key_text_size", 16)
        rootView!!.tv_content_arabic.textSize = textSize.toFloat()
        rootView!!.tv_content_translation.textSize = textSize.toFloat()
    }

    private fun setArabicTextColor() {
        val arabicColor = mPreferences.getInt("key_arabic_color", Color.argb(255, 0, 0, 0))
        rootView!!.tv_content_arabic.setTextColor(arabicColor)
    }

    private fun setTranslationTextColor() {
        val translationColor = mPreferences.getInt("key_translation_color", Color.argb(255, 0, 0, 0))
        rootView!!.tv_content_translation.setTextColor(translationColor)
    }

    private fun isTextTranslate() {

        val state = mPreferences.getBoolean("key_is_text_translation", false)

        if (state) {
            rootView!!.tv_content_translation.visibility = View.GONE
        } else {
            rootView!!.tv_content_translation.visibility = View.VISIBLE
        }
    }
}