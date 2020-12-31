package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.exception.ScheduleControllerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule save(Schedule schedule){
      return scheduleRepository.save(schedule);
    }

    public List<Schedule> listAll(){
       // List<Schedule> listSchedule = scheduleRepository.findAll();
       // return listSchedule.stream().map( schedule -> convertScheduleToDTO(schedule) ).collect(Collectors.toList());
        return scheduleRepository.findAll();
    }




}
