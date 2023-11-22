import java.io.*;

public class Serializer {
    public static void serializeLibrary(Library library) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library2.ser"))) {
            oos.writeObject(library);
            System.out.println("Library serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Library deserializeLibrary() {
        Library library = null;
        try {
            FileInputStream fileIn = new FileInputStream("library2.ser");

            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            library = (Library) objectIn.readObject();

            objectIn.close();

            fileIn.close();

            System.out.println("Library deserialized successfully.");

            System.out.println("Name 1 book: " + library.getBooks().get(0).getName());
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return library;
    }

}
