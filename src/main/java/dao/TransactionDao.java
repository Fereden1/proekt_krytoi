package dao;
import entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionDao {

    List<Transaction> getAllByUser(int userId);

    List<Transaction> getByUserAndType(int userId, String type);

    List<Transaction> getByUserAndPeriod(int userId, LocalDate from, LocalDate to);

    Transaction getById(int id, int userId);

    void save(Transaction t);

    void update(Transaction t);

    void delete(int id, int userId);

    BigDecimal sumByUserAndType(int userId, String type);
}