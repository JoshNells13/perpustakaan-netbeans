/*
 * File: PengembalianDAO.java
 * Package: dao
 * Deskripsi: Interface DAO untuk tabel pengembalian
 */

package dao;

import java.util.Date;
import java.util.List;
import model.PengembalianModel;

public interface PengembalianDao {
    boolean insert(PengembalianModel pengembalian);
    boolean update(PengembalianModel pengembalian);
    boolean delete(int idPengembalian);
    PengembalianModel getById(int idPengembalian);
    PengembalianModel getByNoPengembalian(String noPengembalian);
    PengembalianModel getByPeminjaman(int idPeminjaman);
    List<PengembalianModel> getAll();
    List<PengembalianModel> getByAnggota(int idAnggota);
    List<PengembalianModel> getByTanggal(Date tanggalMulai, Date tanggalAkhir);
    String generateNoPengembalian();
    double hitungDenda(Date tanggalJatuhTempo, Date tanggalKembali);
}