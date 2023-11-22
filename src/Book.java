import java.io.Serializable;

public class Book implements Serializable {
    private String author;
    private String name;
    private int yearOfPublication;

    public Book(String author, String name, int yearOfPublication) {
        this.author = author;
        this.name = name;
        this.yearOfPublication = yearOfPublication;
    }

    public int getYear() {
        return yearOfPublication;
    }
    public String getAuthor() {
        return author;
    }
    public String getName() {
        return name;
    }


}
