package br.com.erudio.rest_spring_java_erudio.integrationtests.vo.pagedmodel;

import br.com.erudio.rest_spring_java_erudio.integrationtests.vo.PersonVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class PagedModelPerson {
    @XmlElement(name = "content")
    private List<PersonVO> content;

    public PagedModelPerson() {}

    public List<PersonVO> getContent() {
        return content;
    }

    public void setContent(List<PersonVO> content) {
        this.content = content;
    }
}
