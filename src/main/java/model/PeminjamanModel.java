/*
 * File: PeminjamanModel.java
 * Package: model
 * Deskripsi: Class model untuk tabel peminjaman
 */

package model;

import java.util.Date;
import java.util.List;

public class PeminjamanModel {
    private int idPeminjaman;
    private String noPeminjaman;
    private int idAnggota;
    private AnggotaModel anggota;
    private Date tanggalPinjam;
    private Date tanggalJatuhTempo;
    private String statusPeminjaman;
    private String keterangan;
    private int createdBy;
    private Date createdAt;
    private List<DetailPeminjamanModel> detailPeminjaman;
    
    // Constructor
    public PeminjamanModel() {
    }
    
    public PeminjamanModel(int idPeminjaman, String noPeminjaman, int idAnggota, 
                           Date tanggalPinjam, Date tanggalJatuhTempo, 
                           String statusPeminjaman, String keterangan, 
                           int createdBy, Date createdAt) {
        this.idPeminjaman = idPeminjaman;
        this.noPeminjaman = noPeminjaman;
        this.idAnggota = idAnggota;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.statusPeminjaman = statusPeminjaman;
        this.keterangan = keterangan;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getIdPeminjaman() {
        return idPeminjaman;
    }
    
    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }
    
    public String getNoPeminjaman() {
        return noPeminjaman;
    }
    
    public void setNoPeminjaman(String noPeminjaman) {
        this.noPeminjaman = noPeminjaman;
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
    
    public Date getTanggalPinjam() {
        return tanggalPinjam;
    }
    
    public void setTanggalPinjam(Date tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }
    
    public Date getTanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }
    
    public void setTanggalJatuhTempo(Date tanggalJatuhTempo) {
        this.tanggalJatuhTempo = tanggalJatuhTempo;
    }
    
    public String getStatusPeminjaman() {
        return statusPeminjaman;
    }
    
    public void setStatusPeminjaman(String statusPeminjaman) {
        this.statusPeminjaman = statusPeminjaman;
    }
    
    public String getKeterangan() {
        return keterangan;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<DetailPeminjamanModel> getDetailPeminjaman() {
        return detailPeminjaman;
    }
    
    public void setDetailPeminjaman(List<DetailPeminjamanModel> detailPeminjaman) {
        this.detailPeminjaman = detailPeminjaman;
    }
}