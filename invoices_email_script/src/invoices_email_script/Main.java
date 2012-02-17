package invoices_email_script;

import au.com.bytecode.opencsv.CSVReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ini4j.Wini;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Muhammad Usman
 * @date modified 13-05-2011
 * @version 2.1
 */
public class Main {

    String invoicesDirectory = "";//directory of customer invoices
    String csvFileName = "";//csv containg costumer details
    String emailSubject = "";//email Subject
    int fileCount = 0;
    int total_threads = 0;
    int emailCounter = 0;
    long startTimeMs = 0;
    String test_scenario = "";//whether to send it to own ID for testing
    String test_receiver_id = "";//test invoices reciever ID
    int no_of_test_invoices = 0;//total number of test invoices to be sent
    static int total_email_sent = 0;//total number of email sent
    static int total_mail_not_send = 0;//total failures while email sending
    static int total_cust_id_not_found = 0;//keep track of Customer ID's not found in csv
    BufferedWriter writer;
    FileWriter out = null;
    FileWriter fstream = null;
    String logs_directory = null;
    String log_file = null;

    public static void main(String[] args) {
        try {
            Main mainObj = new Main();
            try {
                //to read credentials from configration.ini
                Wini configObj = new Wini(new File("Configration.ini"));
                mainObj.invoicesDirectory = configObj.get("Customer_Invoices_email", "invoicesDirectory");
                mainObj.csvFileName = configObj.get("Customer_Invoices_email", "csvFileName");
                mainObj.emailSubject = configObj.get("Customer_Invoices_email", "emailSubject");
                mainObj.test_scenario = configObj.get("Customer_Invoices_email", "test_scenario");
                mainObj.test_receiver_id = configObj.get("Customer_Invoices_email", "test_receiver_id");
                mainObj.no_of_test_invoices = Integer.parseInt(configObj.get("Customer_Invoices_email", "no_of_test_invoices"));
                mainObj.total_threads = Integer.parseInt(configObj.get("Customer_Invoices_email", "total_threads"));
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
            mainObj.sendInvoices();
        } catch (InterruptedException ex) {
            System.out.println("Thread exception occured..");
        }
    }

    private void sendInvoices() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        logs_directory = "logs-" + getCurrentDate();
        log_file = logs_directory + "/logfile.txt";
        boolean dirStatus = createDirectoryIfNeeded(logs_directory);
        if (dirStatus == true) {
            try {
                fstream = new FileWriter(log_file, true);
                writer = new BufferedWriter(fstream);
            } catch (IOException ex) {
                System.out.println("Exception in opening file for writing logs.");
                System.out.println(ex.toString());
            }
        } else {
            System.out.println("Enable to create directory for logging file..Please try again.");
        }
        System.out.println("Script started time : " + getCurrentDateTime());
        writeToLog("Script started time : " + getCurrentDateTime());

        String[] fileNames = readDirectoryFileNames(invoicesDirectory);//read all the invoices file names
        if (fileNames != null) {
            fileCount = fileNames.length;//number of invoices
            System.out.println("Number of invoices in directory \" " + invoicesDirectory + " \" are " + fileCount);
            writeToLog("Number of invoices in directory \" " + invoicesDirectory + " \" are " + fileCount);
            try {
                CSVReader reader = new CSVReader(new FileReader(csvFileName));//to read csv file
                List records = new ArrayList<String[]>();
                records = reader.readAll();
                int totalRecords = records.size();
                System.out.println("Number of records in csv file \" " + csvFileName + " \" are " + totalRecords);
                writeToLog("Number of records in csv file \" " + csvFileName + " \" are " + totalRecords);
                System.out.println("Total threads are " + total_threads);
                writeToLog("Total threads are " + total_threads);

                if (test_scenario.equals("true")) {
                    totalRecords = no_of_test_invoices;
                } else if (test_scenario.equals("false")) {
                } else {
                    System.out.println("Please select between true and false");
                    writeToLog("Please select between true and false");
                    System.exit(0);
                }

                if (total_threads > totalRecords) {
                    System.out.println("no of threads should be less than total records in csv");
                    writeToLog("no of threads should be less than total records in csv");
                    System.exit(0);
                }else if (total_threads > fileCount) {
                    System.out.println("no of threads should be less than total invoices");
                    writeToLog("no of threads should be less than total invoices");
                    System.exit(0);
                }

                int divider = totalRecords / total_threads;
                int[][] indexArr = new int[total_threads][2];

                int startIndex = 0;
                int endIndex = -1;

                for (int i = 0; i < total_threads; i++) {
                    startIndex = endIndex + 1;
                    endIndex = endIndex + divider;
                    indexArr[i][0] = startIndex;
                    indexArr[i][1] = endIndex;

                }

                indexArr[total_threads - 1][1] = totalRecords - 1;


                for (int a = 0; a < total_threads; a++) {
                    System.out.println("Invoices starting from " + indexArr[a][0] + " to " + indexArr[a][1] + " are handed over to thread " + a);
                    writeToLog("Invoices starting from " + indexArr[a][0] + " to " + indexArr[a][1] + " are handed over to thread " + a);
                }

                invoiceSender[] senderObj = new invoiceSender[total_threads];

                Thread[] threadsObj = new Thread[total_threads];

                for (int j = 0; j < senderObj.length; j++) {
                    senderObj[j] = new invoiceSender(indexArr[j][0], indexArr[j][1], records, fileNames, String.valueOf(j));
                }

                for (int j = 0; j < senderObj.length; j++) {
                    threadsObj[j] = new Thread(senderObj[j]);
                    threadsObj[j].start();
                }

                for (int j = 0; j < senderObj.length; j++) {
                    threadsObj[j].join();
                }
                long endTime = System.currentTimeMillis();
                float elapsedTime = ((endTime - startTime) / 1000) / 60;

                System.out.println("Total Customer ID's Not found are " + total_cust_id_not_found);
                writeToLog("Total Customer ID's Not found are " + total_cust_id_not_found);
                System.out.println("Total mail failures are " + total_mail_not_send);
                writeToLog("Total mail failures are " + total_mail_not_send);
                System.out.println("Script ended time : " + getCurrentDateTime());
                writeToLog("Script ended time : " + getCurrentDateTime());
                System.out.println("Total time taken in minutes " + Float.toString(elapsedTime));
                writeToLog("Total time taken in minutes " + Float.toString(elapsedTime));
                closeLogFile();

            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        } else {
            System.out.println("try entering a valid directory");
            writeToLog("try entering a valid directory");
        }

    }//end of sendEmail method

    private String[] readDirectoryFileNames(String directory) {//to read all filenames of invoices
        try {
            File in = new File(directory);
            if (in.isDirectory()) {//checking if directory exists
                String[] childFiles = in.list();//all files in the directory
                return childFiles;
            } else {//if the directory name is not valid or its a filename
                System.out.println(directory + " is not a valid directory.");
                //    log.info(directory + " is not a valid directory.");
                return null;
            }

        } catch (Exception e) {
            //  log.info(e.toString());
            System.out.println(e.toString());
            //e.printStackTrace();
            return null;
        }
    }

    private synchronized boolean createDirectoryIfNeeded(String directoryName) {
        File dir = new File(directoryName);
        // if the directory does not exist, create it
        if (!dir.exists()) {
            System.out.println("creating directory: " + directoryName);
            boolean dirFlag = dir.mkdir();
            if (dirFlag == true) {
                return true;
            } else {
                return false;
            }
        } else {//if directory exists
            return true;
        }
    }

    private synchronized String getCurrentDateTime() {
        final String DATE_FORMAT_NOW = "dd MMM yyyy HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    private synchronized String getCurrentDate() {
        final String DATE_FORMAT_NOW = "dd-MMM-yyyy";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    private synchronized void writeToLog(String log_message) {
        String timeStamp = getCurrentDateTime();
        String message = null;
        try {
            message = timeStamp + " - " + log_message + "\n";
            writer.append(message);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    private synchronized void closeLogFile() {
        try {
            writer.close();

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
