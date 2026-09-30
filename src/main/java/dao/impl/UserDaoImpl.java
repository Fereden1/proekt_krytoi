package dao.impl;

import dao.UserDao;
import entity.User;
import until.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (login, password, name) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
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

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, password = ? WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean loginExists(String login) {
        String sql = "SELECT 1 FROM users WHERE login = ? LIMIT 1";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("login"),
                rs.getString("password"),
                rs.getString("name")
        );
    }
}