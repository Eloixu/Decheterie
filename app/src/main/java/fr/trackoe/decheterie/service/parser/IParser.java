package fr.trackoe.decheterie.service.parser;

/**
 *
 */
public interface IParser<T> {

    T parse(String stream) throws Exception;

    String parseError(String stream) throws Exception;
}
