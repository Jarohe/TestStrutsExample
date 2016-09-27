package common.listeners;

import common.db.dao.DaoFactory;
import common.db.dao.UserDaoFactoryImpl;
import org.apache.commons.dbcp.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class DbServletContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        String dbUrl = context.getInitParameter("dbUrl");
        String dbUser = context.getInitParameter("dbUser");
        String dbPassword = context.getInitParameter("dbPassword");
        String dbDriver = context.getInitParameter("dbDriver");

        BasicDataSource db = new BasicDataSource();
        db.setDriverClassName(dbDriver);
        db.setUrl(dbUrl);
        db.setUsername(dbUser);
        db.setPassword(dbPassword);

        db.setDefaultAutoCommit(false);
        context.setAttribute("db",db);

        DaoFactory factory = new UserDaoFactoryImpl();
        context.setAttribute("daoFactory", factory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
