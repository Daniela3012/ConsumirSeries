package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporada;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ce076481";

    public void mostrarMenu() {
        System.out.print("Ingrese la serie que está buscando: ");
        String nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+API_KEY);
        var datosSerie = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datosSerie);

        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datosSerie.totalDeTemporadas(); i++) {
            json = consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+"&Season="+i+API_KEY);
            DatosTemporada temporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(temporada);
        }

        temporadas.forEach(System.out::println);

        //Mostrar todos los episodios de todas las temporadas
        //for (int i = 0; i <datosSerie.totalDeTemporadas() ; i++) {
         //   List<DatosEpisodio> episodioXTemporada = new ArrayList<>();
           // for (int j = 0; j < episodioXTemporada.size(); j++) {
             //   System.out.println(episodioXTemporada.get(i).titulo());
            //}
       // }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}

