package org.papertrail.persistence.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/diagnostic")
public class DiagnosticController {

    @GetMapping("/threadinfo")
    public ResponseEntity<Map<String, String>> currentThreadInfo () {

        Map<String, String> threadInfo= new HashMap<>();
        threadInfo.put("Thread", Thread.currentThread().toString());
        threadInfo.put("Is Virtual", String.valueOf(Thread.currentThread().isVirtual()));

        return ResponseEntity.ok(threadInfo);
    }
}
