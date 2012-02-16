package invoices_email_script;

import emailManager.emailSender;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.ini4j.Wini;

/**
 * @author Muhammad Usman
 * @date modified 11-04-2011
 * @version 2.0
 */
public class invoiceSender implements Runnable {

    int startingIndex, endingIndex;
    List records;
    String[] invoices_filenames;
    String invoiceDirectory;
    emailSender emailObj;
    String thread_name;
    String log_file_name, dir_name;
    BufferedWriter writer;
    FileWriter out = null;
    FileWriter fstream = null;
    int emailCounter = 0;
    String test_scenario = "";//whether to send it to own ID for testing
    String test_receiver_id = "";//test invoices reciever ID
    int no_of_test_invoices = 0;//total number of test invoices to be sent
    String emailSubject = null;
    int reattemptCount = 0;

    // Main mainObj = new Main();
    public invoiceSender(int startIndex, int endIndex, List recordsList, String[] filenames, String threadName) {

        try {
            startingIndex = startIndex;
            endingIndex = endIndex;
            records = recordsList;
            invoices_filenames = filenames;
            emailObj = new emailSender();
            thread_name = threadName;
            dir_name = "logs-" + getCurrentDate();
            log_file_name = dir_name + "/logfile_thread_" + threadName + ".txt";


            try {
                //to read credentials from configration.ini
                Wini configObj = new Wini(new File("Configration.ini"));
                invoiceDirectory = configObj.get("Customer_Invoices_email", "invoicesDirectory");
                test_scenario = configObj.get("Customer_Invoices_email", "test_scenario");
                test_receiver_id = configObj.get("Customer_Invoices_email", "test_receiver_id");
                no_of_test_invoices = Integer.parseInt(configObj.get("Customer_Invoices_email", "no_of_test_invoices"));
                emailSubject = configObj.get("Customer_Invoices_email", "emailSubject");
                reattemptCount = Integer.parseInt(configObj.get("Customer_Invoices_email", "reattempt_count"));

            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

            boolean dirStatus = createDirectoryIfNeeded(dir_name);
            if (dirStatus == true) {
                fstream = new FileWriter(log_file_name, true);
                writer = new BufferedWriter(fstream);
            } else {
                System.out.println("Enable to create directory for logging file..Please try again.");
            }

        } catch (IOException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(invoiceSender.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean createDirectoryIfNeeded(String directoryName) {
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

    private synchronized String searchInvoice(String custID, String[] fileNames) {
        int fileCount = fileNames.length;
        int flag = 0;
        if (custID.equals("") || custID == null) {
            System.out.println("Customer ID is either empty or null");
            writeToLog("Customer ID is either empty or null");
            return null;
        }
        for (int j = 0; j < fileCount; j++) {
            String[] custDetails = fileNames[j].split("_");//splitting ivoice name
            if (custDetails[0].equals(custID)) {//if cust ID of user in csv and invoive name matches
                flag = 1;
                return fileNames[j];
            }
        }
        if (flag == 0) {
            System.out.println("* " + Main.total_email_sent + " * - " + "Invoice not found for Customer " + custID);
            writeToLog("* " + Main.total_email_sent + " * - " + "Invoice not found for Customer " + custID);
            return null;
        }
        return null;
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

    private synchronized void incrementTotalSent() {
        Main.total_email_sent++;
    }

    private synchronized void incrementFailureCount() {
        Main.total_mail_not_send++;
    }

    private synchronized int getFailureCount() {
        return Main.total_mail_not_send;
    }

    private synchronized void incrementCIDsNotFound() {
        Main.total_cust_id_not_found++;
    }

    private synchronized int getCIDsNotFound() {
        return Main.total_cust_id_not_found;
    }

    public void run() {
        writeToLog("From " + startingIndex + " to " + endingIndex);
        for (int i = startingIndex; i <= endingIndex && !Thread.interrupted(); i++) {
            String[] record = (String[]) records.get(i);
            String customerID = record[0];
            String name = record[1] + " " + record[2];
            String email = record[3];
            if (customerID == null || customerID.equals("")) {
                if (customerID == null) {
                    System.out.println("* " + Main.total_email_sent + " * - " + "Customer ID is null...Please check.");
                    writeToLog("* " + Main.total_email_sent + " * - " + "Customer ID is null...Please check.");
                } else if (customerID.equals("")) {
                    System.out.println("* " + Main.total_email_sent + " * - " + "Customer ID is empty...Please check.");
                    writeToLog("* " + Main.total_email_sent + " * - " + "Customer ID is empty...Please check.");
                }
                incrementCIDsNotFound();
                System.out.println("* " + Main.total_email_sent + " * - " + " Total Customer ID's not found are " + getCIDsNotFound());
                writeToLog("* " + Main.total_email_sent + " * - " + " Total Customer ID's not found are " + getCIDsNotFound());
                continue;
            }

            int flag = 1;

            String invoice_file = searchInvoice(customerID, invoices_filenames);
            if (invoice_file != null) {
                String directory = invoiceDirectory + "/" + invoice_file;//customer invoice directory
                String messageContent = getFileContents(directory);//invoice html content
                String emailStatus = null;
                String email_address = "";
                if (messageContent != null) {
                    if (test_scenario.equals("true")) {
                        email_address = test_receiver_id;
                    } else if (test_scenario.equals("false")) {
                        email_address = email;
                    } else {
                        System.out.println("please select between true and false");
                        writeToLog("please select between true and false");
                    }

                    try {
                        emailStatus = emailObj.sendEmail(emailSubject, messageContent, email_address, name);
                        if (emailStatus.equals("sent")) {
                            incrementTotalSent();
                            writeToLog("* " + Main.total_email_sent + " * - " + "Username : " + name + " , email address : " + email_address + " , cutomer ID : " + customerID + " , invoice file : " + invoice_file);
                            System.out.println("* " + Main.total_email_sent + " * - " + "Username : " + name + " , email address : " + email_address + " , cutomer ID : " + customerID + " , invoice file : " + invoice_file);
                        } else {
                            writeToLog("* " + Main.total_email_sent + " * - " + "Exception while sending to " + customerID + " - " + name + " - " + emailStatus);
                            System.out.println("* " + Main.total_email_sent + " * - " + "Exception while sending to " + customerID + " - " + name + " - " + emailStatus);
                        }
                    } catch (MessagingException ex) {
                        flag = 2;
                        for (int counter = 0; counter < reattemptCount && !Thread.interrupted(); counter++) {
                            try {
                                System.out.println("Attempt # " + flag + " : to send email to " + customerID + " at " + email_address);
                                writeToLog("Attempt # " + flag + " : to send email to " + customerID + " at " + email_address);
                                emailStatus = emailObj.sendEmail(emailSubject, messageContent, email_address, name);
                                System.out.println("Attempt # " + flag + " succeeded , email Successfully sent to " + customerID + " , invoice file : " + invoice_file + " , email address : " + email_address);
                                writeToLog("Attempt # " + flag + " succeeded , email Successfully sent to " + customerID + " , invoice file : " + invoice_file + " , email address : " + email_address);
                                flag = 1;
                                break;
                            } catch (MessagingException ex1) {
                                flag++;
                                writeToLog("* " + Main.total_email_sent + " * - " + "Exception while sending to " + customerID + " - " + " - " + name + " - " + ex1.toString());
                                System.out.println("* " + Main.total_email_sent + " * - " + "Exception while sending to " + customerID + " - " + " - " + name + " - " + ex1.toString());
                            } finally {
                                if (counter == reattemptCount - 1) {
                                    incrementFailureCount();
                                    System.out.println("Total unsuccessful attempt so far are " + getFailureCount());
                                    writeToLog("Total unsuccessful attempt so far are " + getFailureCount());
                                }
                            }
                        }
                    }

                } else {//if there is problem reading invoice
                    System.out.println("problem getting contents of " + invoice_file);
                    writeToLog("problem getting contents of " + invoice_file);
                }
            }
        }
        closeLogFile();
    }

    private String getFileContents(String fileName) {//to get the contents of invoice
        FileReader in = null;
        try {
            File invoiceFile = new File(fileName);
            in = new FileReader(invoiceFile);
            BufferedReader br = new BufferedReader(in);
            String content = "";//to hold complete html content
            String text = "";//to hold a line
            while ((text = br.readLine()) != null) {//reading a single line
                content = content + text;
            }
            in.close();
            return content;
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        } finally {
            try {
                in.close();//closing connection
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }
    }
}
