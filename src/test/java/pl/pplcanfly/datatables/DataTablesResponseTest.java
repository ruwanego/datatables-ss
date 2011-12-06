package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.DataTablesResponse;
import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.params.ResponseParams;
import pl.pplcanfly.datatables.types.Types;

public class DataTablesResponseTest {

    @Test
    public void should_transform_to_json() {
        // given
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .column(Types.text(), "foo")
                .column(Types.numeric(), "bar")
                .done();

        List<Something> rows = Arrays.asList(new Something("abc", 123), new Something("def", 987));
        DataTablesResponse response = new DataTablesResponse(new ResponseParams(3, 20, 2), dataTable, rows);

        // when
        String json = response.toJson();

        // then
        assertThat(json).isEqualTo("{\"sEcho\":3," +
        		"\"iTotalRecords\":20," +
        		"\"iTotalDisplayRecords\":2," +
        		"\"aaData\":[[\"abc\",123],[\"def\",987]]}");
    }

}
