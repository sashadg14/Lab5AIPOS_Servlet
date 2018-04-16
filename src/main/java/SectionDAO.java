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

    public List<Section> listAllSubections() throws SQLException {
        List<Section> sectionList = new ArrayList<>();

        String sql = "SELECT * FROM section";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        disconnect();

        while (resultSet.next()) {
            String section1 = resultSet.getString("name");
            String info = resultSet.getString("info");

            Section section = new Section(section1,info);
            sectionList.add(section);

        }

        resultSet.close();
        statement.close();


        return sectionList;
    }

    public List<String> listAllSubections(String mainSectionName) throws SQLException {
        List<String> sectionList = new ArrayList<>();

        String sql = "SELECT * FROM main_section WHERE name=\'"+mainSectionName+"\'";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String section1 = resultSet.getString("subsection_name");
            if(!section1.equals(""))
            sectionList.add(section1);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return sectionList;
    }

    public List<MainSection> listAllSections() throws SQLException {
        List<MainSection> sectionList = new ArrayList<>();

        String sql = "SELECT DISTINCT name FROM main_section";
        connect();
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String section1 = resultSet.getString("name");
            sectionList.add(new MainSection(section1));
        }

        resultSet.close();
        statement.close();
        disconnect();
        for(MainSection mainSection:sectionList)
            mainSection.setSections(listAllSubections(mainSection.getName()));
        return sectionList;
    }
/*
    public static void main(String[] args) throws SQLException {
        String jdbcURL =("jdbc:mysql://localhost:3306/jsreference");
        String jdbcUsername = ("root");
        String jdbcPassword = ("root");
        SectionDAO sectionDAO = new SectionDAO(jdbcURL, jdbcUsername, jdbcPassword);
        System.out.println(sectionDAO.listAllSections());
    }*/

    public Section getSection(String name) throws SQLException {
        Section section= null;
        String sql = "SELECT * FROM section WHERE name = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String section1 = resultSet.getString("name");
            String info = resultSet.getString("info");
            section = new Section(section1,info);
        }
        resultSet.close();
        statement.close();
        return section;
    }

    public void updateSection(Section section) throws SQLException {
        String sql = "UPDATE section SET name = ?, info = ?";
        sql += " WHERE name = ?";
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

    public void deleteSection(String section) throws SQLException {
        String sql = "DELETE FROM section where name = ?";
        String sql2 = "DELETE FROM main_section where subsection_name = ?";
        connect();
        //System.out.println(section);
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, section.trim());
        statement.executeUpdate();

        PreparedStatement statement2 = jdbcConnection.prepareStatement(sql2);
        statement2.setString(1, section.trim());
        statement2.executeUpdate();

        statement.close();
        statement2.close();
        disconnect();
    }

    public void deleteMainSection(String  section) throws SQLException {
        String sql = "DELETE FROM main_section where name = ?";
        connect();
        System.out.println(section);
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, section);
        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public void insertSection(String nameMain,Section section) throws SQLException {
        String sql = "INSERT INTO section (name, info) VALUES (?, ?)";
        String sql2 = "INSERT INTO main_section (name, subsection_name) VALUES (?, ?)";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, section.getSection());
        statement.setString(2, section.getInfo());
        statement.executeUpdate();

        statement = jdbcConnection.prepareStatement(sql2);
        statement.setString(1, nameMain);
        statement.setString(2, section.getSection());
        statement.executeUpdate();

        statement.close();
        disconnect();
    }

    public void insertMainSection(String name) throws SQLException {
        String sql = "INSERT INTO main_section (name, subsection_name) VALUES (?, ?)";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, "");
        statement.executeUpdate();
        statement.close();
        disconnect();
    }
}
