package id.web.noxymon.concurrentspringboot.controllers;

import id.web.noxymon.concurrentspringboot.services.UpdateServiceByOptimisticLocking;
import id.web.noxymon.concurrentspringboot.services.UpdateServiceByTicketingSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/test", produces = "application/json", consumes = "application/json")
public class TestController
{
    private final UpdateServiceByTicketingSystem updateServiceByTicketingSystem;
    private final UpdateServiceByOptimisticLocking updateServiceByOptimisticLocking;

    @GetMapping("/optimistic/{masterId}/{maxUsage}")
    public ResponseEntity test(@PathVariable Long masterId, @PathVariable Integer maxUsage)
    {
        updateServiceByOptimisticLocking.update(masterId, maxUsage);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/ticketing/{masterId}/{maxUsage}")
    public ResponseEntity method(@PathVariable Long masterId, @PathVariable Integer maxUsage)
    {
        updateServiceByTicketingSystem.update(masterId, maxUsage);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
