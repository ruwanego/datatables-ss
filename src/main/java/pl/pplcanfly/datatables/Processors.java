package pl.pplcanfly.datatables;

final class Processors {
    private Processors() {}

    public static RowsProcessor filter(ServerSideDataTable dataTable, RequestParams params) {
        return new Filter(dataTable.getColumns(params.getSearchableCols()), params.getSearch());
    }

    public static RowsProcessor sorter(ServerSideDataTable dataTable, RequestParams params) {
        return new Sorter(dataTable.getColumns(params.getSortCols()), SortOrder.toEnumList(params.getSortDirs()));
    }

    public static RowsProcessor pager(ServerSideDataTable dataTable, RequestParams params) {
        return new Pager(params.getDisplayStart(), params.getDisplayLength());
    }
}
