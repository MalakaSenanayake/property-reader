package com.mavora.property;

import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyReader {

    //-folder names ----------------------------------------------------------------------------------------------------
    private static final String CONFIG_FOLDER_NAME = "config";
    private static final String LOG_FOLDER_NAME = "logs";
    private static final String LICENSE_FOLDER_NAME = "license";
    //-file names-------------------------------------------------------------------------------------------------------
    private static final String PROPERTY_FILE_NAME = "config.properties";
    private static final String LOG4J_PROPERTY_FILE_NAME = "log4j.properties";
    private static final String LICENSE_FILE_NAME = "mavora.lic";
    //--public Properties-----------------------------------------------------------------------------------------------
    public static final String CONFIG_FOLDER_PATH;
    public static final String LOGS_FOLDER_PATH;
    public static final String LICENSE_FOLDER_PATH;
    public static final String LOG4J_PROPERTY_FILE_PATH;

    public static String DATABASE_DEFAULT_BACKUP_PATH;
    public static  String DATABASE_SERVER_IP;
    public static  String DATABASE_SERVER_PORT;
    public static  String DATABASE_SQL_DUMP_PATH;

    public static  String SERVICE_BASE_URL;

    public static  String APP_NAME;
    public static  String SOFTWARE_VERSION;

    public static  String BUSINESS_NAME;
    public static  String BUSINESS_ADDRESS;
    public static  String BUSINESS_CONTACT;

    public static  String DEFAULT_DATE_RANGE;
    public static  String DEFAULT_CHART_DATE_RANGE;
    public static  String INVOICE_SUMMARY_YEAR_RANGE;
    public static  String INVOICE_IGNORE_BALANCE;
    public static  String SUMMARY_MONTH_RANGE;


    private static String relativePath;

    //------------------------------------------------------------------------------------------------------------------
    static {
        Print.info("------------------------------------------------------------------------");
        getRelativePath();
        CONFIG_FOLDER_PATH = relativePath + "/" + CONFIG_FOLDER_NAME;
        LOG4J_PROPERTY_FILE_PATH = CONFIG_FOLDER_PATH + "/" + LOG4J_PROPERTY_FILE_NAME;
        createConfigFolder();
        createLogfilesFolder();
        createLicenseFolder();
        //--------------------------------------------------------------------------------------------------------------
        LOGS_FOLDER_PATH = CONFIG_FOLDER_PATH + "/" + LOG_FOLDER_NAME;
        LICENSE_FOLDER_PATH = CONFIG_FOLDER_PATH + "/" + LICENSE_FOLDER_NAME;
        //-Property file------------------------------------------------------------------------------------------------
        readPropertyFile();
        log4jConfigure();
        Print.info("------------------------------------------------------------------------");
        Print.msgGreen("PROPERTY FILE CONFIGURED");
        Print.info("------------------------------------------------------------------------");
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String getRelativePath() {
        try {
            relativePath = new File("").getCanonicalPath();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "config folder is not found");
            System.err.println("[ERROR] Exception in get relative path " + ex);
            System.exit(0);
        }
        return relativePath;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void readPropertyFile() {
        try {
            FileReader propertyFileReader = new FileReader(CONFIG_FOLDER_PATH + "/" + PROPERTY_FILE_NAME);
            Properties properties = new Properties();
            properties.load(propertyFileReader);
            // ---------------------------------------------------------------------------------------------------------
            DATABASE_DEFAULT_BACKUP_PATH = properties.getProperty(Property.DATABASE_DEFAULT_BACKUP_PATH);
            DATABASE_SERVER_IP = properties.getProperty(Property.DATABASE_SERVER_IP);
            DATABASE_SQL_DUMP_PATH =properties.getProperty(Property.DATABASE_SQL_DUMP_PATH);
            DATABASE_SERVER_PORT = properties.getProperty(Property.DATABASE_SERVER_PORT);
            SERVICE_BASE_URL = properties.getProperty(Property.SERVICE_BASE_URL);
            APP_NAME = properties.getProperty(Property.APP_NAME);
            SOFTWARE_VERSION = properties.getProperty(Property.SOFTWARE_VERSION);
            BUSINESS_NAME = properties.getProperty(Property.BUSINESS_NAME);
            BUSINESS_ADDRESS = properties.getProperty(Property.BUSINESS_ADDRESS);
            BUSINESS_CONTACT = properties.getProperty(Property.BUSINESS_CONTACT);
            DEFAULT_DATE_RANGE = properties.getProperty(Property.DEFAULT_DATE_RANGE);
            DEFAULT_CHART_DATE_RANGE = properties.getProperty(Property.DEFAULT_CHART_DATE_RANGE);
            INVOICE_SUMMARY_YEAR_RANGE = properties.getProperty(Property.INVOICE_SUMMARY_YEAR_RANGE);
            INVOICE_IGNORE_BALANCE = properties.getProperty(Property.INVOICE_IGNORE_BALANCE);
            SUMMARY_MONTH_RANGE = properties.getProperty(Property.SUMMARY_MONTH_RANGE);
            //----------------------------------------------------------------------------------------------------------

        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] "+ex);
            JOptionPane.showMessageDialog(null, "Configurations file cannot be found !!",
                    "Error message", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (IOException ex) {
            System.err.println("[ERROR] "+ex);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void createConfigFileInConfigFolder() {
        OutputStream os = null;
        Properties prop = new Properties();
        prop.setProperty(Property.DATABASE_DEFAULT_BACKUP_PATH, "");
        prop.setProperty(Property.DATABASE_SERVER_IP, "127.0.0.1");
        prop.setProperty(Property.DATABASE_SQL_DUMP_PATH, "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin");
        prop.setProperty(Property.DATABASE_SERVER_PORT, "3306");
        prop.setProperty(Property.SERVICE_BASE_URL, "");
        prop.setProperty(Property.APP_NAME, "App");
        prop.setProperty(Property.SOFTWARE_VERSION, "1.0.0");
        prop.setProperty(Property.BUSINESS_NAME, "Business Name");
        prop.setProperty(Property.BUSINESS_ADDRESS, "Gampaha");
        prop.setProperty(Property.BUSINESS_CONTACT, "0770000000");
        prop.setProperty(Property.DEFAULT_DATE_RANGE, "3");
        prop.setProperty(Property.DEFAULT_CHART_DATE_RANGE, "12");
        prop.setProperty(Property.INVOICE_SUMMARY_YEAR_RANGE, "1");
        prop.setProperty(Property.INVOICE_IGNORE_BALANCE, "1");
        prop.setProperty(Property.SUMMARY_MONTH_RANGE, "24");
        try {
            os = Files.newOutputStream(Paths.get(CONFIG_FOLDER_PATH + "/" + PROPERTY_FILE_NAME));
            prop.store(os, "Application Property File");
            os.close();
            Print.info("Created config file in config folder");
        } catch (Exception e) {
            System.err.println("[ERROR] "+e);
            System.exit(0);
        }finally {
            try {
                if (os != null) os.close();
            } catch (Exception ignore) {}
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void createConfigFolder() {
        File f = new File(relativePath + "/" + CONFIG_FOLDER_NAME);
        if (f.mkdir()) {
            Print.info("Config folder is created.");
            createConfigFileInConfigFolder();
        } else {
           Print.info("Config folder already exists.");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void createLogfilesFolder() {
        File f = new File(CONFIG_FOLDER_PATH + "/" + LOG_FOLDER_NAME);
        if (f.mkdir()) {
            Print.info("Log folder is created.");
            createLog4JConfigFileInConfigFolder();
        } else {
            Print.info("Logs folder already exists.");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void createLicenseFolder() {
        File f = new File(CONFIG_FOLDER_PATH + "/" + LICENSE_FOLDER_NAME);
        if (f.mkdir()) {
            Print.info("License folder is created.");
            createLicenseFileInLicenseFolder();
        } else {
            Print.info("License folder already exists.");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
   
    private static void createLog4JConfigFileInConfigFolder() {
        OutputStream os = null;
        Properties prop = new Properties();
        prop.setProperty("log4j.rootLogger", "INFO, file, errorfile");
        prop.setProperty("log4j.appender.file", "org.apache.log4j.RollingFileAppender");
        prop.setProperty("log4j.appender.file.File", CONFIG_FOLDER_PATH + "/" + LOG_FOLDER_NAME + "/Logs.log");
        prop.setProperty("log4j.appender.file.MaxFileSize", "10MB");
        prop.setProperty("log4j.appender.file.MaxBackupIndex", "10");
        prop.setProperty("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
        prop.setProperty("log4j.appender.file.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
        prop.setProperty("log4j.appender.errorfile", "org.apache.log4j.RollingFileAppender");
        prop.setProperty("log4j.appender.errorfile.Threshold", "ERROR");
        prop.setProperty("log4j.appender.errorfile.File", CONFIG_FOLDER_PATH + "/" + LOG_FOLDER_NAME + "/ErrorLogs.log");
        prop.setProperty("log4j.appender.errorfile.MaxFileSize", "10MB");
        prop.setProperty("log4j.appender.errorfile.MaxBackupIndex", "10");
        prop.setProperty("log4j.appender.errorfile.layout", "org.apache.log4j.PatternLayout");
        prop.setProperty("log4j.appender.errorfile.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
        try {
            os = Files.newOutputStream(Paths.get(LOG4J_PROPERTY_FILE_PATH));
            prop.store(os, "Log4j Property File");
            os.close();
            Print.info("Created log4j property file in Logs folder");
        } catch (Exception e) {
            System.err.println("[ERROR] "+e);
            System.exit(0);
        }finally {
            try {
                if (os != null) os.close();
            } catch (Exception ignore) {}
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void createLicenseFileInLicenseFolder() {
        try {
            File f = new File(CONFIG_FOLDER_PATH + "/" + LICENSE_FOLDER_NAME + "/" + LICENSE_FILE_NAME);
            if (f.createNewFile()) {
                Print.info("License file is created in License folder");
            }
        } catch (Exception e) {
            System.err.println("[ERROR] "+e);
            System.exit(0);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void log4jConfigure() {
        PropertyConfigurator.configure(LOG4J_PROPERTY_FILE_PATH);
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void initialize() {
    }
    //------------------------------------------------------------------------------------------------------------------
}
