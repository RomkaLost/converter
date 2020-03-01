package ru.lost.converter.workers;

import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class StaxStreamProcessor {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
    private final XMLStreamReader reader;

    public StaxStreamProcessor() throws XMLStreamException {
        InputStream in = null;
        try {
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            in = con.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader = FACTORY.createXMLStreamReader(in);
    }


    public XMLStreamReader getReader() {
        return reader;
    }
}