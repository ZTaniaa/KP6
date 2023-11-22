import java.util.Scanner;

public class LibraryManager {
    private static final char SORT_YEAR = '1';
    private static final char EMAIL_LIST = '2';
    private static final char PRINT_AUTHORS = '3';
    private static final char MAX_BOOK_READER = '4';
    private static final char TWO_GROUP = '5';
    private static final char DEBTORS = '6';
    private static final char QUIT = 'q';

    public static void performAction(char action, Library library, Scanner sc) {
        //Library library = Serializer.deserializeLibrary();
        switch (action) {
            case SORT_YEAR:
                library.sortYearOfPublication();
                break;
            case EMAIL_LIST:
                library.emailListMore2Books();
                break;
            case PRINT_AUTHORS:
                library.printAuthorsSet();
                System.out.print("Choose author(enter number): ");
                int ch = sc.nextInt();
                library.countReadersByAuthor(library.getAuthors().toArray()[ch - 1].toString());
                break;
            case MAX_BOOK_READER:
                library.getMaxBookReader();
                break;
            case TWO_GROUP:
                library.twoGroup();
                break;
            case DEBTORS:
                library.getDebtors();
                break;
            case QUIT:
                System.exit(0);
            default:
                System.out.println("Wrong letter, try again");
        }
        //Serializer.serializeLibrary(library);
    }
}
