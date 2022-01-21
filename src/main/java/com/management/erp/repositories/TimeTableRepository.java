package com.management.erp.repositories;

import com.management.erp.models.repository.CourseModel;
import com.management.erp.models.repository.TimeTableModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface TimeTableRepository extends JpaRepository<TimeTableModel, Long> {
    List<TimeTableModel> findAllByCourseModel(CourseModel courseModel);
    List<TimeTableModel> findAllByCourseModelAndType(CourseModel courseModel, char type);
    Optional<TimeTableModel> findByCourseModelAndDayAndHour(CourseModel courseModel, DayOfWeek day, int hour);
    List<TimeTableModel> findAllByCourseModelAndDayAndHourGreaterThanEqual(
        CourseModel courseModel, DayOfWeek day, int hour
    );
    List<TimeTableModel> findAllByCourseModelAndDay(CourseModel courseModel, DayOfWeek day);
}
