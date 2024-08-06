package org.example.progetto_cinema.Spettacoli.Utility_Classes;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {

    public static LocalDateTime getSelectedDateTime(DatePicker datePicker, ComboBox<Integer> hoursComboBox, ComboBox<Integer> minutesComboBox) {
        LocalDate date = datePicker.getValue();
        Integer hour = hoursComboBox.getValue();
        Integer minute = minutesComboBox.getValue();
        if (date != null && hour != null && minute != null) {
            return LocalDateTime.of(date, LocalTime.of(hour, minute));
        } else {
            return null;
        }
    }
}
