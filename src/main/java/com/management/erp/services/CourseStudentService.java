package com.management.erp.services;

import com.management.erp.models.repository.CourseEnrolModel;
import com.management.erp.models.repository.CourseModel;
import com.management.erp.models.repository.EnrolmentModel;
import com.management.erp.models.repository.StudentModel;
import com.management.erp.repositories.CourseEnrolRepository;
import com.management.erp.repositories.EnrolmentRepository;
import com.management.erp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseStudentService {

    @Autowired
    private CourseEnrolRepository courseEnrolRepository;
    @Autowired
    private EnrolmentRepository enrolmentRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<StudentModel> getAllRegisteredStudents(CourseModel course) {
        List<CourseEnrolModel> enrolments = courseEnrolRepository.findAllByCourseId(course);
        List<StudentModel> registeredStudents = new ArrayList<>();
        for(CourseEnrolModel courseEnrolModel: enrolments) {
            if(courseEnrolModel.getSemester() == 0) {
                List<EnrolmentModel> enrolmentModels =
                        enrolmentRepository.findAllByEnrolNo(courseEnrolModel.getEnrolment());
                for(EnrolmentModel enrols: enrolmentModels)
                    registeredStudents.add(enrols.getStudent());
            } else {
                registeredStudents.addAll(studentRepository.findAllBySemesterAndDegree(
                        courseEnrolModel.getSemester(), courseEnrolModel.getDegreeId()
                ));
            }
        }
        return registeredStudents;
    }
}
