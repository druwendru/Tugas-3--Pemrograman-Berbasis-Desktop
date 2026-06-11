/* Kelas Diskon - Subkelas dari MenuItem.
 * Mengimplementasikan konsep INHERITANCE dari kelas MenuItem.
 * Digunakan untuk menerapkan diskon khusus pada menu tertentu.
 * POLYMORPHISM diterapkan melalui override metode tampilMenu().
 */
public class Diskon extends MenuItem {

    // Atribut tambahan khusus untuk Diskon
    private double persenDiskon;       // persentase diskon (0.0 - 100.0)
    private String namaItemTarget;     // nama item yang mendapat diskon
    private String deskripsiDiskon;    // deskripsi promosi

    /**
     * Constructor Diskon
     * @param nama              Nama promo diskon
     * @param harga             Harga asli item (sebelum diskon)
     * @param persenDiskon      Persentase diskon (contoh: 20.0 untuk 20%)
     * @param namaItemTarget    Nama item menu yang dikenai diskon
     * @param deskripsiDiskon   Deskripsi promo diskon
     */
    public Diskon(String nama, double harga, double persenDiskon,
                  String namaItemTarget, String deskripsiDiskon) {
        // Memanggil constructor superclass (MenuItem)
        super(nama, harga, "Diskon");
        setPersenDiskon(persenDiskon); // validasi via setter
        this.namaItemTarget = namaItemTarget;
        this.deskripsiDiskon = deskripsiDiskon;
    }

    // ========== GETTER & SETTER ==========

    public double getPersenDiskon() {
        return persenDiskon;
    }

    public void setPersenDiskon(double persenDiskon) {
        if (persenDiskon < 0 || persenDiskon > 100) {
            throw new IllegalArgumentException("Persentase diskon harus antara 0 dan 100!");
        }
        this.persenDiskon = persenDiskon;
    }

    public String getNamaItemTarget() {
        return namaItemTarget;
    }

    public void setNamaItemTarget(String namaItemTarget) {
        this.namaItemTarget = namaItemTarget;
    }

    public String getDeskripsiDiskon() {
        return deskripsiDiskon;
    }

    public void setDeskripsiDiskon(String deskripsiDiskon) {
        this.deskripsiDiskon = deskripsiDiskon;
    }

    /**
     * Menghitung harga setelah diskon
     * @return Harga setelah diskon diterapkan
     */
    public double getHargaSetelahDiskon() {
        return getHarga() * (1 - persenDiskon / 100.0);
    }

    /**
     * Menghitung jumlah penghematan
     * @return Jumlah uang yang dihemat
     */
    public double getJumlahHemat() {
        return getHarga() * (persenDiskon / 100.0);
    }

    /**
     * POLYMORPHISM: Override metode abstrak tampilMenu() dari MenuItem.
     * Menampilkan informasi khusus tentang diskon yang ditawarkan.
     */
    @Override
    public void tampilMenu() {
        System.out.println("  +------------------------------------------+");
        System.out.printf("  | %-40s |%n", " " + getNama());
        System.out.printf("  | Kategori  : %-27s |%n", getKategori());
        System.out.printf("  | Promo     : %-27s |%n", deskripsiDiskon);
        System.out.printf("  | Item      : %-27s |%n", namaItemTarget);
        System.out.printf("  | Harga Asal: Rp%-25.0f |%n", getHarga());
        System.out.printf("  | Diskon    : %.0f%%%s%-22s |%n", persenDiskon, " -> ", "Hemat Rp" + String.format("%.0f", getJumlahHemat()));
        System.out.printf("  | Harga Jadi: Rp%-25.0f |%n", getHargaSetelahDiskon());
        System.out.println("  +------------------------------------------+");
    }

    /**
     * Konversi ke format CSV untuk penyimpanan file
     * Format: DISKON,nama,harga,persenDiskon,namaItemTarget,deskripsiDiskon
     */
    @Override
    public String toCSV() {
        return String.format("DISKON,%s,%.0f,%.1f,%s,%s",
                getNama(), getHarga(), persenDiskon, namaItemTarget, deskripsiDiskon);
    }
}
