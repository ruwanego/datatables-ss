package pl.pplcanfly.datatables;

import pl.pplcanfly.datatables.accessors.ReflectionValueAccessor;
import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.Type;

class Column {
    private Type type;
    private String name;
    private ValueAccessor valueAccessor;
    private DisplayConverter displayConverter;
    private SortBy sortBy = SortBy.MODEL_VALUE;

    public Column(String name) {
        this.name = name;
        this.valueAccessor = new ReflectionValueAccessor(name);
    }

    public Column(Type type, String name) {
        this(type, name, new ReflectionValueAccessor(name));
    }

    public Column(Type type, String name, DisplayConverter converter) {
        this(type, name, new ReflectionValueAccessor(name));
        this.displayConverter = converter;
    }

    public Column(Type type, String name, DisplayConverter converter, SortBy sortBy) {
        this(type, name, new ReflectionValueAccessor(name));
        this.displayConverter = converter;
        this.sortBy = sortBy;
    }

    public Column(Type type, String name, ValueAccessor valueAccessor) {
        this.type = type;
        this.name = name;
        this.valueAccessor = valueAccessor;
    }

    public int compareValues(Object o1, Object o2) {
        switch (sortBy) {
        case MODEL_VALUE:
            return type.compare(getValueFrom(o1), getValueFrom(o2));
        case DISPLAY_VALUE:
            return type.compare(getDisplayValueFrom(o1), getDisplayValueFrom(o2));
        default:
            throw new IllegalStateException("unknown option");
        }
    }

    public Object getDisplayValueFrom(Object row) {
        if (displayConverter != null) {
            return displayConverter.convert(valueAccessor.getValueFrom(row));
        }
        return valueAccessor.getValueFrom(row);
    }

    public Object getValueFrom(Object row) {
        return valueAccessor.getValueFrom(row);
    }

    public String getName() {
        return name;
    }

    Type getType() {
        return type;
    }

    void setType(Type type) {
        this.type = type;
    }

    ValueAccessor getValueAccessor() {
        return valueAccessor;
    }

    void setValueAccessor(ValueAccessor valueAccessor) {
        this.valueAccessor = valueAccessor;
    }

    DisplayConverter getDisplayConverter() {
        return displayConverter;
    }

    void setDisplayConverter(DisplayConverter converter) {
        this.displayConverter = converter;
    }

    void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    SortBy getSortBy() {
        return sortBy;
    }
}
