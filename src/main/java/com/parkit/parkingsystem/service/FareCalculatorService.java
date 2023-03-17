package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket, boolean regularCustomer) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        double inTime = ticket.getInTime().getTime();
        double outTime = ticket.getOutTime().getTime();

        double duration = (outTime - inTime) / (60 * 60 * 1000);

        if (duration <= 0.5) {

            ticket.setPrice(0);

        } else {
            double discount = 0;

            if (regularCustomer) {
                discount = 0.95;
            } else {
                discount = 1;
            }

            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR * discount);
                    break;
                }
                case BIKE: {
                    ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR * discount);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
            }
        }

    }

    public void calculateFare(Ticket ticket) {
        calculateFare(ticket, false);
    }
}