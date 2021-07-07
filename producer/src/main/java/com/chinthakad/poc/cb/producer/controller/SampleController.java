package com.chinthakad.poc.cb.producer.controller;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/produce")
public class SampleController {

    private int delay = 0;
    private AtomicInteger count = new AtomicInteger();
    private AtomicInteger alternativeCount = new AtomicInteger();

    @PostMapping("/delay")
    public void postDelay(@RequestBody int delay) {
        this.delay = delay;
    }

    @GetMapping
    public String produce() throws InterruptedException {
        Thread.sleep(delay);
        throw new RuntimeException();
        // return String.format("PRODUCE-%d-%d", delay, count.get());
    }

    @GetMapping("/alternative")
    public String produceAlternative() throws InterruptedException {
        Thread.sleep(100);
        return String.format("ALTERNATE-%d", alternativeCount.incrementAndGet());
    }
}
