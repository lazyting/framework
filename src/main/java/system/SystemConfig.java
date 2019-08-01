package system;

import utils.EmptyUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class SystemConfig {

    private static Properties props = new Properties();
    private static String[] propsFileList = {"config.properties"};

    static {
        load();
    }

    private static void load() {
        for (int i = 0, length = propsFileList.length; i < length; i++) {
            try (InputStream in = SystemConfig.class.getClassLoader().getResourceAsStream(propsFileList[i])) {
                if (in != null) {
                    props.load(new InputStreamReader(in, "UTF-8"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(final String name) {
        return props.getProperty(name);
    }

    public static int getIntProperty(final String name) {
        String value = getProperty(name);
        return EmptyUtil.isEmpty(value) ? -1 : Integer.parseInt(props.getProperty(name));
    }
}
