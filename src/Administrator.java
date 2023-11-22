import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Administrator implements Serializable {
    private static List<LoanRecord> loanRecords;

    public Administrator() {
        this.loanRecords = new ArrayList<>();
    }

    public void borrowBook(Book book, Subscription subscription, LocalDate borrowDate) {

        LoanRecord loanRecord = new LoanRecord(book, subscription, borrowDate);
        loanRecords.add(loanRecord);
        subscription.borrowBook(book);
    }

    public void returnBook(Book book, Subscription subscription, LocalDate actualReturnDate) {
        for (LoanRecord loanRecord : loanRecords) {
            if (loanRecord.book.equals(book) && loanRecord.subscription.equals(subscription)) {
                loanRecord.setActualReturnDate(actualReturnDate);
                subscription.returnBook(book);
                break;
            }
        }
    }
    public static LoanRecord searchLoan(Subscription subscriber, Book book) {
        for (LoanRecord loanRecord : loanRecords) {
            if (loanRecord.book.equals(book) && loanRecord.subscription.equals(subscriber)) {
                return loanRecord;
            }
        }
        return null;
    }

    public long getDaysToReturn(Subscription subscriber) {
        List<Book> borrowedBooks = subscriber.getBorrowedBooks();

        if (!borrowedBooks.isEmpty()) {
            Book firstBook = borrowedBooks.get(0);
            LoanRecord loanRecord = searchLoan(subscriber, firstBook);
            LocalDate returnDate = loanRecord.getPlannedReturnDate();

            return ChronoUnit.DAYS.between(returnDate, LocalDate.now());
        } else {
            // Якщо читач не взяв жодної книги
            return 0;
        }
    }

    public List<LoanRecord> getLoanRecords() {
        return loanRecords;
    }
}
