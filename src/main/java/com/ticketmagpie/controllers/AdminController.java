package com.ticketmagpie.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticketmagpie.User;
import com.ticketmagpie.infrastructure.persistence.ConcertRepository;
import com.ticketmagpie.infrastructure.persistence.UserRepository;

@Controller
@RequestMapping("/user/admin")
public class AdminController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ConcertRepository concertRepository;

  @RequestMapping("")
  public String home() {
    return "admin/home";
  }

  @RequestMapping(value = "/users", method = GET)
  public String listUsers(Model model) {
    model.addAttribute("users", userRepository.getAllUsers());
    return "admin/users";
  }

  @RequestMapping(value = "/users", method = POST)
  public String createUser(@RequestParam("username") String username,
      @RequestParam("role") String role,
      @RequestParam("password") String password,
      Model model) {
    userRepository.save(new User(username, password, role));
    return listUsers(model);
  }

  @RequestMapping(value = "/users/delete", method = POST)
  public String deleteUser(@RequestParam("username") String username, Model model) {
    userRepository.delete(username);
    return listUsers(model);
  }

  @RequestMapping(value = "/concerts", method = GET)
  public String listConcerts(Model model) {
    model.addAttribute("concerts", concertRepository.getAllConcerts());
    return "admin/concerts";
  }

  @RequestMapping(value = "/concerts/delete", method = POST)
  public String deleteConcert(@RequestParam("id") int id, Model model) {
    concertRepository.delete(id);
    return listConcerts(model);
  }
}
