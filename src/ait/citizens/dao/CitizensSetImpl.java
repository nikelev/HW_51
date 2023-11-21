package ait.citizens.dao;

import ait.citizens.model.Person;

import java.time.LocalDate;
import java.util.*;

public class CitizensSetImpl implements Citizens {
    private List<Person> idList;
    private List<Person> lastNameList;
    private List<Person> ageList;
    private static Comparator<Person> lastNameComparator = (p1, p2) -> {
        int res= p1.getLastName().compareTo(p2.getLastName());
        return res!=0?res:Integer.compare(p1.getId(), p2.getId());
    };
    private static Comparator<Person> ageComparator = (p1, p2) -> {
        int res = Integer.compare(p1.getAge(), p2.getAge());
        return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
    };

    public CitizensSetImpl() {
        idList = new ArrayList<>();
        lastNameList = new ArrayList<>();
        ageList = new ArrayList<>();
    }

    public CitizensSetImpl(List<Person> citizens) {
        this();
        citizens.forEach(p -> add(p));
    }
    //O(l)
    @Override
    public boolean add(Person person) {
        Set<Person> idSet=new TreeSet<>(idList);
        Set<Person> lastNameSet=new TreeSet<>(lastNameList);
        Set<Person> ageSet=new TreeSet<>(ageList);

        if (person == null) {
            return false;
        }

        if (idSet.contains(person)) {
            return false;
        }

         idSet.add( person);                                                        //O(l)
        ageSet.add(person);                                                              //O(l)
        lastNameSet.add(person);                                                      //O(l)
        idList.addAll(idSet);
        lastNameList.addAll(lastNameSet);
        ageList.addAll(ageSet);
        return true;
    }

    //O(n)
    @Override
    public boolean remove(int id) {
        Person victim = find(id);
        if (victim == null) {
            return false;
        }
        idList.remove(victim);
        ageList.remove(victim);
        lastNameList.remove(victim);
        return true;
    }
    //O(log(n))
    @Override
    public Person find(int id) {
        Person pattern = new Person(id, null, null, null);
        int index = Collections.binarySearch(idList, pattern);
        return index < 0 ? null : idList.get(index);
    }
    //O(log(n))
    @Override
    public Iterable<Person> find(int minAge, int maxAge) {
        LocalDate now = LocalDate.now();
        Person pattern = new Person(Integer.MIN_VALUE, null, null, now.minusYears(minAge));
        int from = -Collections.binarySearch(ageList, pattern, ageComparator) - 1;
        pattern = new Person(Integer.MAX_VALUE, null, null, now.minusYears(maxAge));
        int to = -Collections.binarySearch(ageList, pattern, ageComparator) - 1;
        return ageList.subList(from, to);
    }
    //O(log(n))
    @Override
    public Iterable<Person> find(String lastName) {
        Person pattern= new Person(Integer.MIN_VALUE,null,lastName,null);
        int from =-Collections.binarySearch(lastNameList,pattern,lastNameComparator)-1;
        pattern = new Person(Integer.MAX_VALUE,null, lastName,null);
        int to=-Collections.binarySearch(lastNameList,pattern,lastNameComparator)-1;
        return lastNameList.subList(from,to);
    }
    //O(1)
    @Override
    public Iterable<Person> getAllPersonSortedById() {
        return idList;
    }
    //O(1)
    @Override
    public Iterable<Person> getAllPersonSortedByLastName() {
        return lastNameList;
    }
    //O(1)
    @Override
    public Iterable<Person> getAllPersonSortedByAge() {
        return ageList;
    }
    //O(1)
    @Override
    public int size() {
        return idList.size();
    }
}
