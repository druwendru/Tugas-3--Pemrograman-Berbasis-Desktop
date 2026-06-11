==========================================================
  TUGAS PRAKTIK 3 - MANAJEMEN RESTORAN
  Pemrograman Berbasis Desktop - Java
  Nama : Andre Bintang Pramastyo
  NIM  : 050600902
  UPBJJ UT Surabaya
==========================================================

STRUKTUR PROYEK:
  src/
    MenuItem.java      - Kelas ABSTRAK (dasar hierarki)
    Makanan.java       - Subkelas Makanan (INHERITANCE)
    Minuman.java       - Subkelas Minuman (INHERITANCE)
    Diskon.java        - Subkelas Diskon  (INHERITANCE)
    Menu.java          - Pengelola daftar menu + File I/O
    Pesanan.java       - Pencatatan pesanan + Struk + File I/O
    RestoranException.java - Kelas-kelas Exception kustom
    RestoranApp.java   - Kelas MAIN (menu utama)
  bin/
    RestoranApp.jar    - File JAR siap jalankan

KONSEP OOP YANG DIIMPLEMENTASIKAN:
  ✓ ABSTRAKSI    : abstract class MenuItem, abstract method tampilMenu()
  ✓ INHERITANCE  : Makanan, Minuman, Diskon extends MenuItem
  ✓ ENCAPSULATION: Semua atribut private, akses via getter/setter
  ✓ POLYMORPHISM : Override tampilMenu() di setiap subkelas
  ✓ EXCEPTION    : Custom exception (MenuItemNotFoundException, dll.)
  ✓ I/O & FILE   : Simpan/muat menu (data_menu.txt) & struk (struk/)
  ✓ ARRAY/STRING : ArrayList<MenuItem>, ArrayList<ItemPesanan>, String ops
  ✓ STRUKTUR     : switch-case, for-each, while loop, if-else

CARA MENJALANKAN:
  1. Kompilasi:
     cd src
     javac MenuItem.java RestoranException.java Makanan.java Minuman.java Diskon.java Menu.java Pesanan.java RestoranApp.java

  2. Jalankan:
     java RestoranApp

  3. Atau jalankan JAR:
     java -jar bin/RestoranApp.jar

FITUR PROGRAM:
  1. Tambah item menu (Makanan / Minuman / Diskon)
  2. Tampilkan seluruh menu restoran
  3. Buat pesanan pelanggan baru
  4. Tambah item ke pesanan (by nomor / by nama)
  5. Hapus item dari pesanan
  6. Tampilkan & simpan struk pesanan ke file
  7. Simpan menu ke file (data_menu.txt)
  8. Muat menu dari file (data_menu.txt)
  9. Lihat pesanan aktif
==========================================================
