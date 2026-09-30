package service;

import dao.CategoryDao;
import dao.impl.CategoryDaoImpl;
import entity.Category;

import java.util.List;

public class CategoryService {

    private final CategoryDao categoryDao = new CategoryDaoImpl();

    public List<Category> all() {
        return categoryDao.getAll();
    }

    public List<Category> income() {
        return categoryDao.getByType("income");
    }

    public List<Category> expense() {
        return categoryDao.getByType("expense");
    }

    public Category get(int id) {
        return categoryDao.getById(id);
    }
}