package gameutil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manager to use property.
 *
 * @author Nanthakarn Limkool
 */
public class PropertyManager {

    private static final String PROPERTIES_FILE = "config.properties";
    private Properties props = null;
    private static PropertyManager instance;

    private PropertyManager() {
        loadProperties(PROPERTIES_FILE);
    }

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    /** load properties from file. */
    private void loadProperties(String filename) {

        props = new Properties();
        // find the file as application resource
        ClassLoader loader = this.getClass().getClassLoader();
        InputStream inStream = loader.getResourceAsStream(filename);
        if (inStream == null) {
            System.err.println("Unable to open properties file " + filename);
            return;
        }
        // Load all the properties
        try {
            props.load(inStream);
        } catch (IOException e) {
            System.err.println("Error reading properties file " + filename);
            System.err.println(e.getMessage());
        }
        // close input stream to free resources
        try {
            inStream.close();
        } catch (IOException ioe) {
            /* should not happen */ }
    }

    /**
     * Get the value of a property.
     * 
     * @param name is the name of the property.
     * @return the property value or null if not found.
     */
    public String getproperty(String name) {
        return props.getProperty(name.trim()).trim();
    }
}
