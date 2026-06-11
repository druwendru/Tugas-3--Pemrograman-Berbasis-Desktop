# TUGAS PRAKTIK 3 - MANAJEMEN RESTORAN

**Mata Kuliah:** Pemrograman Berbasis Desktop (Java)

**Nama:** Andre Bintang Pramastyo
**NIM:** 050600902
**UPBJJ:** UT Surabaya

---

## Struktur Proyek

```text
src/
├── MenuItem.java
├── Makanan.java
├── Minuman.java
├── Diskon.java
├── Menu.java
├── Pesanan.java
├── RestoranException.java
└── RestoranApp.java

bin/
└── RestoranApp.jar
```

### Penjelasan File

| File                   | Fungsi                                        |
| ---------------------- | --------------------------------------------- |
| MenuItem.java          | Kelas abstrak sebagai dasar seluruh item menu |
| Makanan.java           | Subclass untuk menu makanan                   |
| Minuman.java           | Subclass untuk menu minuman                   |
| Diskon.java            | Subclass untuk menu diskon                    |
| Menu.java              | Mengelola daftar menu dan operasi file        |
| Pesanan.java           | Mengelola pesanan pelanggan dan struk         |
| RestoranException.java | Kumpulan custom exception                     |
| RestoranApp.java       | Kelas utama (Main Program)                    |
| RestoranApp.jar        | File executable Java                          |

---

# Konsep Pemrograman Berbasis Objek yang Diimplementasikan

## 1. Abstraksi (Abstraction)

Menggunakan kelas abstrak:

```java
abstract class MenuItem
```

serta method abstrak:

```java
abstract void tampilMenu();
```

---

## 2. Inheritance

Menggunakan pewarisan kelas:

```java
Makanan extends MenuItem
Minuman extends MenuItem
Diskon extends MenuItem
```

---

## 3. Encapsulation

Semua atribut dibuat private dan diakses melalui getter serta setter.

Contoh:

```java
private String nama;
private double harga;
```

---

## 4. Polymorphism

Method `tampilMenu()` dioverride pada masing-masing subclass sehingga menghasilkan tampilan yang berbeda sesuai jenis menu.

---

## 5. Exception Handling

Menggunakan custom exception untuk menangani kesalahan program, seperti:

* MenuItemNotFoundException
* PesananKosongException
* InvalidMenuException

---

## 6. Input/Output dan Operasi File

Program dapat:

* Menyimpan data menu ke file `data_menu.txt`
* Membaca data menu dari file `data_menu.txt`
* Menyimpan struk transaksi ke folder `struk/`

---

## 7. Array dan String

Menggunakan:

* ArrayList
* Manipulasi String
* Pencarian berdasarkan nama menu

---

## 8. Struktur Kontrol Program

Mengimplementasikan:

* if-else
* switch-case
* while
* for-each

---

#  Cara Menjalankan Program

## Kompilasi Program

```bash
cd src

javac MenuItem.java RestoranException.java Makanan.java Minuman.java Diskon.java Menu.java Pesanan.java RestoranApp.java
```

## Menjalankan Program

```bash
java RestoranApp
```

## Menjalankan File JAR

```bash
java -jar bin/RestoranApp.jar
```

---

#  Fitur Program

### Manajemen Menu

* Menambah menu makanan
* Menambah menu minuman
* Menambah menu diskon
* Menampilkan seluruh menu restoran

### Manajemen Pesanan

* Membuat pesanan baru
* Menambah item ke pesanan
* Menghapus item dari pesanan
* Menampilkan pesanan aktif

### Struk dan File

* Menampilkan struk transaksi
* Menyimpan struk ke file
* Menyimpan data menu ke file
* Memuat data menu dari file

---

#  Output Program

Program berjalan menggunakan tampilan Console (CLI) dan menyediakan menu interaktif untuk:

1. Mengelola menu restoran
2. Mengelola pesanan pelanggan
3. Menyimpan serta memuat data dari file
4. Membuat dan menyimpan struk transaksi
5. Menampilkan informasi pesanan secara real-time

---

#  Author

**Andre Bintang Pramastyo**
Universitas Terbuka - UPBJJ Surabaya
Tugas Praktik 3 Pemrograman Berbasis Desktop (Java)
