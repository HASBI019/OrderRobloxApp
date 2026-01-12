-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Waktu pembuatan: 12 Jan 2026 pada 16.00
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_order`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_order_roblox`
--

CREATE TABLE `tb_order_roblox` (
  `id` int(11) NOT NULL,
  `nama_klien` varchar(100) NOT NULL,
  `akun_roblox` varchar(100) NOT NULL,
  `password_roblox` varchar(100) NOT NULL,
  `nama_mount` varchar(100) NOT NULL,
  `detail_request` text NOT NULL,
  `tanggal_order` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Antri'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_order_roblox`
--

INSERT INTO `tb_order_roblox` (`id`, `nama_klien`, `akun_roblox`, `password_roblox`, `nama_mount`, `detail_request`, `tanggal_order`, `status`) VALUES
(2, 'BHRU', 'bhru_pro', 'siti456', 'Mount Everest', 'Obby susah, jangan ada checkpoint di awal', '07-01-2026', 'Proses'),
(5, 'hasbi', 'jinfeihasbi17@gmail.com', 'muhasbi', 'mount has', '25 cp rintangannya sulit buat balap', '2026-1-10', 'Selesai'),
(6, 'dev has', 'jinfeihasbi17@gmail.com', 'hhhhhhh', 'HAS OBSTACLE', 'cp7 rintangan balap', '2026-1-12', 'Antri'),
(8, 'hchch', 'vhch@gmail.com', 'v hxh', 'hchchc', 'GX ', '2026-1-8', 'Antri');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_order_roblox`
--
ALTER TABLE `tb_order_roblox`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_order_roblox`
--
ALTER TABLE `tb_order_roblox`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
