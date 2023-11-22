import java.io.Serializable;
import java.time.LocalDate;

public class LoanRecord implements Serializable {
    protected Book book;
    protected Subscription subscription;
    private LocalDate borrowDate;
    private LocalDate plannedReturnDate;
    private LocalDate actualReturnDate;

    public LoanRecord(Book book, Subscription subscription, LocalDate borrowDate) {
        this.book = book;
        this.subscription = subscription;
        this.borrowDate = borrowDate;
        this.plannedReturnDate = borrowDate.plusMonths(1);
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public LocalDate getBorrowDate() {return borrowDate;}

    public LocalDate getPlannedReturnDate() {return plannedReturnDate;}

    public Subscription getSubscription() {return subscription;}

    public Book getBook() {return book;}
}
