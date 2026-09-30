package dao.impl;

import dao.TransactionDao;
import entity.Transaction;
import until.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    private static final String BASE_SELECT =
            "SELECT t.*, c.name AS category_name " +
                    "FROM transactions t " +
                    "LEFT JOIN categories c ON c.id = t.category_id ";

    @Override
    public List<Transaction> getAllByUser(int userId) {
        String sql = BASE_SELECT +
                "WHERE t.user_id = ? ORDER BY t.operation_date DESC, t.id DESC";
        return queryList(sql, userId);
    }

    @Override
    public List<Transaction> getByUserAndType(int userId, String type) {
        String sql = BASE_SELECT +
                "WHERE t.user_id = ? AND t.type = ? ORDER BY t.operation_date DESC, t.id DESC";
        List<Transaction> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Transaction> getByUserAndPeriod(int userId, LocalDate from, LocalDate to) {
        String sql = BASE_SELECT +
                "WHERE t.user_id = ? AND t.operation_date BETWEEN ? AND ? " +
                "ORDER BY t.operation_date DESC, t.id DESC";
        List<Transaction> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setObject(2, from);
            stmt.setObject(3, to);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Transaction getById(int id, int userId) {
        String sql = BASE_SELECT + "WHERE t.id = ? AND t.user_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Transaction t) {
        String sql = "INSERT INTO transactions " +
                "(user_id, type, amount, category_id, operation_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, t.getUserId());
            stmt.setString(2, t.getType());
            stmt.setBigDecimal(3, t.getAmount());
            if (t.getCategoryId() == null) stmt.setNull(4, Types.INTEGER);
            else stmt.setInt(4, t.getCategoryId());
            stmt.setObject(5, t.getOperationDate());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) t.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Transaction t) {
        String sql = "UPDATE transactions SET type = ?, amount = ?, " +
                "category_id = ?, operation_date = ? " +
                "WHERE id = ? AND user_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, t.getType());
            stmt.setBigDecimal(2, t.getAmount());
            if (t.getCategoryId() == null) stmt.setNull(3, Types.INTEGER);
            else stmt.setInt(3, t.getCategoryId());
            stmt.setObject(4, t.getOperationDate());
            stmt.setInt(5, t.getId());
            stmt.setInt(6, t.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id, int userId) {
        String sql = "DELETE FROM transactions WHERE id = ? AND user_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal sumByUserAndType(int userId, String type) {
        String sql = "SELECT COALESCE(SUM(amount), 0) AS total " +
                "FROM transactions WHERE user_id = ? AND type = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getBigDecimal("total") : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Transaction> queryList(String sql, int userId) {
        List<Transaction> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private Transaction mapRow(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setId(rs.getInt("id"));
        t.setUserId(rs.getInt("user_id"));
        t.setType(rs.getString("type"));
        t.setAmount(rs.getBigDecimal("amount"));
        int catId = rs.getInt("category_id");
        t.setCategoryId(rs.wasNull() ? null : catId);
        t.setOperationDate(rs.getObject("operation_date", LocalDate.class));
        t.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
        t.setCategoryName(rs.getString("category_name"));
        return t;
    }
}