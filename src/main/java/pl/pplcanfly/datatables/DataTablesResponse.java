package pl.pplcanfly.datatables;

import java.util.List;

public class DataTablesResponse {

    private List<?> totalDisplayRows;
    private List<?> displayRows;
    private String json;

    public DataTablesResponse(List<?> totalDisplayRows, List<?> displayRows, String json) {
        this.totalDisplayRows = totalDisplayRows;
        this.displayRows = displayRows;
        this.json = json;
    }

    public List<?> getDisplayRows() {
        return displayRows;
    }

    public List<?> getTotalDisplayRows() {
      return totalDisplayRows;
    }

    public String toJson() {
        return json;
    }

}
