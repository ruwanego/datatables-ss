package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.DateType;
import pl.pplcanfly.datatables.types.TextType;

public class ServerSideDataTableBuilderTest {

    @Test
    public void should_define_text_column() {
        // when
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .text("col")
                .done();

        // then
        assertThat(dataTable.getColumns().get(0).getType()).isInstanceOf(TextType.class);
    }

    @Test
    public void should_define_date_column() {
        // when
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .date("col")
                .done();

        // then
        assertThat(dataTable.getColumns().get(0).getType()).isInstanceOf(DateType.class);
    }

    @Test
    public void should_allow_to_define_display_converter() {
        // given
        DisplayConverter textConverter = new DisplayConverter() {
            @Override
            public String convert(Object value) {
                return null;
            }
        };

        DisplayConverter dateConverter = new DisplayConverter() {
            @Override
            public String convert(Object value) {
                return null;
            }
        };

        // when
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .text("text").displayedWith(textConverter)
                .date("date").displayedWith(dateConverter)
                .done();

        // then
        assertThat(dataTable.getColumns()).hasSize(2);

        assertThat(dataTable.getColumns().get(0).getName()).isEqualTo("text");
        assertThat(dataTable.getColumns().get(0).getType()).isInstanceOf(TextType.class);
        assertThat(dataTable.getColumns().get(0).getDisplayConverter()).isSameAs(textConverter);

        assertThat(dataTable.getColumns().get(1).getName()).isEqualTo("date");
        assertThat(dataTable.getColumns().get(1).getType()).isInstanceOf(DateType.class);
        assertThat(dataTable.getColumns().get(1).getDisplayConverter()).isSameAs(dateConverter);
    }


    @Test
    public void should_allow_to_define_value_accessor() {
        // given
        ValueAccessor textAccessor = new ValueAccessor() {
            @Override
            public Object getValueFrom(Object obj) {
                return null;
            }
        };

        ValueAccessor dateAccessor = new ValueAccessor() {
            @Override
            public Object getValueFrom(Object obj) {
                return null;
            }
        };

        // when
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .text("text").accessibleWith(textAccessor)
                .date("date").accessibleWith(dateAccessor)
                .done();

        // then
        assertThat(dataTable.getColumns()).hasSize(2);

        assertThat(dataTable.getColumns().get(0).getName()).isEqualTo("text");
        assertThat(dataTable.getColumns().get(0).getType()).isInstanceOf(TextType.class);
        assertThat(dataTable.getColumns().get(0).getValueAccessor()).isSameAs(textAccessor);

        assertThat(dataTable.getColumns().get(1).getName()).isEqualTo("date");
        assertThat(dataTable.getColumns().get(1).getType()).isInstanceOf(DateType.class);
        assertThat(dataTable.getColumns().get(1).getValueAccessor()).isSameAs(dateAccessor);
    }

}
