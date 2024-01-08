package org.mrmelon__.diverwars.game.GameItems;

public enum Armor {
    LEATHER_DIVING_SUIT("LEATHER DIVING SUIT",2),
    IRON_DIVING_SUIT("IRON DIVING SUIT",3),
    DIAMOND_DIVING_SUIT("DIAMOND DIVING SUIT",4),
    NETHERITE_DIVING_SUIT("NETHERITE DIVING SUIT",6);

    //

    private String name;
    private int multiplySecond;

    Armor(String name, int multiplySecond) {
        this.name = name;
        this.multiplySecond = multiplySecond;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMultiplySecond() {
        return multiplySecond;
    }

    public void setMultiplySecond(int multiplySecond) {
        this.multiplySecond = multiplySecond;
    }
}
