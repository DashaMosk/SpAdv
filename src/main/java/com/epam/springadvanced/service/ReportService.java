package com.epam.springadvanced.service;

import com.epam.springadvanced.entity.Event;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public interface ReportService {
    void getTicketRepPDF(HttpServletResponse response, TRepKind repKind, long userID, Event event,
                            LocalDateTime dateTime) throws JRException, IOException;
}
