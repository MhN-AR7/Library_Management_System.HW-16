package ir.library.service;

import ir.library.exception.DatabaseRepositoryException;
import ir.library.exception.DuplicateMemberException;
import ir.library.model.Member;
import ir.library.repository.BookRepository;
import ir.library.repository.MemberRepository;

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
}
