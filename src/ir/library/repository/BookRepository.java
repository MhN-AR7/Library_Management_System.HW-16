package ir.library.repository;

import ir.library.exception.DatabaseRepositoryException;
import ir.library.model.Book;
import ir.library.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    public Long insert(Book book) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO books (title, author, price, stock) VALUES (?, ?, ?, ?) RETURNING id"
        )) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getStock());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong("id");

            throw new DatabaseRepositoryException("Member ID Not Returned!");
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Book Insertion to Database Failed!");
        }
    }

    public Optional<Book> findById(Long id) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM books WHERE id = ?"
        )) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );

                return Optional.of(book);
            }

            return Optional.empty();
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Book Find By ID From Database Failed!");
        }
    }

    public boolean updatePrice(Long id, Double newPrice) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE books SET price = ? WHERE id = ?"
        )) {
            ps.setDouble(1, newPrice);
            ps.setLong(2, id);

            int rowsAffected = ps.executeUpdate();

            return !(rowsAffected == 0);
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Update Book Price From Database Failed!");
        }
    }

    public boolean delete(Long id) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM books WHERE id = ?"
        )) {
            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();

            return !(rowsAffected == 0);
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Delete Book From Database Failed!");
        }
    }

    public List<Book> findAll() throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM books"
        )) {
            ResultSet rs = ps.executeQuery();
            List<Book> books = new ArrayList<>();

            while (rs.next()) {
                Book newBook = new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );

                books.add(newBook);
            }

            return books;
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Find All Books From Database Failed!");
        }
    }
}
