package hasbi.order_roblox

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class FragmentStatus : Fragment() {

    private lateinit var rvData: RecyclerView
    private var statusFilter: String = "Antri"

    // GANTI IP ADDRESS JADI URL CPANEL
    private val serverUrl = "https://appocalypse.my.id/jokimap.php"

    companion object {
        fun newInstance(status: String): FragmentStatus {
            val fragment = FragmentStatus()
            val args = Bundle()
            args.putString("status", status)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, saved: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_status, container, false)
        statusFilter = arguments?.getString("status") ?: "Antri"
        rvData = view.findViewById(R.id.rvData)
        rvData.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onResume() {
        super.onResume()
        getDataOrder()
    }

    private fun getDataOrder() {
        // GANTI URL KE ONLINE
        val url = "$serverUrl?proc=getdata"
        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Fragment", "Gagal load: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body?.string()
                try {
                    val jsonArray = JSONArray(responseText)
                    val listOrder = ArrayList<ModelOrder>()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val statusDB = obj.optString("status", "Antri")

                        // Memfilter data berdasarkan tab (Antri/Proses/Selesai)
                        if (statusDB.equals(statusFilter, ignoreCase = true)) {
                            listOrder.add(
                                ModelOrder(
                                    obj.optString("id", "0"),
                                    obj.optString("nama_klien", "Tanpa Nama"),
                                    obj.optString("akun_roblox", "-"),
                                    obj.optString("password_roblox", "-"),
                                    obj.optString("nama_mount", "-"),
                                    obj.optString("detail_request", "-"),
                                    obj.optString("tanggal_order", "-"),
                                    statusDB
                                )
                            )
                        }
                    }

                    activity?.runOnUiThread {
                        val adapter = AdapterOrder(listOrder,
                            onEdit = { order ->
                                val intent = Intent(context, InputActivity::class.java)
                                intent.putExtra("ID", order.id)
                                intent.putExtra("NAMA", order.nama_klien)
                                intent.putExtra("AKUN", order.akun_roblox)
                                intent.putExtra("PASS", order.password_roblox)
                                intent.putExtra("MOUNT", order.nama_mount)
                                intent.putExtra("REQ", order.detail_request)
                                intent.putExtra("TGL", order.tanggal_order)
                                intent.putExtra("STATUS", order.status)
                                startActivity(intent)
                            },
                            onDelete = { order ->
                                confirmDelete(order)
                            }
                        )
                        rvData.adapter = adapter
                        // Panggil update stats di MainActivity agar angka dashboard berubah
                        (activity as? MainActivity)?.updateDashboardStats()
                    }
                } catch (e: Exception) { e.printStackTrace() }
            }
        })
    }

    private fun confirmDelete(order: ModelOrder) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Hapus?")
        builder.setMessage("Yakin hapus ${order.nama_klien}?")

        builder.setPositiveButton("Hapus") { _, _ ->
            deleteDataAPI(order.id)
        }
        builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun deleteDataAPI(idOrder: String) {
        // GANTI URL KE ONLINE & SESUAIKAN proc=del (sesuai di PHP)
        val url = "$serverUrl?proc=del&id=$idOrder"

        OkHttpClient().newCall(Request.Builder().url(url).build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Terhapus!", Toast.LENGTH_SHORT).show()
                    getDataOrder() // Refresh list setelah hapus
                }
            }
        })
    }
}