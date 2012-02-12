package pl.pplcanfly.datatables;

import pl.pplcanfly.datatables.accessors.ReflectionValueAccessor;
import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.Type;

class Column {
    private Type type;
    private String name;
    private ValueAccessor valueAccessor;
    private DisplayConverter formatter;

    public Column(String name) {
        this.name = name;
        this.valueAccessor = new ReflectionValueAccessor(name);
    }

    public Column(Type type, String name) {
        this(type, name, new ReflectionValueAccessor(name));
    }

    public Column(Type type, String name, DisplayConverter formatter) {
        this(type, name, new ReflectionValueAccessor(name));
        this.formatter = formatter;
    }

    public Column(Type type, String name, ValueAccessor valueAccessor) {
        this.type = type;
        this.name = name;
        this.valueAccessor = valueAccessor;
    }

    public Object getDisplayValueFrom(Object row) {
        if (formatter != null) {
            return formatter.convert(valueAccessor.getValueFrom(row));
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
        return formatter;
    }

    void setDisplayConverter(DisplayConverter converter) {
        this.formatter = converter;
    }
}
