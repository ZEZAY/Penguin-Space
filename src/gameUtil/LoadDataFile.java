package gameutil;

import model.ModelPlayer;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * LoadDataFile has loadMap() and updateDataFile() to download in initial
 * ranking data from a file, and update that file.
 * 
 * @author Nanthakarn Limkool
 */
public class LoadDataFile {

    // string represent data file path
    private final String PLAYER_DATA_FILE = "playerData.txt";

    /**
     * Read and Return initial map from data file.
     * 
     * @return Map of ranking from "playerData.txt" file
     */
    public Map<ModelPlayer, String> loadMap() {
        Map<ModelPlayer, String> loadedMap = new TreeMap<>(new MapComparator());
        File dataFile = new File(PLAYER_DATA_FILE);
        if (!dataFile.exists() || !dataFile.isFile())
            return loadedMap;

        try (BufferedReader bR = new BufferedReader(new FileReader(dataFile))) {
            String line;
            String[] inline;
            while ((line = bR.readLine()) != null) {
                inline = line.split(" ");
                if (inline.length >= 2)
                    loadedMap.put(new ModelPlayer(inline[0], Integer.parseInt(inline[1])), inline[0]);
            }
        } catch (IOException e) {
            System.out.println("Read file 'playerData.txt' error!");
        }
        return loadedMap;
    }

    /**
     * Write newMap (as string) to data file.
     * 
     * @param newMap that want to update to data file
     */
    public void updateDataFile(Map<ModelPlayer, String> newMap) {
        File dataFile = new File(PLAYER_DATA_FILE);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        Map<ModelPlayer, String> map = new TreeMap<>();
        map.putAll(newMap);

        try (BufferedOutputStream bOS = new BufferedOutputStream(new FileOutputStream(dataFile))) {
            for (Map.Entry<ModelPlayer, String> entry : map.entrySet()) {
                String txt = entry.getKey().toString() + "\n";
                bOS.write(txt.getBytes());
            }
        } catch (IOException e) {
            System.out.println("Write file 'playerData.txt' error!");
        }
    }
}
