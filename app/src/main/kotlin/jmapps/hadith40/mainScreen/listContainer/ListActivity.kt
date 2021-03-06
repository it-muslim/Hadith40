package jmapps.hadith40.mainScreen.listContainer

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jmapps.hadith40.R
import jmapps.hadith40.database.ApartContract
import jmapps.hadith40.database.ApartPresenterImpl
import jmapps.hadith40.mainScreen.listContainer.apater.ApartAdapter
import jmapps.hadith40.mainScreen.listContainer.holder.ApartHolder
import jmapps.hadith40.mainScreen.listContainer.player.ApartPlayerPresenterImpl
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.list_content.*

class ListActivity : AppCompatActivity(), ApartContract.ListContentView, ApartAdapter.OnItemClicksApart,
    View.OnClickListener {

    private lateinit var databasePresenter: ApartPresenterImpl
    private lateinit var apartPresenterImpl: ApartPlayerPresenterImpl
    private var apartAdapter: ApartAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setSupportActionBar(toolbar_list)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val currentPosition: Int = intent.getIntExtra("key_current_view_pager_position", 1)

        databasePresenter = ApartPresenterImpl(this, this, currentPosition)
        databasePresenter.getMainContent()
        apartAdapter = ApartAdapter(this, databasePresenter.getApartList, this)
        apartPresenterImpl =
            ApartPlayerPresenterImpl(this, databasePresenter.getApartList, rv_apart_list, apartAdapter!!)

        rv_apart_list.layoutManager = LinearLayoutManager(this)
        rv_apart_list.adapter = apartAdapter

        rv_apart_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    fab_play_items.hide()
                } else {
                    fab_play_items.show()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        fab_play_items.setOnClickListener(this)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        apartPresenterImpl.clearPlayer()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        apartPresenterImpl.clearPlayer()
    }

    override fun onClick(v: View?) {
        apartPresenterImpl.playAllTracks(0)
    }

    override fun setHadeethNumber(hadeethNumber: String) {
        tv_hadeeth_number_apart.text = hadeethNumber
    }

    override fun setHadeethTitle(hadeethTitle: String) {
        tv_hadeeth_title_apart.text = hadeethTitle
    }

    override fun onItemClickApart(apartHolder: ApartHolder, position: Int) {
        apartPresenterImpl.playOnlyTrack(position)
    }
}