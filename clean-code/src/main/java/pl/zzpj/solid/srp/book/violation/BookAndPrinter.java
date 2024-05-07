package pl.zzpj.solid.srp.book.violation;

import java.util.HashMap;
import java.util.Map;

public class BookAndPrinter {

    private int currentPage = 0;

    private Map<Integer, String> pages = new HashMap<>();

    private String libraryRoomName;
    private String rowLocator;
    private int indexOnShelf;

    public String getTitle() {
        return "A Great Book";
    }

    public String getAuthor() {
        return "John Doe";
    }

    public String getCurrentPageContents() {
        return pages.get(currentPage);
    }

    public void turnPage() {
        currentPage ++;
    }

    /**
     * Prints the current page.
     */
    public void printCurrentPage() {
        System.out.println(pages.get(currentPage));
    }

    /**
     * Gives the library name
     *
     * @return
     */
    public String libraryRoomName() {
        return libraryRoomName;
    }

    /**
     * Gives the row location of the book.
     * @return
     */
    public String getLocationRowLocator() {
        return rowLocator;
    }

    /**
     * Gives the number from shelf.
     * @return
     */
    public int getIndexOnShelf() {
        return indexOnShelf;
    }

    /**
     * Prints all pages
     * @return
     */
    public String printAllPages() {

        String allPages = new String();
        for(Map.Entry<Integer, String> page : pages.entrySet()) {
            allPages += (page.getKey() + " " + page.getValue());
        }
        return  allPages;
    }
}
