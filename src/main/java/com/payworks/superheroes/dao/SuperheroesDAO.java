package com.payworks.superheroes.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.payworks.superheroes.dto.Superhero;

/**
 * @author jon.urrutxurtu
 *
 */
public class SuperheroesDAO
{

    private static Map<Integer, Superhero> superheroDB;

    private static AtomicInteger idCounter;
    
    public static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    
    public static final SimpleDateFormat isoFormatter = new SimpleDateFormat(ISO_FORMAT);

    public static Superhero addSuperhero(Superhero superhero)
    {
        superhero.setId(idCounter.incrementAndGet());
        superheroDB.put(superhero.getId(), superhero);
        return superhero;
    }

    public static Superhero updateSuperhero(Integer id, Superhero superhero)
    {
        superheroDB.put(id, superhero);
        return superhero;
    }

    public static Superhero getSuperhero(Integer id)
    {
        return superheroDB.get(id);
    }

    public static List<Superhero> superheroes()
    {
        return new ArrayList<Superhero>(superheroDB.values());
    }
    
    static
    {
        superheroDB = new HashMap<Integer, Superhero>();
        idCounter = new AtomicInteger();
        
        Superhero batman = new Superhero();
        batman.setName("Batman");
        batman.setId(idCounter.incrementAndGet());
        
        batman.setFirstAppearance(isoFormatter.format(new Date()));
        batman.setPseudonym("Minuteman");
        batman.setPublisher("DC Comics");
        
        List<String> batmanAllies = new ArrayList<String>();
        batmanAllies.add("Robin");
        batmanAllies.add("Green Arrow");
        batman.setAllies(batmanAllies);
        
        List<String> batmanSkills = new ArrayList<String>();
        batmanSkills.add("Genius-level intellect");
        batmanSkills.add("Peak human physical and mental conditioning");
        batman.setSkills(batmanSkills);
        
        Superhero spiderman = new Superhero();
        spiderman.setName("Spiderman");
        spiderman.setId(idCounter.incrementAndGet());
        spiderman.setFirstAppearance(isoFormatter.format(new Date()));
        spiderman.setPseudonym("Human spider");
        spiderman.setPublisher("Marvel Comics");
        
        List<String> spidermanAllies = new ArrayList<String>();
        spidermanAllies.add("Hulk");
        spidermanAllies.add("Iron Man");
        
        spiderman.setAllies(spidermanAllies);
        
        List<String> SpidermanSkills = new ArrayList<String>();
        SpidermanSkills.add("Superhuman strength");
        SpidermanSkills.add("Ability to cling to most surfaces");
        SpidermanSkills.add("Precognitive Spider-sense");
        spiderman.setSkills(SpidermanSkills);
        
        superheroDB.put(batman.getId(), batman);
        superheroDB.put(spiderman.getId(), spiderman);
    }

}
