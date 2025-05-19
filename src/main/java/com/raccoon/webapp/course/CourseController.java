package com.raccoon.webapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {


    @Autowired
    private CourseService service;

     @GetMapping
    public String viewCourses(Model model) {
        List<Course> list = service.findEnabled();
        model.addAttribute("courseList", list);
        return "all_courses";
    }

    @GetMapping("/new")
    public String newCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("pageTitle", "New Course");
        return "course_form";
    }




    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        try {
            Course course = service.getById(id);
            model.addAttribute("course", course);
            model.addAttribute("pageTitle", "Edit Course (ID: " + id + ")");
            return "course_form";
        } catch (CourseNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/courses";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Course deleted successfully.");
        } catch (CourseNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/courses";
    }

    @PostMapping("/save")
    public String saveCourse(Course course, RedirectAttributes ra) {
        try {
            service.save(course);
            ra.addFlashAttribute("message", "Curso guardado exitosamente.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("message", "Ya existe un curso con ese nombre.");
        }
        return "redirect:/courses";
    }

}
