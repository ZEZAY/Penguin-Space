package view;

import model.ModelPlayer;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class LoadDataFile {
    private final String PLAYER_DATA_FILE = "playerData.txt";

    public LoadDataFile() {

    }

    public Map loadMap() {
        Map<ModelPlayer, String> loadedMap = new TreeMap<>(new MapComparator());
        File dataFile = new File(PLAYER_DATA_FILE);
        if (!dataFile.exists() || !dataFile.isFile())
            return loadedMap;

        try (BufferedReader bR = new BufferedReader(new FileReader(dataFile))) {
            String line;
            String[] inline;
            while ((line = bR.readLine()) != null) {
                inline = line.split(" ");
                if (inline.length>=2)
                    loadedMap.put(new ModelPlayer(inline[0], Integer.parseInt(inline[1])),inline[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedMap;
    }

    public void updateDataFile(Map<ModelPlayer, String> newMap) {
        File dataFile = new File(PLAYER_DATA_FILE);
        if (!dataFile.exists()){
            try {
                dataFile.createNewFile();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        try (BufferedOutputStream bOS = new BufferedOutputStream(new FileOutputStream(dataFile))) {
            for (Map.Entry entey: newMap.entrySet()){
                ModelPlayer playerinfo = (ModelPlayer) entey.getKey();
                String txt = playerinfo.getName() + " " + playerinfo.getScore() +"\n";
                bOS.write(txt.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
