package org.example.controller;

import org.example.model.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PageController {

    private List<Page> pages = new ArrayList<>();

    @GetMapping("/pages")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Page>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var result = pages.stream().limit(limit).toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(pages.size()))
                .body(result);
    }

    @PostMapping("/pages")
    @ResponseStatus(HttpStatus.CREATED)
    public Page create(@RequestBody Page page) {
        pages.add(page);
        return page;
    }

    @GetMapping("/pages/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page> show(@PathVariable String id) {
        var page = pages.stream()
                .filter(p -> p.getSlug().equals(id))
                .findFirst();
        return ResponseEntity.of(page);
    }

    @PutMapping("/pages/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page update(@PathVariable String id, @RequestBody Page data) {
        var maybePage = pages.stream()
                .filter(p -> p.getSlug().equals(id))
                .findFirst();
        if (maybePage.isPresent()) {
            var page = maybePage.get();
            page.setSlug(data.getSlug());
            page.setName(data.getName());
            page.setBody(data.getBody());
        }
        return data;
    }

    @DeleteMapping("/pages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable String id) {
        pages.removeIf(p -> p.getSlug().equals(id));
    }
}
