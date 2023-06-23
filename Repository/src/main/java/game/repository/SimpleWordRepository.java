package game.repository;

import game.domain.SimpleWord;

public interface SimpleWordRepository extends Repository<Integer, SimpleWord> {
    long getWordsNumber();
}
