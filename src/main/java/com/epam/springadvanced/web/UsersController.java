package com.epam.springadvanced.web;

import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.UserService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/bookingservice")
public class UsersController {

    @Autowired
    private UserService userService;

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

    @RequestMapping(path = "/user/bookedtickets", produces={"application/pdf"})
    public void getTicketsReport(HttpServletResponse response) throws JRException, IOException {
        InputStream jasperStream = this.getClass().getResourceAsStream("/jasperreports/ticketsrep.jasper");
        Map<String,Object> params = new HashMap<>();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        //params.put("title", "Booked Tickets");
        Collection<Ticket> tickets = userService.getBookedTickets();
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(tickets);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, JRdataSource);

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=tickets.pdf");

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @RequestMapping(value = "/user/errortest", method = RequestMethod.GET)
    public void getError() throws Exception {
        IOException ex = new IOException("Test exception");
        throw ex;
    }

}
