/*
 * File: BukuDAO.java
 * Package: dao
 * Deskripsi: Interface DAO untuk tabel buku
 */

package dao;

import java.util.List;
import model.BukuModel;

public interface BukuDao {
    boolean insert(BukuModel buku);
    boolean update(BukuModel buku);
    boolean delete(int idBuku);
    BukuModel getById(int idBuku);
    BukuModel getByKode(String kodeBuku);
    List<BukuModel> getAll();
    List<BukuModel> search(String keyword);
    List<BukuModel> getByKategori(String kategori);
    List<BukuModel> getTersedia();
    int getCount();
    boolean updateStok(int idBuku, int perubahan);
}