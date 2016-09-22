package com.payworks.superheroes.dto;

import java.util.List;

public class Superhero
{
    private int id;
    private String name;
    private String pseudonym;
    private String publisher;
    private List<String> skills;
    private List<String> allies;
    private String firstAppearance;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPseudonym()
    {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym)
    {
        this.pseudonym = pseudonym;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public List<String> getSkills()
    {
        return skills;
    }

    public void setSkills(List<String> skills)
    {
        this.skills = skills;
    }

    public List<String> getAllies()
    {
        return allies;
    }

    public void setAllies(List<String> allies)
    {
        this.allies = allies;
    }

    public String getFirstAppearance()
    {
        return firstAppearance;
    }

    public void setFirstAppearance(String firstAppearance)
    {
        this.firstAppearance = firstAppearance;
    }

}
