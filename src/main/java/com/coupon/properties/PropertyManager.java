package com.coupon.properties;

import java.util.Properties;

/**
 * @author customfurnish.com
 */
public final class PropertyManager {
    private static Properties properties = null;

    /**
     * Its a private constructor.
     */
    private PropertyManager() {

    }

    private static String getProperty(final String name) {
        try {
            if (properties == null) {
                properties = new PropertyLoader().getProperties();
            }
            if (properties.containsKey(name)) {
                return properties.getProperty(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Long getTokenExpirationMsec() {
        return Long.parseLong(getProperty("auth.tokenExpirationMsec"));
    }

    public static String getTokenSecret() {
        return getProperty("auth.tokenSecret");
    }

    public static String getCampaignRedirectionAPI() {
        return getProperty("campaign.redirection.api");
    }
    public static String getCampaignNativeAPI() {
        return getProperty("campaign.native.api");
    }
    public static String getAllowedCORSOrigins() {
        return getProperty("campaign.allow.cross.origin.hosts");
    }
    public static String getAllowedCORSOrigin() {
        return getProperty("campaign.allow.cross.origin.host");
    }
    public static String getAllowedCORSOriginMethods() {
        return getProperty("campaign.allow.cross.origin.methods");
    }
    public static String[] getAllowedCORSOriginPath() {
        return getProperty("campaign.allow.cross.origin.path").split(",");
    }

    public static String getS3AssetsURL() {
        return getProperty("campaign.assets.url.prefix");
    }

    public static String getWebUrl() {
        return getProperty("campaign.web.url");
    }
}
