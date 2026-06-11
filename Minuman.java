/**
 * Kelas Minuman - Subkelas dari MenuItem.
 * Mengimplementasikan konsep INHERITANCE dari kelas MenuItem.
 * POLYMORPHISM diterapkan melalui override metode tampilMenu().
 */
public class Minuman extends MenuItem {

    // Atribut tambahan khusus untuk Minuman
    private String jenisMinuman;  // contoh: "Jus", "Kopi", "Teh", "Soda"
    private boolean tersediaDingin; // apakah tersedia versi dingin

    /**
     * Constructor Minuman
     * @param nama             Nama minuman
     * @param harga            Harga minuman
     * @param jenisMinuman     Jenis minuman (Jus, Kopi, Teh, dll.)
     * @param tersediaDingin   Apakah tersedia versi dingin
     */
    public Minuman(String nama, double harga, String jenisMinuman, boolean tersediaDingin) {
        // Memanggil constructor superclass (MenuItem)
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
        this.tersediaDingin = tersediaDingin;
    }

    // ========== GETTER & SETTER ==========

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    public void setJenisMinuman(String jenisMinuman) {
        this.jenisMinuman = jenisMinuman;
    }

    public boolean isTersediaDingin() {
        return tersediaDingin;
    }

    public void setTersediaDingin(boolean tersediaDingin) {
        this.tersediaDingin = tersediaDingin;
    }

    /**
     * POLYMORPHISM: Override metode abstrak tampilMenu() dari MenuItem.
     * Menampilkan informasi khusus tentang minuman.
     */
    @Override
    public void tampilMenu() {
        System.out.println("  +------------------------------------------+");
        System.out.printf("  | %-40s |%n", "  " + getNama());
        System.out.printf("  | Kategori  : %-27s |%n", getKategori());
        System.out.printf("  | Jenis     : %-27s |%n", jenisMinuman);
        System.out.printf("  | Harga     : Rp%-25.0f |%n", getHarga());
        System.out.printf("  | Dingin    : %-27s |%n", tersediaDingin ? " Tersedia" : " Tidak tersedia");
        System.out.println("  +------------------------------------------+");
    }

    /**
     * Konversi ke format CSV untuk penyimpanan file
     * Format: MINUMAN,nama,harga,jenisMinuman,tersediaDingin
     */
    @Override
    public String toCSV() {
        return String.format("MINUMAN,%s,%.0f,%s,%b",
                getNama(), getHarga(), jenisMinuman, tersediaDingin);
    }
}
