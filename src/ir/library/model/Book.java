package ir.library.model;

public class Book {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;

    public Book(Long id, String title, String author, Double price, Integer stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("Book{ID: %d | Title: %s | Author: %s\nPrice: %.2f | Stock: %d}",
                id, title, author, price, stock);
    }
}
