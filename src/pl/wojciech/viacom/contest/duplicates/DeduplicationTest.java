package pl.wojciech.viacom.contest.duplicates;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wojciechm on 2015-07-08.
 */
public class DeduplicationTest {

    @Test
    public void testHashableOverwritten() throws Exception {
        assertFalse(Deduplication.hashableOverwritten(Person.class));
        assertTrue(Deduplication.hashableOverwritten(PersonWithHashcode.class));
    }

    @Test
    public void testCustomHash() throws Exception {
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();
        Person p4 = new Person();
        p1.name = "A";
        p2.name = "A";
        p3.name = "B";
        p4.name = "A";
        p1.age = 1;
        p2.age = 1;
        p3.age = 1;
        p4.age = 2;
        assertNotEquals(p1.hashCode(), p2.hashCode());
        assertEquals(Deduplication.customHash(p1), Deduplication.customHash(p2));
        assertNotEquals(Deduplication.customHash(p1), Deduplication.customHash(p3));
        assertNotEquals(Deduplication.customHash(p1), Deduplication.customHash(p4));
        assertNotEquals(Deduplication.customHash(p3), Deduplication.customHash(p4));
    }

    @Test
    public void testDeduplicateHasHashcode() throws Exception {
        PersonWithHashcode alice1 = new PersonWithHashcode();
        PersonWithHashcode alice2 = new PersonWithHashcode();
        PersonWithHashcode bob1 = new PersonWithHashcode();
        alice1.name = "alice";
        alice1.age = 1;
        alice2.name = "alice";
        alice2.age = 1;
        bob1.name = "bob";
        bob1.age = 1;

        List<PersonWithHashcode> list = new ArrayList<PersonWithHashcode>();
        list.add(alice1);
        list.add(bob1);
        list.add(alice2);
        Deduplication.deduplicate(list);
        assertEquals(2, list.size());
    }

    @Test
    public void testDeduplicateCustomHashcode() throws Exception {
        Person alice1 = new Person();
        Person alice2 = new Person();
        Person bob1 = new Person();
        alice1.name = "alice";
        alice1.age = 1;
        alice2.name = "alice";
        alice2.age = 1;
        bob1.name = "bob";
        bob1.age = 1;

        List<Person> list = new ArrayList<Person>();
        list.add(alice1);
        list.add(bob1);
        list.add(alice2);
        Deduplication.deduplicate(list);
        assertEquals(2, list.size());
    }
}