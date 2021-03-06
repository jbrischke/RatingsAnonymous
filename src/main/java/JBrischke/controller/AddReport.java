package JBrischke.controller;

import JBrischke.entity.*;
import JBrischke.persistence.GenericDao;
import JBrischke.persistence.PriceDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.util.Set;

/**
 * The type Add report.
 */
@WebServlet(
        urlPatterns = {"/AddReport"}
)

public class AddReport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenericDao<Game> gameGenericDao = new GenericDao<>(Game.class);
        GenericDao<Report> reportGenericDao = new GenericDao<>(Report.class);
        Logger logger = LogManager.getLogger(this.getClass());

        if (req.getParameter("submit").equals("Report")) {
            //grabs an id from a hidden input field and sets it in the game entity
            Game game = new Game();
            int id = Integer.parseInt(req.getParameter("inReportID"));
            game.setId(id);

            //entered values be added to the reports object set
            String reportDescription = req.getParameter("addReportOpinion");
            int hoursPlayed = Integer.parseInt(req.getParameter("addReportHoursPlayed"));

            //creates a report entity with the entered information and adds it to the set object in the game entity
            Report report = new Report(reportDescription, hoursPlayed);
            game.addReport(report);
            reportGenericDao.insert(report);


            //does an api pull and returns and sets a price object
            String apiName = req.getParameter("apiName");
            logger.debug("inside the add report part apiname" + apiName);

            PriceDao priceDao = new PriceDao();
            req.setAttribute("price", priceDao.getPrice(apiName));
            logger.debug("this is the price of minecraft " + priceDao.getPrice(apiName));


        }

        req.setAttribute("games", gameGenericDao.getById(Integer.parseInt(req.getParameter("inReportID"))));

        RequestDispatcher dispatcher = req.getRequestDispatcher("/report.jsp");
        dispatcher.forward(req, resp);
    }
}
