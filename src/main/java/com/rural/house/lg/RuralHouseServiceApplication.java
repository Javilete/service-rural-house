package com.rural.house.lg;

import com.rural.house.lg.config.RuralHouseServiceConfiguration;
import com.rural.house.lg.db.BookingDao;
import com.rural.house.lg.resources.BookingResourceImpl;
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

        BookingDao bookingDao = new BookingDao(configuration.getApplicationConfig().getMongoDbConf());

        environment.jersey().register(new BookingResourceImpl(bookingDao));
    }

    @Override
    public String getName(){
        return SERVICE_RURAL_HOUSE;
    }
}
