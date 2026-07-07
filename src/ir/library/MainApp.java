package ir.library;

import ir.library.exception.*;
import ir.library.model.Book;
import ir.library.model.Member;
import ir.library.service.BookService;
import ir.library.service.MemberService;
import ir.library.util.DatabaseConfig;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    static void main() throws DatabaseRepositoryException, DatabaseConnectionException {
        DatabaseConfig.getConnection();

        MemberService memberService = new MemberService();
        BookService bookService = new BookService();

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    ==== Library System Management ====
                    
                    1. Insert Member
                    2. Insert Book
                    3. Find Member By ID
                    4. Find Book By ID
                    5. Find All Members
                    6. Find All Books
                    7. Update Member's Full Name
                    8. Update Book's Price
                    9. Delete Member
                    10. Delete Book
                    0. Exit
                    """);

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    try {
                        System.out.println("\n---- Inserting Member ----\n");
                        System.out.println("Enter Member's Full Name: ");
                        String fullName = input.nextLine();
                        System.out.println("Enter " + fullName + "'s Phone Number: ");
                        String phoneNumber = input.nextLine();
                        Long id = memberService.register(fullName, phoneNumber);
                        System.out.println("\nMember Inserted Successfully!\nID: " + id);
                    }
                    catch (IllegalArgumentException | DuplicateMemberException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("\n---- Inserting Book ----\n");
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
                        Long id = bookService.register(title, author, price, stock);
                        System.out.println("\nBook Inserted Successfully!\nID: " + id);
                    }
                    catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("\n---- Finding Member By ID ----\n");
                        System.out.println("Enter Member's ID: ");
                        Long id = input.nextLong();
                        input.nextLine();
                        System.out.println(memberService.getById(id));
                    }
                    catch (IllegalArgumentException | MemberNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.println("\n---- Finding Book By ID ----\n");
                        System.out.println("Enter Book's ID: ");
                        Long id = input.nextLong();
                        input.nextLine();
                        System.out.println(bookService.getById(id));
                    }
                    catch (IllegalArgumentException | BookNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("\n---- Finding All Members ----\n");
                    List<Member> members = memberService.getAll();
                    if (members.isEmpty()) System.out.println("No Members Found!");
                    else members.forEach(System.out::println);
                    break;
                case 6:
                    System.out.println("\n---- Finding All Books ----\n");
                    List<Book> books = bookService.getAll();
                    if (books.isEmpty()) System.out.println("No Books Found!");
                    else books.forEach(System.out::println);
                    break;
                case 7:

            }
        }
    }
}
