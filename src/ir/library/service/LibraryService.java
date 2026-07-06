package ir.library.service;

import ir.library.exception.BookNotFoundException;
import ir.library.exception.DatabaseRepositoryException;
import ir.library.exception.DuplicateMemberException;
import ir.library.exception.MemberNotFoundException;
import ir.library.model.Book;
import ir.library.model.Member;
import ir.library.repository.BookRepository;
import ir.library.repository.MemberRepository;

import java.util.Optional;

public class LibraryService {
    private MemberRepository memberRepository;
    private BookRepository bookRepository;

    public LibraryService() {
        this.memberRepository = new MemberRepository();
        this.bookRepository = new BookRepository();
    }

    public Long registerMember(String fullName, String phoneNumber) throws DatabaseRepositoryException {
        if (fullName == null || fullName.isBlank() || fullName.length() > 100)
            throw new IllegalArgumentException("Full Name Cannot be Null or Empty and The Length Must Less Than 100!");

        if (phoneNumber == null || phoneNumber.isBlank() || phoneNumber.length() > 15)
            throw new IllegalArgumentException("Phone Number Cannot be Null or Empty and The Length Must Less Than 15!");

        if (memberRepository.existByPhoneNumber(phoneNumber))
            throw new DuplicateMemberException("Phone Number Already Exist!");

        return memberRepository.insert(new Member(fullName, phoneNumber));
    }

    public Long registerBook(String title, String author, Double price, Integer stock) throws DatabaseRepositoryException {
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

    public Member getMemberById(Long id) throws DatabaseRepositoryException {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID Cannot be Null or Less Than 1!");

//        Optional<Member> optionalMember = memberRepository.findById(id);
//        if (optionalMember.isEmpty()) throw new MemberNotFoundException("Member Not Found!");
//        return optionalMember.get();

        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member Not Found!"));
    }

    public Book getBookById(Long id) throws DatabaseRepositoryException {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID Cannot be Null or Less Than 1!");

        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found!"));
    }


}
