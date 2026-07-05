package ir.library.repository;

import ir.library.exception.BookNotFoundException;
import ir.library.exception.RepositoryException;
import ir.library.model.Book;
import ir.library.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRepository {
    public Long create(Book book) throws RepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO books (title, author, price, stock) VALUES (?, ?, ?, ?)"
        )) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getStock());

            ps.executeUpdate();
            return book.getId();
        }
        catch (SQLException e) {
            throw new RepositoryException("PostgreSQL Syntax Incorrect!");
        }
    }

    public Book read(Long id) throws RepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM books WHERE id = ?"
        )) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new BookNotFoundException("Book Not Found!");
            }

            return new Book(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
            );
        }
        catch (SQLException e) {
            throw new RepositoryException("PostgreSQL Syntax Incorrect!");
        }
    }

    public Book update(Book book) throws RepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE books SET title = ?, author = ?, price = ?, stock = ? WHERE id = ?"
        )) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getStock());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) throw new BookNotFoundException("Book Not Found!");

            return new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getStock());
        }
        catch (SQLException e) {
            throw new RepositoryException("PostgreSQL Syntax Incorrect!");
        }
    }

    public Long delete(Long id) throws RepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM books WHERE id = ?"
        )) {
            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) throw new BookNotFoundException("Book Not Found!");

            return id;
        }
        catch (SQLException e) {
            throw new RepositoryException("PostgreSQL Syntax Incorrect!");
        }
    }
}
