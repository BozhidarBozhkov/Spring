package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BillingDetail{

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "SWIFT_code")
    private String SWIFTCode;


    public BankAccount(String number, User owner, String bankName, String SWIFTCode) {
        super(number, owner);
        this.bankName = bankName;
        this.SWIFTCode = SWIFTCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSWIFTCode() {
        return SWIFTCode;
    }

    public void setSWIFTCode(String SWIFTCode) {
        this.SWIFTCode = SWIFTCode;
    }
}
