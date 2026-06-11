import java.util.ArrayList;
import java.io.*;

/**
 * Kelas Menu - Mengelola semua item menu dalam restoran.
 * Menggunakan ArrayList untuk menyimpan koleksi MenuItem.
 * Implementasi ENCAPSULATION: atribut menuItems bersifat private.
 * Implementasi OPERASI FILE: menyimpan dan memuat menu dari file.
 */
public class Menu {

    // ENCAPSULATION: ArrayList private, diakses melalui method
    private ArrayList<MenuItem> menuItems;
    private static final String FILE_MENU = "data_menu.txt";

    /**
     * Constructor Menu - menginisialisasi ArrayList kosong
     */
    public Menu() {
        this.menuItems = new ArrayList<>();
    }

    // ========== OPERASI DASAR MENU ==========

    /**
     * Menambahkan item baru ke menu
     * @param item Item menu yang akan ditambahkan
     */
    public void tambahItem(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item tidak boleh null!");
        }
        menuItems.add(item);
        System.out.println(" Item '" + item.getNama() + "' berhasil ditambahkan ke menu!");
    }

    /**
     * Menghapus item dari menu berdasarkan nama
     * @param nama Nama item yang akan dihapus
     * @throws MenuItemNotFoundException jika item tidak ditemukan
     */
    public void hapusItem(String nama) throws MenuItemNotFoundException {
        MenuItem itemDitemukan = cariItem(nama);
        menuItems.remove(itemDitemukan);
        System.out.println(" Item '" + nama + "' berhasil dihapus dari menu!");
    }

    /**
     * Mencari item menu berdasarkan nama (case-insensitive)
     * @param nama Nama item yang dicari
     * @return MenuItem yang ditemukan
     * @throws MenuItemNotFoundException jika item tidak ditemukan
     */
    public MenuItem cariItem(String nama) throws MenuItemNotFoundException {
        // Menggunakan STRUKTUR PENGULANGAN untuk pencarian
        for (MenuItem item : menuItems) {
            if (item.getNama().equalsIgnoreCase(nama)) {
                return item;
            }
        }
        // Melempar exception kustom jika item tidak ditemukan
        throw new MenuItemNotFoundException(
                " Item '" + nama + "' tidak ditemukan dalam menu!");
    }

    /**
     * Mencari item menu berdasarkan nomor urut (1-based)
     * @param nomor Nomor urut item di menu
     * @return MenuItem yang ditemukan
     * @throws MenuItemNotFoundException jika nomor tidak valid
     */
    public MenuItem getItemByNomor(int nomor) throws MenuItemNotFoundException {
        if (nomor < 1 || nomor > menuItems.size()) {
            throw new MenuItemNotFoundException(
                    " Nomor item " + nomor + " tidak valid! Menu memiliki " +
                    menuItems.size() + " item.");
        }
        return menuItems.get(nomor - 1);
    }

    /**
     * Menampilkan seluruh menu restoran menggunakan POLYMORPHISM.
     * Setiap subkelas MenuItem memiliki implementasi tampilMenu() berbeda.
     */
    public void tampilkanMenu() {
        if (menuItems.isEmpty()) {
            System.out.println("   Menu masih kosong! Silakan tambahkan item terlebih dahulu.");
            return;
        }

        System.out.println("\n  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║              MENU RESTORAN NUSANTARA         ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");

        // ---- MAKANAN ----
        int nomor = 1;
        System.out.println("\n  DAFTAR MENU:");
        System.out.println("  ─────────────────────────────────────────────");

        // Gunakan STRUKTUR PENGULANGAN untuk iterasi semua item
        for (MenuItem item : menuItems) {
            System.out.printf("%n  [%d] ", nomor++);
            // POLYMORPHISM: memanggil tampilMenu() - implementasi berbeda tiap subkelas
            item.tampilMenu();
        }

        System.out.println("\n  Total item dalam menu: " + menuItems.size() + " item");
    }

    /**
     * Menampilkan menu berdasarkan kategori tertentu
     * @param kategori Kategori yang ingin ditampilkan
     */
    public void tampilkanMenuByKategori(String kategori) {
        System.out.println("\n  Menu Kategori: " + kategori.toUpperCase());
        System.out.println("  ─────────────────────────────────────────────");

        boolean ada = false;
        int nomor = 1;
        for (MenuItem item : menuItems) {
            if (item.getKategori().equalsIgnoreCase(kategori)) {
                System.out.printf("  [%d] ", nomor++);
                item.tampilMenu();
                ada = true;
            }
        }

        if (!ada) {
            System.out.println("Tidak ada item dalam kategori ini.");
        }
    }

    /**
     * Mendapatkan jumlah item dalam menu
     * @return Jumlah item menu
     */
    public int getJumlahItem() {
        return menuItems.size();
    }

    /**
     * Mendapatkan salinan dari daftar menu (defensive copy)
     * @return ArrayList berisi semua item menu
     */
    public ArrayList<MenuItem> getDaftarMenu() {
        return new ArrayList<>(menuItems); // defensive copy
    }

    // ========== OPERASI FILE ==========

    /**
     * Menyimpan daftar menu ke file teks (CSV format)
     * Implementasi OPERASI FILE dengan BufferedWriter
     * @throws FileOperationException jika terjadi kesalahan saat menyimpan
     */
    public void simpanKeFile() throws FileOperationException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_MENU))) {
            writer.write("# File Data Menu Restoran Nusantara");
            writer.newLine();
            writer.write("# Format: TIPE,nama,harga,atribut_tambahan...");
            writer.newLine();

            // STRUKTUR PENGULANGAN: tulis setiap item ke file
            for (MenuItem item : menuItems) {
                writer.write(item.toCSV());
                writer.newLine();
            }

            System.out.println("Menu berhasil disimpan ke file: " + FILE_MENU);
        } catch (IOException e) {
            throw new FileOperationException(
                    "Gagal menyimpan menu ke file: " + e.getMessage(), e);
        }
    }

    /**
     * Memuat daftar menu dari file teks
     * Implementasi OPERASI FILE dengan BufferedReader
     * @throws FileOperationException jika terjadi kesalahan saat memuat
     */
    public void muatDariFile() throws FileOperationException {
        File file = new File(FILE_MENU);
        if (!file.exists()) {
            System.out.println("File menu '" + FILE_MENU + "' tidak ditemukan. Mulai dengan menu kosong.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_MENU))) {
            menuItems.clear(); // bersihkan menu yang ada
            String baris;
            int jumlahDimuat = 0;

            // STRUKTUR PENGULANGAN: baca setiap baris file
            while ((baris = reader.readLine()) != null) {
                // Lewati baris komentar atau kosong
                if (baris.startsWith("#") || baris.trim().isEmpty()) continue;

                String[] bagian = baris.split(",");

                // STRUKTUR KEPUTUSAN: tentukan tipe item berdasarkan token pertama
                try {
                    switch (bagian[0].toUpperCase()) {
                        case "MAKANAN":
                            if (bagian.length >= 5) {
                                Makanan makanan = new Makanan(
                                        bagian[1],
                                        Double.parseDouble(bagian[2]),
                                        bagian[3],
                                        Boolean.parseBoolean(bagian[4])
                                );
                                menuItems.add(makanan);
                                jumlahDimuat++;
                            }
                            break;

                        case "MINUMAN":
                            if (bagian.length >= 5) {
                                Minuman minuman = new Minuman(
                                        bagian[1],
                                        Double.parseDouble(bagian[2]),
                                        bagian[3],
                                        Boolean.parseBoolean(bagian[4])
                                );
                                menuItems.add(minuman);
                                jumlahDimuat++;
                            }
                            break;

                        case "DISKON":
                            if (bagian.length >= 6) {
                                Diskon diskon = new Diskon(
                                        bagian[1],
                                        Double.parseDouble(bagian[2]),
                                        Double.parseDouble(bagian[3]),
                                        bagian[4],
                                        bagian[5]
                                );
                                menuItems.add(diskon);
                                jumlahDimuat++;
                            }
                            break;

                        default:
                            System.out.println("    Baris tidak dikenali: " + baris);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("    Format angka salah pada baris: " + baris);
                }
            }

            System.out.println("Berhasil memuat " + jumlahDimuat + " item dari file: " + FILE_MENU);

        } catch (IOException e) {
            throw new FileOperationException(
                    "Gagal memuat menu dari file: " + e.getMessage(), e);
        }
    }
}
