package com.system.LMS.dto;

import java.time.LocalDate;

public class IssueBookDto {


    private Long bookId;


    private Long userId;

    private LocalDate issuedOnDate;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getIssuedOnDate() {
        return issuedOnDate;
    }

    public void setIssuedOnDate(LocalDate issuedOnDate) {
        this.issuedOnDate = issuedOnDate;
    }

    public LocalDate getIssuedTillDate() {
        return issuedTillDate;
    }

    public void setIssuedTillDate(LocalDate issuedTillDate) {
        this.issuedTillDate = issuedTillDate;
    }

    private LocalDate issuedTillDate;



}
