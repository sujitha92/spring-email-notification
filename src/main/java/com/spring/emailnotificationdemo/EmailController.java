package com.spring.emailnotificationdemo;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/csv")
    public void writecsv() throws IOException, MessagingException {

        Map<String, String> AUTHOR_BOOK_MAP = new HashMap<>() {
            {
                put("Dan Simmons", "Hyperion");
                put("Douglas Adams", "The Hitchhiker's Guide to the Galaxy");
            }
        };

        //create a CSV printer
        FileWriter out = new FileWriter("src/main/resources/books.csv");
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT);
        AUTHOR_BOOK_MAP.forEach((author, title) -> {
            try {
                printer.printRecord(author, title);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //close the printer after the file is complete
        printer.flush();
        printer.close();

        emailService.sendMail();
    }
}
