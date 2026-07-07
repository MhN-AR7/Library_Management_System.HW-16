package ir.library.service;

import ir.library.exception.BookNotFoundException;
import ir.library.exception.DatabaseRepositoryException;
import ir.library.model.Book;
import ir.library.repository.BookRepository;

import java.util.List;

public class BookService {
    private final BookRepository bookRepository;

    public BookService() {
        this.bookRepository = new BookRepository();
    }

    public Long register(String title, String author, Double price, Integer stock) throws DatabaseRepositoryException {
        if (title == null || title.isBlank() || title.length() > 100)
            throw new IllegalArgumentException("Title Cannot be Null or Empty and The Length Must Less Than 100!");

        if (author == null || author.isBlank() || author.length() > 100)
            throw new IllegalArgumentException("Author Cannot be Null or Empty and The Length Must Less Than 100!");

        if (price == null || price < 0)
            throw new IllegalArgumentException("Price Cannot be Null or Negative!");

        if (stock == null || stock < 0)
            throw new IllegalArgumentException("Stock Cannot be Null or Negative!");

        return bookRepository.insert(new Book(title, author, price, stock));
    }

    public Book getById(Long id) throws DatabaseRepositoryException {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID Cannot be Null or Less Than 1!");

//        Optional<Book> optionalBook = bookRepository.findById(id);
//        if (optionalBook.isEmpty()) throw new BookNotFoundException("Book Not Found!");
//        return optionalBook.get();

        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found!"));
    }

    public Book changePrice(Long id, Double newPrice) throws DatabaseRepositoryException {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID Cannot be Null or Less Than 1!");

        if (newPrice == null || newPrice < 0)
            throw new IllegalArgumentException("New Price Cannot be Null or Negative!");

        if (!bookRepository.updatePrice(id, newPrice))
            throw new BookNotFoundException("Book Not Found!");

        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found!"));
    }

    public Long delete(Long id) throws DatabaseRepositoryException {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID Cannot be Null or Less Than 1!");

        if (!bookRepository.delete(id))
            throw new BookNotFoundException("Book Not Found!");

        return id;
    }

    public List<Book> getAll() throws DatabaseRepositoryException {
        return bookRepository.findAll();
    }
}
