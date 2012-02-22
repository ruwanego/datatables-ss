package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RequestParams {
    private Map<String, String[]> params;

    public RequestParams(Map<String, String[]> params) {
        this.params = params;
    }

    /**
     * @return sEcho, casted to int due to security reasons (XSS attacks)
     */
    public int getEcho() {
        return getNumericParam("sEcho");
    }

    /**
     * @return iDisplayStart
     */
    public int getDisplayStart() {
        return getNumericParam("iDisplayStart");
    }

    /**
     * @return List of all column names, based on sColumns param
     */
    public List<String> getColumns() {
        return Arrays.asList(getParam("sColumns").split(","));
    }

    /**
     * @return iSortingCols
     */
    public int getSortingColsCount() {
        return getNumericParam("iSortingCols");
    }

    /**
     * @return List of column names being sorted on
     */
    public List<String> getSortCols() {
        List<String> columns = getColumns();
        List<String> sortingColumns = new ArrayList<String>();
        for (int i = 0; i < getSortingColsCount(); i++) {
            int columnNumber = getNumericParam("iSortCol_" + i);
            boolean sortable = Boolean.parseBoolean(getParam("bSortable_" + columnNumber));
            if (sortable) {
                sortingColumns.add(columns.get(columnNumber));
            }
        }
        return sortingColumns;
    }

    /**
     * @return List of sort directions based on sSortDir_X params
     */
    public List<String> getSortDirs() {
        List<String> sortDirs = new ArrayList<String>();
        for (int i = 0; i < getSortingColsCount(); i++) {
            sortDirs.add(getParam("sSortDir_" + i));
        }
        return sortDirs;
    }

    /**
     * @return iDisplayLength
     */
    public int getDisplayLength() {
        return getNumericParam("iDisplayLength");
    }

    /**
     * @return sSearch
     */
    public String getSearch() {
        return getParam("sSearch");
    }

    /**
     * @return List of column names based on bSearchable_X params
     */
    public List<String> getSearchableCols() {
        List<String> searchableColumns = new ArrayList<String>();

        List<String> columns = getColumns();
        for (int i = 0; i < columns.size(); i++) {
            boolean searchable = Boolean.parseBoolean(getParam("bSearchable_" + i));
            if (searchable) {
                searchableColumns.add(columns.get(i));
            }
        }

        return searchableColumns;
    }

    private String getParam(String key) {
        String[] values = params.get(key);
        return values != null ? values[0] : "";
    }

    private int getNumericParam(String key) {
        String[] values = params.get(key);
        return values != null ? Integer.parseInt(values[0]) : 0;
    }

}
