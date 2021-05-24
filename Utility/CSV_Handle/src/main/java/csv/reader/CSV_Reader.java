package csv.reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class CSV_Reader {

    private String PATH;
    private List<Map<String,String>> filter;

    public CSV_Reader given(String path) {
        this.PATH = path;
        readCSV();
        return this;
    }

    public CSV_Reader when(String header,String value) {
        filter = filter.stream()
                .filter(s-> {
                    return s.entrySet()
                            .stream()
                            .anyMatch(map-> map.getKey().equals(header) &&
                                    map.getValue().equals(value));
                })
                .collect(Collectors.toList());
        return this;
    }

    public CSV_Reader and(String header,String value) {
        filter = filter.stream()
                .filter(s-> {
                    return s.entrySet()
                            .stream()
                            .anyMatch(map-> map.getKey().equals(header) &&
                                    map.getValue().equals(value));
                })
                .collect(Collectors.toList());
        return this;
    }

    public CSV_Reader and(String header,List<String> value) {
        filter = filter.stream()
                .filter(s-> {
                    return s.entrySet()
                            .stream()
                            .anyMatch(map-> map.getKey().equals(header) &&
                                            value.stream()
                                            .anyMatch(val -> map.getValue().equals(val))
                                    );
                })
                .collect(Collectors.toList());
        return this;
    }

    public List<Map<String,String>> then() {
        return filter;
    }



    private void readCSV() {

        List<Map<String,String>> inputLst = new CopyOnWriteArrayList<>();
        //List<Map<String,Integer>> headerLst = new CopyOnWriteArrayList<>();

        CSVFormat format = CSVFormat.DEFAULT.withHeader();

        try(BufferedReader inputReader = new BufferedReader(new FileReader(new File(PATH)));
            CSVParser dataParser = new CSVParser(inputReader,format)) {

            List<CSVRecord> csvRecords = dataParser.getRecords();
            Map<String,Integer> headerMap = dataParser.getHeaderMap();
            //headerLst.add(headerMap);

            csvRecords.forEach(rc -> {
                Map<String,String> inputMap = new LinkedHashMap<>();
                headerMap.forEach((key,value) -> inputMap.put(key,rc.get(value)));
                if(!inputMap.isEmpty()){
                    inputLst.add(inputMap);
                }
            });
        } catch (Exception e) {
            try {
                throw new Exception("IO Exception", e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if(inputLst.isEmpty()) {
            try {
                throw new Exception("CSV File is Empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filter = inputLst;
    }

    public List<String> getFilteredHeaderValue(List<Map<String,String>> data, String header) {
        return data
                .stream()
                .map(s-> s.entrySet()
                        .stream()
                        .filter(key -> key.getKey().equals(header))
                        .map(value -> value.getValue())
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toList())
                .stream()
                .flatMap(v -> v.stream()).collect(Collectors.toList());
    }



}
