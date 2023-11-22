import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subscription implements Serializable {
    private String lastName;
    private String firstName;
    private String patronymic;
    private String email;
    private List<Book> borrowedBooks;

    public Subscription(String lastName, String firstName, String patronymic, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
    public List<Book> getBorrowedBooks() {return borrowedBooks;}
    public String getLastName() { return lastName;}
    public String getPatronymic() { return patronymic;}
    public String getEmail() {  return email;}
    public String getFirstName() {return firstName;}

}
