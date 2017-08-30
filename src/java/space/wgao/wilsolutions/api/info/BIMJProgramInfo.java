package space.wgao.wilsolutions.api.info;

import java.util.Arrays;

/**
 * BIMSolutions
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class BIMJProgramInfo {

    private String _name;
    private String _code;
    private BIMJBookInfo[] book;

    public String getName() {
        return _name;
    }

    public String getCode() {
        return _code;
    }

    public String[] getBookNames() {
        String[] books = new String[this.book.length];
        int i =  0;

        for(BIMJBookInfo info : this.book){
            books[i] = info.getTitle();
            i++;
        }
        return books;
    }

    public BIMJBookInfo getBook(String title) {
        return Arrays.stream(this.book).filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
    }
}
