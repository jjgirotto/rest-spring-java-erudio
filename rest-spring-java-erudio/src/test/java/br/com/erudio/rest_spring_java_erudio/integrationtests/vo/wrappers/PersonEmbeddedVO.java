package br.com.erudio.rest_spring_java_erudio.integrationtests.vo.wrappers;

import br.com.erudio.rest_spring_java_erudio.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PersonEmbeddedVO implements Serializable {

    @JsonProperty("personVOList")
    private List<PersonVO> people;

    public PersonEmbeddedVO() {}

    public List<PersonVO> getPeople() {
        return people;
    }

    public void setPeople(List<PersonVO> people) {
        this.people = people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEmbeddedVO that = (PersonEmbeddedVO) o;
        return Objects.equals(people, that.people);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(people);
    }
}
