package br.com.dio.model;


import java.util.Collection;
import java.util.List;

import static br.com.dio.model.GameStatusEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {


    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> space) {
        this.spaces = space;
    }


    public List<List<Space>> getSpace(){
        return spaces;
    }


    public GameStatusEnum getStatus(){
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){
            return NON_START;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }


    public boolean hasError(){
        if (getStatus()== NON_START){
            return false;
        }

        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }



    public boolean changeValue(final int col, final int row, final int value){
        var space = spaces.get(col).get(row);
        if (space.isFixed()){
            return false;
        }
        space.setActual(value);
        return true;
    }


    public boolean clearValue(final int col, final int row){
        var space = spaces.get(col).get(row);
        if (space.isFixed()){
            return false;
        }
        space.clearSpace();
        return true;
    }


    public void reset(){
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }


    public boolean gameIsFinished(){
        return !hasError() && getStatus().equals(COMPLETE);
    }










}
