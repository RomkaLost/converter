package ru.lost.converter.workers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lost.converter.domain.CourseByDate;
import ru.lost.converter.domain.Valute;
import ru.lost.converter.repos.CourseByDateRepo;
import ru.lost.converter.repos.ValuteRepo;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Component
public class XmlReader {
    @Autowired
    CourseByDateRepo courseByDateRepo;
    @Autowired
    ValuteRepo valuteRepo;
    @Autowired
    StaxStreamProcessor staxStreamProcessor;

    public void fristLoad() {
        try {
            XMLStreamReader r = staxStreamProcessor.getReader();
            int event = r.getEventType();
            LocalDate date= null;
            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println("Start Document.");
                        break;
                    case XMLStreamConstants.START_ELEMENT:

                        if (r.getName().toString().equalsIgnoreCase("valCurs")) {
                            String docDate = r.getAttributeValue(0);
                            date = LocalDate.parse(docDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        }
                        if (r.getName().toString().equalsIgnoreCase("Valute")) {
                            Valute valute = new Valute();
                            valute.setId(r.getAttributeValue(0));
                            valuteLoader(valute, r, date);
                        }
                        break;
                }

                if (!r.hasNext()) {
                    break;
                } else {
                    event = r.next();
                }

            }
        } catch (XMLStreamException e) {
            e.printStackTrace();

        }
    }

    private void valuteLoader(Valute valute, XMLStreamReader r, LocalDate date) throws XMLStreamException {
        int nom = 1;
        boolean endOfValute = false;
        float numberValue = 0;
        while (!endOfValute) {
            r.next();
            int event = r.getEventType();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String eventName = r.getName().toString();
                    switch (eventName) {
                        case ("Name"):
                            r.next();
                            valute.setName(r.getText());
                            break;
                        case ("CharCode"):
                            r.next();
                            valute.setCharCode(r.getText());
                            break;
                        case ("NumCode"):
                            r.next();
                            valute.setNumCode(r.getText());
                            break;
                        case ("Value"):
                            r.next();
                            String value = r.getText();
                            value = value.replace(',', '.');
                            numberValue = Float.valueOf(value);
                            break;
                        case ("Nominal"):
                            r.next();
                            nom = Integer.parseInt(r.getText());
                            break;
                        default:
                            r.next();
                            break;

                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (r.getName().toString().equalsIgnoreCase("Valute")) {
                        endOfValute = true;
                        CourseByDate courseByDate = new CourseByDate();
                        courseByDate.setId(valute.getId());
                        courseByDate.setCourse(numberValue/ nom);
                        courseByDate.setDate(date);
                        courseByDateRepo.save(courseByDate);
                        valuteRepo.save(valute);
                    }
                    break;
                default:
                    r.next();
                    break;
            }

        }
    }
}
