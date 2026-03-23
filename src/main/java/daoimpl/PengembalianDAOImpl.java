/*
 * File: PengembalianDAOImpl.java
 * Package: dao.impl
 * Deskripsi: Implementasi DAO untuk tabel pengembalian
 */

package daoimpl;

import config.Koneksi;
import dao.PengembalianDao;
import dao.PeminjamanDao;
import dao.AnggotaDao;
import model.PengembalianModel;
import model.PeminjamanModel;
import model.AnggotaModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

public class PengembalianDAOImpl implements PengembalianDao {
    private Connection conn;
    private PeminjamanDao peminjamanDAO;
    private AnggotaDao anggotaDAO;
    
    public PengembalianDAOImpl() {
        this.conn = Koneksi.getConnection();
        this.peminjamanDAO = new PeminjamanDAOImpl();
        this.anggotaDAO = new AnggotaDAOImpl();
    }
    
    @Override
    public boolean insert(PengembalianModel pengembalian) {
        String sql = "INSERT INTO pengembalian (no_pengembalian, id_peminjaman, id_anggota, tanggal_pengembalian, total_denda, keterangan, diterima_oleh) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pengembalian.getNoPengembalian());
            ps.setInt(2, pengembalian.getIdPeminjaman());
            ps.setInt(3, pengembalian.getIdAnggota());
            ps.setDate(4, new java.sql.Date(pengembalian.getTanggalPengembalian().getTime()));
            ps.setDouble(5, pengembalian.getTotalDenda());
            ps.setString(6, pengembalian.getKeterangan());
            ps.setInt(7, pengembalian.getDiterimaOleh());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        pengembalian.setIdPengembalian(rs.getInt(1));
                    }
                }
                
                // Update status peminjaman
                peminjamanDAO.updateStatus(pengembalian.getIdPeminjaman(), "dikembalikan");
                
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error insert pengembalian: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(PengembalianModel pengembalian) {
        String sql = "UPDATE pengembalian SET id_peminjaman=?, id_anggota=?, tanggal_pengembalian=?, total_denda=?, keterangan=?, diterima_oleh=? WHERE id_pengembalian=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pengembalian.getIdPeminjaman());
            ps.setInt(2, pengembalian.getIdAnggota());
            ps.setDate(3, new java.sql.Date(pengembalian.getTanggalPengembalian().getTime()));
            ps.setDouble(4, pengembalian.getTotalDenda());
            ps.setString(5, pengembalian.getKeterangan());
            ps.setInt(6, pengembalian.getDiterimaOleh());
            ps.setInt(7, pengembalian.getIdPengembalian());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error update pengembalian: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(int idPengembalian) {
        String sql = "DELETE FROM pengembalian WHERE id_pengembalian=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPengembalian);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error delete pengembalian: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public PengembalianModel getById(int idPengembalian) {
        String sql = "SELECT * FROM pengembalian WHERE id_pengembalian=?";
        PengembalianModel pengembalian = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPengembalian);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pengembalian = mapResultSetToPengembalian(rs);
                    
                    // Set related objects
                    PeminjamanModel peminjaman = peminjamanDAO.getById(pengembalian.getIdPeminjaman());
                    pengembalian.setPeminjaman(peminjaman);
                    
                    AnggotaModel anggota = anggotaDAO.getById(pengembalian.getIdAnggota());
                    pengembalian.setAnggota(anggota);
                    
                    AnggotaModel petugas = anggotaDAO.getById(pengembalian.getDiterimaOleh());
                    pengembalian.setPetugas(petugas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pengembalian;
    }
    
    @Override
    public PengembalianModel getByNoPengembalian(String noPengembalian) {
        String sql = "SELECT * FROM pengembalian WHERE no_pengembalian=?";
        PengembalianModel pengembalian = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, noPengembalian);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pengembalian = mapResultSetToPengembalian(rs);
                    
                    // Set related objects
                    PeminjamanModel peminjaman = peminjamanDAO.getById(pengembalian.getIdPeminjaman());
                    pengembalian.setPeminjaman(peminjaman);
                    
                    AnggotaModel anggota = anggotaDAO.getById(pengembalian.getIdAnggota());
                    pengembalian.setAnggota(anggota);
                    
                    AnggotaModel petugas = anggotaDAO.getById(pengembalian.getDiterimaOleh());
                    pengembalian.setPetugas(petugas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pengembalian;
    }
    
    @Override
    public PengembalianModel getByPeminjaman(int idPeminjaman) {
        String sql = "SELECT * FROM pengembalian WHERE id_peminjaman=?";
        PengembalianModel pengembalian = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPeminjaman);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pengembalian = mapResultSetToPengembalian(rs);
                    
                    // Set related objects
                    PeminjamanModel peminjaman = peminjamanDAO.getById(pengembalian.getIdPeminjaman());
                    pengembalian.setPeminjaman(peminjaman);
                    
                    AnggotaModel anggota = anggotaDAO.getById(pengembalian.getIdAnggota());
                    pengembalian.setAnggota(anggota);
                    
                    AnggotaModel petugas = anggotaDAO.getById(pengembalian.getDiterimaOleh());
                    pengembalian.setPetugas(petugas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pengembalian;
    }
    
    @Override
    public List<PengembalianModel> getAll() {
        String sql = "SELECT * FROM pengembalian ORDER BY tanggal_pengembalian DESC";
        List<PengembalianModel> list = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PengembalianModel pengembalian = mapResultSetToPengembalian(rs);
                
                // Set related objects
                PeminjamanModel peminjaman = peminjamanDAO.getById(pengembalian.getIdPeminjaman());
                pengembalian.setPeminjaman(peminjaman);
                
                AnggotaModel anggota = anggotaDAO.getById(pengembalian.getIdAnggota());
                pengembalian.setAnggota(anggota);
                
                list.add(pengembalian);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<PengembalianModel> getByAnggota(int idAnggota) {
        String sql = "SELECT * FROM pengembalian WHERE id_anggota=? ORDER BY tanggal_pengembalian DESC";
        List<PengembalianModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAnggota);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PengembalianModel pengembalian = mapResultSetToPengembalian(rs);
                    
                    // Set related objects
                    PeminjamanModel peminjaman = peminjamanDAO.getById(pengembalian.getIdPeminjaman());
                    pengembalian.setPeminjaman(peminjaman);
                    
                    AnggotaModel anggota = anggotaDAO.getById(pengembalian.getIdAnggota());
                    pengembalian.setAnggota(anggota);
                    
                    list.add(pengembalian);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<PengembalianModel> getByTanggal(Date tanggalMulai, Date tanggalAkhir) {
        String sql = "SELECT * FROM pengembalian WHERE DATE(tanggal_pengembalian) BETWEEN ? AND ? ORDER BY tanggal_pengembalian DESC";
        List<PengembalianModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(tanggalMulai.getTime()));
            ps.setDate(2, new java.sql.Date(tanggalAkhir.getTime()));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PengembalianModel pengembalian = mapResultSetToPengembalian(rs);
                    
                    // Set related objects
                    PeminjamanModel peminjaman = peminjamanDAO.getById(pengembalian.getIdPeminjaman());
                    pengembalian.setPeminjaman(peminjaman);
                    
                    AnggotaModel anggota = anggotaDAO.getById(pengembalian.getIdAnggota());
                    pengembalian.setAnggota(anggota);
                    
                    list.add(pengembalian);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public String generateNoPengembalian() {
        String noPengembalian = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String bulanTahun = sdf.format(new Date());
        
        String sql = "SELECT COUNT(*) FROM pengembalian WHERE no_pengembalian LIKE 'PG" + bulanTahun + "%'";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int urutan = rs.getInt(1) + 1;
                noPengembalian = "PG" + bulanTahun + String.format("%04d", urutan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return noPengembalian;
    }
    
    @Override
    public double hitungDenda(Date tanggalJatuhTempo, Date tanggalKembali) {
        if (tanggalKembali == null || tanggalKembali.before(tanggalJatuhTempo)) {
            return 0;
        }
        
        long diffInMillies = tanggalKembali.getTime() - tanggalJatuhTempo.getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        
        // Denda Rp 1000 per hari keterlambatan
        return diffInDays * 1000;
    }
    
    private PengembalianModel mapResultSetToPengembalian(ResultSet rs) throws SQLException {
        PengembalianModel pengembalian = new PengembalianModel();
        pengembalian.setIdPengembalian(rs.getInt("id_pengembalian"));
        pengembalian.setNoPengembalian(rs.getString("no_pengembalian"));
        pengembalian.setIdPeminjaman(rs.getInt("id_peminjaman"));
        pengembalian.setIdAnggota(rs.getInt("id_anggota"));
        pengembalian.setTanggalPengembalian(rs.getDate("tanggal_pengembalian"));
        pengembalian.setTotalDenda(rs.getDouble("total_denda"));
        pengembalian.setKeterangan(rs.getString("keterangan"));
        pengembalian.setDiterimaOleh(rs.getInt("diterima_oleh"));
        pengembalian.setCreatedAt(rs.getTimestamp("created_at"));
        return pengembalian;
    }
}