package jmapps.hadith40.mainScreen.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.hadith40.R
import jmapps.hadith40.database.DatabasePresenterImpl
import jmapps.hadith40.mainScreen.favorites.adapter.FavoriteListAdapter
import jmapps.hadith40.mainScreen.favorites.holder.FavoriteHolder
import kotlinx.android.synthetic.main.fragment_favorites.view.*

class FavoritesFragment : DialogFragment(), FavoriteListAdapter.OnItemClicksFavorite {

    private lateinit var clickToCurrentPosition: FavoritesFragment.ClickToCurrentPosition

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootFavorites: View = inflater.inflate(R.layout.fragment_favorites, container, false)

        val databasePresenter = DatabasePresenterImpl(context, null)
        rootFavorites.rv_favorite_list.layoutManager = LinearLayoutManager(context)
        val favoriteAdapter = FavoriteListAdapter(context, databasePresenter.getFavoriteList, this)

        if (favoriteAdapter.itemCount == 0) {
            rootFavorites.tv_is_favorite_list_empty.visibility = View.VISIBLE
            rootFavorites.rv_favorite_list.visibility = View.GONE
        } else {
            rootFavorites.tv_is_favorite_list_empty.visibility = View.GONE
            rootFavorites.rv_favorite_list.visibility = View.VISIBLE
        }

        rootFavorites.rv_favorite_list.adapter = favoriteAdapter

        return rootFavorites
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            clickToCurrentPosition = context as ClickToCurrentPosition
        } catch (e: ClassCastException) {
            throw ClassCastException("$context implements interface")
        }
    }

    interface ClickToCurrentPosition {
        fun toCurrentPosition(position: Int)
    }

    override fun onItemClickFavorite(favoriteHolder: FavoriteHolder, position: Int) {
        clickToCurrentPosition.toCurrentPosition(position - 1)
        dialog!!.dismiss()
    }
}