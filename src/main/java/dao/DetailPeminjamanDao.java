/*
 * File: DetailPeminjamanDAO.java
 * Package: dao
 * Deskripsi: Interface DAO untuk tabel detail_peminjaman
 */

package dao;

import java.util.List;
import model.DetailPeminjamanModel;

public interface DetailPeminjamanDao {
    boolean insert(DetailPeminjamanModel detail);
    boolean update(DetailPeminjamanModel detail);
    boolean delete(int idDetail);
    DetailPeminjamanModel getById(int idDetail);
    List<DetailPeminjamanModel> getByPeminjaman(int idPeminjaman);
    List<DetailPeminjamanModel> getByBuku(int idBuku);
    boolean updateStatusKembali(int idDetail, boolean statusKembali);
    boolean updateTanggalKembali(int idDetail, java.util.Date tanggalKembali);
    int getJumlahBukuDipinjam(int idPeminjaman);
    int getJumlahBukuDikembalikan(int idPeminjaman);
}