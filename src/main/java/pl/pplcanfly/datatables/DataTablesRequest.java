package pl.pplcanfly.datatables;

import java.util.List;
import java.util.Map;


public class DataTablesRequest {

    private RowsProcessor sorter;
    private RowsProcessor filter;
    private RowsProcessor pager;
    private Formatter formatter;

    public DataTablesRequest(Map<String, String[]> params, ServerSideDataTable dataTable) {
        this(new RequestParams(params), dataTable);
    }

    DataTablesRequest(RequestParams params, ServerSideDataTable dataTable) {
        this.filter = Processors.filter(dataTable, params);
        this.sorter = Processors.sorter(dataTable, params);
        this.pager = Processors.pager(dataTable, params);

        this.formatter = new JsonFormatter(dataTable.getColumns(params.getColumns(), params.getDisplayStart() + 1), params);
    }

    public DataTablesResponse process(List<?> rows) {
        List<?> processed = rows;

        processed = filter.process(processed);
        processed = sorter.process(processed);
        List<?> paged = pager.process(processed);

        String json = formatter.format(paged, rows.size(), processed.size());

        return new DataTablesResponse(processed, paged, json);
    }

    void setSorter(RowsProcessor sorter) {
        this.sorter = sorter;
    }

    void setFilter(RowsProcessor filter) {
        this.filter = filter;
    }

    void setPager(RowsProcessor pager) {
        this.pager = pager;
    }

    void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

}
