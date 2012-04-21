#datatables-ss
Server side processing for [datatables](http://datatables.net/).

##How to make basic setup?
###Setup html table somewhere in JSP for instance:

        <table id="example">
          <thead>
            <tr>
              <th>Engine</th>
              <th>Browser</th>
              <th>Platform</th>
              <th>Version</th>
              <th>Grade</th>
            </tr>
          </thead>
          <tbody> </tbody>
        </table>

###Setup your datatable with *bServerSide* and *sAjaxSource* options:

        $(document).ready(function() {
            $('#example').dataTable({
                "aoColumns": [
                  {sName: "engine"},
                  {sName: "browser"},
                  {sName: "version"},
                  {sName: "platform"},
                  {sName: "grade"} ],
                "bServerSide": true,
                "sAjaxSource": "ExampleServlet"
            });
        });
        
Keep in mind that sName values must exactly match column names set in object of *ServerSideDataTable* class (ExampleServlet.java).

###Add ExampleServlet mappings to *web.xml*:

        <servlet>
          <servlet-name>example-servlet</servlet-name>
          <servlet-class>pplcanfly.ExampleServlet</servlet-class>
        </servlet>

        <servlet-mapping>
          <servlet-name>example-servlet</servlet-name>
          <url-pattern>/ExampleServlet</url-pattern>
        </servlet-mapping>

###Create ExampleServlet.java to handle datatable POST action:
        public class HelloServlet extends HttpServlet {
             
            private static ServerSideDataTable dataTable = ServerSideDataTable.build()
                .text("engine")
                .text("browser")
                .text("platform")
                .numeric("version")
                .text("grade")
                .done();

            public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                    IOException {
                List<ExampleRow> rows = DataSource.loadAll();
                
                DataTablesRequest dataTablesRequest = new DataTablesRequest(req.getParameterMap(), dataTable);
                DataTablesResponse dataTablesResponse = dataTablesRequest.process(rows);
                resp.getWriter().write(dataTablesResponse.toJson());
            }

        }

In this case datatables-ss uses reflection to get values. Objects of class ExampleRow are simple POJOs with *getEngine*, *getBrowser* getters. In next section you'll see how to make more customized configuration.

##ServerSideDataTable configuration
###Column types
datatables-ss supports couple of column types out of box:

* text
* numeric
* date
* bool

There's also one special column type *id*. It auto-generates index value when table is being displayed on screen.
All column definitions require column name parameter which corresponds to getter method in model object:

        ServerSideDataTable.build().id("idCol").text("textCol").numeric("numericCol").date("dateCol").bool("boolCol").done();

###Custom column types
###Value accessors
###Display converters
###Sorting by model/display