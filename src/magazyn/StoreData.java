package magazyn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class StoreData {
    public ArrayList<Produkt> store;

    public void addProdukt(Produkt temp){
        store.add(temp);
    }

    public void readFromFile(String fileName) {
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            Produkt temp;

            while((line = bufferedReader.readLine()) != null) {
                String []splitter = line.split(" ");
                temp = new Produkt(Double.parseDouble(splitter[0]), Integer.parseInt(splitter[1]), splitter[2]);
                store.add(temp);
            }
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        }
        catch(IOException ex) {
            System.out.println("IOException");
        }
    }

    public StoreData(){
        store = new ArrayList<Produkt>();       
    }

    public void writeToFile(String fileName) {
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Produkt x:store){
                bufferedWriter.write(String.valueOf(x.getPrice()));
                bufferedWriter.write(" ");;
                bufferedWriter.write(String.valueOf(x.getCount()));
                bufferedWriter.write(" ");
                bufferedWriter.write(x.getName());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();

        }
        catch(IOException ex){
            System.out.println("IOException");
        }
    }

    static class nameComparator implements Comparator<Produkt>{
        public int compare(Produkt p1, Produkt p2){
            return p1.getName().compareTo(p2.getName());
        }
    }

    static class priceComparator implements Comparator<Produkt>{
        public int compare(Produkt p1, Produkt p2){
            if (p1.getPrice() ==  p2.getPrice())
                return 0;
            else if(p1.getPrice() < p2.getPrice())
                return -1;
            else
                return 1;
        }
    }

    static class countComparator implements Comparator<Produkt>{
        public int compare(Produkt p1, Produkt p2){
            if (p1.getCount() ==  p2.getCount())
                return 0;
            else if(p1.getCount() < p2.getCount())
                return -1;
            else
                return 1;
        }
    }

    public void sortByName(){
        nameComparator cmp = new nameComparator();
        store.sort(cmp);
    }

    public void sortByPrice(){
        priceComparator cmp = new priceComparator();
        store.sort(cmp);
    }

    public void sortByCount(){
        countComparator cmp = new countComparator();
        store.sort(cmp);
    }

    /***
     * 
     * @param searchedProductName
     * @return -1 if @param searchedProductName not found
     *          indeks of looking @param serachedProductName
     */
    public int findByName(String searchedProductName){
        int size = store.size();
        for(int i=0; i < size; i++){
            if(store.get(i).getName().equals(searchedProductName))
                return i;
        }
        return -1;
    }

    public Boolean removeItem(String productNameToRemove){
        int index = findByName(productNameToRemove);
        if(index == -1)
            return false;
        
        store.remove(index);
        return true;
    }
}