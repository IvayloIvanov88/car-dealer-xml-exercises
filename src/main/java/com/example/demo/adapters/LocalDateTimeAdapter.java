package com.example.demo.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String inputString) throws Exception {
        return LocalDateTime.parse(inputString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    /**
     *      @XmlJavaTypeAdapter(LocalDateTimeAdaptor.class)
     *
     * @param localDateTime
     *      The value to be convereted. Can be null.
     * @return
     * @throws Exception
     */

    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
        return localDateTime.toString();
    }
}
