class Money {

    private BigDecimal x ;

    public Object clone() {
        Money m1 = new Money();
        m1.setX(this.x);
        return m1;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

}  