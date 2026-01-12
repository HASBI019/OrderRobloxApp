package hasbi.order_roblox

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class AdapterOrder(
    private val dataList: ArrayList<ModelOrder>,
    // Kita tambah 2 listener baru buat Edit dan Hapus
    private val onEdit: (ModelOrder) -> Unit,
    private val onDelete: (ModelOrder) -> Unit
) : RecyclerView.Adapter<AdapterOrder.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardOrder: MaterialCardView = itemView.findViewById(R.id.cardOrder)
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvMap: TextView = itemView.findViewById(R.id.tvMap)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = dataList[position]

        holder.tvNama.text = item.nama_klien
        holder.tvMap.text = "Map: ${item.nama_mount}"
        holder.tvTanggal.text = item.tanggal_order
        holder.tvStatus.text = item.status

        // --- WARNA NEON ---
        when (item.status) {
            "Antri" -> {
                holder.cardOrder.strokeColor = Color.parseColor("#FF9100")
                holder.tvStatus.setBackgroundColor(Color.parseColor("#FF9100"))
            }
            "Proses" -> {
                holder.cardOrder.strokeColor = Color.parseColor("#00E5FF")
                holder.tvStatus.setBackgroundColor(Color.parseColor("#00E5FF"))
            }
            "Selesai" -> {
                holder.cardOrder.strokeColor = Color.parseColor("#00FF00")
                holder.tvStatus.setBackgroundColor(Color.parseColor("#00FF00"))
            }
        }

        // --- AKSI TOMBOL ---

        // Klik tombol EDIT (Pensil)
        holder.btnEdit.setOnClickListener {
            onEdit(item) // Lapor ke Fragment: "Edit data ini!"
        }

        // Klik tombol DELETE (Sampah)
        holder.btnDelete.setOnClickListener {
            onDelete(item) // Lapor ke Fragment: "Hapus data ini!"
        }
    }

    override fun getItemCount(): Int = dataList.size
}