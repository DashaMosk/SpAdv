package com.epam.springadvanced.web;

import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.ReportService;
import com.epam.springadvanced.service.TRepKind;
import com.epam.springadvanced.service.UserService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

@Controller
@RequestMapping("/bookingservice")
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;


    @RequestMapping(path="/user/create", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED, reason = "User created.")
    public void createUser(@RequestParam final String name, @RequestParam final String email) {
        userService.create(name, email, LocalDate.now());
    }

    @RequestMapping(path = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK, reason = "User registered.")
    public void registerUser(@RequestBody final User user){
        userService.register(user);
    }

    @RequestMapping(path = "/user/remove", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK, reason = "User removed.")
    public void removeUser(@RequestParam final long userId){
        userService.remove(userService.getById(userId));
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(@PathVariable final long userId) {
        return  userService.getById(userId);
    }

    @RequestMapping(value = "/user/byemail", method = RequestMethod.GET)
    @ResponseBody
    public User getUserByEmail(@RequestParam final String email) {
        return userService.getUserByEmail(email);
    }

    @RequestMapping(value = "/user/byname", method = RequestMethod.GET)
    @ResponseBody
    public User getUserByName(@RequestParam final String name) {
        return userService.getUserByName(name);
    }

    @RequestMapping(path = "/user/bookedtickets", method = RequestMethod.GET)
    public String getBookedTickets(ModelMap model){
        Collection<Ticket> tickets = userService.getBookedTickets();
        model.addAttribute("tickets", tickets);
        model.addAttribute("title", "Booked tickets");
        return "btickets";
    }

    @RequestMapping(path = "/user/ticketsbyuser", method = RequestMethod.GET)
    public String getTicketsByUser(ModelMap model, @RequestParam final long userId){
        Collection<Ticket> tickets = userService.getBookedTicketsByUserId(userId);
        model.addAttribute("tickets", tickets);
        model.addAttribute("title", "Booked tickets by user");
        return "btickets";
    }

    @RequestMapping(path = "/user/bookedtickets", produces={"application/pdf"})
    public void getBookedTicketsRep(HttpServletResponse response) throws JRException, IOException {
        reportService.getTicketRepPDF(response, TRepKind.BOOKED, 0, null, null);
    }

    @RequestMapping(path = "/user/ticketsbyuser", produces={"application/pdf"})
    public void getTicketsRepByUser(HttpServletResponse response, @RequestParam final long userId) throws JRException, IOException {
        reportService.getTicketRepPDF(response, TRepKind.BYUSER, userId, null, null);
    }

    @RequestMapping(value = "/user/errortest", method = RequestMethod.GET)
    public void getError() throws Exception {
        IOException ex = new IOException("Test exception");
        throw ex;
    }

}
