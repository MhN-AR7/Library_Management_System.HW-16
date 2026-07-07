package ir.library;

import ir.library.service.BookService;
import ir.library.service.MemberService;

import java.util.Scanner;

public class MainApp {
    static void main() {
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
        }
    }
}
