package ait.citizens.test;

import ait.citizens.dao.Citizens;
import ait.citizens.dao.CitizensSetImpl;
import ait.citizens.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CitizensTest {
    Citizens citizens;
    static final LocalDate now = LocalDate.now();
    Person p1;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        p1 = new Person(1, "Peter", "Jackson", now.minusYears(23));
        citizens = new CitizensSetImpl(
                List.of(p1,
                        new Person(2, "John", "Smith", now.minusYears(20)),
                        new Person(3, "Mary", "Jackson", now.minusYears(20)),
                        new Person(4, "Rabindranate", "Anand", now.minusYears(25))));
    }

    @org.junit.jupiter.api.Test
    void testCitizensImplListOfPerson() {
        citizens = new CitizensSetImpl(List.of(p1, p1));
        assertEquals(1, citizens.size());
    }

    @org.junit.jupiter.api.Test
    void add() {
        assertFalse(citizens.add(null));
        assertFalse(citizens.add(new Person(2, "John", "Smith", now.minusYears(20))));
        assertEquals(4, citizens.size());
        assertTrue(citizens.add(new Person(5, "John", "Smith", now.minusYears(20))));
        assertEquals(5, citizens.size());
    }

    @org.junit.jupiter.api.Test
    void remove() {
        assertFalse(citizens.remove(5));
        assertEquals(4, citizens.size());
        assertTrue(citizens.remove(2));
        assertEquals(3, citizens.size());
    }

    @org.junit.jupiter.api.Test
    void findById() {
        Person person = citizens.find(1);
        assertEquals(p1, person);
        assertEquals(1, person.getId());
        assertEquals("Peter", person.getFirstName());
        assertEquals("Jackson", person.getLastName());
        assertEquals(23, person.getAge());
        assertNull(citizens.find(5));
    }

    @org.junit.jupiter.api.Test
    void testFindByAges() {
        Iterable<Person> res = citizens.find(20, 23);
        List<Person> actual = new ArrayList<>();
        res.forEach(p -> actual.add(p));
        Collections.sort(actual);
        Iterable<Person> expected = List.of(new Person(1, "Peter", "Jackson", now.minusYears(23)),
                new Person(2, "John", "Smith", now.minusYears(20)),
                new Person(3, "Mary", "Jackson", now.minusYears(20)));
        assertIterableEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testFindByLastName() {
        Iterable<Person> res = citizens.find("Jackson");
        List<Person> actual = new ArrayList<>();
        res.forEach(p -> actual.add(p));
        Collections.sort(actual);
        Iterable<Person> expected = List.of(
                new Person(1, "Peter", "Jackson", now.minusYears(23)),
                new Person(3, "Mary", "Jackson", now.minusYears(20)));
        assertIterableEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getAllPersonSortedById() {
        Iterable<Person> actual = citizens.getAllPersonSortedById();
        Iterable<Person> expected = List.of(p1,
                new Person(2, "John", "Smith", now.minusYears(20)),
                new Person(3, "Mary", "Jackson", now.minusYears(20)),
                new Person(4, "Rabindranate", "Anand", now.minusYears(25)));
        assertIterableEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getAllPersonSortedByLastName() {
        Iterable<Person> actual = citizens.getAllPersonSortedByLastName();
        String name = "";
        for (Person person : actual) {
            assertTrue(person.getLastName().compareTo(name) >= 0);
            name = person.getLastName();
        }
    }

    @org.junit.jupiter.api.Test
    void getAllPersonSortedByAge() {
        Iterable<Person> actual=citizens.getAllPersonSortedByAge();
        int age=0;
        for (Person pers : actual) {
            assertTrue(pers.getAge()>=age);
            age=pers.getAge();
        }

    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(4, citizens.size());
    }
}