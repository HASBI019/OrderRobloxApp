<?php
// 1. Matikan error warning PHP biar tidak merusak JSON
error_reporting(0);

// 2. Pastikan header JSON
header('Content-Type: application/json; charset=utf-8');

// --- KONEKSI ---
$host = "localhost:3307"; // Sesuaikan port jika perlu
$user = "root";
$pass = "";
$db   = "db_order";  

$koneksi = mysqli_connect($host, $user, $pass, $db);

if (!$koneksi) {
    echo json_encode(array("error" => "Gagal Konek Database"));
    exit();
}

// --- NAMA TABEL ---
// Pastikan ini sesuai dengan yang ada di database kamu
$tabel = "tb_order_roblox"; 


// --- LOGIKA UTAMA ---
$proc = isset($_GET['proc']) ? $_GET['proc'] : '';

switch ($proc) {
    
    // 1. AMBIL SEMUA DATA (Untuk List)
    case 'getdata':
        $sql = "SELECT * FROM $tabel ORDER BY id DESC";
        $query = mysqli_query($koneksi, $sql);
        
        $array_data = array();
        if ($query) {
            while ($baris = mysqli_fetch_assoc($query)) {
                $array_data[] = $baris;
            }
        }
        echo json_encode($array_data);
        break;

    // 2. HITUNG DATA (Untuk Dashboard) --> INI YANG KEMARIN HILANG
    case 'getcount':
        // Kita pakai variabel $tabel biar aman
        $q1 = mysqli_query($koneksi, "SELECT COUNT(*) as total FROM $tabel");
        $q2 = mysqli_query($koneksi, "SELECT COUNT(*) as antri FROM $tabel WHERE status='Antri'");
        $q3 = mysqli_query($koneksi, "SELECT COUNT(*) as proses FROM $tabel WHERE status='Proses'");
        $q4 = mysqli_query($koneksi, "SELECT COUNT(*) as selesai FROM $tabel WHERE status='Selesai'");

        $d1 = mysqli_fetch_assoc($q1);
        $d2 = mysqli_fetch_assoc($q2);
        $d3 = mysqli_fetch_assoc($q3);
        $d4 = mysqli_fetch_assoc($q4);

        $data = array(
            'total'   => $d1['total'],
            'antri'   => $d2['antri'],
            'proses'  => $d3['proses'],
            'selesai' => $d4['selesai']
        );
        echo json_encode($data);
        break;

    // 3. TAMBAH DATA
    case 'add':
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $nama   = $_POST['nama_klien'];
            $akun   = $_POST['akun_roblox'];
            $pass   = $_POST['password_roblox'];
            $mount  = $_POST['nama_mount'];
            $req    = $_POST['detail_request'];
            $tgl    = $_POST['tanggal_order'];
            $status = $_POST['status'];

            $sql = "INSERT INTO $tabel (nama_klien, akun_roblox, password_roblox, nama_mount, detail_request, tanggal_order, status) 
                    VALUES ('$nama', '$akun', '$pass', '$mount', '$req', '$tgl', '$status')";
            
            if (mysqli_query($koneksi, $sql)) echo "Berhasil Disimpan";
            else echo "Gagal SQL"; 
        }
        break;

    // 4. EDIT DATA
    case 'edit':
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $id     = $_POST['id'];
            $nama   = $_POST['nama_klien'];
            $akun   = $_POST['akun_roblox'];
            $pass   = $_POST['password_roblox'];
            $mount  = $_POST['nama_mount'];
            $req    = $_POST['detail_request'];
            $tgl    = $_POST['tanggal_order'];
            $status = $_POST['status'];

            $sql = "UPDATE $tabel SET 
                    nama_klien='$nama', 
                    akun_roblox='$akun',
                    password_roblox='$pass',
                    nama_mount='$mount',
                    detail_request='$req',
                    tanggal_order='$tgl',
                    status='$status'
                    WHERE id='$id'";
            
            if (mysqli_query($koneksi, $sql)) echo "Berhasil Update";
            else echo "Gagal Update";
        }
        break;

    // 5. HAPUS DATA (Ganti 'del' jadi 'delete' biar sinkron sama Android)
    case 'delete': 
        $id = $_GET['id'];
        $sql = "DELETE FROM $tabel WHERE id='$id'";
        if (mysqli_query($koneksi, $sql)) echo "Berhasil Hapus";
        else echo "Gagal Hapus";
        break;
        
    // Jaga-jaga kalau Android kirimnya 'del'
    case 'del': 
        $id = $_GET['id'];
        $sql = "DELETE FROM $tabel WHERE id='$id'";
        if (mysqli_query($koneksi, $sql)) echo "Berhasil Hapus";
        else echo "Gagal Hapus";
        break;
}
?>