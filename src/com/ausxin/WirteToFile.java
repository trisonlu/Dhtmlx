package com.ausxin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lushuqin on 2016/10/17.
 */
public class WirteToFile {

    public static void main(String[] args) {
        try {

            String content = "\r\nThis is the content to write into file中文";

            File file = new File("c:/ausxin/testdata.sql");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String filePath,String content){

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
