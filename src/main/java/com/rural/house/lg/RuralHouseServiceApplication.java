package com.rural.house.lg;

import com.mongodb.MongoClient;
import com.rural.house.lg.config.RuralHouseServiceConfiguration;
import com.rural.house.lg.db.BookingDao;
import com.rural.house.lg.db.impl.BookingDaoImpl;
import com.rural.house.lg.health_checks.DatabaseHealthCheck;
import com.rural.house.lg.resources.BookingResourceImpl;
import com.rural.house.lg.service.BookingService;
import com.rural.house.lg.service.impl.BookingServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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

        MongoClient mongoClient = new MongoClient(
                configuration.getApplicationConfig().getMongoDbConf().getUrl(),
                configuration.getApplicationConfig().getMongoDbConf().getPort());


        BookingDao bookingDaoImpl = new BookingDaoImpl(mongoClient, configuration.getApplicationConfig().getMongoDbConf());
        BookingService bookingServiceImpl = new BookingServiceImpl(bookingDaoImpl, configuration.getApplicationConfig().getRooms());

        environment.healthChecks().register("database", new DatabaseHealthCheck(mongoClient, configuration.getApplicationConfig().getMongoDbConf().getDatabase()));
        environment.jersey().register(new BookingResourceImpl(bookingServiceImpl));
    }

    @Override
    public String getName(){
        return SERVICE_RURAL_HOUSE;
    }
}
