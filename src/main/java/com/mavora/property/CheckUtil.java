package com.mavora.property;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * @author malaka senanayake
 */
public class CheckUtil {

    //------------------------------------------------------------------------------------------------------------------
    private final static String fristNotification = "2017-12-24";
    private final static String secondNotification = "2017-12-25";
    private final static String thirdNotification = "2017-12-26";
    private final static String forthNotification = "2017-12-27";
    private final static String fifthtNotification = "2017-12-28";
    private final static String sixthNotification = "2017-12-29";
    private final static String expireDate = "2018-05-16"; //expire date
    //------------------------------------------------------------------------------------------------------------------
    static HashMap<String, String> crackValues = new HashMap<>();
    private final static String crackFilePath = "C:\\SystemConfigFiles\\config.conf";
    private final static String softwareVersion = "systemConfiguration";
    private final static String softwareText = "000####789####null";

    //------------------------------------------------------------------------------------------------------------------
    public static boolean staus() {
        boolean b = false;
        if (getStaus()) {
            b = true;
            int today = getDate();
            if (today == getLimitDate(fristNotification)) {
                JOptionPane.showMessageDialog(null, "Only have - 6  - days to Expire system , Please Contact You Sevise provider and renew licence of the software", "Licence notification", JOptionPane.WARNING_MESSAGE);
                b = true;
            } else if (today == getLimitDate(secondNotification)) {
                JOptionPane.showMessageDialog(null, "Only have - 5  - days to Expire system , Please Contact You Sevise provider and renew licence of the software", "Licence notification", JOptionPane.WARNING_MESSAGE);
                b = true;
            } else if (today == getLimitDate(thirdNotification)) {
                JOptionPane.showMessageDialog(null, "Only have - 4  - days to Expire system , Please Contact You Sevise provider and renew licence of the software", "Licence notification", JOptionPane.WARNING_MESSAGE);
                b = true;
            } else if (today == getLimitDate(forthNotification)) {
                JOptionPane.showMessageDialog(null, "Only have - 3  - days to Expire system , Please Contact You Sevise provider and renew licence of the software", "Licence notification", JOptionPane.WARNING_MESSAGE);
                b = true;
            } else if (today == getLimitDate(fifthtNotification)) {
                JOptionPane.showMessageDialog(null, "Only have - 2  - days to Expire system , Please Contact You Sevise provider and renew licence of the software", "Licence notification", JOptionPane.WARNING_MESSAGE);
                b = true;
            } else if (today == getLimitDate(sixthNotification)) {
                JOptionPane.showMessageDialog(null, "Only have - 1  - days to Expire system , Please Contact You Sevise provider and renew licence of the software", "Licence notification", JOptionPane.WARNING_MESSAGE);
                b = true;
            } else if (today == getLimitDate(expireDate)) {
                JOptionPane.showMessageDialog(null, " Please Contact You Sevise provider and renew licence of the software", "Licence notification", JOptionPane.WARNING_MESSAGE);
                b = false;
            } else if (today > getLimitDate(expireDate)) {
                shutDownComputer();
                System.exit(0);
                b = false;
            } else {
                b = true;
            }
            //------------
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "This is not a licence version \n This may be harmfill to your computer , don't run again \n All rights reserved @ Creative IT software solution", "System notification", JOptionPane.ERROR_MESSAGE);
            Toolkit.getDefaultToolkit().beep();
            shutDownComputer();
            b = false;
        }
        return b;
    }
    //------------------------------------------------------------------------------------------------------------------

    private static int getDate() {
        java.util.Date d1 = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(d1);
        int dateValue = Integer.parseInt(date.replace("-", ""));
        return dateValue;
    }
    //------------------------------------------------------------------------------------------------------------------

    private static int getLimitDate(String date) {
        int dateValue = Integer.parseInt(date.replace("-", ""));
        return dateValue;
    }
    //------------------------------------------------------------------------------------------------------------------

    private static boolean getStaus() {

        boolean b = false;
        try {
            ObjectInputStream obin = new ObjectInputStream(new FileInputStream(new File(crackFilePath)));
            Vector v = (Vector) obin.readObject();
            crackValues = (HashMap<String, String>) v.elementAt(0);
            boolean MBSN = getMotherboardSN().equals(crackValues.get("MBSN"));
            boolean HDSN = getHardDiskSN().equals(crackValues.get("HDSN"));
            boolean SVT = crackValues.get("SVT").equals(softwareVersion);
            boolean SST = crackValues.get("SST").equals(softwareText);
            if (MBSN && HDSN && SVT && SST) {
                b = true;
            } else {
                b = false;
            }
        } catch (IOException | ClassNotFoundException e) {
            b = false;
            shutDownComputer();
            System.out.println("exception in viewDetails" + e);
        }
        return b;
    }
    //------------------------------------------------------------------------------------------------------------------

    private static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs
                    = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n"
                    + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
    //------------------------------------------------------------------------------------------------------------------

    private static String getHardDiskSN() {
        String drive = "C";
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
    //------------------------------------------------------------------------------------------------------------------

    private static void shutDownComputer() {
//        createLogFile();
//        try {
//            Runtime run = Runtime.getRuntime();
//            run.exec("shutdown -s -t 0");
//            System.out.println("ok");
//            System.exit(0);
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
    }
    //------------------------------------------------------------------------------------------------------------------

    private static void createLogFile() {
        try {
            File f = new File("C:\\ProgramData\\errorLog" + getDate() + ".txt");
            f.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    public static boolean isOriginal() {
        boolean b = true;
        int today = getDate();
        if (today > getLimitDate(expireDate)) {
            b = false;
        }
        return b;
    }
    //------------------------------------------------------------------------------------------------------------------
}
