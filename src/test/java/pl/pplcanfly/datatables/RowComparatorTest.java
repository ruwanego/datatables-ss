package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.RowComparator;
import pl.pplcanfly.datatables.SortOrder;
import pl.pplcanfly.datatables.types.Types;

public class RowComparatorTest {

    @Test
    public void should_compare_objects_by_single_field() {
        // given
        Something s1 = new Something("u1", 5);
        Something s2 = new Something("u2", 10);
        Something s3 = new Something("u3", 5);
        List<Object> list = toList(s1, s2, s3);

        RowComparator comparator = new RowComparator(Types.numeric(), SortOrder.ASC, "bar");

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

        RowComparator comparator = new RowComparator(Types.numeric(), SortOrder.DESC, "bar");

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

        RowComparator comparator = new RowComparator(Types.text(), SortOrder.ASC, "foo");
        comparator.append(new RowComparator(Types.numeric(), SortOrder.ASC, "bar"));

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s1, s2, s3);
    }

    @Test
    public void should_compare_objects_by_multiple_fields_desc_desc() {
        // given
        Something s1 = new Something("aaa", 5);
        Something s2 = new Something("aaa", 10);
        Something s3 = new Something("bbb", 2);
        List<Object> list = toList(s1, s2, s3);

        RowComparator comparator = new RowComparator(Types.text(), SortOrder.DESC, "foo");
        comparator.append(new RowComparator(Types.numeric(), SortOrder.DESC, "bar"));

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

        RowComparator comparator = new RowComparator(Types.text(), SortOrder.ASC, new ReversingValueAccessor());

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

        RowComparator comparator = new RowComparator(Types.text(), SortOrder.DESC, new ReversingValueAccessor());

        // when
        Collections.sort(list, comparator);

        // then
        assertThat(list).containsExactly(s3, s2, s1);
    }

    @Test
    public void should_append_comparators_to_existing_one() {
        // given
        RowComparator comparator1 = new RowComparator(Types.text(), SortOrder.DESC, "foo");
        RowComparator comparator2 = new RowComparator(Types.text(), SortOrder.DESC, "foo");
        RowComparator comparator3 = new RowComparator(Types.text(), SortOrder.DESC, "foo");
        RowComparator comparator4 = new RowComparator(Types.text(), SortOrder.DESC, "foo");

        // when
        comparator1.append(comparator2);
        comparator1.append(comparator3);
        comparator1.append(comparator4);

        // then
        assertThat(comparator1.getNext()).isSameAs(comparator2);
        assertThat(comparator2.getNext()).isSameAs(comparator3);
        assertThat(comparator3.getNext()).isSameAs(comparator4);
        assertThat(comparator4.getNext()).isNull();
    }

    private List<Object> toList(Object... objects) {
        List<Object> list = new ArrayList<Object>();
        for (Object o : objects) {
            list.add(o);
        }
        return list;
    }

}
