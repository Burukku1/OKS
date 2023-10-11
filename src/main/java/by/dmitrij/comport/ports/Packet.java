package by.dmitrij.comport.ports;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Packet {


    private static final String FLAG = "01010010";
    private static final String DESTINATION_ADDRESS = "0000";
    private static final String SOURCE_ADDRESS = "0000";
    private static final int LENGTH = 4;
    private String DATA;
    private static final String FCS = "0";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public String createPackage(String data) {
        DATA = data;
        String newFrame = new String();
        newFrame = FLAG + DESTINATION_ADDRESS + SOURCE_ADDRESS + "0100" + DATA + FCS;
        newFrame = bitStaf(newFrame);
        return newFrame;
    }



    public String bitStaf(String data) {
        String buf = new String();
        String dataPart = data.substring(20);

        for (int i = 0; i < 20; i++) {
            buf = buf + data.charAt(i);
        }

        for (int i = 0; i < dataPart.length() - 1; i++) {
            buf = buf + dataPart.charAt(i);
            if (i == 3 || i == 7 || i == 11 || i == 15) {
                buf = buf + '0';
            }
        }

        buf = buf + "0";

        System.out.println("FLAG is " + FLAG);
        printPackage(buf);

        return buf;
    }


    public void printPackage(String modifiedData) {
//        String frame = FLAG + DESTINATION_ADDRESS + SOURCE_ADDRESS + "0100" + DATA + FCS;
//        System.out.print(frame.substring(0, 20)); // Выводим первые 20 символов без перевода строки

        for (int i = 20; i < modifiedData.length(); i++) { // Начинаем с 20 символа
            if (i == 24 || i == 29 || i == 34 || i == 39) { // Изменяем индексы для бит-стаффинга
                System.out.printf(ANSI_GREEN + modifiedData.charAt(i) + ANSI_RESET);
            } else {
                System.out.print(modifiedData.charAt(i));
            }
        }
       // System.out.println(FCS); // Добавляем перевод строки после вывода всей строки
    }


    public String bitDestaf(String data) {
        printPackage(data);
        String destafResult = data.substring(0, 20); // Сначала копируем первые 20 символов
        String stuffedData = data.substring(20); // Затем копируем остаток строки

        for (int i = 0; i < stuffedData.length(); i++) {
            if (i == 4 || i == 9 || i == 14 || i == 19) {
                System.out.println("Destaffing...\n");
            } else {
                destafResult = destafResult + stuffedData.charAt(i);
            }
        }

        return destafResult;
    }

}









//    private static final String FLAG = "01010010";
//    private static final String DESTINATION_ADDRESS = "0000";
//    private static final String SOURCE_ADDRESS = "0000";
//    private static final int LENGTH = 4;
//    private String DATA;
//    private static final String FCS = "0";
//
//    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_RESET = "\u001B[0m";
//    public Packet() {
//
//    }
//
//    public String createPackage(String data){
//        DATA = data;
//        String newFrame = new String();
//        newFrame =  FLAG + DESTINATION_ADDRESS + SOURCE_ADDRESS + "0100" + DATA + FCS;
//        return newFrame;
//    }
//
//    public void setData(String data){
//        this.DATA = data;
//    }
//
//
//
//    public String bitStaf(String data){
//        // String stafResult;
//        String buf = new String();
//        String dataPart = data.substring(20);
//        for(int i = 0; i < dataPart.length() - 1; i++){
//            buf = buf + dataPart.charAt(i);
//            if(i == 3 || i == 7 || i == 11 || i == 15){
//                buf = buf + '0';
//            }
//        }
//
//        System.out.println("FLAG is " + FLAG);
//        //   stafResult = FLAG + "|" + buf +"|" +FLAG; 111
//        //     System.out.println(stafResult);
//        printPackage(buf);
//
//        //System.out.println(buf.substring(0, 3) + ANSI_PURPLE + buf.substring(4) + ANSI_RESET);
//        return buf;
//
//    }
//
//
//
//    public void printPackage(String modifiedData){
//        String frame = new String();
//        frame = FLAG + DESTINATION_ADDRESS + SOURCE_ADDRESS + "0100" + DATA + FCS;
//        System.out.println(frame);
//        System.out.printf(frame.substring(0, 20));
//        for(int i = 0; i < modifiedData.length(); i++){
//            if(i == 4 || i == 9 || i == 14 || i == 15){
//                System.out.printf(ANSI_GREEN + modifiedData.charAt(i) + ANSI_RESET);
//            }
//            else  System.out.print(modifiedData.charAt(i));
//        }
//        System.out.printf(FCS + "\n");
//    }
//
//    public String bitDestaf(String data){
//        // String stuffedData = data.substring(9, data.length() - 9);
//        String stuffedData = data;
//        String destafResult = new String();
//        for(int i = 0; i < stuffedData.length(); i++){
//
//            if(i == 4 || i == 9 || i == 14 || i == 19){
//                System.out.println("Destaffing...\n");
//            }
//            else destafResult = destafResult + stuffedData.charAt(i);
//        }
//        return destafResult;
//    }
//}
