package com.rural.house.lg;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.rural.house.lg.config.RuralHouseServiceConfiguration;
import com.rural.house.lg.db.BookingDao;
import com.rural.house.lg.db.ReviewDao;
import com.rural.house.lg.db.impl.BookingDaoImpl;
import com.rural.house.lg.db.impl.ReviewDaoImpl;
import com.rural.house.lg.health_checks.DatabaseHealthCheck;
import com.rural.house.lg.resources.BookingResourceImpl;
import com.rural.house.lg.resources.ReviewResourceImpl;
import com.rural.house.lg.service.BookingService;
import com.rural.house.lg.service.ReviewService;
import com.rural.house.lg.service.impl.BookingServiceImpl;
import com.rural.house.lg.service.impl.ReviewServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import java.util.EnumSet;

public class RuralHouseServiceApplication extends Application<RuralHouseServiceConfiguration> {

    private static final String SERVICE_RURAL_HOUSE = "service-rural-house";

    public static void main(String[] args) throws Exception {
        new RuralHouseServiceApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<RuralHouseServiceConfiguration> bootstrap) {

    }

    @Override
    public void run(RuralHouseServiceConfiguration configuration, Environment environment) throws Exception {

        //Configure CORS
        configureCors(environment);

        //DB configuration
        MongoClientURI connectionString = new MongoClientURI(configuration.getApplicationConfig().getMongoDbConf().getUri());
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase(configuration.getApplicationConfig().getMongoDbConf().getDatabase());

        BookingDao bookingDaoImpl = new BookingDaoImpl(database);
        BookingService bookingServiceImpl = new BookingServiceImpl(bookingDaoImpl, configuration.getApplicationConfig().getRooms());

        ReviewDao reviewDaoImpl = new ReviewDaoImpl(database);
        ReviewService reviewServiceImpl = new ReviewServiceImpl(reviewDaoImpl);

        environment.healthChecks().register("database", new DatabaseHealthCheck(mongoClient, configuration.getApplicationConfig().getMongoDbConf().getDatabase()));
        environment.jersey().register(new BookingResourceImpl(bookingServiceImpl));
        environment.jersey().register(new ReviewResourceImpl(reviewServiceImpl));
    }

    @Override
    public String getName(){
        return SERVICE_RURAL_HOUSE;
    }

    private void configureCors(Environment environment) {
        Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,PATCH,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }
}
