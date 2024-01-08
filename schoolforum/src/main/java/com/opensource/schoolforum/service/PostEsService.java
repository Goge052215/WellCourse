package com.opensource.schoolforum.service;

import com.opensource.schoolforum.entity.Postes;

import java.util.Iterator;
import java.util.List;

public interface PostEsService {

    void deleteIndex(String index);

    void save(Postes docBean);

    void saveAll(List<Postes> list);

    Iterator<Postes> findAll();
}
