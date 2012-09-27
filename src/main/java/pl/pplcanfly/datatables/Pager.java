package pl.pplcanfly.datatables;

import java.util.List;

class Pager implements RowsProcessor {
    
    public static final int ALL_ROWS = -1;

    private int displayStart;
    private int displayLength;

    public Pager(int displayStart, int displayLength) {
        this.displayStart = displayStart;
        this.displayLength = displayLength;
    }

    @Override
    public List<?> process(List<?> rows) {
        int max = (displayLength == ALL_ROWS) ? rows.size() : Math.min(rows.size(), displayStart
                + displayLength);
        return rows.subList(displayStart, max);
    }

}
