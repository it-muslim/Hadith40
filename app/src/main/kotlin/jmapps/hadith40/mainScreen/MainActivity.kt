@file:Suppress("DEPRECATION")

package jmapps.hadith40.mainScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import jmapps.hadith40.R
import jmapps.hadith40.database.DBAssetHelper
import jmapps.hadith40.mainScreen.adapter.SectionsPagerAdapter
import jmapps.hadith40.mainScreen.chapters.ChaptersFragment
import jmapps.hadith40.mainScreen.favorites.FavoritesFragment
import jmapps.hadith40.mainScreen.listContainer.ListActivity
import jmapps.hadith40.setting.BottomSheetSettings
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    ChaptersFragment.ClickToCurrentPosition, FavoritesFragment.ClickToCurrentPosition, ViewPager.OnPageChangeListener {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var dbAssetHelper: DBAssetHelper

    private lateinit var mPreferences: SharedPreferences
    private lateinit var mEditor: SharedPreferences.Editor

    private lateinit var nightMode: Switch
    private var isNightMode: Boolean? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mEditor = mPreferences.edit()

        isNightMode = mPreferences.getBoolean("key_night_mode", false)
        isNightModeEnabled(isNightMode!!)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        dbAssetHelper = DBAssetHelper(this)
        dbAssetHelper.readableDatabase

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        main_container.adapter = mSectionsPagerAdapter

        nav_view.setNavigationItemSelectedListener(this)
        fab_main_chapters.setOnClickListener(this)
        loadLastPosition()

        app_bar_main.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset < 0) {
                fab_main_chapters.hide()
            } else {
                fab_main_chapters.show()
            }
        })

        main_container.addOnPageChangeListener(this)

        nav_view.menu.findItem(R.id.nav_switch).actionView = Switch(this)
        nightMode = nav_view.menu.findItem(R.id.nav_switch).actionView as Switch
        nightMode.isClickable = false
        nightMode.isChecked = isNightMode as Boolean
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_favorites -> {
                val favoriteList = FavoritesFragment()
                favoriteList.show(supportFragmentManager, "favorite_list")
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_switch -> {
                toggleNightMode(!nightMode.isChecked)
                nightMode.toggle()
            }
            R.id.nav_list -> {
                val toListActivity = Intent(this@MainActivity, ListActivity::class.java)
                toListActivity.putExtra("key_current_view_pager_position", main_container.currentItem + 1)
                startActivity(toListActivity)
            }
            R.id.nav_settings -> {
                val settings = BottomSheetSettings()
                settings.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheet)
                settings.show(supportFragmentManager, "settings")
            }
            R.id.nav_list_apps -> {

            }
            R.id.nav_about_us -> {
                aboutUsDialog()
            }
            R.id.nav_rate_app -> {
                rateApp()
            }
            R.id.nav_share_link -> {
                shareLink()
            }
            R.id.nav_exit -> {
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (position != -1) {
            Toast.makeText(this, "Yes yes yes", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View?) {
        val chaptersFragment = ChaptersFragment()
        chaptersFragment.show(supportFragmentManager, "chapters")
    }

    override fun toCurrentPosition(position: Int) {
        main_container.currentItem = position
    }

    override fun onPause() {
        super.onPause()
        saveLastPosition()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbAssetHelper.close()
    }

    private fun saveLastPosition() {
        mEditor.putInt("key_last_view_pager_position", main_container.currentItem).apply()
    }

    private fun loadLastPosition() {
        main_container.currentItem = mPreferences.getInt("key_last_view_pager_position", 0)
    }

    private fun isNightModeEnabled(state: Boolean) {
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun toggleNightMode(state: Boolean) {
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            mEditor.putBoolean("key_night_mode", true).apply()
            mEditor.putInt("key_arabic_color", Color.argb(255, 255, 255, 255)).apply()
            mEditor.putInt("key_translation_color", Color.argb(255, 255, 255, 255)).apply()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            mEditor.putBoolean("key_night_mode", false).apply()
            mEditor.putInt("key_arabic_color", Color.argb(255, 0, 0, 0)).apply()
            mEditor.putInt("key_translation_color", Color.argb(255, 0, 0, 0)).apply()
        }
        recreate()
    }

    @SuppressLint("InflateParams")
    private fun aboutUsDialog() {
        val inflater = LayoutInflater.from(this)
        val dialogView: View = inflater.inflate(R.layout.dialog_about_us, null)
        val dialogAboutUs = AlertDialog.Builder(this)
        dialogAboutUs.setView(dialogView)
        dialogAboutUs.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        dialogAboutUs.create().show()
    }

    private fun rateApp() {
        val rateApp = Intent(Intent.ACTION_VIEW)
        rateApp.data = Uri.parse("https://play.google.com/store/apps/details?id=jmapps.hadith40")
        startActivity(rateApp)
    }

    private fun shareLink() {
        val descriptionApp = "Серия бесплатных андроид-приложений для мусульман: 40 хадисов имама ан-Навави\n\n"
        val appLink = "https://play.google.com/store/apps/details?id=jmapps.hadith40"
        val shareContent = Intent()
        shareContent.action = Intent.ACTION_SEND
        shareContent.type = "text/plain"
        shareContent.putExtra(Intent.EXTRA_TEXT, descriptionApp + appLink)
        startActivity(Intent.createChooser(shareContent, this.getString(R.string.share_content_to)))
    }
}