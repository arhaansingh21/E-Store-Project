package com.estore.api.estoreapi.model;
import java.util.Objects;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Trade {

    public enum Status {
        @JsonProperty("ACCEPTED")
        ACCEPTED,
        @JsonProperty("DENIED")
        DENIED,
        @JsonProperty("PENDING")
        PENDING,
        @JsonProperty("CANCELED")
        CANCELED;
    }

    private static final Logger LOG = Logger.getLogger(Trade.class.getName());

    static final String STRING_FORMAT = "Trade [Product=%s, status=%s]";
    @JsonProperty("product") private Product product;
    @JsonProperty("status") private Status status;

    /**
     * Create a trade with the given product
     * @param product the product
     * 
     */
    public Trade(@JsonProperty("product") Product product) {
        this.product = product;
        this.status = Status.PENDING;
    }

    /**
     * Gets the product of the trade
     * @return product
     */
    public Product getProduct(){return product;}

    /**
     * Gets the status of the trade
     * @return status
     */
    public Status getStatus(){return status;}

    /**
     * Sets the status of the trade
     * @param status - "denied", "accepted" or "canceled". Any other string sets status to pending 
     */
    public Trade setStatus(String status) {
        LOG.info(this.product + " trade was found and changed to " + status);
        if (status.equals("DENIED")) {
            this.status = Status.DENIED;
            return this;
        } 
        if (status.equals("CANCELED")) {
            this.status = Status.CANCELED;
            return this;
        } 
        if (status.equals("ACCEPTED")) {
            this.status = Status.ACCEPTED;
            return this;
        } 
        this.status = Status.PENDING;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, product, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Trade) {
            Trade tradeObj = (Trade) obj;
            if ((tradeObj.getProduct().equals(this.product)) && (tradeObj.getStatus() == this.status)) {
                return true;
            } 
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.product);
    }
}
