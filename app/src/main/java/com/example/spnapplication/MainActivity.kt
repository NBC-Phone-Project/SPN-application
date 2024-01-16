package com.example.spnapplication

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.spnapplication.databinding.ActivityMainBinding
import com.example.spnapplication.ui.dashboard.DashboardFragment
import com.example.spnapplication.ui.home.HomeFragment
import com.example.spnapplication.ui.notifications.NotificationsFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
    }
    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        val viewPager2Adatper = ViewPager2Adapter(this)
        viewPager2Adatper.addFragment(DashboardFragment())
        viewPager2Adatper.addFragment(HomeFragment())
        viewPager2Adatper.addFragment(NotificationsFragment())
        viewPager2Adatper.addFragment(ContactFragment())
        viewPager2Adatper.addFragment(MyPageFragment())

        //Adapter 연결
        binding.vpViewpagerMain.apply {
            adapter = viewPager2Adatper

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.tlNavigationView, binding.vpViewpagerMain) { tab, position ->
            Log.e("jblee", "ViewPager position: ${position}")
            when (position) {
                0 -> tab.text = "키패드"
                1 -> tab.text = "연락처"
                2 -> tab.text = "최근기록"
                3 -> tab.text = "즐겨찾기"
                4 -> tab.text = "내정보"
            }
        }.attach()
    }
}