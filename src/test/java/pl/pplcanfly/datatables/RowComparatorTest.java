package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.types.Types;

public class RowComparatorTest {

    @Test
    public void should_compare_objects_by_single_field() {
        // given
        Something s1 = new Something("u1", 5);
        Something s2 = new Something("u2", 10);
        Something s3 = new Something("u3", 5);
        List<Object> list = toList(s1, s2, s3);

        RowComparator comparator = new RowComparator(new Column(Types.numeric(), "bar"), SortOrder.ASC);

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s1, s3, s2);
    }

    @Test
    public void should_compare_objects_by_single_field_descending() {
        // given
        Something s1 = new Something("u1", 5);
        Something s2 = new Something("u2", 10);
        Something s3 = new Something("u3", 5);
        List<Object> list = toList(s1, s2, s3);

        RowComparator comparator = new RowComparator(new Column(Types.numeric(), "bar"), SortOrder.DESC);

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s2, s1, s3);
    }

    @Test
    public void should_compare_objects_by_multiple_fields_asc_asc() {
        // given
        Something s1 = new Something("aaa", 5);
        Something s2 = new Something("aaa", 10);
        Something s3 = new Something("bbb", 2);
        List<Object> list = toList(s1, s2, s3);

        RowComparator comparator = new RowComparator(new Column(Types.text(), "foo"), SortOrder.ASC);
        comparator.append(new RowComparator(new Column(Types.numeric(), "bar"), SortOrder.ASC));

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s1, s2, s3);
    }

    @Test
    public void should_compare_objects_using_model_value_even_if_display_converter_is_defined() {
        // given
        SomeObjectWithDate s1 = new SomeObjectWithDate(new Date(3000000000L));
        SomeObjectWithDate s2 = new SomeObjectWithDate(new Date(2000000000L));
        SomeObjectWithDate s3 = new SomeObjectWithDate(new Date(1000000000L));
        List<Object> list = toList(s1, s2, s3);

        RowComparator comparator = new RowComparator(new Column(Types.date(), "date",
                new DateDisplayConverter(), SortBy.MODEL_VALUE), SortOrder.ASC);

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s3, s2, s1);
    }

    @Test
    public void should_compare_objects_using_display_value() {
        // given
        SomeObjectWithDate s1 = new SomeObjectWithDate(new Date(3880000000000L));
        SomeObjectWithDate s2 = new SomeObjectWithDate(new Date(2880000000000L));
        SomeObjectWithDate s3 = new SomeObjectWithDate(new Date(1880000000000L));
        List<Object> list = toList(s1, s2, s3);

        // sorting by display values implies that column values are always Strings
        // and therefore column type must be 'text' (not 'date')
        RowComparator comparator = new RowComparator(new Column(Types.text(), "date",
                new DateDisplayConverter(), SortBy.DISPLAY_VALUE), SortOrder.ASC);

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s2, s1, s3);
    }

    @Test
    public void should_compare_objects_by_multiple_fields_desc_desc() {
        // given
        Something s1 = new Something("aaa", 5);
        Something s2 = new Something("aaa", 10);
        Something s3 = new Something("bbb", 2);
        List<Object> list = toList(s1, s2, s3);

        RowComparator comparator = new RowComparator(new Column(Types.text(), "foo"), SortOrder.DESC);
        comparator.append(new RowComparator(new Column(Types.numeric(), "bar"), SortOrder.DESC));

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s3, s2, s1);
    }

    @Test
    public void should_compare_objects_using_custom_value_accessor_asc() {
        // given
        Something s1 = new Something("abc3", 1);
        Something s2 = new Something("abc2", 1);
        Something s3 = new Something("abc1", 1);
        List<Object> list = toList(s1, s2, s3);

        RowComparator comparator = new RowComparator(new Column(Types.text(), "foo", new ReversingValueAccessor()), SortOrder.ASC);

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s3, s2, s1);
    }

    @Test
    public void should_compare_objects_using_custom_value_accessor_desc() {
        // given
        Something s1 = new Something("abc1", 1);
        Something s2 = new Something("abc2", 1);
        Something s3 = new Something("abc3", 1);
        List<Object> list = toList(s1, s2, s3);

        RowComparator comparator = new RowComparator(new Column(Types.text(), "foo", new ReversingValueAccessor()), SortOrder.DESC);

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s3, s2, s1);
    }

    @Test
    public void should_append_comparators_to_existing_one() {
        // given
        RowComparator comparator1 = new RowComparator(new Column(Types.text(), "foo"), SortOrder.DESC);
        RowComparator comparator2 = new RowComparator(new Column(Types.text(), "foo"), SortOrder.DESC);
        RowComparator comparator3 = new RowComparator(new Column(Types.text(), "foo"), SortOrder.DESC);
        RowComparator comparator4 = new RowComparator(new Column(Types.text(), "foo"), SortOrder.DESC);

        // when
        comparator1.append(comparator2);
        comparator1.append(comparator3);
        comparator1.append(comparator4);

        // then
        assertThat(comparator1.getNextComparator()).isSameAs(comparator2);
        assertThat(comparator2.getNextComparator()).isSameAs(comparator3);
        assertThat(comparator3.getNextComparator()).isSameAs(comparator4);
        assertThat(comparator4.getNextComparator()).isNull();
    }

    private List<Object> toList(Object... objects) {
        List<Object> list = new ArrayList<Object>();
        for (Object o : objects) {
            list.add(o);
        }
        return list;
    }

}

class SomeObjectWithDate {
    private Date date;

    public SomeObjectWithDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

}

class DateDisplayConverter implements DisplayConverter {

    @Override
    public String convert(Object value) {
        return new SimpleDateFormat("dd-MM-yyyy").format((Date) value);
    }

}