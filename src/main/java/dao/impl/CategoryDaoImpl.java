package dao.impl;

import dao.CategoryDao;
import entity.Category;
import until.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY type, name";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Category> getByType(String type) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE type = ? ORDER BY name";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type")
        );
    }
}