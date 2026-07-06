package ir.library;

import ir.library.service.LibraryService;

import java.util.Scanner;

public class MainApp {
    static void main() {
        Scanner input = new Scanner(System.in);

        LibraryService libraryService = new LibraryService();

        while (true) {
            System.out.println("""
                    ==== Library Management System ====
                    
                    1. Insert Member
                    2. Insert Book
                    3. Find Member By ID
                    4. Find Book By ID
                    5. Update Member's Full Name
                    6. Update Book's Price
                    7. Delete Member
                    8. Delete Book
                    9. Exit
                    """);
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    libraryService.insertMember();
                    break;
                case 2:
                    libraryService.insertBook();
                    break;
                case 3:
                    libraryService.findMemberById();
                    break;
                case 4:
                    libraryService.findBookById();
                    break;
                case 5:
                    libraryService.updateMemberFullName();
                    break;
                case 6:
                    libraryService.updateBookPrice();
                    break;
                case 7:
                    libraryService.deleteMemberById();
                    break;
                case 8:
                    libraryService.deleteBookById();
                    break;
                case 9:
                    System.out.println("Exiting Program...");
                    return;
                default:
                    System.out.println("Invalid Choice!\nTry Again.");
                    break;
            }
        }
    }
}
