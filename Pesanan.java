import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Pesanan {

    public static class ItemPesanan {
        private MenuItem menuItem;
        private int jumlah;
        private double hargaSatuan;

        public ItemPesanan(MenuItem menuItem, int jumlah) {
            this.menuItem = menuItem;
            this.jumlah = jumlah;
            this.hargaSatuan = (menuItem instanceof Diskon)
                    ? ((Diskon) menuItem).getHargaSetelahDiskon()
                    : menuItem.getHarga();
        }

        public MenuItem getMenuItem() { return menuItem; }
        public int getJumlah() { return jumlah; }
        public double getHargaSatuan() { return hargaSatuan; }
        public double getSubtotal() { return hargaSatuan * jumlah; }
    }

    private static final String FOLDER_STRUK = "struk/";
    private ArrayList<ItemPesanan> daftarPesanan;
    private String namaPelanggan;
    private String nomorMeja;
    private LocalDateTime waktuPesan;
    private static int counterPesanan = 1;
    private String nomorStruk;

    public Pesanan(String namaPelanggan, String nomorMeja) {
        this.daftarPesanan = new ArrayList<>();
        this.namaPelanggan = namaPelanggan;
        this.nomorMeja = nomorMeja;
        this.waktuPesan = LocalDateTime.now();
        this.nomorStruk = String.format("INV-%04d", counterPesanan++);
    }

    public String getNamaPelanggan() { return namaPelanggan; }
    public String getNomorMeja() { return nomorMeja; }
    public String getNomorStruk() { return nomorStruk; }
    public ArrayList<ItemPesanan> getDaftarPesanan() {
        return new ArrayList<>(daftarPesanan);
    }

    public void tambahItem(MenuItem item, int jumlah) throws InputTidakValidException {
        if (jumlah <= 0) {
            throw new InputTidakValidException("Jumlah pesanan harus lebih dari 0!");
        }
        if (item == null) {
            throw new InputTidakValidException("Item pesanan tidak boleh null!");
        }

        for (ItemPesanan ip : daftarPesanan) {
            if (ip.getMenuItem().getNama().equalsIgnoreCase(item.getNama())) {
                daftarPesanan.remove(ip);
                daftarPesanan.add(new ItemPesanan(item, ip.getJumlah() + jumlah));
                System.out.println("[OK] Jumlah '" + item.getNama() + "' diperbarui menjadi " + (ip.getJumlah() + jumlah));
                return;
            }
        }

        daftarPesanan.add(new ItemPesanan(item, jumlah));
        System.out.println("[OK] '" + item.getNama() + "' x" + jumlah + " ditambahkan ke pesanan!");
    }

    public void hapusItem(String namaItem) throws MenuItemNotFoundException {
        ItemPesanan itemDitemukan = null;

        for (ItemPesanan ip : daftarPesanan) {
            if (ip.getMenuItem().getNama().equalsIgnoreCase(namaItem)) {
                itemDitemukan = ip;
                break;
            }
        }

        if (itemDitemukan == null) {
            throw new MenuItemNotFoundException("Item '" + namaItem + "' tidak ada dalam pesanan!");
        }

        daftarPesanan.remove(itemDitemukan);
        System.out.println("[OK] '" + namaItem + "' dihapus dari pesanan!");
    }

    public double hitungTotal() throws PesananKosongException {
        if (daftarPesanan.isEmpty()) {
            throw new PesananKosongException("Pesanan masih kosong!");
        }

        double total = 0;
        for (ItemPesanan ip : daftarPesanan) {
            total += ip.getSubtotal();
        }
        return total;
    }

    public double hitungTotalHemat() {
        double totalHemat = 0;

        for (ItemPesanan ip : daftarPesanan) {
            if (ip.getMenuItem() instanceof Diskon) {
                Diskon d = (Diskon) ip.getMenuItem();
                totalHemat += d.getJumlahHemat() * ip.getJumlah();
            }
        }

        return totalHemat;
    }

    public void tampilkanStruk() throws PesananKosongException {
        if (daftarPesanan.isEmpty()) {
            throw new PesananKosongException("Tidak ada item dalam pesanan untuk ditampilkan!");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        double total = hitungTotal();
        double totalHemat = hitungTotalHemat();

        System.out.println("\n  +================================================+");
        System.out.println("  |           STRUK PESANAN PELANGGAN              |");
        System.out.println("  +================================================+");
        System.out.printf("  | No. Struk  : %-34s |\n", nomorStruk);
        System.out.printf("  | Pelanggan  : %-34s |\n", namaPelanggan);
        System.out.printf("  | Meja No.   : %-34s |\n", nomorMeja);
        System.out.printf("  | Waktu      : %-34s |\n", waktuPesan.format(formatter));
        System.out.println("  +------------------------------------------------+");
        System.out.println("  | ITEM PESANAN:                                  |");
        System.out.println("  +------------------------------------------------+");

        for (ItemPesanan ip : daftarPesanan) {
            String ket = (ip.getMenuItem() instanceof Diskon) ? " [DISKON]" : "";
            System.out.printf("  | %-46s |\n", ip.getMenuItem().getNama() + ket);
            System.out.printf("  |   x%-3d @ Rp%-10.0f = Rp%-17.0f |\n",
                    ip.getJumlah(),
                    ip.getHargaSatuan(),
                    ip.getSubtotal());
        }

        System.out.println("  +------------------------------------------------+");

        if (totalHemat > 0) {
            System.out.printf("  | Total Hemat : Rp%-31.0f |\n", totalHemat);
        }

        System.out.printf("  | TOTAL BAYAR : Rp%-31.0f |\n", total);
        System.out.println("  +================================================+");
        System.out.println("  |      Terima kasih atas kunjungan Anda!          |");
        System.out.println("  |      Selamat menikmati makanan Anda!            |");
        System.out.println("  +================================================+");
    }

    public void simpanStrukKeFile() throws FileOperationException, PesananKosongException {
        if (daftarPesanan.isEmpty()) {
            throw new PesananKosongException("Tidak ada pesanan untuk disimpan!");
        }

        File folder = new File(FOLDER_STRUK);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String namaFile = FOLDER_STRUK + nomorStruk + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            double total = hitungTotal();
            double totalHemat = hitungTotalHemat();

            writer.write("================================================");
            writer.newLine();
            writer.write("       STRUK PESANAN - RESTORAN NUSANTARA       ");
            writer.newLine();
            writer.write("================================================");
            writer.newLine();
            writer.write("No. Struk  : " + nomorStruk);
            writer.newLine();
            writer.write("Pelanggan  : " + namaPelanggan);
            writer.newLine();
            writer.write("Meja No.   : " + nomorMeja);
            writer.newLine();
            writer.write("Waktu      : " + waktuPesan.format(formatter));
            writer.newLine();
            writer.write("------------------------------------------------");
            writer.newLine();
            writer.write("ITEM PESANAN:");
            writer.newLine();

            for (ItemPesanan ip : daftarPesanan) {
                String ket = (ip.getMenuItem() instanceof Diskon) ? " [DISKON]" : "";
                writer.write(ip.getMenuItem().getNama() + ket);
                writer.newLine();
                writer.write(String.format("  x%-3d @ Rp%-10.0f = Rp%.0f",
                        ip.getJumlah(),
                        ip.getHargaSatuan(),
                        ip.getSubtotal()));
                writer.newLine();
            }

            writer.write("------------------------------------------------");
            writer.newLine();

            if (totalHemat > 0) {
                writer.write(String.format("Total Hemat : Rp%.0f", totalHemat));
                writer.newLine();
            }

            writer.write(String.format("TOTAL BAYAR : Rp%.0f", total));
            writer.newLine();
            writer.write("================================================");
            writer.newLine();
            writer.write("Terima kasih atas kunjungan Anda!");
            writer.newLine();

            System.out.println("[OK] Struk berhasil disimpan ke file: " + namaFile);

        } catch (IOException e) {
            throw new FileOperationException("Gagal menyimpan struk ke file: " + e.getMessage(), e);
        }
    }

    public boolean isEmpty() {
        return daftarPesanan.isEmpty();
    }

    public void resetPesanan(String namaPelanggan, String nomorMeja) {
        this.daftarPesanan.clear();
        this.namaPelanggan = namaPelanggan;
        this.nomorMeja = nomorMeja;
        this.waktuPesan = LocalDateTime.now();
        this.nomorStruk = String.format("INV-%04d", counterPesanan++);
    }
}