package com.validity.csvduplicatereader.rest;

import com.validity.csvduplicatereader.service.HelloService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.validity.csvduplicatereader.utility.Helper;
import com.validity.csvduplicatereader.service.GetDuplicatesService;

import javax.inject.Inject;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Inject
    private HelloService helloService;

    @Autowired
    Helper helper;

    @Autowired
    GetDuplicatesService getDuplicatesService;

    @GetMapping("/hello")
    public String getHelloMessage() {
        return helloService.getHelloMessage();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody List<Set<Map<String, String>>> uploadCSV(@RequestParam MultipartFile csvFile) {

        File originalFile = helper.convertMultiPartToFile(csvFile);
        List<Set<Map<String, String>>> finalList = getDuplicatesService.checkDuplicates(originalFile);

        return finalList;
    }

    @GetMapping("/headers")
    List<String> headers() {
        List<String> listOfColumnHeaders = helper.getFieldNames();
        return listOfColumnHeaders;
    }
}
