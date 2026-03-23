/*
 * File: AnggotaModel.java
 * Package: model
 * Deskripsi: Class model untuk tabel anggota
 */

package model;

import java.util.Date;

public class AnggotaModel {
    private int idAnggota;
    private String nis;
    private String namaLengkap;
    private String kelas;
    private String jenisKelamin;
    private String alamat;
    private String noTelepon;
    private String username;
    private String password;
    private String role;
    private Date tanggalDaftar;
    private boolean statusAktif;
    
    // Constructor kosong
    public AnggotaModel() {
    }
    
    // Constructor dengan parameter
    public AnggotaModel(int idAnggota, String nis, String namaLengkap, String kelas, 
                        String jenisKelamin, String alamat, String noTelepon, 
                        String username, String password, String role, 
                        Date tanggalDaftar, boolean statusAktif) {
        this.idAnggota = idAnggota;
        this.nis = nis;
        this.namaLengkap = namaLengkap;
        this.kelas = kelas;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.username = username;
        this.password = password;
        this.role = role;
        this.tanggalDaftar = tanggalDaftar;
        this.statusAktif = statusAktif;
    }
    
    // Getters and Setters
    public int getIdAnggota() {
        return idAnggota;
    }
    
    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }
    
    public String getNis() {
        return nis;
    }
    
    public void setNis(String nis) {
        this.nis = nis;
    }
    
    public String getNamaLengkap() {
        return namaLengkap;
    }
    
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
    
    public String getKelas() {
        return kelas;
    }
    
    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
    
    public String getJenisKelamin() {
        return jenisKelamin;
    }
    
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    
    public String getAlamat() {
        return alamat;
    }
    
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public String getNoTelepon() {
        return noTelepon;
    }
    
    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Date getTanggalDaftar() {
        return tanggalDaftar;
    }
    
    public void setTanggalDaftar(Date tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }
    
    public boolean isStatusAktif() {
        return statusAktif;
    }
    
    public void setStatusAktif(boolean statusAktif) {
        this.statusAktif = statusAktif;
    }
    
    @Override
    public String toString() {
        return namaLengkap + " (" + nis + ")";
    }
}