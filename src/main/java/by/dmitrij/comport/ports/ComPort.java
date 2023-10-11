package by.dmitrij.comport.ports;

import com.fazecast.jSerialComm.SerialPort;
// socat -d -d pty,raw,echo=0,link=./COM2 pty,raw,echo=0,link=./COM3

import java.io.InputStream;
import java.io.OutputStream;




public class ComPort {
    private SerialPort comPort;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Packet packet = new Packet();

    public ComPort(String portDescriptor) {
        this.comPort = SerialPort.getCommPort(portDescriptor);
    }

    public boolean openPort() {
        if (comPort.openPort()) {
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 1000);
            return true;
        } else {
            System.out.println("Порт не открылся.");
            return false;
        }
    }

    public void send(String message) {
        String dataToSend = packet.createPackage(message);

            comPort.writeBytes(dataToSend.getBytes(), dataToSend.length());

    }




    public void startReading() {
        if (comPort.isOpen()) {
            Thread readerThread = new Thread(() -> {
                try {
                    while (true) {
                        while (comPort.bytesAvailable() == 0) {
                            Thread.sleep(100);
                        }

                        System.out.println("Данные пришли");

                        byte[] receivedBytes = new byte[1024];
                        int numRead = comPort.readBytes(receivedBytes, receivedBytes.length);
                        if (numRead > 0) {
                            String receivedData = new String(receivedBytes, 0, numRead);
                            System.out.println("Received data: " + receivedData);
                            String afterDeBitStuff = packet.bitDestaf(receivedData);
                            System.out.println("After deBitStaff " + afterDeBitStuff);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            readerThread.start();
        }
    }



    public void setBaudRate(int baud) {
        comPort.setBaudRate(baud);
    }

    public void close() {
        if (comPort.isOpen()) {
            comPort.closePort();
            System.out.println("COM порт закрыт.");
        }
    }
}
