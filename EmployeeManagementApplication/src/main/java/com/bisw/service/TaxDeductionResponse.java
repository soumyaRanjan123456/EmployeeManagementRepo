package com.bisw.service;

import lombok.Data;

@Data
public  class TaxDeductionResponse {
        private String employeeId;
        private String lastName;
        private double yearlySalary;
        private double taxAmount;
        private double cess;

        public TaxDeductionResponse(String employeeId, String lastName, double yearlySalary, double taxAmount, double cess) {
            this.employeeId = employeeId;
            this.lastName = lastName;
            this.yearlySalary = yearlySalary;
            this.taxAmount = taxAmount;
            this.cess = cess;
        }

       
    }