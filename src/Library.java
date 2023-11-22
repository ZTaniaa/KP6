import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Library implements Serializable {
    private List<Subscription> subscriptionList;
    private List<Book> bookList;
    private Administrator administrator;
    private Set<String> authors;

    public Library() {
        this.bookList = new ArrayList<>();
        this.subscriptionList = new ArrayList<>();
        this.administrator = new Administrator();
        this.authors = new HashSet<>();
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void addSubscription(Subscription subscription) {
        subscriptionList.add(subscription);
    }

    public List<Book> getBooks() {
        return bookList;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptionList;
    }

    public Set<String> getAuthors() {return authors;}
    public void addInfo() {
        administrator.borrowBook(bookList.get(3), subscriptionList.get(0), LocalDate.of(2023, 10, 10));
        administrator.borrowBook(bookList.get(4), subscriptionList.get(0), LocalDate.of(2023, 10, 12));
        administrator.borrowBook(bookList.get(9), subscriptionList.get(0), LocalDate.of(2023, 9, 21));
        administrator.borrowBook(bookList.get(21), subscriptionList.get(0), LocalDate.of(2023, 11, 10));
        administrator.borrowBook(bookList.get(2), subscriptionList.get(1), LocalDate.of(2023, 10, 10));
        administrator.borrowBook(bookList.get(6), subscriptionList.get(2), LocalDate.of(2023, 10, 11));
        administrator.borrowBook(bookList.get(8), subscriptionList.get(2), LocalDate.of(2023, 10, 27));
        administrator.borrowBook(bookList.get(11), subscriptionList.get(2), LocalDate.of(2023, 11, 01));
        administrator.borrowBook(bookList.get(15), subscriptionList.get(2), LocalDate.of(2023, 11, 05));
        administrator.borrowBook(bookList.get(17), subscriptionList.get(3), LocalDate.of(2023, 11, 01));
        administrator.borrowBook(bookList.get(18), subscriptionList.get(3), LocalDate.of(2023, 11, 9));
        administrator.borrowBook(bookList.get(22), subscriptionList.get(4), LocalDate.of(2023, 10, 19));
        administrator.borrowBook(bookList.get(23), subscriptionList.get(4), LocalDate.of(2023, 11, 04));
        administrator.returnBook(bookList.get(9), subscriptionList.get(0), LocalDate.of(2023, 10,11));
        administrator.borrowBook(bookList.get(9), subscriptionList.get(4), LocalDate.of(2023, 10, 17));
    }

    public void sortYearOfPublication() {
        Comparator<Book> byPublicationYear = Comparator.comparingInt(Book::getYear);
        List<Book> books = bookList.stream()
                .sorted(byPublicationYear)
                .toList();
        System.out.println("Sorted books by year:");
        System.out.format("| %s | %-40s | %-25s |\n", "Year", "Name", "Author");
        System.out.println("--------------------------------------------------------------------------------");
        for (Book b : books)
        {
            System.out.format("| %d | %-40s | %-25s |\n", b.getYear(), b.getName(), b.getAuthor());
        }
    }

    public void emailListMore2Books() {
        printSubscriptionsList();
        List<Subscription> emails = subscriptionList.stream()
                .filter(sub -> sub.getBorrowedBooks().size() > 2)
                .toList();

        System.out.println("List of emails of those readers who took more than two books: ");
        System.out.format("| %-30s | %-10s |\n", "Email", "Name");
        System.out.println("-----------------------------------------------");
        for (Subscription s : emails)
        {
            System.out.format("| %-30s | %-10s |\n", s.getEmail(), s.getFirstName());
        }
    }
    public void printSubscriptionsList() {
        System.out.println("Subscription list: ");
        System.out.format("| %-15s | %-10s | %-15s | %-30s | %-3s |\n", "Last name", "First name", "Patronymic", "Email", "Book count");
        System.out.println("------------------------------------------------------------------------------------------");
        for (Subscription s : subscriptionList)
        {
            System.out.format("| %-15s | %-10s | %-15s | %-30s | %-3d |\n", s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getEmail(), s.getBorrowedBooks().size());
        }
    }

    public void printAuthorsSet() {
        authors = bookList.stream()
                .map(Book::getAuthor)
                .collect(Collectors.toSet());
        int i = 1;
        for(String s : authors) {
            System.out.format("%d. %-20s\n", i, s);
            i++;
        }
    }
    public void countReadersByAuthor(String author) {
        List<Subscription> subscription = subscriptionList.stream()
                .filter(sub -> sub.getBorrowedBooks().stream()
                        .anyMatch(book -> book.getAuthor().equals(author)))
                .toList();
        if(!subscription.isEmpty()) {
            System.out.format("%d reads %s!\n", subscription.size(), author);
        }
        else {
            System.out.format("No one reads %20s\n", author);
        }
    }
    public void getMaxBookReader() {
        int maxBooksCount = subscriptionList.stream()
                .mapToInt(subscriber -> subscriber.getBorrowedBooks().size())
                .max()
                .orElse(0);

        List<Subscription> resultList = subscriptionList.stream()
                .filter(subscriber -> subscriber.getBorrowedBooks().size() == maxBooksCount)
                .toList();
        for(Subscription s : resultList)
            System.out.format("%s reads %d books!\n", s.getFirstName(), maxBooksCount);
    }
    public void twoGroup() {
        List<Subscription> group1 = subscriptionList.stream()
                .filter(subscriber -> subscriber.getBorrowedBooks().size() < 2)
                .toList();
        String newBooks = "\nOur new book: ".concat(bookList.get(bookList.size() - 1).getName());
        sendNewsletter(group1, newBooks);

        List<Subscription> group2 = subscriptionList.stream()
                .filter(subscriber -> subscriber.getBorrowedBooks().size() >= 2)
                .toList();
        ArrayList<String> reminds = new ArrayList<>();
        for(Subscription s : group2)
        {
            if(administrator.getDaysToReturn(s) > 0)
                reminds.add(s.getFirstName() + ", you have only " + administrator.getDaysToReturn(s) + " days to return your first book: " + s.getBorrowedBooks().get(0).getName());
            else
                reminds.add(s.getFirstName() + ", you should have returned your first book (" + s.getBorrowedBooks().get(0).getName() + ") " + (administrator.getDaysToReturn(s)*(-1)) + " days ago ");
        }
        sendReminder(group2, reminds);
    }
    private void sendNewsletter(List<Subscription> subscribers, String message) {
        for (Subscription subscriber : subscribers) {
            sendEmail(subscriber, message);
        }
    }
    private void sendReminder(List<Subscription> subscribers, ArrayList<String> message) {
        int i = 0;
        for (Subscription subscriber : subscribers) {
            sendEmail(subscriber, message.get(i));
            i++;
        }
    }
    private static void sendEmail(Subscription subscriber, String message) {
        System.out.println("Sending email to: " + subscriber.getEmail() + "\nMessage: " + message + "\n");
    }

    public void getDebtors() {
        LocalDate currentDate = LocalDate.now();
        List<LoanRecord> loanRecords = administrator.getLoanRecords();
        List<LoanRecord> debtorsLoanRecord = loanRecords.stream()
                .filter(loanRecord -> currentDate.isAfter(loanRecord.getPlannedReturnDate()))
                .toList();
        for(LoanRecord lr : debtorsLoanRecord)
            System.out.println("Name: " + lr.getSubscription().getFirstName() + " " + lr.getSubscription().getLastName() + " \tBook: " + lr.getBook().getAuthor() + " \"" + lr.getBook().getName() + "\" \tOverdue days: " + ChronoUnit.DAYS.between(lr.getPlannedReturnDate(), currentDate));
    }
}
