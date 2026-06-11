import java.util.Scanner;
import java.util.ArrayList;

/**
 * Kelas utama aplikasi Manajemen Restoran.
 * Menyediakan antarmuka menu berbasis teks (CLI) untuk pengguna.
 *
 * Konsep OOP yang diimplementasikan:
 * - ABSTRAKSI      : Kelas abstrak MenuItem
 * - INHERITANCE    : Makanan, Minuman, Diskon extends MenuItem
 * - ENCAPSULATION  : Semua atribut private dengan getter/setter
 * - POLYMORPHISM   : Override metode tampilMenu() di setiap subkelas
 * - EXCEPTION      : Custom exception untuk error handling
 * - I/O & FILE     : Menyimpan dan memuat data dari file
 * - ARRAY & STRING : Penggunaan ArrayList dan manipulasi String
 */
public class RestoranApp {

    private static Menu menu = new Menu();
    private static Pesanan pesananAktif = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        tampilkanHeader();

        // Muat data menu dari file saat program dimulai
        try {
            menu.muatDariFile();
        } catch (FileOperationException e) {
            System.out.println(" " + e.getMessage());
        }

        // Jika menu kosong, tambahkan data contoh
        if (menu.getJumlahItem() == 0) {
            inisialisasiMenuContoh();
        }

        // Loop menu utama
        boolean berjalan = true;
        while (berjalan) {
            tampilkanMenuUtama();
            int pilihan = bacaInputInteger("Masukkan pilihan: ");

            // STRUKTUR KEPUTUSAN: proses pilihan pengguna
            switch (pilihan) {
                case 1:
                    prosesMenuTambahItem();
                    break;
                case 2:
                    menu.tampilkanMenu();
                    break;
                case 3:
                    prosesBuatPesanan();
                    break;
                case 4:
                    prosesTambahItemKePesanan();
                    break;
                case 5:
                    prosesHapusItemDariPesanan();
                    break;
                case 6:
                    prosesTampilkanStruk();
                    break;
                case 7:
                    prosesSimpanMenu();
                    break;
                case 8:
                    prosesMuatMenu();
                    break;
                case 9:
                    prosesLihatPesananAktif();
                    break;
                case 0:
                    berjalan = prosesTutupProgram();
                    break;
                default:
                    System.out.println(" Pilihan tidak valid! Silakan coba lagi.");
            }

            if (berjalan) {
                System.out.println("\n  Tekan ENTER untuk melanjutkan...");
                scanner.nextLine();
            }
        }
    }

    // ========== TAMPILAN HEADER & MENU UTAMA ==========

    private static void tampilkanHeader() {
        System.out.println("\n");
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║      SISTEM MANAJEMEN RESTORAN NUSANTARA         ║");
        System.out.println("  ║          Tugas Praktik 3 - PBO Java              ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void tampilkanMenuUtama() {
        String statusPesanan = (pesananAktif != null && !pesananAktif.isEmpty())
                ? " [Pesanan Aktif: " + pesananAktif.getNomorStruk() + "]"
                : " [Belum ada pesanan]";

        System.out.println("\n  ┌────────────────────────────────────────────────┐");
        System.out.println("  │                 MENU UTAMA                     │");
        System.out.printf("  │  Status: %-38s│%n", statusPesanan);
        System.out.println("  ├────────────────────────────────────────────────┤");
        System.out.println("  │  1. Tambah Item Menu (Makanan/Minuman/Diskon)  │");
        System.out.println("  │  2. Tampilkan Menu Restoran                    │");
        System.out.println("  │  3. Buat Pesanan Baru                          │");
        System.out.println("  │  4. Tambah Item ke Pesanan Aktif               │");
        System.out.println("  │  5. Hapus Item dari Pesanan Aktif              │");
        System.out.println("  │  6. Tampilkan & Simpan Struk Pesanan           │");
        System.out.println("  │  7. Simpan Menu ke File                        │");
        System.out.println("  │  8. Muat Menu dari File                        │");
        System.out.println("  │  9. Lihat Pesanan Aktif                        │");
        System.out.println("  │  0. Keluar Program                             │");
        System.out.println("  └────────────────────────────────────────────────┘");
    }

    // ========== PROSES: TAMBAH ITEM MENU ==========

    private static void prosesMenuTambahItem() {
        System.out.println("\n  ── TAMBAH ITEM MENU ──");
        System.out.println("  Pilih jenis item:");
        System.out.println("  [1] Makanan");
        System.out.println("  [2] Minuman");
        System.out.println("  [3] Diskon");

        int jenis = bacaInputInteger("  Pilihan: ");

        try {
            switch (jenis) {
                case 1:
                    tambahMakanan();
                    break;
                case 2:
                    tambahMinuman();
                    break;
                case 3:
                    tambahDiskon();
                    break;
                default:
                    System.out.println(" Pilihan tidak valid!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void tambahMakanan() {
        System.out.println("\n    Tambah Makanan Baru:");
        System.out.print("  Nama makanan       : ");
        String nama = scanner.nextLine().trim();

        double harga = bacaInputDouble("  Harga (Rp)         : ");

        System.out.print("  Jenis makanan      : ");
        String jenis = scanner.nextLine().trim();

        System.out.print("  Tersedia vegan?    (y/n): ");
        boolean vegan = scanner.nextLine().trim().equalsIgnoreCase("y");

        Makanan makanan = new Makanan(nama, harga, jenis, vegan);
        menu.tambahItem(makanan);
    }

    private static void tambahMinuman() {
        System.out.println("\n    Tambah Minuman Baru:");
        System.out.print("  Nama minuman       : ");
        String nama = scanner.nextLine().trim();

        double harga = bacaInputDouble("  Harga (Rp)         : ");

        System.out.print("  Jenis minuman      : ");
        String jenis = scanner.nextLine().trim();

        System.out.print("  Tersedia dingin?   (y/n): ");
        boolean dingin = scanner.nextLine().trim().equalsIgnoreCase("y");

        Minuman minuman = new Minuman(nama, harga, jenis, dingin);
        menu.tambahItem(minuman);
    }

    private static void tambahDiskon() {
        System.out.println("\n    Tambah Promo Diskon:");
        System.out.print("  Nama promo         : ");
        String nama = scanner.nextLine().trim();

        System.out.print("  Item yang didiskon : ");
        String namaItem = scanner.nextLine().trim();

        double hargaAsli = bacaInputDouble("  Harga asli (Rp)    : ");
        double persen = bacaInputDouble("  Persentase diskon (%) : ");

        System.out.print("  Deskripsi promo    : ");
        String deskripsi = scanner.nextLine().trim();

        try {
            Diskon diskon = new Diskon(nama, hargaAsli, persen, namaItem, deskripsi);
            menu.tambahItem(diskon);
        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    // ========== PROSES: BUAT PESANAN BARU ==========

    private static void prosesBuatPesanan() {
        System.out.println("\n  ── BUAT PESANAN BARU ──");
        System.out.print("  Nama pelanggan : ");
        String nama = scanner.nextLine().trim();

        System.out.print("  Nomor meja     : ");
        String meja = scanner.nextLine().trim();

        pesananAktif = new Pesanan(nama, meja);
        System.out.println("Pesanan baru dibuat untuk: " + nama + " (Meja " + meja + ")");
        System.out.println("  No. Struk: " + pesananAktif.getNomorStruk());
        System.out.println("  Sekarang Anda dapat menambahkan item ke pesanan.");
    }

    // ========== PROSES: TAMBAH ITEM KE PESANAN ==========

    private static void prosesTambahItemKePesanan() {
        if (pesananAktif == null) {
            System.out.println(" Belum ada pesanan aktif! Silakan buat pesanan baru terlebih dahulu (Pilihan 3).");
            return;
        }

        if (menu.getJumlahItem() == 0) {
            System.out.println(" Menu masih kosong! Silakan tambahkan item menu terlebih dahulu.");
            return;
        }

        System.out.println("\n  ── TAMBAH ITEM KE PESANAN ──");
        menu.tampilkanMenu();

        System.out.println("\n  Cara pemilihan:");
        System.out.println("  [A] Pilih berdasarkan nomor urut");
        System.out.println("  [B] Cari berdasarkan nama");
        System.out.print("  Pilihan: ");
        String cara = scanner.nextLine().trim().toUpperCase();

        MenuItem itemDipilih = null;

        try {
            if (cara.equals("A")) {
                int nomor = bacaInputInteger("  Masukkan nomor item: ");
                itemDipilih = menu.getItemByNomor(nomor);
            } else if (cara.equals("B")) {
                System.out.print("  Masukkan nama item: ");
                String namaItem = scanner.nextLine().trim();
                itemDipilih = menu.cariItem(namaItem);
            } else {
                System.out.println(" Pilihan tidak valid!");
                return;
            }

            System.out.println("  Item dipilih: " + itemDipilih.getNama());
            int jumlah = bacaInputInteger("  Jumlah yang dipesan: ");

            pesananAktif.tambahItem(itemDipilih, jumlah);

        } catch (MenuItemNotFoundException e) {
            System.out.println(" " + e.getMessage());
        } catch (InputTidakValidException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    // ========== PROSES: HAPUS ITEM DARI PESANAN ==========

    private static void prosesHapusItemDariPesanan() {
        if (pesananAktif == null || pesananAktif.isEmpty()) {
            System.out.println(" Tidak ada pesanan aktif atau pesanan masih kosong!");
            return;
        }

        prosesLihatPesananAktif();

        System.out.print("\n  Masukkan nama item yang ingin dihapus: ");
        String namaItem = scanner.nextLine().trim();

        try {
            pesananAktif.hapusItem(namaItem);
        } catch (MenuItemNotFoundException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    // ========== PROSES: TAMPILKAN & SIMPAN STRUK ==========

    private static void prosesTampilkanStruk() {
        if (pesananAktif == null || pesananAktif.isEmpty()) {
            System.out.println(" Tidak ada pesanan aktif atau pesanan masih kosong!");
            return;
        }

        try {
            // Tampilkan struk di layar
            pesananAktif.tampilkanStruk();

            // Tanya apakah ingin menyimpan ke file
            System.out.print("\n  Simpan struk ke file? (y/n): ");
            String simpan = scanner.nextLine().trim();

            if (simpan.equalsIgnoreCase("y")) {
                pesananAktif.simpanStrukKeFile();
            }

            // Tanya apakah pesanan selesai
            System.out.print("\n  Selesaikan pesanan dan mulai pesanan baru? (y/n): ");
            String selesai = scanner.nextLine().trim();

            if (selesai.equalsIgnoreCase("y")) {
                System.out.print("  Nama pelanggan baru: ");
                String namaBaru = scanner.nextLine().trim();
                System.out.print("  Nomor meja baru    : ");
                String mejaBaru = scanner.nextLine().trim();
                pesananAktif.resetPesanan(namaBaru, mejaBaru);
                System.out.println("Pesanan baru dibuat: " + pesananAktif.getNomorStruk());
            }

        } catch (PesananKosongException e) {
            System.out.println(" " + e.getMessage());
        } catch (FileOperationException e) {
            System.out.println(" Gagal menyimpan struk: " + e.getMessage());
        }
    }

    // ========== PROSES: SIMPAN & MUAT MENU ==========

    private static void prosesSimpanMenu() {
        if (menu.getJumlahItem() == 0) {
            System.out.println(" Menu masih kosong! Tidak ada yang perlu disimpan.");
            return;
        }
        try {
            menu.simpanKeFile();
        } catch (FileOperationException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    private static void prosesMuatMenu() {
        System.out.print("    Memuat dari file akan mengganti menu saat ini. Lanjutkan? (y/n): ");
        String konfirmasi = scanner.nextLine().trim();
        if (!konfirmasi.equalsIgnoreCase("y")) {
            System.out.println("  Dibatalkan.");
            return;
        }
        try {
            menu.muatDariFile();
        } catch (FileOperationException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    // ========== PROSES: LIHAT PESANAN AKTIF ==========

    private static void prosesLihatPesananAktif() {
        if (pesananAktif == null || pesananAktif.isEmpty()) {
            System.out.println(" Belum ada pesanan aktif atau pesanan kosong.");
            return;
        }

        System.out.println("\n  ── PESANAN AKTIF: " + pesananAktif.getNomorStruk() + " ──");
        System.out.println("  Pelanggan : " + pesananAktif.getNamaPelanggan());
        System.out.println("  Meja      : " + pesananAktif.getNomorMeja());
        System.out.println("  ─────────────────────────────────────────────");

        ArrayList<Pesanan.ItemPesanan> daftar = pesananAktif.getDaftarPesanan();
        int no = 1;
        double total = 0;

        // STRUKTUR PENGULANGAN: tampilkan setiap item pesanan
        for (Pesanan.ItemPesanan ip : daftar) {
            String ket = (ip.getMenuItem() instanceof Diskon) ? " " : "";
            System.out.printf("  [%d] %-25s x%-3d Rp%.0f%s%n",
                    no++, ip.getMenuItem().getNama(), ip.getJumlah(), ip.getSubtotal(), ket);
            total += ip.getSubtotal();
        }

        System.out.println("  ─────────────────────────────────────────────");
        System.out.printf("  Total Sementara: Rp%.0f%n", total);
    }

    // ========== PROSES: KELUAR PROGRAM ==========

    private static boolean prosesTutupProgram() {
        // Tawari menyimpan menu sebelum keluar
        if (menu.getJumlahItem() > 0) {
            System.out.print("\n  Simpan menu sebelum keluar? (y/n): ");
            String simpan = scanner.nextLine().trim();
            if (simpan.equalsIgnoreCase("y")) {
                try {
                    menu.simpanKeFile();
                } catch (FileOperationException e) {
                    System.out.println("Gagal menyimpan: " + e.getMessage());
                }
            }
        }

        System.out.println("\n  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║   Terima kasih telah menggunakan sistem ini! ║");
        System.out.println("  ║         Sampai jumpa!    Selamat makan!      ║");
        System.out.println("  ╚══════════════════════════════════════════════╝\n");
        scanner.close();
        return false; // berhenti loop
    }

    // ========== UTILITAS: BACA INPUT ==========

    /**
     * Membaca input integer dari pengguna dengan validasi dan exception handling
     * @param prompt Pesan prompt yang ditampilkan
     * @return Integer yang valid
     */
    private static int bacaInputInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(" Input tidak valid! Masukkan angka.");
            }
        }
    }

    /**
     * Membaca input double dari pengguna dengan validasi dan exception handling
     * @param prompt Pesan prompt yang ditampilkan
     * @return Double yang valid
     */
    private static double bacaInputDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                double nilai = Double.parseDouble(input);
                if (nilai < 0) {
                    System.out.println(" Nilai tidak boleh negatif!");
                    continue;
                }
                return nilai;
            } catch (NumberFormatException e) {
                System.out.println(" Input tidak valid! Masukkan angka.");
            }
        }
    }

    // ========== INISIALISASI DATA CONTOH ==========

    /**
     * Menambahkan data menu contoh untuk demonstrasi
     */
    private static void inisialisasiMenuContoh() {
        System.out.println("\n  Menambahkan data menu contoh...");

        // Makanan
        menu.tambahItem(new Makanan("Nasi Goreng Spesial", 35000, "Nasi", false));
        menu.tambahItem(new Makanan("Mie Ayam Bakso", 28000, "Mie", false));
        menu.tambahItem(new Makanan("Gado-Gado", 25000, "Sayuran", true));
        menu.tambahItem(new Makanan("Ayam Penyet", 40000, "Ayam", false));
        menu.tambahItem(new Makanan("Tempe Mendoan", 15000, "Snack", true));

        // Minuman
        menu.tambahItem(new Minuman("Es Teh Manis", 8000, "Teh", true));
        menu.tambahItem(new Minuman("Jus Alpukat", 18000, "Jus", true));
        menu.tambahItem(new Minuman("Kopi Susu", 22000, "Kopi", true));
        menu.tambahItem(new Minuman("Air Mineral", 5000, "Air", false));

        // Diskon
        menu.tambahItem(new Diskon("Promo Makan Siang", 35000, 20, "Nasi Goreng Spesial", "Diskon 20% "));
        menu.tambahItem(new Diskon("Happy Hour Minuman", 22000, 15, "Kopi Susu", "Diskon 15% "));

        System.out.println(" Data contoh berhasil dimuat!");
    }
}
