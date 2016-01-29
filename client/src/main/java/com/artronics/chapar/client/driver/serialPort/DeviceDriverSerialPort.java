package com.artronics.chapar.client.driver.serialPort;

import com.artronics.chapar.client.driver.DeviceDriver;
import com.artronics.chapar.client.driver.buffer.BufferDistributor;
import com.artronics.chapar.client.exceptions.DeviceDriverException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

@Component("deviceDriverSerialPort")
public class DeviceDriverSerialPort implements DeviceDriver, SerialPortEventListener {
    private final static Logger log = Logger.getLogger(DeviceDriverSerialPort.class);

    private BufferDistributor bufferDistributor;

    private String connectionString;
    private Integer timeout;

    private InputStream input;
    private OutputStream output;
    private CommPortIdentifier identifier;
    private SerialPort serialPort;

    @Override
    public void init() {
        log.debug("Initializing Device Driver...");
        log.debug("Connection String is: \"" + connectionString + "\"");
        setConnection();
    }

    private void setConnection() {
        Enumeration portsEnum = CommPortIdentifier.getPortIdentifiers();

        log.debug("Searching for available serial ports:");
        int count = 0;
        while (portsEnum.hasMoreElements()) {
            count++;
            CommPortIdentifier port = (CommPortIdentifier) portsEnum.nextElement();

            log.debug(count + " : \"" + port.getName() + "\"");
            if (port.getName().equals(connectionString)) {
                identifier = port;

                log.debug("Match found.");
                return;
            }
        }
        log.debug("No match found. Remember Connection String must be equal to com port's name");
    }

    @Override
    public void open() throws DeviceDriverException {
        log.debug("Opening Serial port...");

        if (identifier == null) {
            throw new DeviceDriverException("Attempt to open a null connection.");
        }

        final CommPort commPort;
        SerialPort serialPort;

        try {
            commPort = identifier.open("SinkPort", timeout);
            //the CommPort object can be casted to a SerialPort object
            serialPort = (SerialPort) commPort;

            serialPort.setSerialPortParams(115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);


        } catch (Exception e) {
            e.printStackTrace();
            throw new DeviceDriverException("Can not open the connection");
        }

        this.serialPort = serialPort;
        initEventListenersAndIO();
        addInputToBuffDistributor();

    }

    private void addInputToBuffDistributor() {
        bufferDistributor.setInput(input);
    }

    private void initEventListenersAndIO() throws DeviceDriverException {
        try {
            log.debug("Initializing Event Listener, Input and Output Streams.");
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

        } catch (TooManyListenersException e) {
            e.printStackTrace();
            log.error("Too many listener");
            throw new DeviceDriverException("Too many listener");
        } catch (IOException e) {
            e.printStackTrace();
            throw new DeviceDriverException("IO exception while opening streams.");
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            bufferDistributor.bufferReceived();
        }
    }

    @Override
    public void close() throws DeviceDriverException {

    }

    @Autowired
    public void setBufferDistributor(
            BufferDistributor bufferDistributor) {
        this.bufferDistributor = bufferDistributor;
    }

    @Value("${com.artronics.chapar.client.driver.connection_string}")
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    @Value("${com.artronics.chapar.client.driver.timeout}")
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
