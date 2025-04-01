package org.example.db;

import org.example.utility.InputUtils;

import java.util.List;

public class Pokemon {
    int id;
    int trenerId;
    String name;
    String ability;
    String weakness;
    int lvl;

    public Pokemon(int id, int trenerId, String name, String ability, String weakness, int lvl) {
        this.id = id;
        this.trenerId = trenerId;
        this.name = name;
        this.ability = ability;
        this.weakness = weakness;
        this.lvl = lvl;
    }


    public int getLvl() {
        return lvl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getAbility() {
        return ability;
    }

    public String getName() {
        return name;
    }

    public int getTrenerId() {
        return trenerId;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setTrenerId(int trenerId) {
        this.trenerId = trenerId;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public String toString() {
        return
                "id = " + id
                        + ", trenerId = " + trenerId +
                        ", name = '" + name + '\'' +
                        ", ability = '" + ability + '\'' +
                ", weakness = '" + weakness + '\'' +
                ", lvl = " + lvl;
    }
}
