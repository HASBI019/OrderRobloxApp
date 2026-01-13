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
import org.json.JSONArray
import java.io.IOException

class MainActivity : AppCompatActivity() {

    // URL SERVER CPANEL (Ganti IP Lokal ke Domain Online)
    private val serverUrl = "https://appocalypse.my.id/jokimap.php"

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

        // Supaya pas geser tab atau balik ke home, data refresh
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDashboardStats()
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
        updateDashboardStats()
    }

    // --- FUNGSI AMBIL DATA & HITUNG STATISTIK DARI SERVER ---
    fun updateDashboardStats() {
        // Mengambil data menggunakan proc=getdata sesuai modul
        val url = "$serverUrl?proc=getdata"
        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body?.string()
                if (responseText != null) {
                    try {
                        val jsonArray = JSONArray(responseText)

                        // Inisialisasi hitungan
                        var total = jsonArray.length()
                        var antri = 0
                        var proses = 0
                        var selesai = 0

                        // Looping data untuk menghitung status
                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val status = item.optString("status")

                            if (status.equals("Antri", ignoreCase = true)) antri++
                            if (status.equals("Proses", ignoreCase = true)) proses++
                            if (status.equals("Selesai", ignoreCase = true)) selesai++
                        }

                        runOnUiThread {
                            // Update Angka di Layar Dashboard
                            tvTotal.text = total.toString()
                            tvAntri.text = antri.toString()
                            tvProses.text = proses.toString()
                            tvSelesai.text = selesai.toString()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}