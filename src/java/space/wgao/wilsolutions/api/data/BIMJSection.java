package space.wgao.wilsolutions.api.data;

/**
 * BIMSolutions
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class BIMJSection {

    private String _name;
    private String _num_ex;
    private String _prefix;

    public BIMJSection(String _name, String _num_ex, String _prefix) {
        this._name = _name;
        this._num_ex = _num_ex;
        this._prefix = _prefix;
    }

    public String getName() {
        return _name;
    }

    public int getNumEx() {
        return Integer.parseInt(_num_ex);
    }

    public String getPrefix() {
        return _prefix;
    }
}
