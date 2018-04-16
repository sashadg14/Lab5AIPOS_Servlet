import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public SectionDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public List<Section> listAllSections() throws SQLException {
        List<Section> sectionList = new ArrayList<>();

        String sql = "SELECT * FROM section";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String section1 = resultSet.getString("section");
            String info = resultSet.getString("info");

            Section section = new Section(section1,info);
            sectionList.add(section);

        }

        resultSet.close();
        statement.close();

        disconnect();

        return sectionList;
    }

    public Section getSection(String name) throws SQLException {
        Section section= null;
        String sql = "SELECT * FROM section WHERE section = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String section1 = resultSet.getString("section");
            String info = resultSet.getString("info");
            section = new Section(section1,info);
        }
        resultSet.close();
        statement.close();
        return section;
    }

    public void updateSection(Section section) throws SQLException {
        String sql = "UPDATE section SET section = ?, info = ?";
        sql += " WHERE section = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, section.getSection());
        statement.setString(2, section.getInfo());
        statement.setString(3, section.getSection());
        statement.executeUpdate();
       // System.out.println("-"+section.getSection()+"-"+"-"+section.getInfo()+"-");
        statement.close();
        disconnect();
    }

    public boolean deleteSection(String  section) throws SQLException {
        String sql = "DELETE FROM section where section = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, section);

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }

    public void insertSection(Section section) throws SQLException {
        String sql = "INSERT INTO section (section, info) VALUES (?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, section.getSection());
        statement.setString(2, section.getInfo());
        statement.executeUpdate();
      //  System.out.println("-"+section.getSection()+"-"+"-"+section.getInfo()+"-");
        statement.close();
        disconnect();
    }
}
