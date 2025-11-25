public class Book {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private int availableCopies;

    public boolean checkAvailability() {
        if(availableCopies>0)
            return true;
        else
            return false;
    }

    public void updateCopies(int n) {
        if(n<=0) return;
        availableCopies+=n;
    }

    public void displayInfo() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Copies available: " + availableCopies);
    }

}
