import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FilesReader {
    public static void readBooksFromFile(Library library) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/TextFile/Books.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Припускаємо, що дані в файлі розділені комою
                String[] bookData = line.split(";");
                if (bookData.length == 3) {
                    String author = bookData[0].trim();
                    String title = bookData[1].trim();
                    int year = Integer.parseInt(bookData[2].trim());

                    Book book = new Book(author, title, year);
                    library.addBook(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readSubscriptionsFromFile(Library library) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/TextFile/Subscriptions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Припускаємо, що дані в файлі розділені комою
                String[] subscriptionData = line.split(",");
                if (subscriptionData.length == 4) {
                    String lastName = subscriptionData[0].trim();
                    String firstName = subscriptionData[1].trim();
                    String middleName = subscriptionData[2].trim();
                    String emailAddress = subscriptionData[3].trim();

                    Subscription subscription = new Subscription(lastName, firstName, middleName, emailAddress);
                    library.addSubscription(subscription);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Library ReadFromFiles() {
        Library library = new Library();
        FilesReader.readBooksFromFile(library);
        FilesReader.readSubscriptionsFromFile(library);
        return library;
    }
}
