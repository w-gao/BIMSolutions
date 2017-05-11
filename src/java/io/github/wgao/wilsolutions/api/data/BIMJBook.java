package io.github.wgao.wilsolutions.api.data;

/**
 * BIMSolutions
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class BIMJBook {

    private String _title;
    private String _program;
    private String _thumbnail;
    private String _solution_base;

    private BIMJChapter[] chapter;

    public BIMJBook(String title, String program, String thumbnail, String solution_base, BIMJChapter[] chapter){

        this._title = title;
        this._program = program;
        this._thumbnail = thumbnail;
        this._solution_base = solution_base;

        this.chapter = chapter;
    }

    public String getTitle() {
        return _title;
    }

    public String getProgram() {
        return _program;
    }

    public String getThumbnail() {
        return _thumbnail;
    }

    public String getSolutionBase() {
        return _solution_base;
    }

    public BIMJChapter[] getChapters() {
        return chapter;
    }

    private BIMJChapter getChapter(int chp) {
        return chapter[chp - 1];
    }

    private String getPrefix(int chp, int sec){
        return this.getChapter(chp).getSection(sec).getPrefix();
    }

    /**
     * get the exercise URL with the given chapter, section, and exercise
     *
     * @param chp chapter
     * @param sec section
     * @param exc exersice
     * @return answer URL
     */
    public String getExercise(int chp, int sec, int exc){

        // check validation
        if(chp > this.chapter.length || sec > this.getChapter(chp).getSections().length || exc > this.getChapter(chp).getSection(sec).getNumEx()){
            return null;
        }

        String padChp = String.format("%02d", chp);
        String padSec = String.format("%02d", sec);
        String padExc = String.format("%03d", exc);

        return _solution_base + padChp + "/" + padSec + "/" + getPrefix(chp, sec) + "_" + padExc + ".png";
    }
}
