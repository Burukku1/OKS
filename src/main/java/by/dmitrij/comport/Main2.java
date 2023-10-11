package by.dmitrij.comport;

import by.dmitrij.comport.ports.ComPort;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args)  {

        ComPort senderPort = new ComPort("/Users/dmitrijskackov/comports/COM1");



        ComPort receiverPort = new ComPort("/Users/dmitrijskackov/comports/COM2");

        if (senderPort.openPort() && receiverPort.openPort()){
            System.out.println("COM порты успешно открыты.");

            receiverPort.startReading();


            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Меню:");
                System.out.println("1. Отправить сообщение");
                System.out.println("2. Изменить скорость отправки");
                System.out.println("3. Выход");
                System.out.print("Выберите действие: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:

                        System.out.print("Введите сообщение для отправки: ");
                        String messageToSend = scanner.next();
                        senderPort.send(messageToSend);

                        break;
                    case 2:

                        System.out.print("Введите новую скорость (бит/сек): ");
                        int newBaudRate = scanner.nextInt();
                        senderPort.setBaudRate(newBaudRate);
                        System.out.println("Скорость отправки изменена на " + newBaudRate + " бит/сек");
                        break;
                    case 3:

                        senderPort.close();
                        receiverPort.close();
                        System.out.println("Программа завершена.");
                        System.exit(0);
                    default:
                        System.out.println("Некорректный выбор. Попробуйте снова.");
                }
            }
        } else {
            System.err.println("Не удалось открыть COM порты.");
        }
    }
}
