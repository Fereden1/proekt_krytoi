package service;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import dto.UserDto;
import entity.User;
import until.PasswordUtil;

public class UserService {

    private final UserDao userDao = new UserDaoImpl();

    public UserDto register(String login, String rawPassword, String name) {
        validate(login, rawPassword, name);
        if (userDao.loginExists(login)) {
            throw new IllegalArgumentException("Такой логин уже занят");
        }
        User user = new User(login, PasswordUtil.hash(rawPassword), name);
        userDao.save(user);
        return new UserDto(user.getLogin(), user.getName());
    }

    public User login(String login, String rawPassword) {
        if (login == null || login.isBlank() || rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Логин и пароль обязательны");
        }
        User user = userDao.getByLogin(login);
        if (user == null || !PasswordUtil.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }
        return user;
    }

    private void validate(String login, String password, String name) {
        if (login == null || login.isBlank() || login.length() < 3) {
            throw new IllegalArgumentException("Логин должен быть не короче 3 символов");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Пароль должен быть не короче 6 символов");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя обязательно");
        }
    }
}