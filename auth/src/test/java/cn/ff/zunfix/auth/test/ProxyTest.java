package cn.ff.zunfix.auth.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * _
 *
 * @author fengfan 2020/8/29
 */
public class ProxyTest {

    @Test
    public void staticTest() {
        IslandHero jet = new IslandHero("Jet", 900);
        HeroProxy proxy = new HeroProxy(jet);
        proxy.buy(900);
    }


}


interface Hero {
    Integer gold = 0;

    String getName();

    void buy(Integer total);
}

@Slf4j
class IslandHero implements Hero {
    private Integer gold;
    private String name;

    public IslandHero(String name, Integer gold) {
        this.gold = gold;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void buy(Integer total) {
        log.error("err");
    }

    public Integer getGold() {
        return gold;
    }

    public void setName(String name) {
        this.name = name;
    }
}

@Slf4j
class HeroProxy implements Hero {
    private Hero hero;

    public HeroProxy(Hero hero) {
        this.hero = hero;
    }


    @Override
    public String getName() {
        if (StringUtils.isBlank(hero.getName())) {
            return "";
        }
        log.info("static-proxy：{}", hero.getName());
        return hero.getName();
    }

    @Override
    public void buy(Integer total) {
        int gold = hero.gold;
        this.getName();
        if (hero.gold < 0) {
            gold = 0;
        } else if (hero.gold > 100) {
            gold = gold + (gold - 100) / 2;
        }
        if (gold > total){
            log.error("Insufficient balance");
        }else {
            log.info("price => {}, balance => {}", gold, total - gold);
        }
    }
}
