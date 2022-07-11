package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //1
        List<Flight> ls = FlightBuilder.createFlights();
        System.out.println(ls);
        LocalDateTime time = LocalDateTime.now();
        List<Flight> result = new ArrayList<>();
        List<Flight> result2 = new ArrayList<>();
        for (Flight f : ls) {
            {
                for (Segment s : f.getSegments()) {
                    if (!s.getDepartureDate().isBefore(time)) {
                        result.add(f);
                    }
                }
                ;
            }
        }
        Set<Flight> set = new HashSet(result);
        System.out.println(set);
        //2
        for (Flight f : set) {
            {
                for (Segment s : f.getSegments()) {
                    if (!s.getDepartureDate().isAfter(s.getArrivalDate())) {
                        result2.add(f);
                    }
                }
                ;
            }
        }
        Set<Flight> set2 = new HashSet(result2);
        System.out.println(set2);
        System.out.println();
        System.out.println();
        System.out.println();
        //3
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> filtered = flights.stream().filter(flight -> {
            if (flight.getSegments() != null && flight.getSegments().size() >= 2) {
                List<Segment> sortedSegments = flight.getSegments()
                        .stream()
                        .sorted(Comparator.comparing(Segment::getDepartureDate))
                        .collect(Collectors.toList());
                Iterator<Segment> iterator = sortedSegments.iterator();
                while (iterator.hasNext()) {
                    LocalDateTime arrivalDate = iterator.next().getArrivalDate();
                    if (iterator.hasNext()) {
                        LocalDateTime nextSegmentDepartureDate = iterator.next().getDepartureDate();
                        if (Duration.between(arrivalDate, nextSegmentDepartureDate).toHours() >= 2) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }).collect(Collectors.toList());
        System.out.println(filtered.size());
        System.out.println(filtered);
    }
}
