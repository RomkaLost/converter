package ru.lost.converter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lost.converter.domain.Convertation;
import ru.lost.converter.domain.CourseByDate;
import ru.lost.converter.domain.User;
import ru.lost.converter.domain.Valute;
import ru.lost.converter.repos.ConvertationRepo;
import ru.lost.converter.repos.CourseByDateRepo;
import ru.lost.converter.repos.ValuteRepo;
import ru.lost.converter.workers.XmlReader;

import java.time.LocalDate;
import java.util.Map;

@Controller
public class ConverterController {
    @Autowired
    private ValuteRepo valuteRepo;
    @Autowired
    private CourseByDateRepo courseByDateRepo;
    @Autowired
    private ConvertationRepo convertationRepo;
    @Autowired
    private XmlReader xmlReader;

    @GetMapping("/")
    private String home(Map<String, Object> model){
        return "home";
    }

    @GetMapping("/main")
    private String main(Map<String, Object> model) {
        xmlReader.fristLoad();
        Iterable<Valute> valutes = valuteRepo.findAll();
        model.put("valutes", valutes);
        return "main";
    }

    @PostMapping("/main")
    private String convert(@AuthenticationPrincipal User user,
            @RequestParam String firstId,
            @RequestParam String secondId,
            @RequestParam Float quantity, Map<String, Object> model) {
        Valute firstValute = valuteRepo.findById(firstId);
        Valute secondValute = valuteRepo.findById(secondId);
        LocalDate today = LocalDate.now();
        LocalDate lastDateInBd= courseByDateRepo.getMaxDate(firstId);
        if(today.isAfter(lastDateInBd)){
            xmlReader.fristLoad();
            lastDateInBd= courseByDateRepo.getMaxDate(firstId);
        }
        CourseByDate firstCourse = courseByDateRepo.findByIdAndDate(firstId, lastDateInBd);
        CourseByDate secondCourse = courseByDateRepo.findByIdAndDate(secondId, lastDateInBd);
        Convertation convertation = new Convertation(firstValute,
                secondValute,
                firstCourse.getCourse(),
                secondCourse.getCourse(),
                quantity,
                user);
        convertationRepo.save(convertation);
        Iterable<Convertation> convertations =convertationRepo.findByAuthor(user);
        model.put("convertations", convertations);
        Iterable<Valute> valutes = valuteRepo.findAll();
        model.put("valutes", valutes);
        return "main";
    }

    @GetMapping("/dictionary")
    private String dictionary(Map<String, Object> model) {
        Iterable<Valute> valutes = valuteRepo.findAll();
        model.put("valutes", valutes);
        return "dictionary";
    }

}


