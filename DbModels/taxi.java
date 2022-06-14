package DbModels;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class taxi {
    public int taxiId;
    public int bookedOn;
    public int bookedUtil;
    public char source;
    public int customerId;
    public char destination;
    public int pricePerHour;
    private File taxiTable;
    private FileOutputStream fos;
    private BufferedReader br;
    private BufferedWriter wr;

    public int status;

    public taxi() {

        this.taxiId = 0;
        this.customerId = 0;
        this.bookedOn = 0;
        this.bookedUtil = 0;
        this.source = '\0';
        this.destination = '\0';
    }

    public taxi(int taxiId, int customerId, int bookedOn, char source,
            char destination) {

        this.taxiId = taxiId;
        this.customerId = customerId;
        this.bookedOn = bookedOn;
        this.bookedUtil = Math.abs((int) (source) - (int) destination) + bookedOn;
        this.source = source;
        this.destination = destination;
    }

    public taxi(int customerId, int bookedOn, char source,
            char destination) {

        this.taxiId = 0;
        this.customerId = customerId;
        this.bookedOn = bookedOn;
        this.bookedUtil = Math.abs((int) (source) - (int) destination) + bookedOn;
        this.source = source;
        this.destination = destination;
    }

    public void createTaxi() {

        try {
            this.taxiTable = new File("taxiTable.txt");
            fos = new FileOutputStream(taxiTable, true);

            String row = this.taxiId + "%" + this.customerId + "%" + this.bookedOn + "%"
                    + this.source + "%" + destination + "\n";
            byte[] line = row.getBytes();
            fos.write(line);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.status = 200;
        return;
    }

    public String searchTaxi() {
        try {
            br = new BufferedReader(new FileReader(new File("taxiTable.txt")));

            String instance = "";
            ArrayList<taxi> taxiDb = new ArrayList<taxi>();
            while ((instance = br.readLine()) != null) {
                String[] strarr = instance.split("%");
                taxi currentTaxiInstance = new taxi(Integer.parseInt(strarr[0]),
                        Integer.parseInt(strarr[1]),
                        Integer.parseInt(strarr[2]),
                        strarr[3].charAt(0),
                        strarr[4].charAt(0));

                taxiDb.add(currentTaxiInstance);

            }

            HashSet<Integer> availTaxi = new HashSet<Integer>();
            char locationleft = this.source;
            char locationRight = this.source;
            boolean locationSearch = true;
            do {
                for (int i = 0; i < taxiDb.size(); i++) {
                    if ((taxiDb.get(i).bookedOn > this.bookedOn || taxiDb.get(i).bookedUtil < this.bookedOn) &&
                            (taxiDb.get(i).bookedOn > this.bookedUtil || taxiDb.get(i).bookedUtil < this.bookedUtil) &&
                            (taxiDb.get(i).destination == locationleft || taxiDb.get(i).destination == locationRight)) {
                        availTaxi.add(taxiDb.get(i).taxiId);
                    } else {
                        if (availTaxi.contains(taxiDb.get(i).taxiId)) {
                            availTaxi.remove(taxiDb.get(i).taxiId);
                        }
                    }
                }
                if (locationleft >= 'a') {
                    locationleft--;
                }

                if (locationRight <= 'f') {
                    locationRight++;
                }
                if (locationleft < 'a' && locationRight > 'f') {
                    locationSearch = false;
                }

            } while (availTaxi.size() == 0 && locationSearch);

            if (availTaxi.size() == 0) {
                return "Sorry we run of taxis to supply. Please get lost";
            }
            int minsalary = Integer.MAX_VALUE;
            int currentTaxiRecommened = availTaxi.stream().findFirst().get();
            HashMap<Integer, Integer> taxiTotalEarning = new HashMap<Integer, Integer>();

            for (int i = 0; i < taxiDb.size() && availTaxi.size() > 1; i++) {
                if (availTaxi.contains(taxiDb.get(i).taxiId)) {
                    if (taxiTotalEarning.containsKey(taxiDb.get(i).taxiId)) {
                        taxiTotalEarning.put(taxiDb.get(i).taxiId,
                                taxiTotalEarning.get(taxiDb.get(i).taxiId) +
                                        taxiDb.get(i).CaluculateAmount());
                    } else {
                        taxiTotalEarning.put(taxiDb.get(i).taxiId, taxiDb.get(i).CaluculateAmount());
                    }

                }
            }
            for (var key : taxiTotalEarning.keySet()) {
                if (taxiTotalEarning.get(key) < minsalary) {
                    minsalary = taxiTotalEarning.get(key);
                    currentTaxiRecommened = key;
                }
            }
            this.taxiId = currentTaxiRecommened;
            wr = new BufferedWriter(new FileWriter(new File("taxiTable.txt"), true));
            String row = this.taxiId + "%" + this.customerId + "%" + this.bookedOn + "%"
                    + this.source + "%" + destination + "\n";
            wr.write(row);
            br.close();
            wr.close();

            return "The taxi no : " + currentTaxiRecommened + " Has been booked happy journey";

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Some error occured and you are stupid";
    }

    public static void printStatus() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("taxiTable.txt")));

            String instance = "";
            ArrayList<taxi> taxiDb = new ArrayList<taxi>();
            while ((instance = br.readLine()) != null) {
                String[] strarr = instance.split("%");
                taxi currentTaxiInstance = new taxi(Integer.parseInt(strarr[0]),
                        Integer.parseInt(strarr[1]),
                        Integer.parseInt(strarr[2]),
                        strarr[3].charAt(0),
                        strarr[4].charAt(0));

                taxiDb.add(currentTaxiInstance);

            }
            HashSet<Integer> availTaxi = new HashSet<Integer>();
            for (int i = 0; i < taxiDb.size(); i++) {
                availTaxi.add(taxiDb.get(i).taxiId);
            }
            HashMap<Integer, Integer> taxiTotalEarning = new HashMap<Integer, Integer>();
           
            for(int taxiId: availTaxi){
                System.out.println("Taxi-"+taxiId);
                for (int i = 0; i < taxiDb.size(); i++) {
                    if(taxiDb.get(i).taxiId == taxiId && taxiDb.get(i).customerId != -1){
                        System.out.println(taxiDb.get(i).customerId + "\t"+
                                           Character.toString(taxiDb.get(i).source).toUpperCase() + "\t"+
                                           Character.toString(taxiDb.get(i).destination).toUpperCase() + "\t"+
                                           taxiDb.get(i).bookedOn + "\t"+
                                           taxiDb.get(i).bookedUtil + "\t"+
                                           taxiDb.get(i).CaluculateAmount() + "\n");
                        if (taxiTotalEarning.containsKey(taxiDb.get(i).taxiId)) {
                            taxiTotalEarning.put(taxiDb.get(i).taxiId,
                            taxiTotalEarning.get(taxiDb.get(i).taxiId) +
                            taxiDb.get(i).CaluculateAmount());
                        } else {
                             taxiTotalEarning.put(taxiDb.get(i).taxiId, taxiDb.get(i).CaluculateAmount());
                        }
                        
                    }
                }
                System.out.println("Total Earnings: "+ taxiTotalEarning.get(taxiId));
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;

    }

    public int CaluculateAmount() {
        return this.source == this.destination ?  0 : 100 + ((((bookedUtil - bookedOn) * 15) - 5) * 10);
    }
}
