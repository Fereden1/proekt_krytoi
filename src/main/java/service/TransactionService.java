package service;

import dao.TransactionDao;
import dao.impl.TransactionDaoImpl;
import entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransactionService {

    private final TransactionDao transactionDao = new TransactionDaoImpl();

    public List<Transaction> listForUser(int userId) {
        return transactionDao.getAllByUser(userId);
    }

    public List<Transaction> listForUser(int userId, String type) {
        if (type == null || type.isBlank()) {
            return transactionDao.getAllByUser(userId);
        }
        return transactionDao.getByUserAndType(userId, type);
    }

    public List<Transaction> listForPeriod(int userId, LocalDate from, LocalDate to) {
        return transactionDao.getByUserAndPeriod(userId, from, to);
    }

    public BigDecimal totalIncome(int userId) {
        return transactionDao.sumByUserAndType(userId, "income");
    }

    public BigDecimal totalExpense(int userId) {
        return transactionDao.sumByUserAndType(userId, "expense");
    }

    public BigDecimal balance(int userId) {
        return totalIncome(userId).subtract(totalExpense(userId));
    }

    public Transaction add(int userId, String type, BigDecimal amount, Integer categoryId, LocalDate operationDate) {
        validate(type, amount, operationDate);
        Transaction t = new Transaction(userId, type, amount, categoryId, operationDate);
        transactionDao.save(t);
        return t;
    }

    public void update(int userId, int id, String type, BigDecimal amount, Integer categoryId, LocalDate operationDate) {
        validate(type, amount, operationDate);
        Transaction t = transactionDao.getById(id, userId);
        if (t == null) throw new IllegalArgumentException("Операция не найдена");
        t.setType(type);
        t.setAmount(amount);
        t.setCategoryId(categoryId);
        t.setOperationDate(operationDate);
        transactionDao.update(t);
    }

    public Transaction get(int userId, int id) {
        Transaction t = transactionDao.getById(id, userId);
        if (t == null) throw new IllegalArgumentException("Операция не найдена");
        return t;
    }

    public void delete(int userId, int id) {
        transactionDao.delete(id, userId);
    }

    private void validate(String type, BigDecimal amount, LocalDate date) {
        if (!"income".equals(type) && !"expense".equals(type)) {
            throw new IllegalArgumentException("Тип должен быть income или expense");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше нуля");
        }
        if (date == null) {
            throw new IllegalArgumentException("Дата обязательна");
        }
    }
}