import authoriztion_utils.AuthorizationGitHub;
import authoriztion_utils.AuthorizationVk;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class RequestOperations {
    SectionDAO sectionDAO;

    public RequestOperations(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    String currMainSection;

    public void listMainSection(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<MainSection> listSection = sectionDAO.listAllSections();
        request.setAttribute("listMainSection", listSection);
        RequestDispatcher dispatcher = request.getRequestDispatcher("MainSection.jsp");
        dispatcher.forward(request, response);
    }

    public void listSection(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<MainSection> listMainSection = sectionDAO.listAllSections();
        String name = request.getParameter("name");

        currMainSection = name;

        for (MainSection mainSection : listMainSection)
            if (mainSection.getName().equals(name))
                request.setAttribute("listSection", mainSection.getSections());
        RequestDispatcher dispatcher = request.getRequestDispatcher("SectionList.jsp");
        dispatcher.forward(request, response);
    }

    public void getSectionInfo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        Section section = sectionDAO.getSection(name);
        RequestDispatcher dispatcher = request.getRequestDispatcher("SectionInfo.jsp");
        request.setAttribute("section", section);
        dispatcher.forward(request, response);
    }

    public void updateSection(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String info = request.getParameter("info");
        System.out.println(info);
        Section section = new Section(name.trim(), info.trim());
        sectionDAO.updateSection(section);
        response.sendRedirect("/");
    }


    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("NewSectionForm.jsp");
        dispatcher.forward(request, response);
    }

    public void showNewMainForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("NewMainSectionForm.jsp");
        dispatcher.forward(request, response);
    }

    public void showAuth(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Auth.jsp");
        dispatcher.forward(request, response);
    }

    public void insertSection(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String info = request.getParameter("info");
        Section section = new Section(name.trim(), info.trim());

        sectionDAO.insertSection(currMainSection, section);
        response.sendRedirect("/");
    }

    public void insertMainSection(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        sectionDAO.insertMainSection(name);
        response.sendRedirect("/");
    }

    public void deleteSection(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        sectionDAO.deleteSection(name);
        response.sendRedirect("/");
    }

    public void deleteMainSection(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        //System.out.println(name);
        sectionDAO.deleteMainSection(name);
        response.sendRedirect("/");
    }

    public void vkAUTH(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        new AuthorizationVk().execute(response);
    }

    public void gitAUTH(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        new AuthorizationGitHub().execute(response);
    }

    public void showHomePage(HttpServletRequest request, HttpServletResponse response)
            throws  IOException, ServletException {
        String code = request.getParameter("code");
        String type = request.getParameter("type");
        System.out.println(code);
        //System.out.println(type);
        String name = "";
        switch (type) {
            case "vk":
                name=new AuthorizationVk().getName(code);
                break;
            case "github":
                name=new AuthorizationGitHub().getName(code);
        }
        System.out.println(name);
        request.getSession().setAttribute("name",name);
        request.setAttribute("userName", name);
        request.getRequestDispatcher("Home.jsp").forward(request, response);
    }
}
