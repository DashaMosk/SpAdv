package com.epam.springadvanced.web;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.User;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/bookingservice")
public class FileUploadController {

    private static String OUTPUT_LOCATION="C:/mytemp/";

    @Autowired
    MultiFileValidator multiFileValidator;

    @InitBinder("multiFileBucket")
    protected void initBinderMultiFileBucket(WebDataBinder binder) {
        binder.setValidator(multiFileValidator);
    }

    @RequestMapping(path="/uploadform", method = RequestMethod.GET)
    public String getuploaderPage(ModelMap model) {
        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);
        return "fileuploader";
    }

    @RequestMapping(path="/uploadfile", method = RequestMethod.POST)
    public String fileUpload(@RequestPart("file") MultipartFile file, ModelMap model) throws IOException {
        JsonFactory f = new JsonFactory();
        JsonParser jp = f.createParser(file.getInputStream());
//      File jp = new File(OUTPUT_LOCATION + file.getOriginalFilename());
//      FileCopyUtils.copy(file.getBytes(), jp);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        if(file.getOriginalFilename().equals("${users.file}")) {
            User[] users = mapper.readValue(jp, User[].class);
        }
        if(file.getOriginalFilename().equals("${events.file}")) {
            Event[] events = mapper.readValue(jp, Event[].class);
        }
        model.addAttribute("fileName", file.getOriginalFilename());
        return "successupload";
    }
}
