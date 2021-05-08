package com.parse.youtube.controllers;

import com.parse.youtube.entity.Error;
import com.parse.youtube.entity.YoutubeResult;
import com.parse.youtube.services.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final ParseService parseService;

    @Autowired
    public MainController(ParseService parseService) {
        this.parseService = parseService;
    }

    @GetMapping("/getEntity")
    public ResponseEntity<?> getEntity(@RequestParam(name = "videoId", required = false) String videoId,
                                       @RequestParam(name = "url", required = false) String url) {
        if (StringUtils.hasLength(videoId) || StringUtils.hasLength(url)) {
            YoutubeResult yr = parseService.getEntity(StringUtils.hasLength(videoId) ? videoId : url);

            if (yr == null) {
                return new ResponseEntity<>(
                        new Error(String.valueOf(HttpStatus.I_AM_A_TEAPOT.value()), "Not enough handled errors"),
                        HttpStatus.I_AM_A_TEAPOT);
            } else return new ResponseEntity<>(yr, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Error(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Empty url"),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getHeader")
    public ResponseEntity<?> getHeader(@RequestParam(name = "videoId", required = false) String videoId,
                                       @RequestParam(name = "url", required = false) String url) {
        if (StringUtils.hasLength(videoId) || StringUtils.hasLength(url)) {
            YoutubeResult yr = parseService.getHeader(StringUtils.hasLength(videoId) ? videoId : url);

            if (yr == null) {
                return new ResponseEntity<>(
                        new Error(String.valueOf(HttpStatus.I_AM_A_TEAPOT.value()), "Not enough handled errors"),
                        HttpStatus.I_AM_A_TEAPOT);
            } else return new ResponseEntity<>(yr, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Error(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Empty url"),
                HttpStatus.BAD_REQUEST);
    }
}
