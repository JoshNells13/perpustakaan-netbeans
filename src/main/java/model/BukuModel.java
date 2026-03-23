/*
 * File: BukuModel.java
 * Package: model
 * Deskripsi: Class model untuk tabel buku
 */

package model;

import java.util.Date;

public class BukuModel {
    private int idBuku;
    private String kodeBuku;
    private String judulBuku;
    private String pengarang;
    private String penerbit;
    private String tahunTerbit;
    private String isbn;
    private String kategori;
    private int jumlahTersedia;
    private int jumlahTotal;
    private String letakRak;
    private String deskripsi;
    private Date tanggalMasuk;
    private String statusBuku;
    
    // Constructor
    public BukuModel() {
    }
    
    public BukuModel(int idBuku, String kodeBuku, String judulBuku, String pengarang, 
                     String penerbit, String tahunTerbit, String isbn, String kategori, 
                     int jumlahTersedia, int jumlahTotal, String letakRak, 
                     String deskripsi, Date tanggalMasuk, String statusBuku) {
        this.idBuku = idBuku;
        this.kodeBuku = kodeBuku;
        this.judulBuku = judulBuku;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.isbn = isbn;
        this.kategori = kategori;
        this.jumlahTersedia = jumlahTersedia;
        this.jumlahTotal = jumlahTotal;
        this.letakRak = letakRak;
        this.deskripsi = deskripsi;
        this.tanggalMasuk = tanggalMasuk;
        this.statusBuku = statusBuku;
    }
    
    // Getters and Setters
    public int getIdBuku() {
        return idBuku;
    }
    
    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }
    
    public String getKodeBuku() {
        return kodeBuku;
    }
    
    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }
    
    public String getJudulBuku() {
        return judulBuku;
    }
    
    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }
    
    public String getPengarang() {
        return pengarang;
    }
    
    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }
    
    public String getPenerbit() {
        return penerbit;
    }
    
    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }
    
    public String getTahunTerbit() {
        return tahunTerbit;
    }
    
    public void setTahunTerbit(String tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getKategori() {
        return kategori;
    }
    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    public int getJumlahTersedia() {
        return jumlahTersedia;
    }
    
    public void setJumlahTersedia(int jumlahTersedia) {
        this.jumlahTersedia = jumlahTersedia;
    }
    
    public int getJumlahTotal() {
        return jumlahTotal;
    }
    
    public void setJumlahTotal(int jumlahTotal) {
        this.jumlahTotal = jumlahTotal;
    }
    
    public String getLetakRak() {
        return letakRak;
    }
    
    public void setLetakRak(String letakRak) {
        this.letakRak = letakRak;
    }
    
    public String getDeskripsi() {
        return deskripsi;
    }
    
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    public Date getTanggalMasuk() {
        return tanggalMasuk;
    }
    
    public void setTanggalMasuk(Date tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }
    
    public String getStatusBuku() {
        return statusBuku;
    }
    
    public void setStatusBuku(String statusBuku) {
        this.statusBuku = statusBuku;
    }
    
    @Override
    public String toString() {
        return judulBuku + " (" + kodeBuku + ")";
    }
}