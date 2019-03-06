package cc.vimc.bot.rtti;

import com.github.t9t.minecraftrconclient.RconClient;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EditField {

    public static  <T> void editField(Charset fieldContent, String fieldName, Class<T> clazz) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.setAccessible(true);
        field.set(null, fieldContent);
    }
}
