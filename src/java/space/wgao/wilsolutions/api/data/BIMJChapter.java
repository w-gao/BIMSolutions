package space.wgao.wilsolutions.api.data;

/**
 * BIMSolutions
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class BIMJChapter {

    private String _name;
    private BIMJSection[] section;

    public BIMJChapter(String _name, BIMJSection[] section) {
        this._name = _name;
        this.section = section;
    }

    public String getName() {
        return _name;
    }

    public BIMJSection[] getSections() {
        return section;
    }

    public BIMJSection getSection(int sec) {
        return section[sec - 1];
    }
}
