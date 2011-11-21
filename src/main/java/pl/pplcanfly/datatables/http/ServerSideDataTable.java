package pl.pplcanfly.datatables.http;

import java.util.ArrayList;
import java.util.List;

import pl.pplcanfly.datatables.Column;
import pl.pplcanfly.datatables.Type;

public class ServerSideDataTable {

    private List<Column> columns = new ArrayList<Column>();

    public void addColumn(Type type, String name) {
        columns.add(new Column(type, name));
    }
    
    public List<Column> getColumns() {
        return columns;
    }

}