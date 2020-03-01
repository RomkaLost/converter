package ru.lost.converter.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Convertation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "first_valute_id")
    private Valute firstValute;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second_valute_id")
    private Valute secondValute;
    private Float firstQuantity;
    private Float secondQuantity;
    private Float course;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Valute getFirstValute() {
        return firstValute;
    }

    public void setFirstValute(Valute firstValute) {
        this.firstValute = firstValute;
    }

    public Valute getSecondValute() {
        return secondValute;
    }

    public void setSecondValute(Valute secondValute) {
        this.secondValute = secondValute;
    }

    public Float getCourse() {
        return course;
    }

    public void setCourse(Float course) {
        this.course = course;
    }

    public Float getFirstQuantity() {
        return firstQuantity;
    }

    public void setFirstQuantity(Float firstQuantity) {
        this.firstQuantity = firstQuantity;
    }

    public Float getSecondQuantity() {
        return secondQuantity;
    }

    public void setSecondQuantity(Float secondQuantity) {
        this.secondQuantity = secondQuantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Convertation(){};
    public Convertation(Valute firstValute,
                        Valute secondValute,
                        float firstCourse,
                        float secondCourse,
                        float quantity,
                        User user){
        this.author =user;
        this.firstQuantity=quantity;
        this.firstValute=firstValute;
        this.secondValute=secondValute;
        course=firstCourse/secondCourse;
        secondQuantity=(quantity*course);
        date=LocalDate.now();
    }

}
