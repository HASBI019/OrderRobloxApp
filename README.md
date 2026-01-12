# 🌌 JokiMap - Admin Dashboard System

> Aplikasi manajemen pesanan Jasa Joki Game berbasis Android Native (Kotlin) dengan antarmuka Modern Dark UI & Glassmorphism.

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple?style=flat&logo=kotlin)
![Platform](https://img.shields.io/badge/Platform-Android-green?style=flat&logo=android)
![Status](https://img.shields.io/badge/Status-Active-blue)

## 📱 Preview Aplikasi

<p align="center">
  <img src="LINK_GAMBAR_DASHBOARD.jpg" width="280" alt="Dashboard View"/>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <img src="LINK_GAMBAR_ADD_ORDER.jpg" width="280" alt="List View"/>
</p>

## 📖 Tentang Projek
**JokiMap Admin** adalah sistem dashboard yang dirancang untuk mempermudah admin dalam mengelola antrian pesanan (order) jasa joki game. Aplikasi ini tidak hanya berfokus pada fungsionalitas CRUD (Create, Read, Update, Delete), tetapi juga menonjolkan estetika desain futuristik.

Menggunakan konsep **Neon & Glassmorphism**, aplikasi ini memberikan pengalaman visual yang menarik dengan background Galaxy, kartu transparan, dan tipografi yang modern.

## ✨ Fitur Unggulan

### 1. 📊 Real-time Dashboard Statistic
Menampilkan ringkasan data secara visual di halaman utama:
* **Total Order:** Jumlah keseluruhan pesanan.
* **Antrian:** Pesanan yang belum dikerjakan.
* **Proses:** Pesanan yang sedang dikerjakan.
* **Selesai:** Pesanan yang telah tuntas.

### 2. 🗂️ Order Management (CRUD)
* **Input Data:** Form tambah pesanan dengan validasi input.
* **Edit & Update:** Mengubah status pengerjaan (Antri -> Proses -> Selesai).
* **Hapus Data:** Menghapus history pesanan yang tidak valid.

### 3. 🎨 Modern User Interface (UI)
* **Transparent Glass Effect:** Menggunakan `alpha` dan warna hex transparan (`#80121212`) untuk menciptakan efek kaca pada CardView.
* **Full Screen Background:** Background Galaxy yang responsif menutupi seluruh layar (di balik status bar & navigasi).
* **Neon Text:** Efek bayangan (`shadowRadius`) pada teks untuk kesan menyala.

### 4. 🧭 Tab Navigation
Navigasi yang mulus menggunakan **ViewPager2** dan **TabLayout** untuk memisahkan daftar pesanan berdasarkan statusnya.

## 🛠️ Tech Stack (Teknologi)

Project ini dibangun menggunakan:
* **Language:** Kotlin
* **UI Layout:** XML (ConstraintLayout, RelativeLayout, FrameLayout, LinearLayout)
* **Components:**
    * `RecyclerView` (List Data)
    * `CardView` (Material Design)
    * `ViewPager2` & `TabLayout`
    * `FloatingActionButton`
* **Architecture:** MVC (Model-View-Controller) Pattern
* **Icon:** Android Material Vector Assets

## 🚀 Cara Instalasi

1.  **Clone Repository**
    ```bash
    [git clone [https://github.com/username-kamu/JokiMap-Admin.git](https://github.com/username-kamu/JokiMap-Admin.git)](https://github.com/HASBI019/OrderRobloxApp.git)
    ```
2.  **Buka di Android Studio**
    * File > Open > Pilih folder hasil clone.
3.  **Sync Gradle**
    * Tunggu hingga proses download dependency selesai.
4.  **Run Application**
    * Hubungkan HP via USB atau gunakan Emulator.
    * Klik tombol ▶️ (Run).

---

## 👨‍💻 Author
Dibuat dengan ❤️ dan Kopi oleh **[Nama Kamu]**.

*Terbuka untuk kontribusi dan saran pengembangan!*
