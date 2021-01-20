package life.genny.utils;

import io.netty.util.internal.StringUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class EnvKeyReaderUtil {

    private EnvKeyReaderUtil(){
        // private constructor makes the util class static
    }

    private static final String BASE_DIR ="/tmp/";

    private static final Predicate<String> isNotEmpty = str -> !str.isEmpty();

    private final static Function<String, String> readKeyFromFileSystem = fName -> {
        try {
            File file = new File(BASE_DIR + fName);
            return FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return StringUtil.EMPTY_STRING;
    };

    public static String retrieveKey(String envName, String defaultFileName){
        return Optional.ofNullable(System.getenv(envName))
                .filter(Objects::nonNull)
                .filter(isNotEmpty)
                .orElse(readKeyFromFileSystem.apply(defaultFileName));
    }
}
