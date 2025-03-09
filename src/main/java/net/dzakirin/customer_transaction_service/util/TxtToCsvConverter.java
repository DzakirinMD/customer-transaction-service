package net.dzakirin.customer_transaction_service.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class TxtToCsvConverter {

    private static final String INPUT_FILE = "src/main/resources/dataSource.txt";
    private static final String OUTPUT_FILE = "src/main/resources/dataSource.csv";

    public static void convertTxtToCsv() {
        try (Stream<String> linesStream = Files.lines(Paths.get(INPUT_FILE))) {
            List<String> lines = linesStream
                    // Remove empty lines
                    .filter(line -> !line.trim().isEmpty())
                    .toList();

            if (lines.isEmpty()) {
                log.info("No data found in input file!");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
                for (String line : lines) {
                    // Replace "|" with "," for CSV format
                    writer.write(line.replace("|", ","));
                    writer.newLine();
                }
            }
            log.info("Conversion completed: {}", OUTPUT_FILE);

        } catch (IOException e) {
            log.error("Error occurred during conversion", e);
        }
    }
}
