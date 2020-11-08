package com.example.watcher;


import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class watcherController {

    @Autowired
    public watcherService watcherService;


//    @GetMapping
//    public void send() throws Exception {
//        watcherService.request();
//    }


}
