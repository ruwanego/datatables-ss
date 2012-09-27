package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.utils.TestUtils;

public class PagerTest {

    @Test
    public void should_return_sublist_of_input_rows() {
        // given
        List<Something> rows = TestUtils.load("1");

        Pager pager = new Pager(1, 2);

        // when
        List<?> processed = pager.process(rows);

        // then
        assertThat(processed).hasSize(2).onProperty("foo").containsExactly("a", "d");
    }
    
    @Test
    public void should_return_full_list_if_display_length_is_set_to_all_rows() {
        // given
        List<Something> rows = TestUtils.load("1");

        Pager pager = new Pager(0, Pager.ALL_ROWS);

        // when
        List<?> processed = pager.process(rows);

        // then
        assertThat(processed).hasSize(4).onProperty("foo").containsExactly("c", "a", "d", "b");
    }

    @Test
    public void should_return_sublist_if_display_length_is_set_to_all_rows() {
        // given
        List<Something> rows = TestUtils.load("1");

        Pager pager = new Pager(1, Pager.ALL_ROWS); // is it a valid case?

        // when
        List<?> processed = pager.process(rows);

        // then
        assertThat(processed).hasSize(3).onProperty("foo").containsExactly("a", "d", "b");
    }

}
