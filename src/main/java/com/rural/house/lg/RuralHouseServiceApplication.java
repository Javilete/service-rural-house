package com.rural.house.lg;

import com.rural.house.lg.config.RuralHouseServiceConfiguration;
import com.rural.house.lg.db.BookingDao;
import com.rural.house.lg.db.impl.BookingDaoImpl;
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

        BookingDao bookingDaoImpl = new BookingDaoImpl(configuration.getApplicationConfig().getMongoDbConf());
        BookingService bookingServiceImpl = new BookingServiceImpl(bookingDaoImpl);

        environment.jersey().register(new BookingResourceImpl(bookingServiceImpl));
    }

    @Override
    public String getName(){
        return SERVICE_RURAL_HOUSE;
    }
}
