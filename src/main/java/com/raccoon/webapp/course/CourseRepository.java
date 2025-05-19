package com.raccoon.webapp.course;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer> {

     Long countById(Integer id);

      List<Course> findByEnabledTrue();
}
