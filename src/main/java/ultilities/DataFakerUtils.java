package ultilities;

import net.datafaker.Faker;

import java.util.Locale;

public class DataFakerUtils {
    private static Faker faker;

    public static Faker createFaker() {
        faker = new Faker(new Locale("en"));
        return faker;
    }

    public static Faker createFakerByLocate(String locateName) {
        faker = new Faker(new Locale(locateName));
        return faker;
    }

    public static Faker getDataFaker() {
        if (faker == null) {
            faker = createFaker();
        }
        return faker;
    }

    public static Faker getFakerByLocate(String locateName) {
        faker = createFakerByLocate(locateName);
        return faker;
    }

    public static void setFaker(Faker faker) {
        DataFakerUtils.faker = faker;
    }

}
