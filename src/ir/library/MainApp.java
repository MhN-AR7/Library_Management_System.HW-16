package ir.library;

import ir.library.exception.DatabaseConnectionException;
import ir.library.exception.DatabaseRepositoryException;
import ir.library.exception.DuplicateMemberException;
import ir.library.service.BookService;
import ir.library.service.MemberService;
import ir.library.util.DatabaseConfig;

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
                    System.out.println("\n---- Inserting Member ----\n");
                    System.out.println("Enter Member's Full Name: ");
                    String fullName = input.nextLine();
                    System.out.println("Enter " + fullName + "'s Phone Number: ");
                    String phoneNumber = input.nextLine();
                    try {
                        Long id = memberService.register(fullName, phoneNumber);
                        System.out.println("\nMember Inserted Successfully!\nID: " + id);
                    }
                    catch (IllegalArgumentException | DuplicateMemberException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
            }
        }
    }
}
