package com.raccoon.webapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
  
  
       @Autowired
    private CourseRepository repo;

    public List<Course> findAll() {
        return (List<Course>) repo.findAll();
    }

    public List<Course> findEnabled() {
        return repo.findByEnabledTrue();
    }

    public void save(Course course) {
        repo.save(course);
    }

    public Course getById(Integer id) throws CourseNotFoundException {
        Optional<Course> result = repo.findById(id);
        if (result.isPresent()) return result.get();
        throw new CourseNotFoundException("Course ID " + id + " not found");
    }

    public void delete(Integer id) throws CourseNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) throw new CourseNotFoundException("Course ID " + id + " not found");
        repo.deleteById(id);
    }

    

}
