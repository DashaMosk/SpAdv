package com.epam.springadvanced.service.impl;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.service.BookingService;
import com.epam.springadvanced.service.ReportService;
import com.epam.springadvanced.service.TRepKind;
import com.epam.springadvanced.service.UserService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    UserService userService;
    @Autowired
    BookingService bookingService;

    @Override
    public void getTicketRepPDF(HttpServletResponse response, TRepKind repKind, long userID, Event event,
                                   LocalDateTime dateTime) throws JRException, IOException {
        InputStream jasperStream = this.getClass().getResourceAsStream("/jasperreports/ticketsrep.jasper");
        Map<String,Object> params = new HashMap<>();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        //params.put("title", "Booked Tickets");
        Collection<Ticket> tickets = new ArrayList<>();
        if(repKind == TRepKind.BOOKED)
            tickets = userService.getBookedTickets();
        if(repKind == TRepKind.BYUSER)
            tickets = userService.getBookedTicketsByUserId(userID);
        if(repKind == TRepKind.FOREVENT)
            tickets = bookingService.getTicketsForEvent(event, dateTime);

        JRDataSource JRdataSource = new JRBeanCollectionDataSource(tickets);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, JRdataSource);

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=tickets.pdf");

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }
}
