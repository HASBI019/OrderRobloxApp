package hasbi.order_roblox

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException
import java.util.Calendar

class InputActivity : AppCompatActivity() {

    lateinit var etNama: EditText
    lateinit var etAkun: EditText
    lateinit var etPass: EditText
    lateinit var etMount: EditText
    lateinit var etRequest: EditText
    lateinit var etTanggal: EditText // Ini yang buat Kalender
    lateinit var spStatus: Spinner
    lateinit var btnSimpan: Button

    var idData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        // 1. Inisialisasi Komponen
        etNama = findViewById(R.id.etNama)
        etAkun = findViewById(R.id.etAkun)
        etPass = findViewById(R.id.etPass)
        etMount = findViewById(R.id.etMount)
        etRequest = findViewById(R.id.etRequest)
        etTanggal = findViewById(R.id.etTanggal)
        spStatus = findViewById(R.id.spinnerStatus)
        btnSimpan = findViewById(R.id.btnSimpan)

        // 2. Setup Kalender (DatePicker)
        // Saat kolom tanggal diklik, jalankan fungsi ini:
        etTanggal.setOnClickListener {
            val kalender = Calendar.getInstance()
            val tahun = kalender.get(Calendar.YEAR)
            val bulan = kalender.get(Calendar.MONTH)
            val hari = kalender.get(Calendar.DAY_OF_MONTH)

            // Munculkan Popup Kalender
            val datePicker = DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    // Format tanggal jadi: 2026-01-09 (sesuai format database MySQL)
                    // Ingat: Bulan di Android mulai dari 0, jadi harus +1
                    val bulanFix = month + 1
                    val tanggalString = "$year-$bulanFix-$dayOfMonth"
                    etTanggal.setText(tanggalString)
                },
                tahun, bulan, hari
            )
            datePicker.show()
        }

        // 3. Setup Spinner Status
        val opsiStatus = arrayOf("Antri", "Proses", "Selesai")
        val adapterSpin = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opsiStatus)
        spStatus.adapter = adapterSpin

        // 4. Cek apakah ini mode Edit?
        idData = intent.getStringExtra("ID")
        if (idData != null) {
            etNama.setText(intent.getStringExtra("NAMA"))
            etAkun.setText(intent.getStringExtra("AKUN"))
            etPass.setText(intent.getStringExtra("PASS"))
            etMount.setText(intent.getStringExtra("MOUNT"))
            etRequest.setText(intent.getStringExtra("REQ"))
            etTanggal.setText(intent.getStringExtra("TGL"))

            val statusLama = intent.getStringExtra("STATUS")
            val posisi = opsiStatus.indexOf(statusLama)
            if (posisi >= 0) spStatus.setSelection(posisi)

            btnSimpan.text = "UPDATE DATA"
        }

        // 5. Tombol Simpan
        btnSimpan.setOnClickListener {
            simpanKeServer()
        }
    }

    private fun simpanKeServer() {
        val nama = etNama.text.toString()
        val akun = etAkun.text.toString()
        val pass = etPass.text.toString()
        val mount = etMount.text.toString()
        val req = etRequest.text.toString()
        val tgl = etTanggal.text.toString()
        val status = spStatus.selectedItem.toString()

        if (nama.isEmpty() || mount.isEmpty()) {
            Toast.makeText(this, "Nama & Mount wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val formBuilder = FormBody.Builder()
            .add("nama_klien", nama)
            .add("akun_roblox", akun)
            .add("password_roblox", pass)
            .add("nama_mount", mount)
            .add("detail_request", req)
            .add("tanggal_order", tgl)
            .add("status", status)

        // Pastikan IP dan Folder Benar
        var url = "http://192.168.100.21/db_order/api.php?proc=add"

        if (idData != null) {
            url = "http://192.168.100.21/db_order/api.php?proc=edit"
            formBuilder.add("id", idData!!)
        }

        val request = Request.Builder()
            .url(url)
            .post(formBuilder.build())
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Gagal Konek: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Sukses: $responseText", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Error Server: ${response.code}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}