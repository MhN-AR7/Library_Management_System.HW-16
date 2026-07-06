package ir.library.service;

import ir.library.model.Book;
import ir.library.model.Member;
import ir.library.repository.BookRepository;
import ir.library.repository.MemberRepository;

import java.util.Scanner;

public class LibraryService {
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public LibraryService() {
        this.memberRepository = new MemberRepository();
        this.bookRepository = new BookRepository();
    }

    public void insertMember() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n==== Inserting Member ====\n");
        System.out.println("Enter Member's Full Name: ");
        String fullName = input.nextLine();
        System.out.println("Enter " + fullName + "'s Phone Number: ");
        String phoneNumber = input.nextLine();

        Long id = memberRepository.create(new Member(0L, fullName, phoneNumber));

        System.out.println("Member Added Successfully!\nID: " + id);
    }

    public void insertBook() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n==== Inserting Book ====\n");
        System.out.println("Enter Book's Title: ");
        String title = input.nextLine();
        System.out.println("Enter " + title + "'s Author: ");
        String author = input.nextLine();
        System.out.println("Enter " + title + "'s Price: ");
        Double price = input.nextDouble();
        input.nextLine();
        System.out.println("Enter " + title + "'s Stock: ");
        Integer stock = input.nextInt();
        input.nextLine();

        Long id = bookRepository.create(new Book(0L, title, author, price, stock));

        System.out.println("Book Added Successfully!\nID: " + id);
    }

    public void findMemberById() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n==== Finding Member By ID ====\n");
        System.out.println("Enter Member's ID You Want to Find: ");
        Long id = input.nextLong();
        input.nextLine();

        System.out.println(memberRepository.read(id));
    }

    public void findBookById() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n==== Finding Book By ID ====\n");
        System.out.println("Enter Book's ID You Want to Find: ");
        Long id = input.nextLong();
        input.nextLine();

        System.out.println(bookRepository.read(id));
    }

    public void updateMemberFullName() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n==== Updating Member's Full Name ====\n");
        System.out.println("Enter Member's ID You Want to Update: ");
        Long id = input.nextLong();
        input.nextLine();
        Member member = memberRepository.read(id);
        System.out.println("Enter New Full Name: ");
        String newFullName = input.nextLine();

        System.out.println("New Info: ");
        System.out.println(memberRepository.update(new Member(id, newFullName, member.getPhoneNumber())));
    }

    public void updateBookPrice() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n==== Updating Book's Price ====\n");
        System.out.println("Enter Book's ID You Want to Update: ");
        Long id = input.nextLong();
        input.nextLine();
        Book book = bookRepository.read(id);
        System.out.println("Enter New Price: ");
        Double newPrice = input.nextDouble();
        input.nextLine();

        System.out.println("New Info: ");
        System.out.println(bookRepository.update(new Book(id, book.getTitle(), book.getAuthor(), newPrice, book.getStock())));
    }

    public void deleteMemberById() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n==== Deleting Member ====\n");
        System.out.println("Enter Member's ID You Want to Delete: ");
        Long id = input.nextLong();
        input.nextLine();

        Long deletedId = memberRepository.delete(id);
        System.out.println("ID: " + deletedId);
    }

    public void deleteBookById() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n==== Deleting Book ====\n");
        System.out.println("Enter Book's ID You Want to Delete: ");
        Long id = input.nextLong();
        input.nextLine();

        Long deletedId = bookRepository.delete(id);
        System.out.println("ID: " + deletedId);
    }
}
