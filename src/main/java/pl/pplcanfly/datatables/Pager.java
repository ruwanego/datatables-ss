package pl.pplcanfly.datatables;

import java.util.List;

class Pager implements RowsProcessor {

    private int displayStart;
    private int displayLength;

    public Pager(int displayStart, int displayLength) {
        this.displayStart = displayStart;
        this.displayLength = displayLength;
    }

    @Override
    public List<?> process(List<?> rows) {
        return rows.subList(displayStart, Math.min(rows.size(), displayStart + displayLength));
    }

}
