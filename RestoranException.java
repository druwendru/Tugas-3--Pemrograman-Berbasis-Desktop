/**
 * Kelas Exception kustom untuk penanganan kesalahan pada aplikasi restoran.
 * Mengimplementasikan konsep EXCEPTION HANDLING.
 */

// Exception untuk item menu yang tidak ditemukan
class MenuItemNotFoundException extends Exception {
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}

// Exception untuk pesanan yang kosong
class PesananKosongException extends Exception {
    public PesananKosongException(String message) {
        super(message);
    }
}

// Exception untuk input tidak valid
class InputTidakValidException extends Exception {
    public InputTidakValidException(String message) {
        super(message);
    }
}

// Exception untuk operasi file
class FileOperationException extends Exception {
    public FileOperationException(String message) {
        super(message);
    }
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
