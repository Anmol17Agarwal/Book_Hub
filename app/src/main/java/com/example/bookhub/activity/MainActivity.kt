package com.example.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bookhub.*
import com.example.bookhub.fragment.AboutUsFragment
import com.example.bookhub.fragment.DashboardFragment
import com.example.bookhub.fragment.FavouriteFragment
import com.example.bookhub.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView:NavigationView
     var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frame)
        navigationView=findViewById(R.id.navigatonView)


        openDashboard()

        setUpToolbar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem != null)
            {
                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it


            when(it.itemId) {
                R.id.dashboard -> {
                    openDashboard()
                    drawerLayout.closeDrawers()
                    navigationView.setCheckedItem(R.id.dashboard)
                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        FavouriteFragment()
                    ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Favourites"
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        ProfileFragment()
                    ).commit()
                        drawerLayout.closeDrawers()
                    supportActionBar?.title="Profile"
                }
                R.id.about_us -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        AboutUsFragment()
                    )

                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="About Us"
                }
            }
            return@setNavigationItemSelectedListener true

        }
    }
    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar Tittle"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun openDashboard(){
        val fragment= DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace((R.id.frame),fragment)
        transaction.commit()
        supportActionBar?.title = "Dashboard"
    }

    override fun onBackPressed() {
        val frag= supportFragmentManager.findFragmentById(R.id.frame)

        when(frag)
        {
            !is DashboardFragment -> openDashboard()

            else-> super.onBackPressed()
        }
    }
}
