package jmapps.hadith40.mainScreen.favorites.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.hadith40.R
import jmapps.hadith40.mainScreen.favorites.holder.FavoriteHolder
import jmapps.hadith40.mainScreen.favorites.model.FavoriteListModel

class FavoriteListAdapter(
    private val context: Context?,
    private val favoriteModel: List<FavoriteListModel>,
    private val onItemClicksFavorite: OnItemClicksFavorite
) :
    RecyclerView.Adapter<FavoriteHolder>() {

    interface OnItemClicksFavorite {
        fun onItemClickFavorite(favoriteHolder: FavoriteHolder, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {

        val positionId: Int = favoriteModel[position].positionId!!
        val strHadeethNumber: String = favoriteModel[position].hadeethNumber!!
        val strHadeethTitle: String = favoriteModel[position].hadeethTitle!!

        holder.tvHadeethNumber.text = strHadeethNumber
        holder.tvHadeethTitle.text = strHadeethTitle

        holder.findOnItemClick(onItemClicksFavorite, holder, positionId)
    }

    override fun getItemCount(): Int {
        return favoriteModel.size
    }
}