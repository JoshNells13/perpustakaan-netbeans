/*
 * File: PeminjamanDAO.java
 * Package: dao
 * Deskripsi: Interface DAO untuk tabel peminjaman
 */

package dao;

import java.util.Date;
import java.util.List;
import model.PeminjamanModel;

public interface PeminjamanDao {
    boolean insert(PeminjamanModel peminjaman);
    boolean update(PeminjamanModel peminjaman);
    boolean delete(int idPeminjaman);
    PeminjamanModel getById(int idPeminjaman);
    PeminjamanModel getByNoPeminjaman(String noPeminjaman);
    List<PeminjamanModel> getAll();
    List<PeminjamanModel> getByAnggota(int idAnggota);
    List<PeminjamanModel> getByStatus(String status);
    List<PeminjamanModel> getByTanggal(Date tanggalMulai, Date tanggalAkhir);
    List<PeminjamanModel> search(String keyword);
    String generateNoPeminjaman();
    int getCount();
    boolean updateStatus(int idPeminjaman, String status);
}