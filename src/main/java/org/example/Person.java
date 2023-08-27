package org.example;

import java.util.Objects;
import java.util.OptionalInt;

public class Person {
    protected final String name;
    protected final String surname;
    private OptionalInt age = OptionalInt.empty();
    private String address;

    private Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    private Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = OptionalInt.of(age);
    }

    private Person(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }
    private Person(String name, String surname, int age, String address) {
        this.name = name;
        this.surname = surname;
        this.age = OptionalInt.of(age);
        this.address = address;
    }


    public boolean hasAge() {
        return age.isPresent();
    }

    public boolean hasAddress() {
        return address != null;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void happyBirthday() {
        age.ifPresent(value -> age = OptionalInt.of(value + 1));
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public OptionalInt getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "\nname = " + name +
                "\nsurname = " + surname +
                "\nage = " + age +
                "\naddress = " + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(age, person.age) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age, address);
    }

    public static class PersonBuilder {
        private String name;
        private String surname;
        private OptionalInt age = OptionalInt.empty();
        private String address;

        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public PersonBuilder setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Age can't be negative");
            }
            this.age = OptionalInt.of(age);
            return this;
        }

        public PersonBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Person build() {
            if (name == null || surname == null) {
                throw new IllegalStateException("Name and surname are required");
            }

            if (age.isPresent() && !address.isEmpty()) {
                return new Person(name, surname, age.getAsInt(), address);
            } else if (age.isPresent() && address.isEmpty()) {
                return new Person(name, surname, age.getAsInt());
            } else if (!age.isPresent() && !address.isEmpty()) {
                return new Person(name, surname, address);
            } else {
                return new Person(name, surname);
            }

        }

    }
    public PersonBuilder newChildBuilder() {
        PersonBuilder childBuilder = new PersonBuilder();
        childBuilder.surname = this.surname; // Наследование фамилии от родителя
        childBuilder.address = this.address; // Наследование адреса от родителя
        return childBuilder;
    }

}
