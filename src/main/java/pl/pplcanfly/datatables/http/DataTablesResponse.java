package pl.pplcanfly.datatables.http;

import java.util.List;

import pl.pplcanfly.datatables.formatters.JsonFormatter;

public class DataTablesResponse {

    private List<?> processedRows;
    private ServerSideDataTable dataTable;
    private ResponseParams params;

    public DataTablesResponse(List<?> processedRows, ServerSideDataTable dataTable) {
        this.processedRows = processedRows;
        this.dataTable = dataTable;
    }

    public DataTablesResponse(ResponseParams params, ServerSideDataTable dataTable, List<?> processedRows) {
        this.params = params;
        this.processedRows = processedRows;
        this.dataTable = dataTable;
    }

    public List<?> getProcessedRows() {
        return processedRows;
    }

    public String toJson() {
        return new JsonFormatter().format(params, dataTable, processedRows);
    }

}
