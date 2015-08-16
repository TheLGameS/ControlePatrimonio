package br.ufscar.dc.controledepatrimonio.Util.Webservice;

import java.util.List;

public interface ITask<E> {
    void getJSON(List<E> JSON);
}
