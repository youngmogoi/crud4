package com.csc340.crud4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/goals")
public class GoalController {
    @Autowired
    private GoalService goalService;

    @GetMapping("/all")
    public String getAllGoals(Model model) {
        List<Goal> goals = goalService.getAllGoals();
        model.addAttribute("goals", goals);
        return "goalsList";
    }

    @GetMapping("/{id}")
    public String getGoal(@PathVariable int id, Model model) {
        Goal goal = goalService.getGoalById(id);
        model.addAttribute("goal", goal);
        return "goalDetails";
    }

    @GetMapping("/create")
    public String showCreateGoalForm(Model model) {
        model.addAttribute("goal", new Goal());
        return "createGoal";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createGoal(@ModelAttribute Goal goal) {
        goalService.saveGoal(goal);
        return "redirect:/goals/all";
    }

    @GetMapping("/update/{id}")
    public String showUpdateGoalForm(@PathVariable int id, Model model) {
        Goal goal = goalService.getGoalById(id);
        model.addAttribute("goal", goal);
        return "updateGoal";
    }

    @PostMapping("/update/{id}")
    public String updateGoal(@PathVariable int id, @ModelAttribute Goal goalDetails, @RequestParam("targetDate") String targetDate) {
        Goal existingGoal = goalService.getGoalById(id);
        if (existingGoal != null) {
            existingGoal.setTitle(goalDetails.getTitle());
            existingGoal.setDetails(goalDetails.getDetails());
            existingGoal.setStatus(goalDetails.getStatus());

            // Parse and set the targetDate
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date parsedDate = dateFormat.parse(targetDate);
                existingGoal.setTargetDate(parsedDate);
            } catch (ParseException e) {
                e.printStackTrace(); // Handle or log the parsing exception
            }

            goalService.saveGoal(existingGoal);
        }
        return "redirect:/goals/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteGoal(@PathVariable int id) {
        goalService.deleteGoal(id);
        return "redirect:/goals/all";
    }
}

