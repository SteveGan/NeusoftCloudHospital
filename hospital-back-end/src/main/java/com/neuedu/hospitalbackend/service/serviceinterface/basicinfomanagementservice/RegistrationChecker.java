package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;


import java.util.Date;

/**
 * Registration Service that uses Redis Cluster
 */
public interface RegistrationChecker {


    /**
     * Update available registrations of a doctor
     * in the Redis Cluster
     * @param appointmentDate
     * @param roleId
     * @param timeSlot
     * @return true if operate successful
     */
    boolean regist(String appointmentDate, String roleId, Byte timeSlot);
}
