package com.epam.springadvanced.web;

import com.epam.springadvanced.service.UploadService;
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

    @Autowired
    MultiFileValidator multiFileValidator;
    @Autowired
    UploadService uploadService;

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
        uploadService.uploadFile(file);
        model.addAttribute("fileName", file.getOriginalFilename());
        return "successupload";
    }
}
