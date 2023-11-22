import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Library library = Serializer.deserializeLibrary();
        Library library = FilesReader.ReadFromFiles();

        library.addInfo();

        char action;
        while (true) {
            System.out.println("What do you want to do? (1 - first task, 2 - second task, 3 - third task, 4 - fourth task, 5 - fifth task, 6 - sixth task, q - quit)");
            action = sc.next().charAt(0);
            LibraryManager.performAction(action, library, sc);
        }
    }
}