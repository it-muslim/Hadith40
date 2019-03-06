package jmapps.hadith40.mainScreen.listContainer

import android.os.Bundle
import android.view.MenuItem
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

class ListActivity : AppCompatActivity(), ApartContract.ListContentView, ApartAdapter.OnItemClicksApart {

    private lateinit var databasePresenter: ApartPresenterImpl
    private lateinit var apartPresenterImpl: ApartPlayerPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setSupportActionBar(toolbar_list)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val currentPosition: Int = intent.getIntExtra("key_current_view_pager_position", 1)

        databasePresenter = ApartPresenterImpl(this, this, currentPosition)
        databasePresenter.getMainContent()
        apartPresenterImpl = ApartPlayerPresenterImpl(this)

        rv_apart_list.layoutManager = LinearLayoutManager(this)
        val apartAdapter = ApartAdapter(this, databasePresenter.getApartList, this)
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
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setHadeethNumber(hadeethNumber: String) {
        tv_hadeeth_number_apart.text = hadeethNumber
    }

    override fun setHadeethTitle(hadeethTitle: String) {
        tv_hadeeth_title_apart.text = hadeethTitle
    }

    override fun onItemClickApart(apartHolder: ApartHolder, position: Int) {
        apartPresenterImpl.playOnlyTrack(databasePresenter.getApartList, position)
    }
}