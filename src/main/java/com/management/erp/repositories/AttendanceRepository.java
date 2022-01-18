package com.management.erp.repositories;

import com.management.erp.models.repository.AttendanceModel;
import com.management.erp.models.repository.StudentModel;
import com.management.erp.models.repository.TimeTableModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<AttendanceModel, Long> {
    List<AttendanceModel> findAllByStudentModelAndTimetable(
            StudentModel studentModel, TimeTableModel timeTableModel
    );
    List<AttendanceModel> findAllByStudentModelAndTimetableAndPresent(
            StudentModel studentModel, TimeTableModel timeTableModel, boolean present
    );
    Optional<AttendanceModel> findAttendanceModelByStudentModelAndDateAndTimetable(
            StudentModel studentModel, LocalDate localDate, TimeTableModel timeTableModel
    );
    List<AttendanceModel> findAllByDateAndTimetable(LocalDate date, TimeTableModel timeTableModel);
}
