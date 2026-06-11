/**
 * Kelas abstrak MenuItem - Kelas dasar untuk semua item menu restoran.
 * Mengimplementasikan konsep ABSTRAKSI dengan mendefinisikan template
 * yang harus diimplementasikan oleh kelas-kelas turunannya.
 */
public abstract class MenuItem {

    // ENCAPSULATION: atribut dibuat private, diakses via getter/setter
    private String nama;
    private double harga;
    private String kategori;

    /**
     * Constructor MenuItem
     * @param nama     Nama item menu
     * @param harga    Harga item menu
     * @param kategori Kategori item menu
     */
    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // ========== GETTER & SETTER (Encapsulation) ==========

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong!");
        }
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif!");
        }
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    /**
     * Metode abstrak yang WAJIB diimplementasikan oleh setiap subkelas.
     * Inilah inti dari ABSTRAKSI - mendefinisikan kontrak tanpa implementasi.
     */
    public abstract void tampilMenu();

    /**
     * Metode untuk mengonversi data ke format CSV (untuk penyimpanan file)
     */
    public abstract String toCSV();

    @Override
    public String toString() {
        return String.format("[%s] %s - Rp%.0f", kategori, nama, harga);
    }
}
