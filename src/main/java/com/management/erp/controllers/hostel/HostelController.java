package com.management.erp.controllers.hostel;

import com.management.erp.models.repository.*;
import com.management.erp.repositories.GatePassRepository;
import com.management.erp.repositories.HostelRegRepository;
import com.management.erp.repositories.HostelRepository;
import com.management.erp.repositories.HostelRoomRepository;
import com.management.erp.services.FindFacultyService;
import com.management.erp.services.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/hostel")
public class HostelController {

    @Autowired
    HostelRegRepository hostelRegRepository;
    @Autowired
    FindStudentService findStudentService;
    @Autowired
    HostelRepository hostelRepository;
    @Autowired
    HostelRoomRepository hostelRoomRepository;
    @Autowired
    GatePassRepository gatePassRepository;
    @Autowired
    FindFacultyService findFacultyService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<HostelModel> getAllHostels() {
        return hostelRepository.findAll();
    }

    @RequestMapping(value = "/{hostelId}/rooms", method = RequestMethod.GET)
    public @ResponseBody List<HostelRoomModel> getAllRooms(@PathVariable("hostelId") long id) {
        Optional<HostelModel> hostelModelOptional = hostelRepository.findById(id);
        if(hostelModelOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hostel not found");
        return hostelRoomRepository.findAllByHostel(hostelModelOptional.get());
    }

    @RequestMapping(value = "/{hostelId}/students", method = RequestMethod.GET)
    public @ResponseBody List<HostelRegModel> getAllStudents(@PathVariable("hostelId") long id) {
        Optional<HostelModel> hostelModelOptional = hostelRepository.findById(id);
        if(hostelModelOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hostel not found");

        List<HostelRoomModel> rooms = hostelRoomRepository.findAllByHostel(hostelModelOptional.get());
        List<HostelRegModel> regs = new ArrayList<>();
        for (HostelRoomModel room: rooms) {
            regs.addAll(hostelRegRepository.findAllByRoom(room));
        }
        return regs;
    }

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public @ResponseBody HostelRegModel getStudModel(Principal principal) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());
        Optional<HostelRegModel> hostelRegOptional = hostelRegRepository.findByStudent(student);
        if(hostelRegOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not enrolled in any hostel");

        return hostelRegOptional.get();
    }

    @RequestMapping(value = "/student/pass", method = RequestMethod.GET)
    public @ResponseBody List<GatePassModel> getGatePasses(Principal principal) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());
        Optional<HostelRegModel> hostelRegOptional = hostelRegRepository.findByStudent(student);
        if(hostelRegOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not enrolled in any hostel");

        HostelRegModel hostelRegModel = hostelRegOptional.get();

        return gatePassRepository.findAllByHostelReg(hostelRegModel);
    }

    @RequestMapping(value = "/student/pass/upcoming", method = RequestMethod.GET)
    public @ResponseBody List<GatePassModel> getUpcomingGatePasses(Principal principal) {
        StudentModel studentModel = findStudentService.findStudentModelByEmail(principal.getName());
        Optional<HostelRegModel> hostelRegOptional = hostelRegRepository.findByStudent(studentModel);
        if(hostelRegOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not enrolled in any hostel");

        HostelRegModel hostelRegModel = hostelRegOptional.get();
        return gatePassRepository.findAllByHostelRegAndDateGreaterThanEqual(hostelRegModel, LocalDate.now());
    }

    @RequestMapping(value = "/student/pass", method = RequestMethod.POST)
    public @ResponseBody GatePassModel postGatePass(@RequestBody GatePassModel gatePassModel, Principal principal) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());
        Optional<HostelRegModel> hostelRegOptional = hostelRegRepository.findByStudent(student);
        if(hostelRegOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not enrolled in any hostel");

        HostelRegModel hostelRegModel = hostelRegOptional.get();
        gatePassModel.setHostelReg(hostelRegModel);
        gatePassModel.setPermission(false);
        return gatePassRepository.save(gatePassModel);
    }

    @RequestMapping(value = "/faculty/{hostelId}/pass", method = RequestMethod.GET)
    public @ResponseBody List<GatePassModel> getHostelGatePasses(@PathVariable("hostelId") long id) {
        Optional<HostelModel> hostelModelOptional = hostelRepository.findById(id);
        if(hostelModelOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hostel not found");

        List<HostelRoomModel> rooms = hostelRoomRepository.findAllByHostel(hostelModelOptional.get());
        List<HostelRegModel> regs = new ArrayList<>();
        for (HostelRoomModel room: rooms) {
            regs.addAll(hostelRegRepository.findAllByRoom(room));
        }

        List<GatePassModel> passes = new ArrayList<>();
        for(HostelRegModel reg: regs) {
            passes.addAll(gatePassRepository.findAllByHostelRegAndSignedOnIsNull(reg));
        }

        return passes;
    }

    @RequestMapping(value = "/faculty/pass/{passId}", method = RequestMethod.PUT)
    public @ResponseBody GatePassModel signGatePass(@PathVariable("passId") long id, @RequestParam String allowed) {
        Optional<GatePassModel> gatePassOptional = gatePassRepository.findById(id);
        if(gatePassOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gate Pass not found");

        GatePassModel gatePass = gatePassOptional.get();
        gatePass.setPermission(allowed.equals("true"));
        FacultyModel faculty = findFacultyService.findFacultyModelById("WARD789");
        gatePass.setSignedBy(faculty);
        gatePass.setSignedOn(LocalDateTime.now());
        return gatePassRepository.save(gatePass);
    }

    @RequestMapping(value = "/pass/{id}", method = RequestMethod.GET)
    public @ResponseBody GatePassModel getGaePass(@PathVariable("id") long id) {
        Optional<GatePassModel> gatePassModelOptional = gatePassRepository.findById(id);
        if(gatePassModelOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gate pass not found");
        return gatePassModelOptional.get();
    }
}
