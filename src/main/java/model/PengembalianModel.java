/*
 * File: PengembalianModel.java
 * Package: model
 * Deskripsi: Class model untuk tabel pengembalian
 */

package model;

import java.util.Date;

public class PengembalianModel {
    private int idPengembalian;
    private String noPengembalian;
    private int idPeminjaman;
    private PeminjamanModel peminjaman;
    private int idAnggota;
    private AnggotaModel anggota;
    private Date tanggalPengembalian;
    private double totalDenda;
    private String keterangan;
    private int diterimaOleh;
    private AnggotaModel petugas;
    private Date createdAt;
    
    // Constructor
    public PengembalianModel() {
    }
    
    public PengembalianModel(int idPengembalian, String noPengembalian, int idPeminjaman, 
                             int idAnggota, Date tanggalPengembalian, double totalDenda, 
                             String keterangan, int diterimaOleh, Date createdAt) {
        this.idPengembalian = idPengembalian;
        this.noPengembalian = noPengembalian;
        this.idPeminjaman = idPeminjaman;
        this.idAnggota = idAnggota;
        this.tanggalPengembalian = tanggalPengembalian;
        this.totalDenda = totalDenda;
        this.keterangan = keterangan;
        this.diterimaOleh = diterimaOleh;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getIdPengembalian() {
        return idPengembalian;
    }
    
    public void setIdPengembalian(int idPengembalian) {
        this.idPengembalian = idPengembalian;
    }
    
    public String getNoPengembalian() {
        return noPengembalian;
    }
    
    public void setNoPengembalian(String noPengembalian) {
        this.noPengembalian = noPengembalian;
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
    
    public int getIdAnggota() {
        return idAnggota;
    }
    
    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }
    
    public AnggotaModel getAnggota() {
        return anggota;
    }
    
    public void setAnggota(AnggotaModel anggota) {
        this.anggota = anggota;
    }
    
    public Date getTanggalPengembalian() {
        return tanggalPengembalian;
    }
    
    public void setTanggalPengembalian(Date tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }
    
    public double getTotalDenda() {
        return totalDenda;
    }
    
    public void setTotalDenda(double totalDenda) {
        this.totalDenda = totalDenda;
    }
    
    public String getKeterangan() {
        return keterangan;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public int getDiterimaOleh() {
        return diterimaOleh;
    }
    
    public void setDiterimaOleh(int diterimaOleh) {
        this.diterimaOleh = diterimaOleh;
    }
    
    public AnggotaModel getPetugas() {
        return petugas;
    }
    
    public void setPetugas(AnggotaModel petugas) {
        this.petugas = petugas;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}