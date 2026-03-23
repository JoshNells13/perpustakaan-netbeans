/*
 * File: PeminjamanDAOImpl.java
 * Package: dao.impl
 * Deskripsi: Implementasi DAO untuk tabel peminjaman
 */

package daoimpl;

import config.Koneksi;
import dao.PeminjamanDao;
import dao.AnggotaDao;
import model.PeminjamanModel;
import model.AnggotaModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class PeminjamanDAOImpl implements PeminjamanDao {
    private Connection conn;
    private AnggotaDao anggotaDAO;
    
    public PeminjamanDAOImpl() {
        this.conn = Koneksi.getConnection();
        this.anggotaDAO = new AnggotaDAOImpl();
    }
    
    @Override
    public boolean insert(PeminjamanModel peminjaman) {
        String sql = "INSERT INTO peminjaman (no_peminjaman, id_anggota, tanggal_pinjam, tanggal_jatuh_tempo, status_peminjaman, keterangan, created_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, peminjaman.getNoPeminjaman());
            ps.setInt(2, peminjaman.getIdAnggota());
            ps.setDate(3, new java.sql.Date(peminjaman.getTanggalPinjam().getTime()));
            ps.setDate(4, new java.sql.Date(peminjaman.getTanggalJatuhTempo().getTime()));
            ps.setString(5, peminjaman.getStatusPeminjaman());
            ps.setString(6, peminjaman.getKeterangan());
            ps.setInt(7, peminjaman.getCreatedBy());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        peminjaman.setIdPeminjaman(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error insert peminjaman: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(PeminjamanModel peminjaman) {
        String sql = "UPDATE peminjaman SET id_anggota=?, tanggal_pinjam=?, tanggal_jatuh_tempo=?, status_peminjaman=?, keterangan=? WHERE id_peminjaman=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, peminjaman.getIdAnggota());
            ps.setDate(2, new java.sql.Date(peminjaman.getTanggalPinjam().getTime()));
            ps.setDate(3, new java.sql.Date(peminjaman.getTanggalJatuhTempo().getTime()));
            ps.setString(4, peminjaman.getStatusPeminjaman());
            ps.setString(5, peminjaman.getKeterangan());
            ps.setInt(6, peminjaman.getIdPeminjaman());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error update peminjaman: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(int idPeminjaman) {
        String sql = "DELETE FROM peminjaman WHERE id_peminjaman=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPeminjaman);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error delete peminjaman: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public PeminjamanModel getById(int idPeminjaman) {
        String sql = "SELECT * FROM peminjaman WHERE id_peminjaman=?";
        PeminjamanModel peminjaman = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPeminjaman);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    peminjaman = mapResultSetToPeminjaman(rs);
                    
                    // Set anggota
                    AnggotaModel anggota = anggotaDAO.getById(peminjaman.getIdAnggota());
                    peminjaman.setAnggota(anggota);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return peminjaman;
    }
    
    @Override
    public PeminjamanModel getByNoPeminjaman(String noPeminjaman) {
        String sql = "SELECT * FROM peminjaman WHERE no_peminjaman=?";
        PeminjamanModel peminjaman = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, noPeminjaman);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    peminjaman = mapResultSetToPeminjaman(rs);
                    
                    // Set anggota
                    AnggotaModel anggota = anggotaDAO.getById(peminjaman.getIdAnggota());
                    peminjaman.setAnggota(anggota);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return peminjaman;
    }
    
    @Override
    public List<PeminjamanModel> getAll() {
        String sql = "SELECT * FROM peminjaman ORDER BY tanggal_pinjam DESC";
        List<PeminjamanModel> list = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PeminjamanModel peminjaman = mapResultSetToPeminjaman(rs);
                
                // Set anggota
                AnggotaModel anggota = anggotaDAO.getById(peminjaman.getIdAnggota());
                peminjaman.setAnggota(anggota);
                
                list.add(peminjaman);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<PeminjamanModel> getByAnggota(int idAnggota) {
        String sql = "SELECT * FROM peminjaman WHERE id_anggota=? ORDER BY tanggal_pinjam DESC";
        List<PeminjamanModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAnggota);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PeminjamanModel peminjaman = mapResultSetToPeminjaman(rs);
                    
                    // Set anggota
                    AnggotaModel anggota = anggotaDAO.getById(peminjaman.getIdAnggota());
                    peminjaman.setAnggota(anggota);
                    
                    list.add(peminjaman);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<PeminjamanModel> getByStatus(String status) {
        String sql = "SELECT * FROM peminjaman WHERE status_peminjaman=? ORDER BY tanggal_pinjam DESC";
        List<PeminjamanModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PeminjamanModel peminjaman = mapResultSetToPeminjaman(rs);
                    
                    // Set anggota
                    AnggotaModel anggota = anggotaDAO.getById(peminjaman.getIdAnggota());
                    peminjaman.setAnggota(anggota);
                    
                    list.add(peminjaman);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<PeminjamanModel> getByTanggal(Date tanggalMulai, Date tanggalAkhir) {
        String sql = "SELECT * FROM peminjaman WHERE DATE(tanggal_pinjam) BETWEEN ? AND ? ORDER BY tanggal_pinjam DESC";
        List<PeminjamanModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(tanggalMulai.getTime()));
            ps.setDate(2, new java.sql.Date(tanggalAkhir.getTime()));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PeminjamanModel peminjaman = mapResultSetToPeminjaman(rs);
                    
                    // Set anggota
                    AnggotaModel anggota = anggotaDAO.getById(peminjaman.getIdAnggota());
                    peminjaman.setAnggota(anggota);
                    
                    list.add(peminjaman);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<PeminjamanModel> search(String keyword) {
        String sql = "SELECT p.* FROM peminjaman p " +
                     "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                     "WHERE p.no_peminjaman LIKE ? OR a.nama_lengkap LIKE ? OR a.nis LIKE ? " +
                     "ORDER BY p.tanggal_pinjam DESC";
        List<PeminjamanModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PeminjamanModel peminjaman = mapResultSetToPeminjaman(rs);
                    
                    // Set anggota
                    AnggotaModel anggota = anggotaDAO.getById(peminjaman.getIdAnggota());
                    peminjaman.setAnggota(anggota);
                    
                    list.add(peminjaman);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public String generateNoPeminjaman() {
        String noPeminjaman = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String bulanTahun = sdf.format(new Date());
        
        String sql = "SELECT COUNT(*) FROM peminjaman WHERE no_peminjaman LIKE 'PJ" + bulanTahun + "%'";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int urutan = rs.getInt(1) + 1;
                noPeminjaman = "PJ" + bulanTahun + String.format("%04d", urutan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return noPeminjaman;
    }
    
    @Override
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM peminjaman";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    @Override
    public boolean updateStatus(int idPeminjaman, String status) {
        String sql = "UPDATE peminjaman SET status_peminjaman=? WHERE id_peminjaman=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, idPeminjaman);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private PeminjamanModel mapResultSetToPeminjaman(ResultSet rs) throws SQLException {
        PeminjamanModel peminjaman = new PeminjamanModel();
        peminjaman.setIdPeminjaman(rs.getInt("id_peminjaman"));
        peminjaman.setNoPeminjaman(rs.getString("no_peminjaman"));
        peminjaman.setIdAnggota(rs.getInt("id_anggota"));
        peminjaman.setTanggalPinjam(rs.getDate("tanggal_pinjam"));
        peminjaman.setTanggalJatuhTempo(rs.getDate("tanggal_jatuh_tempo"));
        peminjaman.setStatusPeminjaman(rs.getString("status_peminjaman"));
        peminjaman.setKeterangan(rs.getString("keterangan"));
        peminjaman.setCreatedBy(rs.getInt("created_by"));
        peminjaman.setCreatedAt(rs.getTimestamp("created_at"));
        return peminjaman;
    }
}