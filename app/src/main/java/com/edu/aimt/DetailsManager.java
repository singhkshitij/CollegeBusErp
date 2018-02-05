package com.edu.aimt;

/**
 * Created by PRAV on 07-04-2016.
 */
public class DetailsManager {
public static String names[]={"Rishabh Raj",
                              "Siddharth Awasthi","Anil Kumar","Arjit Srivastava","Kshitij Singh","Krishna Kunj",
                              "Ayush Singh","Vipul Shukla","Shashi Kel","Sandeep Kumar"};
public static String branches[] ={"EC 3rd","ME 2nd","ME 3rd","CS 3rd","CS 3rd","CE 2nd","ME 4th","CE 4th","CS 3rd","CE 2nd"};
    public static String[] getNames(int limit){
        String name[]=new String[limit];
        for(int i=0;i<limit;i++){
            name[i]=names[i];
        }
        return name;
    }
    public static String[] getBranches(int limit){
        String name[]=new String[limit];
        for(int i=0;i<limit;i++){
            name[i]=branches[i];
        }
        return name;
    }
}
