package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.types.Types;

public class JsonFormatterTest {

    @Test
    public void should_transform_to_json_with_column_order_determied_by_request_params() {
        // given
        RequestParams requestParams = mock(RequestParams.class);
        stub(requestParams.getEcho()).toReturn(3);

        List<Something> rowsToShow = Arrays.asList(new Something("abc", 123), new Something("def", 987));

        int totalRows = 20;
        int displayRows = 10;

        JsonFormatter formatter = new JsonFormatter(Arrays.asList(new Column(Types.text(), "foo"),
                new Column(Types.numeric(), "bar")), requestParams);

        // when
        String json = formatter.format(rowsToShow, totalRows, displayRows);

        // then
        assertThat(json).isEqualTo("{\"sEcho\":3," +
                "\"iTotalRecords\":20," +
                "\"iTotalDisplayRecords\":10," +
                "\"aaData\":[[\"abc\",\"123\"],[\"def\",\"987\"]]}");
    }

    @Test
    public void should_generate_id_numbers_for_rows() {
        // given
        RequestParams requestParams = mock(RequestParams.class);
        stub(requestParams.getEcho()).toReturn(3);

        List<Object> rowsToShow = Arrays.asList(new Object(), new Object(), new Object());

        int totalRows = 20;
        int displayRows = 10;

        JsonFormatter formatter = new JsonFormatter(Arrays.asList((Column) new IdColumn("id", 1)), requestParams);

        // when
        String json = formatter.format(rowsToShow, totalRows, displayRows);

        // then
        assertThat(json).isEqualTo("{\"sEcho\":3," +
                "\"iTotalRecords\":20," +
                "\"iTotalDisplayRecords\":10," +
                "\"aaData\":[[\"1\"],[\"2\"],[\"3\"]]}");
    }

    @Test
    public void should_use_display_value_for_generating_column_content() {
        // given
        RequestParams requestParams = mock(RequestParams.class);
        stub(requestParams.getEcho()).toReturn(3);

        List<FooDate> rowsToShow = Arrays.asList(new FooDate(new Date(1)));

        int totalRows = 20;
        int displayRows = 10;

        JsonFormatter formatter = new JsonFormatter(Arrays.asList(new Column(Types.date(), "date")), requestParams);

        // when
        String json = formatter.format(rowsToShow, totalRows, displayRows);

        // then
        assertThat(json).isEqualTo("{\"sEcho\":3," +
                "\"iTotalRecords\":20," +
                "\"iTotalDisplayRecords\":10," +
                "\"aaData\":[[\"Thu Jan 01 01:00:00 CET 1970\"]]}");
    }

    @Test
    public void should_generate_empty_string_if_model_value_is_null() {
        // given
        RequestParams requestParams = mock(RequestParams.class);
        stub(requestParams.getEcho()).toReturn(3);

        List<Something> rowsToShow = Arrays.asList(new Something(null, 0));

        int totalRows = 20;
        int displayRows = 10;

        JsonFormatter formatter = new JsonFormatter(Arrays.asList(new Column(Types.text(), "foo")), requestParams);

        // when
        String json = formatter.format(rowsToShow, totalRows, displayRows);

        // then
        assertThat(json).isEqualTo("{\"sEcho\":3," +
                "\"iTotalRecords\":20," +
                "\"iTotalDisplayRecords\":10," +
                "\"aaData\":[[\"\"]]}");
    }
}

class FooDate {
    private Date date;

    public FooDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

}