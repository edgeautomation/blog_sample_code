package xml_generation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetTCID {

    public List<String> run_TCID(String suite_file_name) {
        List<String> tc_id = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("suite/" + suite_file_name + ".suite"));
            String line = "";
            while (line != null) {
                line = reader.readLine();
                if (line != null)
                    tc_id.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
        tc_id.removeIf(s -> s.trim().equals(""));
        return tc_id;
    }

}
