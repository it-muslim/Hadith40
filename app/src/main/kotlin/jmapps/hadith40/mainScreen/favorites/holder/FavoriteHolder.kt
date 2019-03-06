package jmapps.hadith40.mainScreen.favorites.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import jmapps.hadith40.mainScreen.favorites.adapter.FavoriteListAdapter
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvHadeethNumber = itemView.tv_hadeeth_number_favorite!!
    val tvHadeethTitle = itemView.tv_hadeeth_title_favorite!!

    fun findOnItemClick(
        onItemClick: FavoriteListAdapter.OnItemClicksFavorite,
        favoriteHolder: FavoriteHolder,
        position: Int) {
        itemView.setOnClickListener {
            onItemClick.onItemClickFavorite(favoriteHolder, position)
        }
    }
}