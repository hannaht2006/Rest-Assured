package com.cydeo.pojo;

import java.util.List;

public class Search {
    //search class has relation with Spartan class
    //it is called as HAS-A relationship
    private List<Spartan> content;

    public int totalElement;

    public List<Spartan> getContent() {
        return content;
    }

    public void setContent(List<Spartan> content) {
        this.content = content;
    }

    public int getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(int totalElement) {
        this.totalElement = totalElement;
    }


}
