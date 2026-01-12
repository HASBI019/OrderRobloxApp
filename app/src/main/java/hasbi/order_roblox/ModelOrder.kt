package hasbi.order_roblox

data class ModelOrder(
    val id: String,
    val nama_klien: String,
    val akun_roblox: String,
    val password_roblox: String,
    val nama_mount: String,
    val detail_request: String,
    val tanggal_order: String,
    val status: String
)