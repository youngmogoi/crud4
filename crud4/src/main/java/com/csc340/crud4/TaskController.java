package com.csc340.crud4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/all")
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasksList";
    }

    @GetMapping("/{id}")
    public String getTask(@PathVariable int id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "taskDetails";
    }

    @GetMapping("/create")
    public String showCreateTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "createTask";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        taskService.saveTask(task);
        return "redirect:/tasks/all";
    }

    @GetMapping("/update/{id}")
    public String showUpdateTaskForm(@PathVariable int id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "updateTask";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable int id, @ModelAttribute Task taskDetails) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            task.setTitle(taskDetails.getTitle());
            task.setDetails(taskDetails.getDetails());
            task.setStatus(taskDetails.getStatus());
            taskService.saveTask(task);
        }
        return "redirect:/tasks/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/all";
    }
}
