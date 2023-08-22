package org.example;

import java.io.*;
import java.util.ArrayList;

public class Main {

    static final String maxString = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";


    public static void main(String[] args) throws IOException {

        if (args.length < 3) {
            System.out.println("Error, not enough parameters");
            System.exit(1);
        }

        String Flag = args[0];
        String outputPath = args[1];
        ArrayList<String> inputPaths = new ArrayList<>();
        ArrayList<File> files = new ArrayList<>();
        ArrayList<FileReader> fr = new ArrayList<>();
        ArrayList<BufferedReader> reader = new ArrayList<>();
        for (int i = 0; i < args.length - 2; i++)
        {
            inputPaths.add(args[2+i]);
        }

        if(!Flag.equals("i") && !Flag.equals("s")){
            System.out.println("Error, incorrectly specified parameters");
            System.exit(1);
        }
        File output = new File(outputPath);
        output.createNewFile();
        for (int i = 0; i < inputPaths.size(); i++)
        {
            File tmp = new File(inputPaths.get(i));
            if(tmp.exists())
            {
                files.add(tmp);
                FileReader tmpR = null;
                try {
                    tmpR = new FileReader(files.get(i));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                fr.add(tmpR);
                BufferedReader tmpBR = new BufferedReader(fr.get(i));
                reader.add(tmpBR);
            }
            else {
                System.out.printf("Error, file %s does not exist", inputPaths.get(i));
                System.exit(1);
            }
            if(!files.get(i).canRead()) {
                System.console().printf("Error, file %s cannot be read", inputPaths.get(i));
                System.exit(1);
            }
        }

        if(Flag.equals("i"))
        {
            Integer minNumber = 100000000;
            int fileNumber = -1;
            ArrayList<Integer[]> inputNumbers = new ArrayList<>();
            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter(outputPath));
            for (int i = 0; i < files.size(); i++) {

                Integer[] tmpInt = new Integer[2];
                String line1 = reader.get(i).readLine();
                String line2 = reader.get(i).readLine();
                try {
                    if (line1 != null) tmpInt[0] = Integer.parseInt(line1);
                    if (line2 != null) tmpInt[1] = Integer.parseInt(line2);
                    else tmpInt[1] = Integer.MAX_VALUE;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error, data type mismatch, use text mode(-s)");
                    System.exit(1);
                }
                while (line1 != null && tmpInt[0] > tmpInt[1]) {
                    tmpInt[0] = tmpInt[1];
                    line2 = reader.get(i).readLine();
                    try{
                        if(line2 != null) tmpInt[1] = Integer.parseInt(line2);
                        else tmpInt[1] = Integer.MAX_VALUE;
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Error, data type mismatch, use text mode(-s)");
                        System.exit(1);
                    }
                }
                if(line1 == null){
                    reader.remove(i);
                    files.remove(i);
                    fr.remove(i);
                    i--;
                    continue;
                }
                inputNumbers.add(tmpInt);

            }

            while (files.size() > 0)
            {
                for (int i = 0; i < files.size(); i++)
                {
                    if(minNumber > inputNumbers.get(i)[0])
                    {
                        minNumber = inputNumbers.get(i)[0];
                        fileNumber = i;
                    }
                }
                writer.write(minNumber.toString());
                writer.write("\n");
                while (inputNumbers.get(fileNumber)[0] > inputNumbers.get(fileNumber)[1]) {
                    String tmpLine = reader.get(fileNumber).readLine();
                    try{
                        if (tmpLine != null)
                            inputNumbers.get(fileNumber)[1] = Integer.parseInt(tmpLine);
                        else inputNumbers.get(fileNumber)[1] = Integer.MAX_VALUE;
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Error, data type mismatch, use text mode(-s)");
                        System.exit(1);
                    }
                }
                inputNumbers.get(fileNumber)[0] = inputNumbers.get(fileNumber)[1];

                String tmpLine = reader.get(fileNumber).readLine();
                try{
                    if(tmpLine != null)
                        inputNumbers.get(fileNumber)[1] = Integer.parseInt(tmpLine);
                    else inputNumbers.get(fileNumber)[1] = Integer.MAX_VALUE;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error, data type mismatch, use text mode(-s)");
                    System.exit(1);
                }
                if(inputNumbers.get(fileNumber)[0] == Integer.MAX_VALUE)
                {
                    reader.remove(fileNumber);
                    files.remove(fileNumber);
                    fr.remove(fileNumber);
                    inputNumbers.remove(fileNumber);
                }
                minNumber = Integer.MAX_VALUE;
                fileNumber = 0;
            }
            writer.close();
        }
        else
        {
            String minString = maxString;
            int fileNumber = -1;
            ArrayList<String[]> inputStrings = new ArrayList<>();
            //ArrayList<String> result = new ArrayList<>();
            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter(outputPath));
            for (int i = 0; i < files.size(); i++) {

                String[] tmpInt = new String[2];
                String line1 = reader.get(i).readLine();
                String line2 = reader.get(i).readLine();
                if(line1 != null) tmpInt[0] = line1;
                if(line2 != null) tmpInt[1] = line2;
                else tmpInt[1] = maxString;
                while (line1 != null && tmpInt[0].compareTo(tmpInt[1])>0) {
                    tmpInt[0] = tmpInt[1];
                    line2 = reader.get(i).readLine();
                    if(line2 != null)
                        tmpInt[1] = line2;
                    else tmpInt[1] = null;
                }
                if(line1 == null){
                    reader.remove(i);
                    files.remove(i);
                    fr.remove(i);
                    i--;
                    continue;
                }
                inputStrings.add(tmpInt);
            }
            while (files.size() > 0)
            {
                for (int i = 0; i < files.size(); i++)
                {
                    if(minString.compareTo(inputStrings.get(i)[0]) > 0)
                    {
                        minString = inputStrings.get(i)[0];
                        fileNumber = i;
                    }
                }
                writer.write(minString);
                writer.write("\n");
                while (inputStrings.get(fileNumber)[0].compareTo(inputStrings.get(fileNumber)[1]) > 0) {
                    String tmpLine = reader.get(fileNumber).readLine();
                    if (tmpLine != null)
                        inputStrings.get(fileNumber)[1] = tmpLine;
                    else inputStrings.get(fileNumber)[1] = maxString;
                }
                inputStrings.get(fileNumber)[0] = inputStrings.get(fileNumber)[1];
                String tmpLine = reader.get(fileNumber).readLine();
                if(tmpLine != null)
                    inputStrings.get(fileNumber)[1] = tmpLine;
                else inputStrings.get(fileNumber)[1] = maxString;
                if(inputStrings.get(fileNumber)[0].equals(maxString))
                {
                    reader.remove(fileNumber);
                    files.remove(fileNumber);
                    fr.remove(fileNumber);
                    inputStrings.remove(fileNumber);
                }
                minString = maxString;
                fileNumber = 0;
            }
            writer.close();
        }
    }
}