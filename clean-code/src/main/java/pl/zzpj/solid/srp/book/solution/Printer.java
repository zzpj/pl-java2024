package pl.zzpj.solid.srp.book.solution;

import java.util.Map;

// represents a printer for a book
public class Printer {
    private int currentPage = 0;
    private final Book book;

    public Printer(Book book) {
        this.book = book;
    }

    public String getCurrentPageContents() {
        return this.book.getPages().get(currentPage);
    }

    public void turnPage() {
        currentPage++;
    }

    /**
     * Prints the current page.
     */
    public void printCurrentPage() {
        System.out.println(this.book.getPages().get(currentPage));
    }

    /**
     * Prints all pages
     *
     * @return
     */
    public String printAllPages() {

        String allPages = new String();
        for (Map.Entry<Integer, String> page : this.book.getPages().entrySet()) {
            allPages += (page.getKey() + " " + page.getValue());
        }
        return allPages;
    }
}