package pl.wojciechm.viacom.contest.duplicates;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
        assertThat(p1.hashCode(), not(equalTo(p2.hashCode())));
        assertThat(Deduplication.customHash(p1), equalTo(Deduplication.customHash(p2)));
        assertThat(Deduplication.customHash(p1), not(equalTo(Deduplication.customHash(p3))));
        assertThat(Deduplication.customHash(p1), not(equalTo(Deduplication.customHash(p4))));
        assertThat(Deduplication.customHash(p3), not(equalTo(Deduplication.customHash(p4))));
    }

    @Test
    public void testDeduplicatePrimitives() throws Exception {
        //Autoboxing and unboxing occurs so this works as intended
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(1);
        Deduplication.deduplicate(list);
        assertEquals(2, list.size());
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

    @Test
    public void testDeduplicateHasHashcodeAndSubclass() throws Exception {
        PersonWithHashcode alice1 = new PersonWithHashcode();
        PersonWithHashcode alice2 = new PersonWithHashcode();
        PersonWithHashcode bob1 = new PersonWithHashcode();
        SubPersonWithHashcode alice3 = new SubPersonWithHashcode();
        alice1.name = "alice";
        alice1.age = 1;
        alice2.name = "alice";
        alice2.age = 1;
        bob1.name = "bob";
        bob1.age = 1;
        alice3.name = "alice";
        alice3.age = 1;

        List<PersonWithHashcode> list = new ArrayList<PersonWithHashcode>();
        list.add(alice1);
        list.add(bob1);
        list.add(alice2);
        list.add(alice3);
        Deduplication.deduplicate(list);
        assertEquals(3, list.size());
    }

    @Test
    public void testDeduplicateMixedRaw() throws Exception {
        Person alice1 = new Person();
        Person alice2 = new Person();
        Person bob1 = new Person();
        alice1.name = "alice";
        alice1.age = 1;
        alice2.name = "alice";
        alice2.age = 1;
        bob1.name = "bob";
        bob1.age = 1;

        PersonWithHashcode alice3 = new PersonWithHashcode();
        PersonWithHashcode alice4 = new PersonWithHashcode();
        PersonWithHashcode bob2 = new PersonWithHashcode();
        alice3.name = "alice";
        alice3.age = 1;
        alice4.name = "alice";
        alice4.age = 1;
        bob2.name = "bob";
        bob2.age = 1;

        List list = new ArrayList();
        list.add(alice3);
        list.add(bob2);
        list.add(alice4);
        list.add(alice1);
        list.add(bob1);
        list.add(alice2);
        Deduplication.deduplicate(list);
        assertEquals(4, list.size());
    }

    @Test
    public void testDeduplicateMixedRawWithHashcodes() throws Exception {
        OtherWithHashcode alice1 = new OtherWithHashcode();
        OtherWithHashcode alice2 = new OtherWithHashcode();
        OtherWithHashcode bob1 = new OtherWithHashcode();
        alice1.name = "alice";
        alice1.age = 1;
        alice2.name = "alice";
        alice2.age = 1;
        bob1.name = "bob";
        bob1.age = 1;

        PersonWithHashcode alice3 = new PersonWithHashcode();
        PersonWithHashcode alice4 = new PersonWithHashcode();
        PersonWithHashcode bob2 = new PersonWithHashcode();
        alice3.name = "alice";
        alice3.age = 1;
        alice4.name = "alice";
        alice4.age = 1;
        bob2.name = "bob";
        bob2.age = 1;

        List list = new ArrayList();
        list.add(alice3);
        list.add(bob2);
        list.add(alice4);
        list.add(alice1);
        list.add(bob1);
        list.add(alice2);
        Deduplication.deduplicate(list);
        assertEquals(4, list.size());
    }
}