package hasbi.order_roblox

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    // IP SERVER (Cek lagi, jangan sampai salah!)
    private val ipAddress = "192.168.100.21"

    lateinit var tvTotal: TextView
    lateinit var tvAntri: TextView
    lateinit var tvProses: TextView
    lateinit var tvSelesai: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Hubungkan ID
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val fabAdd: FloatingActionButton = findViewById(R.id.fabAdd)

        tvTotal = findViewById(R.id.tvTotal)
        tvAntri = findViewById(R.id.tvCountAntri)
        tvProses = findViewById(R.id.tvCountProses)
        tvSelesai = findViewById(R.id.tvCountSelesai)

        // 2. Setup Tab
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Supaya pas geser tab, datanya refresh
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                getDataCount() // Update angka setiap ganti tab
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Antri"
                1 -> tab.text = "Proses"
                2 -> tab.text = "Selesai"
            }
        }.attach()

        // 3. Tombol Tambah
        fabAdd.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
    }

    // Biar pas balik dari halaman Input, angka langsung update
    override fun onResume() {
        super.onResume()
        getDataCount()
    }

    // --- FUNGSI AMBIL ANGKA DARI SERVER ---
    fun getDataCount() {
        val url = "http://$ipAddress/db_order/api.php?proc=getcount"
        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body?.string()
                if (responseText != null) {
                    try {
                        val jsonObject = JSONObject(responseText)
                        runOnUiThread {
                            // Update Angka di Layar
                            tvTotal.text = jsonObject.optString("total", "0")
                            tvAntri.text = jsonObject.optString("antri", "0")
                            tvProses.text = jsonObject.optString("proses", "0")
                            tvSelesai.text = jsonObject.optString("selesai", "0")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}