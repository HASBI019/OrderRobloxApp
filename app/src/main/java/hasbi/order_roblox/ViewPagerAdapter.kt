package hasbi.order_roblox

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Jumlah Tab kita ada 3 (Antri, Proses, Selesai)
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // Mengatur Fragment mana yang muncul di tiap Tab
        return when (position) {
            0 -> FragmentStatus.newInstance("Antri")
            1 -> FragmentStatus.newInstance("Proses")
            2 -> FragmentStatus.newInstance("Selesai")
            else -> FragmentStatus.newInstance("Antri")
        }
    }
}