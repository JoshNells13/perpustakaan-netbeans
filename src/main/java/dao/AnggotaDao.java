/*
 * File: AnggotaDAO.java
 * Package: dao
 * Deskripsi: Interface DAO untuk tabel anggota
 */

package dao;

import java.util.List;
import model.AnggotaModel;

public interface AnggotaDao {
    boolean insert(AnggotaModel anggota);
    boolean update(AnggotaModel anggota);
    boolean delete(int idAnggota);
    AnggotaModel getById(int idAnggota);
    AnggotaModel getByUsername(String username);
    AnggotaModel login(String username, String password);
    List<AnggotaModel> getAll();
    List<AnggotaModel> search(String keyword);
    int getCount();
    boolean activate(int idAnggota);
    boolean deactivate(int idAnggota);
}