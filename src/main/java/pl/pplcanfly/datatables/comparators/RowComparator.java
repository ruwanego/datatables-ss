package pl.pplcanfly.datatables.comparators;

import java.util.Comparator;

import pl.pplcanfly.datatables.SortOrder;
import pl.pplcanfly.datatables.Type;
import pl.pplcanfly.datatables.accessors.ReflectionValueAccessor;
import pl.pplcanfly.datatables.accessors.ValueAccessor;

public class RowComparator implements Comparator<Object> {

    private ValueAccessor valueAccessor;
    private Comparator<Object> valueComparator;
    private SortOrder sortOrder;
    private RowComparator next;
    
    public static RowComparator ascending(Type type, String fieldName) {
        return new RowComparator(type, SortOrder.ASC, fieldName);
    }
    
    public static RowComparator descending(Type type, String fieldName) {
        return new RowComparator(type, SortOrder.DESC, fieldName);
    }

    private RowComparator(Type type, SortOrder sortOrder, String fieldName) {
        this.valueAccessor = new ReflectionValueAccessor(fieldName);
        this.valueComparator = type.getComparator();
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Object o1, Object o2) {
        int compare = valueComparator.compare(valueAccessor.getValueFrom(o1), valueAccessor.getValueFrom(o2));
        
        if (compare == 0) {
            return next != null ? next.compare(o1, o2) : compare;
        } else {
            return applyOrder(compare);
        }
    }
    
    /**
     * Used only internally
     */
    void add(RowComparator rowComparator) {
        next = rowComparator;
    }
    
    private int applyOrder(int compare) {
        if (sortOrder == SortOrder.DESC) {
            return compare * -1;
        }
        return compare;
    }

}
