/*
 * File: DetailPeminjamanModel.java
 * Package: model
 * Deskripsi: Class model untuk tabel detail_peminjaman
 */

package model;

import java.util.Date;

public class DetailPeminjamanModel {
    private int idDetail;
    private int idPeminjaman;
    private PeminjamanModel peminjaman;
    private int idBuku;
    private BukuModel buku;
    private boolean statusKembali;
    private Date tanggalKembali;
    private double denda;
    
    // Constructor
    public DetailPeminjamanModel() {
    }
    
    public DetailPeminjamanModel(int idDetail, int idPeminjaman, int idBuku, 
                                 boolean statusKembali, Date tanggalKembali, double denda) {
        this.idDetail = idDetail;
        this.idPeminjaman = idPeminjaman;
        this.idBuku = idBuku;
        this.statusKembali = statusKembali;
        this.tanggalKembali = tanggalKembali;
        this.denda = denda;
    }
    
    // Getters and Setters
    public int getIdDetail() {
        return idDetail;
    }
    
    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }
    
    public int getIdPeminjaman() {
        return idPeminjaman;
    }
    
    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }
    
    public PeminjamanModel getPeminjaman() {
        return peminjaman;
    }
    
    public void setPeminjaman(PeminjamanModel peminjaman) {
        this.peminjaman = peminjaman;
    }
    
    public int getIdBuku() {
        return idBuku;
    }
    
    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }
    
    public BukuModel getBuku() {
        return buku;
    }
    
    public void setBuku(BukuModel buku) {
        this.buku = buku;
    }
    
    public boolean isStatusKembali() {
        return statusKembali;
    }
    
    public void setStatusKembali(boolean statusKembali) {
        this.statusKembali = statusKembali;
    }
    
    public Date getTanggalKembali() {
        return tanggalKembali;
    }
    
    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }
    
    public double getDenda() {
        return denda;
    }
    
    public void setDenda(double denda) {
        this.denda = denda;
    }
}