package quanmx.listener;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import quanmx.utils.DBHelper;

/**
 * Web application lifecycle listener.
 *
 * @author Dell
 */
public class MyAppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Deploying....");
        ServletContext context = sce.getServletContext();
        try {
            DBHelper.getSiteMaps(context);

        } catch (IOException ex) {
            context.log("MyAppServlet IO" + ex.getMessage());

        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Undeploy........................");

    }
}
