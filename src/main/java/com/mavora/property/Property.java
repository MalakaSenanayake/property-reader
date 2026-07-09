package com.mavora.property;

interface Property {
    public static final String DATABASE_DEFAULT_BACKUP_PATH = "com.database.defaultBackupPath";
    public static final String DATABASE_SERVER_IP = "com.database.serverip";
    public static final String DATABASE_SERVER_PORT = "com.database.serverPort";
    public static final String DATABASE_SQL_DUMP_PATH = "com.database.sqlDumpPath";

    public static final String SERVICE_BASE_URL = "com.service.baseurl";

    public static final String APP_NAME = "com.application.name";
    public static final String SOFTWARE_VERSION = "com.application.version";

    public static final String BUSINESS_NAME = "business.detail.name";
    public static final String BUSINESS_ADDRESS = "business.detail.address";
    public static final String BUSINESS_CONTACT = "business.detail.contact";

    public static final String DEFAULT_DATE_RANGE = "date.range.default.months";
    public static final String DEFAULT_CHART_DATE_RANGE = "date.range.chart.months";
    public static final String INVOICE_SUMMARY_YEAR_RANGE = "invoice.summary.yearRange";
}
