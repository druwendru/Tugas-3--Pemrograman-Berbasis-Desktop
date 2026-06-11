/**
 * Kelas Makanan - Subkelas dari MenuItem.
 * Mengimplementasikan konsep INHERITANCE (pewarisan) dari kelas MenuItem.
 * POLYMORPHISM diterapkan melalui override metode tampilMenu().
 */
public class Makanan extends MenuItem {

    // Atribut tambahan khusus untuk Makanan
    private String jenisMakanan;   // contoh: "Nasi", "Mie", "Snack"
    private boolean tersediaVegan; // apakah tersedia versi vegan

    /**
     * Constructor Makanan
     * @param nama           Nama makanan
     * @param harga          Harga makanan
     * @param jenisMakanan   Jenis makanan (Nasi, Mie, Snack, dll.)
     * @param tersediaVegan  Apakah tersedia versi vegan
     */
    public Makanan(String nama, double harga, String jenisMakanan, boolean tersediaVegan) {
        // Memanggil constructor superclass (MenuItem)
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
        this.tersediaVegan = tersediaVegan;
    }

    // ========== GETTER & SETTER ==========

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }

    public boolean isTersediaVegan() {
        return tersediaVegan;
    }

    public void setTersediaVegan(boolean tersediaVegan) {
        this.tersediaVegan = tersediaVegan;
    }

    /**
     * POLYMORPHISM: Override metode abstrak tampilMenu() dari MenuItem.
     * Menampilkan informasi khusus tentang makanan.
     */
    @Override
    public void tampilMenu() {
        System.out.println("  +------------------------------------------+");
        System.out.printf("  | %-40s |%n", " " + getNama());
        System.out.printf("  | Kategori  : %-27s |%n", getKategori());
        System.out.printf("  | Jenis     : %-27s |%n", jenisMakanan);
        System.out.printf("  | Harga     : Rp%-25.0f |%n", getHarga());
        System.out.printf("  | Vegan     : %-27s |%n", tersediaVegan ? "Tersedia" : " Tidak tersedia");
        System.out.println("  +------------------------------------------+");
    }

    /**
     * Konversi ke format CSV untuk penyimpanan file
     * Format: MAKANAN,nama,harga,jenisMakanan,tersediaVegan
     */
    @Override
    public String toCSV() {
        return String.format("MAKANAN,%s,%.0f,%s,%b",
                getNama(), getHarga(), jenisMakanan, tersediaVegan);
    }
}
