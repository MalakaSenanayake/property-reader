package com.property;

class Print {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    public static void info(String message) {
        System.out.println("[" + BLUE + "INFO" + RESET + "] " + message);
    }

    public static void error(String message) {
        System.out.println("[" + RED + "ERROR" + RESET + "] " + message);
    }

    public static void warning(String message) {
        System.out.println("[" + YELLOW + "WARNING" + RESET + "] " + message);
    }

    public static void msgGreen(String message) {
        System.out.println("[" + BLUE + "INFO" + RESET + "] " + GREEN+message+RESET);
    }
    public static void msgBlue(String message) {
        System.out.println("[" + BLUE + "INFO" + RESET + "] " + BLUE+message+RESET);
    }
}
