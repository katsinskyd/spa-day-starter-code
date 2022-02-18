package org.launchcode.spaday.controllers;


import org.launchcode.spaday.data.UserData;
import org.launchcode.spaday.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("add")
    public String displayAddUserForm(){
        return "user/add";
    }

    @PostMapping("")
    public String processAddUserForm(Model model, @ModelAttribute User user, String verify) {
        // add form submission handling code here
        if (user.getPassword().isBlank() || user.getUsername().isBlank() || user.getEmail().isBlank()) {
            model.addAttribute("error", "ERROR: Fields cannot be left blank!");
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            return "user/add";
        } else if (user.getPassword().equals(verify)) {
            model.addAttribute("username", user.getUsername());
            UserData.add(new User(user.getUsername(), user.getPassword(), user.getEmail()));
            model.addAttribute("users", UserData.getAll());
            return "user/index";
        } else {
            model.addAttribute("error", "ERROR: Passwords do not match!");
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            return "user/add";
        }
    }

    @GetMapping("detail/{userId}")
    public String displayUserDetails(Model model, @PathVariable int userId) {
        User thisUser = UserData.getById(userId);
        model.addAttribute("user", thisUser);
        return "user/detail";
    }
}
