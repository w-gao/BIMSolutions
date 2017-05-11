package io.github.wgao.wilsolutions.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.wgao.wilsolutions.WilSolutions;
import io.github.wgao.wilsolutions.api.data.BIMJBook;
import io.github.wgao.wilsolutions.api.info.BIMJProgramInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BIMSolutions
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class BIMAPI {

    private List<BIMJProgramInfo> programs = new ArrayList<>();
    private List<BIMJBook> books = new ArrayList<>();

    /**
     * load all the sources from the 'content/bim_app_sources.json'
     * file. Parse all the json entries into json objects corresponding
     * to api/info/BIMJProgramInfo.
     */
    public void loadSources(){
        try(Reader reader = new InputStreamReader(WilSolutions.class.getClassLoader().getResourceAsStream("content/bim_app_sources.json"))) {
            Gson gson = new GsonBuilder().create();
            this.programs = Arrays.asList(gson.fromJson(reader, BIMJProgramInfo[].class));
        }catch (IOException ex){
            //
        }
    }

    /**
     * get a String list of programs
     *
     * @return programs
     */
    public String[] getPrograms(){
        String[] programs = new String[this.programs.size()];
        int i =  0;

        for(BIMJProgramInfo info : this.programs){
            programs[i] = info.getName();
            i++;
        }
        return programs;
    }

    /**
     * get a list of textbook names by the program name provided
     * as the parameter 'program'. If the program does not exist,
     * null return value would result.
     *
     * @param program the program name
     * @return textbooks
     */
    public String[] getTextbookListByProgram(String program){
        for(BIMJProgramInfo info : this.programs){
            if(info.getName().equals(program)){
                return info.getBookNames();
            }
        }
        return new String[]{};
    }

    /**
     * get the BIMJBook instance with the given program name,
     * and book title.
     *
     * @param program program name
     * @param title title
     * @return BIMJBook instance
     */
    public BIMJBook getTextBook(String program, String title){
        for(BIMJBook book : this.books){
            if(book.getProgram().equals(program) && book.getTitle().equals(title)){
                return book;
            }
        }

        // does not exist, create a new instance
        for(BIMJProgramInfo info : this.programs){
            if(info.getName().equals(program)){
                BIMJBook book;
                try(Reader reader = new InputStreamReader(WilSolutions.class.getClassLoader().getResourceAsStream("content/source/" + info.getBook(title).getSolSource()))) {
                    Gson gson = new GsonBuilder().create();
                    book = gson.fromJson(reader, BIMJBook.class);
                }catch (IOException ex){
                    return null;
                }
                this.books.add(book);
                return book;
            }
        }
        return null;
    }

}
