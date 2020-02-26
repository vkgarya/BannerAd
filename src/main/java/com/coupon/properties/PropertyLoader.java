package com.coupon.properties;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author customfurnish.com
 */
public final class PropertyLoader {
    public Properties getProperties() throws Exception {
        InputStream input = null;
        Properties properties = new Properties();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@ File : " + getClass().getClassLoader().getResource("application.properties").getFile());
        input = getClass().getClassLoader().getResourceAsStream("application.properties");
        if (input != null) {
            properties.load(input);
        } else {
            throw new FileNotFoundException("Application.properties doesn't exist");
        }
        return properties;
    }
}
