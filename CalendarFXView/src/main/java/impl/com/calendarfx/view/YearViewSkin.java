/**
 * Copyright (C) 2015, 2016 Dirk Lemmermann Software & Consulting (dlsc.com) 
 * 
 * This file is part of CalendarFX.
 */

package impl.com.calendarfx.view;

import com.calendarfx.view.YearMonthView;
import com.calendarfx.view.YearView;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import static java.lang.Double.MAX_VALUE;

public class YearViewSkin extends DateControlSkin<YearView> {

    public YearViewSkin(YearView view) {
        super(view);

        view.dateProperty().addListener(evt -> updateMonths());

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("container");
        gridPane.setMaxSize(MAX_VALUE, MAX_VALUE);

        for (int row = 0; row < 3; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(Region.USE_PREF_SIZE);
            rowConstraints.setPrefHeight(Region.USE_COMPUTED_SIZE);
            rowConstraints.setMaxHeight(Region.USE_COMPUTED_SIZE);
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int col = 0; col < 4; col++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(Region.USE_PREF_SIZE);
            colConstraints.setPrefWidth(Region.USE_COMPUTED_SIZE);
            colConstraints.setMaxWidth(Region.USE_COMPUTED_SIZE);
            colConstraints.setHgrow(Priority.ALWAYS);
            colConstraints.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(colConstraints);
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                Month month = Month.of(row * 4 + col + 1);

                YearMonthView yearMonthView = view.getMonthView(month);
                yearMonthView.setShowMonthArrows(false);
                yearMonthView.setShowTodayButton(false);
                yearMonthView.setShowUsageColors(true);
                yearMonthView.setClickBehaviour(YearMonthView.ClickBehaviour.SHOW_DETAILS);
                gridPane.add(yearMonthView, col, row);

                // do not bind date, we manage it manually
                view.bind(yearMonthView, false);
            }
        }

        getChildren().add(gridPane);

        updateMonths();
    }

    private void updateMonths() {
        YearView yearPage = getSkinnable();
        LocalDate date = yearPage.getDate();
        int year = date.getYear();

        for (Month month : Month.values()) {
            YearMonth yearMonth = YearMonth.of(year, month);
            YearMonthView view = yearPage.getMonthView(month);
            view.setMinSize(0, 0);
            view.setDate(yearMonth.atDay(1));
        }
    }

}
