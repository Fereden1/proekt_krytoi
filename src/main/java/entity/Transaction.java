package entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {

    private Integer id;
    private Integer userId;
    private String type;          // "income" | "expense"
    private BigDecimal amount;
    private Integer categoryId;
    private LocalDate operationDate;
    private LocalDateTime createdAt;

    // для отображения в JSP (заполняется при чтении, не хранится как поле таблицы)
    private String categoryName;

    public Transaction() {
    }

    public Transaction(Integer userId, String type, BigDecimal amount,
                       Integer categoryId, LocalDate operationDate) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.categoryId = categoryId;
        this.operationDate = operationDate;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public LocalDate getOperationDate() { return operationDate; }
    public void setOperationDate(LocalDate operationDate) { this.operationDate = operationDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", type='" + type +
                "', amount=" + amount + ", operationDate=" + operationDate + '}';
    }
}