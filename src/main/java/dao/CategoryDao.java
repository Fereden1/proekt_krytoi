package dao;

import entity.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> getAll();

    List<Category> getByType(String type); // "income" | "expense"

    Category getById(int id);
}