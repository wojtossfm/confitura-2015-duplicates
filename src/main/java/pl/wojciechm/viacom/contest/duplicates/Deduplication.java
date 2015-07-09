package pl.wojciechm.viacom.contest.duplicates;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
/**
 * Created by wojciechm on 2015-07-08.
 */
public class Deduplication {

    /**
     * Checks whether any of the subclasses of Object overwrote the equals method.
     * @param instance The Class that is to be checked
     * @return true if the equals method was overwritten
     */
    public static boolean equalsOverwritten(Class<?> instance) {
        try {
            return instance.getDeclaredMethod("equals", Object.class).getDeclaringClass() != Object.class;
        } catch (NoSuchMethodException nsme) {
            // not really possible since hashcode is implemented by Object
            return false;
        }
    }

    /**
     * Checks whether any of the subclasses of Object overwrote the hashCode method.
     * @param instance The Class that is to be checked
     * @return true if the hashCode method was overwritten
     */
    public static boolean hashableOverwritten(Class<?> instance) {
        try {
            return instance.getDeclaredMethod("hashCode").getDeclaringClass() != Object.class;
        } catch (NoSuchMethodException nsme) {
            // not really possible since hashcode is implemented by Object
            return false;
        }
    }

    /**
     * Quickly put together attempt at creating a custom hashcode calculation
     * for classes that don't overwrite the hashcode function. Doesn't handle IllegalAccessExceptions.
     * @param instance
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> int customHash(T instance) throws IllegalAccessException {
        Field[] fields = instance.getClass().getDeclaredFields();
        //instance.getClass().
        StringBuilder hash = new StringBuilder();
        hash.append(instance.getClass().toString());
        //System.out.println(fields.length);
        for (int index = 0; index < fields.length; index++) {
            String fieldHash;
            Field field = fields[index];
            Class type = field.getType();
            if (type.isPrimitive()) {
                fieldHash = type.getCanonicalName() + String.valueOf(field.get(instance));
            } else {
                if (Deduplication.hashableOverwritten(type) || type == String.class) {
                    fieldHash = type.getCanonicalName() + Integer.toString(field.get(instance).hashCode());
                } else {
                    //System.out.println(type);
                    fieldHash = type.getCanonicalName() + Deduplication.customHash(field.get(instance));
                }
            }
            //System.out.println(fieldHash);
            hash.append(fieldHash);
        }
        return hash.toString().hashCode();
    }

    /**
     * Removes duplicate values from a list modifying the list in-place.
     * Maintains the order of the remaining items on the list.
     * @param input List of items that is to have it's duplicates removed.
     * @param <T> class of objects that are on the list.
     * @throws IllegalAccessException
     */
    public static <T> void deduplicate(List<T> input) throws IllegalAccessException {
        if (input == null || input.isEmpty()) {
            return;
        }

        Iterator<T> iterator = input.iterator();
        boolean useObjectHashCodes = true;
        while (iterator.hasNext()) {
            T current = iterator.next();
            if (!Deduplication.hashableOverwritten(current.getClass())) {
                useObjectHashCodes = false;
                break;
            }
            if (!Deduplication.equalsOverwritten(current.getClass())) {
                useObjectHashCodes = false;
                break;
            }
        }

        iterator = input.iterator();

        if (useObjectHashCodes) {
            //System.out.println("Using class hashcodes for " + input);
            HashSet<T> store = new HashSet<>();
            while (iterator.hasNext()) {
                T current = iterator.next();
                if (store.contains(current)) {
                    //System.out.println("Cache contains " + current.toString());
                    iterator.remove();
                } else {
                    //System.out.println("Cache didn't contain " + current.toString());
                    store.add(current);
                }
            }
        } else {
            //System.out.println("Using custom hashcodes for " + input);
            HashSet<Integer> store = new HashSet<>();
            while (iterator.hasNext()) {
                T current = iterator.next();
                int hashcode = Deduplication.customHash(current);
                if (store.contains(hashcode)) {
                    //System.out.println("Cache contains " + current.toString());
                    iterator.remove();
                } else {
                    //System.out.println("Cache didn't contain " + current.toString());
                    store.add(hashcode);
                }
            }
        }
    }
}
