package com.epam.service;


import com.epam.entity.Order;
import com.epam.entity.OrderStatus;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderParseService {

    public List<Order> parse(String csvFile) throws IOException {
        List<Order> orders = new ArrayList<>();
        FileReader fileReader = new FileReader(csvFile);

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        CSVReader csvReader = new CSVReaderBuilder(fileReader)
                .withCSVParser(parser)
                .build();

        List<String[]> allData = csvReader.readAll();

        for (String[] row : allData) {
            Order order = new Order();
            int counter = 0;
            for (String cell : row) {
                counter++;
                if (counter == 1) {
                    order.setId(Long.parseLong(cell));
                } else {
                    order.setOrderStatus(OrderStatus.valueOf(cell));
                }
            }
            orders.add(order);
        }
        return orders;
    }
}
